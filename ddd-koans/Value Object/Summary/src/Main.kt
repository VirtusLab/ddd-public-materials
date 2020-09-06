fun `which of the below concepts are connected with the Value Object?`(): Set<String> =
    setOf(
        "Immutability",
        "Self-validation",
        "Property-based equality",
        "Domain-Driven Design"
    )

data class TimeSignature(private val numberOfBeats: Numerator, private val noteValue: noteValue) {
    companion object {
        private val timeSignatureRegex = "^(\\d{1,2})/(\\d{1,2})$".toRegex()

        fun create(numberOfBeats: Int, noteValue: Int) =
            runCatching {
                TimeSignature(Numerator(numberOfBeats), NoteValue(noteValue))
            }.getOrElse { throw InvalidTimeSignatureException() }


        fun of(timeSignature: String): TimeSignature =
            timeSignatureRegex.matchEntire(timeSignature)
                ?.groupValues
                ?.takeIf { it.size == 3 }
                ?.let { group -> group[1] to group[2] }
                ?.let { (numberOfBeats, noteValue) -> create(numberOfBeats.toInt(), noteValue.toInt()) }
                ?: throw InvalidTimeSignatureException()
    }
}

data class Numerator(private val value: Int) {
    init {
        require(value in 1..32)
    }
}

data class NoteValue(private val value: Int) {
    init {
        require(value in 1..32 && value.isPowerOfTwo())
    }
}

class InvalidTimeSignatureException : Exception("Failed to create TimeSignature")