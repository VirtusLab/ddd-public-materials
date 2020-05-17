import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Test {
    @ParameterizedTest
    @MethodSource("generateValidTimeSignatures")
    fun testIfCreatingAllValidTimeSignaturesWillGiveValidTimeSignatureInstance(arguments: Pair<Int, Int>) {
        val (numerator, denominator) = arguments
        assertTrue(
            TimeSignature.create(numerator, denominator) is ValidTimeSignature,
            "Creating TimeSignatures using values ($numerator/$denominator) should provide ValidTimeSignature"
        )
    }

    @ParameterizedTest
    @MethodSource("generateInvalidTimeSignatures")
    fun testIfCreatingInvalidTimeSignaturesWillGiveInvalidTimeSignatureObject(arguments: Pair<Int, Int>) {
        val (numerator, denominator) = arguments
        assertTrue(
            TimeSignature.create(numerator, denominator) is InvalidTimeSignature,
            "Creating TimeSignatures using values ($numerator/$denominator) should provide InvalidTimeSignature"
        )
    }

    companion object {
        @JvmStatic
        fun generateInvalidTimeSignatures(): List<Pair<Int, Int>> {
            val denominatorInValidRangeButNotPowerOfTwo = (1..32).flatMap { numerator ->
                (1..32).filterNot { it.isPowerOfTwo() }.map { numerator to it }
            }
            val denominatorIsZero = 1 to 0
            val denominatorIs33 = 1 to 33
            val denominatorIsPowerOfTwoButBiggerThan32 = 1 to 64
            val numeratorIsZero = 0 to 1
            val numeratorIs33 = 33 to 1
            return denominatorInValidRangeButNotPowerOfTwo + denominatorIsZero + denominatorIs33 +
                    denominatorIsPowerOfTwoButBiggerThan32 + numeratorIs33 + numeratorIsZero
        }

        @JvmStatic
        fun generateValidTimeSignatures() = allValidValuesForTimeSignature()
    }
}