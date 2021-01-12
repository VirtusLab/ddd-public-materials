import java.math.BigDecimal
import java.math.MathContext
import java.time.Duration

enum class NoteValue(private val relativeValue: Int): LengthInTempo {
    WholeNote(1),
    HalfNote(2),
    QuarterNote(4),
    EighthNote(8),
    SixteenthNote(16),
    ThirtySecondNote(32);

    private val lengthInBeats: BigDecimal = 4.toBigDecimal().divide(relativeValue.toBigDecimal())

    override fun lengthIn(tempo: Tempo): Length {
        val minuteInSeconds = 60.toBigDecimal()
        val durationInSeconds = minuteInSeconds
            .multiply(lengthInBeats).divide(tempo.bpm.toBigDecimal(), MathContext.DECIMAL32)
        return Length(durationInSeconds)
    }
}

data class Tempo(val bpm: Int) {
    init {
        require(bpm in 1..399) { "Beat per minute values should be between zero and 400 (both exclusive)" }
    }
}

data class Length(private val durationInSeconds: BigDecimal) {
    val duration: Duration = Duration.ofMillis(durationInSeconds.multiply(1000.toBigDecimal()).toLong())
}

interface LengthInTempo {
    fun lengthIn(tempo: Tempo): Length
}

fun main() {
    val length = NoteValue.HalfNote.lengthIn(Tempo(120))
    println("Example length in tempo: $length and its duration: ${length.duration}")
}
