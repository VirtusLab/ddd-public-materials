class TimeSignature(private val numerator: Int, private val denominator: Int) {
    init {
        require(numerator in 1..32) {
            "Numerator must be integer between 1 and 32"
        }
        require(denominator in 1..32 && denominator.isPowerOfTwo()) {
            "Denominator must be integer that is power of two and between 1 and 32"
        }
    }

    override fun equals(other: Any?): Boolean =
        if (other == null || other !is TimeSignature)
            false
        else {
            other.denominator == denominator && other.numerator == numerator
        }


    override fun hashCode(): Int {
        var result = numerator
        result = 31 * result + denominator
        return result
    }

    override fun toString(): String = "TimeSignature($numerator/$denominator)"

    companion object {
        fun of(timeSignature: String): TimeSignature {
            val timeSignatureRegex = "^(\\d{1,2})/(\\d{1,2})$".toRegex()
            return runCatching {
                timeSignatureRegex.matchEntire(timeSignature)
                    ?.groupValues
                    ?.let { group -> group[1] to group[2] }
                    ?.let { (numerator, denominator) -> TimeSignature(numerator.toInt(), denominator.toInt()) }
                    ?: throw InvalidTimeSignatureException()
            }.getOrElse { throw InvalidTimeSignatureException() }
        }

    }
}

class InvalidTimeSignatureException: Exception("Failed to create TimeSignature")