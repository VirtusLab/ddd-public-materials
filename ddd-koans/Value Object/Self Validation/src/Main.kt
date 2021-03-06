class TimeSignature(private val numberOfBeats: Int, private val noteValue: Int) {
    init {
        require(numberOfBeats in 1..32) {
            "Number of beats must be integer between 1 and 32"
        }
        require(noteValue in 1..32 && noteValue.isPowerOfTwo()) {
            "Note value must be integer that is power of two and between 1 and 32"
        }
    }

    override fun toString(): String = "TimeSignature($numberOfBeats/$noteValue)"
}
