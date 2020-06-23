sealed class TimeSignature

data class ValidTimeSignature(private val numerator: Int, private val denominator: Int) : TimeSignature() {
    init {
        require(numerator in 1..32) {
            "Numerator must be integer between 1 and 32"
        }
        require(denominator in 1..32 && denominator.isPowerOfTwo()) {
            "Denominator must be integer that is power of two and between 1 and 32"
        }
    }
}

fun main() {
    //Nothing to do here yet. Read the summary and feel free to move to the next exercise.
}