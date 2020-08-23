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

fun `which of the below concepts are connected with the Value Object?`(): Set<String> =
    setOf(
        "Immutability",
        "Self-validation",
        "Property-based equality",
        "Domain-Driven Design"
    )