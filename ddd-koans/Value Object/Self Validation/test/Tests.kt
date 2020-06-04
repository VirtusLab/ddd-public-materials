import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource


class Test {

    @ParameterizedTest
    @MethodSource("generateValidTimeSignatures")
    fun testIfCreatingAllValidTimeSignaturesWillNotThrowException(arguments: Pair<Int, Int>) {
        val (numerator, denominator) = arguments

        assertThatCode { TimeSignature(numerator, denominator) }
            .`as`("Creating TimeSignatures using values ($numerator/$denominator) should not throw exception on creation")
            .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @MethodSource("generateInvalidTimeSignatures")
    fun testIfCreatingInvalidTimeSignaturesWillThrowException(arguments: Pair<Int, Int>) {
        val (numerator, denominator) = arguments

        assertThatExceptionOfType(Exception::class.java)
            .`as`("Invalid values ($numerator/$denominator) should cause throwing exception when creating TimeSignatures")
            .isThrownBy { TimeSignature(numerator, denominator) }
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