sealed class TimeSignature {
    companion object {
        private val timeSignatureRegex = "^(\\d{1,2})/(\\d{1,2})$".toRegex()

        fun create(numberOfBeats: Int, noteValue: Int): TimeSignature =
            runCatching { ValidTimeSignature(NumberOfBeats(numberOfBeats), NoteValue(noteValue)) }
                .getOrDefault(InvalidTimeSignature)

        fun of(timeSignature: String): TimeSignature =
            timeSignatureRegex.matchEntire(timeSignature)
                ?.groupValues
                ?.takeIf { it.size == 3 }
                ?.let { group -> group[1] to group[2] }
                ?.let { (numberOfBeats, noteValue) -> create(numberOfBeats.toInt(), noteValue.toInt()) }
                ?: InvalidTimeSignature
    }
}

object InvalidTimeSignature : TimeSignature()

data class ValidTimeSignature(private val numberOfBeats: NumberOfBeats, private val noteValue: NoteValue) : TimeSignature()

data class NumberOfBeats(private val value: Int) {
    init {
        require(value in 1..32)
    }
}

data class NoteValue(private val value: Int) {
    init {
        require(value in 1..32 && value.isPowerOfTwo())
    }
}

fun main() {
    val validTimeSignatures = setOf("1/2", "3/4", "4/4")
    val invalidTimeSignatures = setOf("1/3", "5/7")
    val timeSignatureToCreate = validTimeSignatures + invalidTimeSignatures

    timeSignatureToCreate
        .map { input -> TimeSignature.of(input) }
        .forEach(::println)
}