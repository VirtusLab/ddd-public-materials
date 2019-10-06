package com.virtuslab.jit.kafka

import com.virtuslab.jit.*
import com.virtuslab.jit.kafka.EventTranslator.translate
import io.kotlintest.TestCase
import io.kotlintest.data.forall
import io.kotlintest.matchers.types.shouldBeNull
import io.kotlintest.specs.FeatureSpec
import io.kotlintest.tables.row
import org.apache.kafka.common.serialization.Serdes.String
import org.apache.kafka.streams.TopologyTestDriver
import org.apache.kafka.streams.state.KeyValueStore
import org.apache.kafka.streams.test.ConsumerRecordFactory
import org.apache.kafka.streams.test.OutputVerifier
import java.time.Duration
import java.time.LocalDate
import java.util.*


class JustInTimeEventsSpec : FeatureSpec() {

    private val scheduleInterval = Duration.ofSeconds(10)
    private val topology = topology(scheduleInterval)
    private val props = Properties().apply {
        setProperty("bootstrap.servers", "dummy:1234")
        setProperty("application.id", "time-based-events-processing")
    }
    private val recordFactory =
        ConsumerRecordFactory<String, TimeBasedEvent>(String().serializer(), TimeBasedEventSerializer)

    private val testDriver = TopologyTestDriver(topology, props)

    private val employeeId = "12345"

    override fun beforeTest(testCase: TestCase) {
        cleanStateStores()
        Time.restoreDefault()
    }

    init {

        feature("schedule time based event") {
            scenario("should forward past dated event") {
                val pastDatedEvent = pastDatedEvent<EmployeeMarkedAsLeaver>()
                val expectedEvent = translate(pastDatedEvent)

                publishEvent(pastDatedEvent)

                shouldForwardEvent(expectedEvent)
                shouldNotForwardNextEvent()
            }


            scenario("should forward future dated event on effective date") {
                val futureDatedEvent = futureDatedEvent<EmployeeMarkedAsLeaver>()
                val expectedEvent = translate(futureDatedEvent)

                publishEvent(futureDatedEvent)

                shouldNotForwardNextEvent()

                Time.setToday { futureDatedEvent.effectiveDate }
                shouldForwardEvent(expectedEvent)

                shouldNotForwardNextEvent()
            }

            scenario("should forward effective events for different keys") {
                forall(
                    row(pastDatedEvent<EmployeeMarkedAsLeaver>("1")),
                    row(pastDatedEvent("2")),
                    row(pastDatedEvent("3")),
                    row(pastDatedEvent("4"))
                ) { event ->
                    publishEvent(event)

                    shouldForwardEvent(translate(event))
                }

            }
        }

        feature("reschedule time based event") {
            scenario("should forward event rescheduled to the past") {
                val futureDatedEvent = futureDatedEvent<EmployeeMarkedAsLeaver>()

                publishEvent(futureDatedEvent)

                shouldNotForwardNextEvent()

                val newEffectiveDate = Time.today().minusDays(10)
                val effectiveEvent = timeBasedEvent<EmployeePostponedLeaving>(newEffectiveDate)
                val expectedEvent = translate(effectiveEvent)

                publishEvent(effectiveEvent)

                shouldForwardEvent(expectedEvent)

                shouldNotForwardNextEvent()
            }

            scenario("should forward rescheduled event in the future") {
                val futureDatedEvent = futureDatedEvent<EmployeeMarkedAsLeaver>()

                publishEvent(futureDatedEvent)

                shouldNotForwardNextEvent()

                val newEffectiveDate = Time.today().plusDays(10)
                val effectiveEvent = timeBasedEvent<EmployeePostponedLeaving>(newEffectiveDate)
                val expectedEvent = translate(effectiveEvent)

                publishEvent(effectiveEvent)

                Time.setToday { futureDatedEvent.effectiveDate }
                shouldNotForwardNextEvent()


                Time.setToday { newEffectiveDate }
                shouldForwardEvent(expectedEvent)

                shouldNotForwardNextEvent()
            }

            scenario("should forward rescheduled event only for effective key") {
                val keyToReschedule = "444"

                publishEvent(futureDatedEvent<EmployeeMarkedAsLeaver>(keyToReschedule))
                publishEvent(futureDatedEvent<EmployeeMarkedAsLeaver>("1234"))

                shouldNotForwardNextEvent()

                val rescheduledEvent = pastDatedEvent<EmployeePostponedLeaving>(keyToReschedule)
                val expectedEvent = translate(rescheduledEvent)

                publishEvent(rescheduledEvent)

                shouldForwardEvent(expectedEvent)

                shouldNotForwardNextEvent()
            }
        }


        feature("cancel time based event") {
            scenario("should cancel scheduled event") {
                val futureDatedEvent = futureDatedEvent<EmployeeMarkedAsLeaver>()

                publishEvent(futureDatedEvent)

                shouldNotForwardNextEvent()

                publishEvent(timeBasedEvent<EmployeeCancelledLeaving>(futureDatedEvent.effectiveDate))

                Time.setToday { futureDatedEvent.effectiveDate }
                shouldNotForwardNextEvent()
            }

            scenario("should cancel only event with given key") {
                val keyToCancel = "444"
                val eventToCancel = futureDatedEvent<EmployeeMarkedAsLeaver>(keyToCancel)
                val futureDatedEvent = futureDatedEvent<EmployeeMarkedAsLeaver>("1234")

                publishEvent(eventToCancel)
                publishEvent(futureDatedEvent)

                shouldNotForwardNextEvent()

                publishEvent(timeBasedEvent<EmployeeCancelledLeaving>(eventToCancel.leavingDate, keyToCancel))

                Time.setToday { futureDatedEvent.effectiveDate }

                shouldForwardEvent(translate(futureDatedEvent))

                shouldNotForwardNextEvent()
            }
        }

    }

    private inline fun <reified EVENT : TimeBasedEvent> futureDatedEvent(id: String = employeeId) =
        timeBasedEvent<EVENT>(Time.today().plusDays(3), id)

    private inline fun <reified EVENT : TimeBasedEvent> pastDatedEvent(id: String = employeeId) =
        timeBasedEvent<EVENT>(Time.today().minusDays(6), id)

    private inline fun <reified EVENT : TimeBasedEvent> timeBasedEvent(
        date: LocalDate,
        id: String = employeeId
    ): EVENT =
        when (EVENT::class) {
            EmployeeMarkedAsLeaver::class -> EmployeeMarkedAsLeaver(id, date) as EVENT
            EmployeePostponedLeaving::class -> EmployeePostponedLeaving(id, date) as EVENT
            EmployeeCancelledLeaving::class -> EmployeeCancelledLeaving(id, date) as EVENT
            else -> throw IllegalArgumentException("Unsupported event type ${EVENT::class}")
        }


    private fun publishEvent(event: TimeBasedEvent) =
        testDriver.pipeInput(recordFactory.create(INPUT_TOPIC, event.id, event))

    private fun shouldForwardEvent(expectedEvent: EffectiveEvent) {
        advancePunctuationTime()
        OutputVerifier.compareKeyValue(
            nextForwardedEvent(),
            expectedEvent.id,
            expectedEvent
        )
    }

    private fun shouldNotForwardNextEvent() = advancePunctuationTime().also { nextForwardedEvent().shouldBeNull() }

    private fun nextForwardedEvent() =
        testDriver.readOutput(OUTPUT_TOPIC, String().deserializer(), EffectiveEventDeserializer)

    private fun advancePunctuationTime() = testDriver.advanceWallClockTime(scheduleInterval.toMillis())

    private fun cleanStateStores() {
        testDriver.allStateStores.values.forEach {
            val kv = it as KeyValueStore<Any, Any>
            kv.all().forEach {
                kv.delete(it.key)
            }
        }
    }

}

