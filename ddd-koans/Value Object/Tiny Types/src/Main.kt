sealed class TimeSignature {
    companion object {
        private val timeSignatureRegex = "^(\\d{1,2})/(\\d{1,2})$".toRegex()

        fun create(numerator: Int, denominator: Int): TimeSignature {
            return runCatching {
                ValidTimeSignature(Numerator(numerator), Denominator(denominator))
            }.getOrDefault(InvalidTimeSignature)
        }

        fun of(timeSignature: String): TimeSignature {
            val groupValues = timeSignatureRegex.matchEntire(timeSignature)?.groupValues
            return if (groupValues == null) {
                InvalidTimeSignature
            } else {
                val (numerator, denominator) = (groupValues[1] to groupValues[2])
                create(numerator.toInt(), denominator.toInt())
            }
        }
    }
}

object InvalidTimeSignature : TimeSignature()

data class ValidTimeSignature(private val numerator: Numerator, private val denominator: Denominator) : TimeSignature()

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