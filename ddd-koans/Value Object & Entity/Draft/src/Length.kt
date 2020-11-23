import java.math.BigDecimal
import java.time.Duration

data class Length(private val durationInSeconds: BigDecimal) {
    operator fun plus(other: Length) = Length(durationInSeconds.plus(other.durationInSeconds))

    val duration: Duration = Duration.ofMillis(durationInSeconds.multiply(1000.toBigDecimal()).toLong())
}

interface LengthInTempo {
    fun lengthIn(tempo: Tempo): Length
}

data class Tempo(val bpm: Int) {
    init {
        require(bpm in 1..399) { "Beat per minute values should be between zero and 400 (both exclusive)" }
    }
}