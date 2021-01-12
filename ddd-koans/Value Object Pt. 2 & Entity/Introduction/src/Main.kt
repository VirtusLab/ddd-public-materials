import java.math.BigDecimal

enum class NoteValue(private val relativeValue: Int) {
    WholeNote(1),
    HalfNote(2),
    QuarterNote(4),
    EighthNote(8),
    SixteenthNote(16),
    ThirtySecondNote(32);

    private val lengthInBeats: BigDecimal = 4.toBigDecimal().divide(relativeValue.toBigDecimal())
}

fun main() {
    println("Example note value: ${NoteValue.HalfNote}")
}
