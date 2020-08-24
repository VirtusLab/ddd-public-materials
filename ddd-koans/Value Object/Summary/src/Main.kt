fun `which of the below concepts are connected with the Value Object?`(): Set<String> =
    setOf(
        "Immutability",
        "Self-validation",
        "Property-based equality",
        "Domain-Driven Design"
    )

data class TimeSignature(private val numerator: Numerator, private val denominator: Denominator) {
    companion object {
        private val timeSignatureRegex = "^(\\d{1,2})/(\\d{1,2})$".toRegex()

        fun create(numerator: Int, denominator: Int) =
            TimeSignature(Numerator(numerator), Denominator(denominator))

        fun of(timeSignature: String): TimeSignature {
            val groupValues = timeSignatureRegex.matchEntire(timeSignature)?.groupValues
            return groupValues?.let {
                val (numerator, denominator) = (groupValues[1] to groupValues[2])
                create(numerator.toInt(), denominator.toInt())
            } ?: throw Exception("Invalid or not present either denominator or numerator")
        }
    }
}

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