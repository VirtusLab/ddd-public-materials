data class TimeSignature(private val numerator: Numerator, private val denominator: Denominator) {
    companion object {
        private val timeSignatureRegex = "^(\\d{1,2})/(\\d{1,2})$".toRegex()

        fun create(numerator: Int, denominator: Int): TimeSignature =
            runCatching {
                TimeSignature(Numerator(numerator), Denominator(denominator))
            }.getOrElse { throw InvalidTimeSignatureException() }

        fun of(timeSignature: String): TimeSignature =
            timeSignatureRegex.matchEntire(timeSignature)
                ?.groupValues
                ?.let { group -> group[1] to group[2] }
                ?.let { (numerator, denominator) -> create(numerator.toInt(), denominator.toInt()) }
                ?: throw InvalidTimeSignatureException()

    }
}

data class Numerator(private val value: Int) {
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