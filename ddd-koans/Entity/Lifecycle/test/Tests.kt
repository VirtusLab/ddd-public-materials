import NoteValue.*
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal
import java.util.*
import kotlin.random.asKotlinRandom

class Test {

    @ParameterizedTest(name = "Cannot create bar with too much beats")
    @MethodSource("invalidBars")
    fun `Cannot create bar with too much beats`(notes: Notes, timeSignature: ValidTimeSignature) {
        println("${notes.beats(timeSignature.noteValue)}, $timeSignature")
        val errorMessage = "Cannot create bar" +
            "as number of beats in provided notes ${notes.beats(timeSignature.noteValue)} is greater than " +
            "number of beats declared in time signature ${timeSignature.numberOfBeats.value}"
        val exception = Assertions.catchThrowable { Bar(ordinal, notes, timeSignature) }
        Assertions.assertThat(exception)
            .`as`("Creating invalid bar should end in exception")
            .overridingErrorMessage(errorMessage)
            .isExactlyInstanceOf(BarWithTooManyNotesException::class.java)
    }

    @ParameterizedTest(name = "Bar is complete as long as sum of notes' beats is equal to time signature number of beats")
    @MethodSource("completeBars")
    fun `Bar is complete as long as sum of notes' beats is equal to time signature number of beats`(bar: Bar) {
        val errorMessage =
            "Given bar should be considered to be complete " +
                "as number of beats in bar ${bar.notes.beats(bar.timeSignature.noteValue)} is equal to " +
                "number of beats declared in time signature ${bar.timeSignature.numberOfBeats.value}"
        Assertions.assertThat(bar.state())
            .`as`("The bar should has state of Complete")
            .overridingErrorMessage(errorMessage)
            .isEqualTo(Bar.State.Complete)
    }

    @ParameterizedTest(name = "Bar is incomplete as long as sum of notes' beats isn't equal to time signature number of beats")
    @MethodSource("incompleteBars")
    fun `Bar is incomplete as long as sum of notes' beats isn't equal to time signature number of beats`(bar: Bar) {
        val errorMessage =
            "Given bar should be considered to be incomplete " +
                "as number of beats in bar ${bar.notes.beats(bar.timeSignature.noteValue)} is lower than " +
                "number of beats declared in time signature ${bar.timeSignature.numberOfBeats.value}"
        Assertions.assertThat(bar.state())
            .`as`("The bar should has state of Incomplete")
            .overridingErrorMessage(errorMessage)
            .isEqualTo(Bar.State.Incomplete)
    }

    @ParameterizedTest(name = "Two bars are not equal, when their ids are not the same")
    @MethodSource("notEqualTestData")
    fun `Two bars are not equal, when their ids are not the same`(timeSignature: ValidTimeSignature, notes: Notes) {
        val firstBar = Bar(Ordinal(5), notes, timeSignature)
        val secondBar = Bar(Ordinal(6), notes, timeSignature)

        Assertions.assertThat(firstBar == secondBar)
            .`as`("The first bar should not be equal to the second bar")
            .overridingErrorMessage("Two bars are equal, however, they shouldn't be!")
            .isFalse()
        Assertions.assertThat(secondBar == firstBar)
            .overridingErrorMessage("Two bars are not equal, however, they shouldn't be!")
            .`as`("Second bar should not be equal to the first bar")
            .isFalse()
    }

    @ParameterizedTest(name = "Two bars are equal, when their id is the same")
    @MethodSource("equalTestData")
    fun `Two bars are equal, when their ids are the same`(
        firstTimeSignature: ValidTimeSignature,
        firstNotes: Notes,
        secondTimeSignature: ValidTimeSignature,
        secondNotes: Notes
    ) {
        val ordinal = Ordinal(3)

        val firstBar = Bar(ordinal, firstNotes, firstTimeSignature)
        val secondBar = Bar(ordinal, secondNotes, secondTimeSignature)

        Assertions.assertThat(firstBar == secondBar)
            .`as`("First bar should be equal to the second bar")
            .overridingErrorMessage("Two bars are not equal, however, they should be!")
            .isTrue()
        Assertions.assertThat(secondBar == firstBar)
            .overridingErrorMessage("Two bars are not equal, however, they should be!")
            .`as`("Second bar should be equal to the first bar")
            .isTrue()
    }

    @Test
    fun `Bar should have properly defined id method`() {
        val expectedId = Ordinal(1)
        val bar = Bar(
            expectedId,
            Notes(listOf(Pitch.A0.eighthNote())),
            ValidTimeSignature(NumberOfBeats(3), NoteValue.QuarterNote)
        )

        val id = bar.id()
        Assertions.assertThat(id)
            .`as`("$bar should have id defined as expected to provide its identification in context of music piece.")
            .withFailMessage("Provided id=$id is not the expected id.")
            .isEqualTo(expectedId)
    }

    @Test
    fun `Bar should have id of expected type`() {
        val idMemberClassifier = Bar::class.members.find { it.name == "id" }!!.returnType.classifier
        Assertions.assertThat(idMemberClassifier)
            .`as`("Bar's id should be of expected type")
            .withFailMessage("Provided id=$idMemberClassifier is not the expected id type.")
            .isEqualTo(Ordinal::class)
    }

    companion object {
        private val random = Random().asKotlinRandom()
        private val ordinal = Ordinal(1)
        private val sourceForCompleteBars = listOf(
            validTimeSignature("4/4") to listOf(QuarterNote, QuarterNote, QuarterNote, QuarterNote),
            validTimeSignature("1/1") to listOf(QuarterNote, QuarterNote, QuarterNote, QuarterNote),
            validTimeSignature("4/4") to listOf(WholeNote),
            validTimeSignature("2/2") to listOf(WholeNote),
            validTimeSignature("4/4") to listOf(HalfNote, HalfNote),
            validTimeSignature("8/8") to listOf(HalfNote, HalfNote),
            validTimeSignature("4/4") to listOf(HalfNote, QuarterNote, QuarterNote),
            validTimeSignature("16/16") to listOf(HalfNote, QuarterNote, QuarterNote),
            validTimeSignature("3/4") to listOf(HalfNote, QuarterNote),
            validTimeSignature("12/16") to listOf(HalfNote, QuarterNote),
            validTimeSignature("3/4") to listOf(QuarterNote, QuarterNote, QuarterNote),
            validTimeSignature("24/32") to listOf(QuarterNote, QuarterNote, QuarterNote),
            validTimeSignature("3/4") to listOf(QuarterNote, QuarterNote, QuarterNote),
            validTimeSignature("1/2") to listOf(QuarterNote, QuarterNote),
            validTimeSignature("1/2") to listOf(HalfNote),
            validTimeSignature("1/32") to listOf(ThirtySecondNote),
            validTimeSignature("3/32") to listOf(ThirtySecondNote, ThirtySecondNote, ThirtySecondNote),
            validTimeSignature("1/16") to listOf(SixteenthNote),
        ).map { (timeSignature, noteValues) -> timeSignature to Notes(noteValues.map { it(randomPitch()) }) }

        private fun validTimeSignature(timeSignatureStringRepresentation: String) =
            TimeSignature.of(timeSignatureStringRepresentation) as ValidTimeSignature

        @JvmStatic
        fun notEqualTestData() =
            incompleteBars()
                .map { bar -> Arguments.of(bar.timeSignature, bar.notes) }

        @JvmStatic
        fun equalTestData() =
            incompleteBars().zip(incompleteBars())
                .map { (firstBar, secondBar) ->
                    Arguments.of(
                        firstBar.timeSignature,
                        firstBar.notes,
                        secondBar.timeSignature,
                        secondBar.notes
                    )
                }

        @JvmStatic
        fun incompleteBars() =
            generateSequence { incompleteBar() }
                .take(20)
                .toList()

        @JvmStatic
        fun completeBars() =
            sourceForCompleteBars
                .map { (timeSignature, notes) -> Bar(ordinal, notes, timeSignature) }

        @JvmStatic
        fun invalidBars() =
            generateSequence { invalidBar() }
                .take(20)
                .toList()

        private fun invalidBar(): Arguments {
            val numberOfBeats = random.nextInt(1, 33).toBigDecimal()
            val noteValue = randomNoteValue()
            val validTimeSignature = ValidTimeSignature(NumberOfBeats(numberOfBeats.toInt()), noteValue)
            val notes = sequenceOfIncreasingNotes(noteValue)
                .dropWhile { (_, beats) -> beats <= numberOfBeats }
                .map { (noteValues, _) -> Notes(noteValues.map { it(randomPitch()) }) }
                .first()
            return Arguments.of(notes, validTimeSignature)
        }

        private fun incompleteBar(): Bar {
            val numberOfBeats = random.nextInt(1, 33).toBigDecimal()
            val noteValue = randomNoteValue()
            val validTimeSignature = ValidTimeSignature(NumberOfBeats(numberOfBeats.toInt()), noteValue)
            val notes = sequenceOfIncreasingNotes(noteValue)
                .takeWhile { (_, beats) -> beats < numberOfBeats }
                .map { (noteValues, _) -> Notes(noteValues.map { it(randomPitch()) }) }
                .last()
            return Bar(ordinal, notes, validTimeSignature)
        }

        private fun sequenceOfIncreasingNotes(noteValue: NoteValue) = generateSequence { randomNoteValue() }
            .map { it to it.lengthInBeats(noteValue) }
            .scan(emptyList<NoteValue>() to BigDecimal.ZERO) { (noteValues, sumOfBeats), (noteValue, beats) ->
                (noteValues + noteValue) to sumOfBeats.plus(beats)
            }

        private fun randomPitch() = Pitch.values().random()

        private fun randomNoteValue() = values().random()
    }
}
