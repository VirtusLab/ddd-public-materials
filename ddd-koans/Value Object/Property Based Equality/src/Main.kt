class TimeSignature(private val numberOfBeats: Int, private val denominator: Int) {
    init {
        require(numberOfBeats in 1..32) {
            "NumberOfBeats must be integer between 1 and 32"
        }
        require(denominator in 1..32 && denominator.isPowerOfTwo()) {
            "Denominator must be integer that is power of two and between 1 and 32"
        }
    }

    override fun equals(other: Any?): Boolean =
        if (other == null || other !is TimeSignature)
            false
        else {
            other.denominator == denominator && other.numberOfBeats == numberOfBeats
        }


    override fun hashCode(): Int {
        var result = numberOfBeats
        result = 31 * result + denominator
        return result
    }

    override fun toString(): String = "TimeSignature($numberOfBeats/$denominator)"
}
