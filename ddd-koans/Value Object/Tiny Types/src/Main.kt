data class TimeSignature(private val numberOfBeats: NumberOfBeats, private val denominator: Denominator) {
    companion object {
        private val timeSignatureRegex = "^(\\d{1,2})/(\\d{1,2})$".toRegex()

        fun create(numberOfBeats: Int, denominator: Int): TimeSignature =
            runCatching {
                TimeSignature(NumberOfBeats(numberOfBeats), Denominator(denominator))
            }.getOrElse { throw InvalidTimeSignatureException() }

        fun of(timeSignature: String): TimeSignature =
            timeSignatureRegex.matchEntire(timeSignature)
                ?.groupValues
                ?.let { group -> group[1] to group[2] }
                ?.let { (numberOfBeats, denominator) -> create(numberOfBeats.toInt(), denominator.toInt()) }
                ?: throw InvalidTimeSignatureException()

    }
}

data class NumberOfBeats(private val value: Int) {
    init {
        require(value in 1..32)
    }
}

data class Denominator(private val value: Int) {
    init {
        require(value in 1..32 && value.isPowerOfTwo())
    }
}

class InvalidTimeSignatureException : Exception("Failed to create TimeSignature")