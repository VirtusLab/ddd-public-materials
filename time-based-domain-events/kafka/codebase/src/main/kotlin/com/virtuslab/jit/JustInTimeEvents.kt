package com.virtuslab.jit

import com.virtuslab.jit.kafka.topology
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.streams.KafkaStreams
import java.time.Duration
import java.util.*

fun main(args: Array<String>) = processStream()

private fun processStream() {

    val topology = topology(Duration.ofSeconds(30))

    val props = Properties()
    props["bootstrap.servers"] = "localhost:9092"
    props["application.id"] = "just-in-time-events-processing"
    props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
    val streams = KafkaStreams(topology, props)
    streams.cleanUp()
    streams.start()

    Runtime.getRuntime().addShutdownHook(Thread(Runnable { streams.close() }))
}
