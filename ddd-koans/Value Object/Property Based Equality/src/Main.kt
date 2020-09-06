class TimeSignature(private val numberOfBeats: Int, private val noteValue: Int) {
    init {
        require(numberOfBeats in 1..32) {
            "NumberOfBeats must be integer between 1 and 32"
        }
        require(noteValue in 1..32 && noteValue.isPowerOfTwo()) {
            "NoteValue must be integer that is power of two and between 1 and 32"
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
}
