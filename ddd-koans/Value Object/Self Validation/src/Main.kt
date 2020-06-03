class TimeSignature(private val numerator: Int, private val denominator: Int) {
    init {
        require(numerator in 1..32) {
            "Numerator must be integer between 1 and 32"
        }
        require(denominator in 1..32 && denominator.isPowerOfTwo()) {
            "Denominator must be integer that is power of two and between 1 and 32"
        }
    }

    override fun toString(): String = "TimeSignature($numerator/$denominator)"
}
