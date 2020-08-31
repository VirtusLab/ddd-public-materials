sealed class TimeSignature {
    companion object {
        private val timeSignatureRegex = "^(\\d{1,2})/(\\d{1,2})$".toRegex()

        fun create(numerator: Int, denominator: Int): TimeSignature =
            runCatching { ValidTimeSignature(Numerator(numerator), Denominator(denominator)) }
                .getOrDefault(InvalidTimeSignature)

        fun of(timeSignature: String): TimeSignature =
            timeSignatureRegex.matchEntire(timeSignature)
                ?.groupValues
                ?.takeIf { it.size == 3 }
                ?.let { group -> group[1] to group[2] }
                ?.let { (numerator, denominator) -> create(numerator.toInt(), denominator.toInt()) }
                ?: InvalidTimeSignature
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

fun main() {
    val timeSignaturesToBe = listOf(Pair(1, 1), Pair(1, 2), Pair(3,4), Pair(4, 3))

    //val timeSignatures = timeSignaturesToBe.map { (n, d) -> ValidTimeSignature(Numerator(n), Denominator(d)) }
    val timeSignatures = timeSignaturesToBe.map { (n, d) -> TimeSignature.create(n, d) }

    println(timeSignatures)
}