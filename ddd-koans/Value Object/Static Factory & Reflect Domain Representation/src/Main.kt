class TimeSignature(private val numberOfBeats: Int, private val noteValue: Int) {
    init {
        require(numberOfBeats in 1..32) {
            "Number of beats must be integer between 1 and 32"
        }
        require(noteValue in 1..32 && noteValue.isPowerOfTwo()) {
            "Note value must be integer that is power of two and between 1 and 32"
        }
    }

    override fun equals(other: Any?): Boolean =
        if (other == null || other !is TimeSignature)
            false
        else {
            other.noteValue == noteValue && other.numberOfBeats == numberOfBeats
        }


    override fun hashCode(): Int {
        var result = numberOfBeats
        result = 31 * result + noteValue
        return result
    }

    override fun toString(): String = "TimeSignature($numberOfBeats/$noteValue)"

    companion object {
        fun of(timeSignature: String): TimeSignature {
            val timeSignatureRegex = "^(\\d{1,2})/(\\d{1,2})$".toRegex()
            return runCatching {
                timeSignatureRegex.matchEntire(timeSignature)
                    ?.groupValues
                    ?.let { group -> group[1] to group[2] }
                    ?.let { (numberOfBeats, noteValue) -> TimeSignature(numberOfBeats.toInt(), noteValue.toInt()) }
                    ?: throw InvalidTimeSignatureException()
            }.getOrElse { throw InvalidTimeSignatureException() }
        }

    }
}

class InvalidTimeSignatureException: Exception("Failed to create TimeSignature")