import java.math.BigDecimal

sealed class TimeSignature {
    companion object {
        private val timeSignatureRegex = "^(\\d{1,2})/(\\d{1,2})$".toRegex()

        fun create(numberOfBeats: Int, noteValue: NoteValue): TimeSignature =
            runCatching { ValidTimeSignature(NumberOfBeats(numberOfBeats), noteValue) }
                .getOrDefault(InvalidTimeSignature)

        fun of(timeSignature: String): TimeSignature =
            timeSignatureRegex.matchEntire(timeSignature)
                ?.groupValues
                ?.takeIf { it.size == 3 }
                ?.let { group -> group[1] to group[2] }
                ?.let { (numberOfBeats, noteValue) -> noteValueOrNull(noteValue)?.let { numberOfBeats to it } }
                ?.let { (numberOfBeats, noteValue) -> create(numberOfBeats.toInt(), noteValue) }
                ?: InvalidTimeSignature

        private fun noteValueOrNull(noteValue: String) = NoteValue.fromRelativeValue(noteValue.toInt())
    }
}

object InvalidTimeSignature : TimeSignature()

data class ValidTimeSignature(val numberOfBeats: NumberOfBeats, val noteValue: NoteValue) :
    TimeSignature()

data class NumberOfBeats(val value: Int) {
    init {
        require(value in 1..32)
    }

    val asBigDecimal: BigDecimal get() = value.toBigDecimal()
}
