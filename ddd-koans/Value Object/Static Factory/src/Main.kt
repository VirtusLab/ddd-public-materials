sealed class TimeSignature {
    companion object {
        fun create(numerator: Int, denominator: Int): TimeSignature {
            return if (numerator in 1..32 && denominator in 1..32 && denominator.isPowerOfTwo())
                ValidTimeSignature(numerator, denominator)
            else
                InvalidTimeSignature
        }
    }
}

object InvalidTimeSignature : TimeSignature()

class ValidTimeSignature(private val numerator: Int, private val denominator: Int) : TimeSignature() {
    init {
        require(numerator in 1..32) {
            "Numerator must be integer between 1 and 32"
        }
        require(denominator in 1..32 && denominator.isPowerOfTwo()) {
            "Denominator must be integer that is power of two and between 1 and 32"
        }
    }

    override fun equals(other: Any?): Boolean =
        if (other == null || other !is ValidTimeSignature)
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
}
