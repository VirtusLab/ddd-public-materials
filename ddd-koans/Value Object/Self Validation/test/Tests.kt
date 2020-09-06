import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource


class Test {

    @ParameterizedTest
    @MethodSource("generateValidTimeSignatures")
    fun testIfCreatingAllValidTimeSignaturesWillNotThrowException(arguments: Pair<Int, Int>) {
        val (numberOfBeats, denominator) = arguments

        assertThatCode { TimeSignature(numberOfBeats, denominator) }
            .`as`("Creating TimeSignatures using values ($numberOfBeats/$denominator) should not throw exception on creation")
            .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @MethodSource("generateInvalidTimeSignatures")
    fun testIfCreatingInvalidTimeSignaturesWillThrowException(arguments: Pair<Int, Int>) {
        val (numberOfBeats, denominator) = arguments

        assertThatExceptionOfType(Exception::class.java)
            .`as`("Invalid values ($numberOfBeats/$denominator) should cause throwing exception when creating TimeSignatures")
            .isThrownBy { TimeSignature(numberOfBeats, denominator) }
    }

    companion object {
        @JvmStatic
        fun generateInvalidTimeSignatures(): List<Pair<Int, Int>> {
            val denominatorInValidRangeButNotPowerOfTwo = (1..32).flatMap { numberOfBeats ->
                (1..32).filterNot { it.isPowerOfTwo() }.map { numberOfBeats to it }
            }
            val denominatorIsZero = 1 to 0
            val denominatorIs33 = 1 to 33
            val denominatorIsPowerOfTwoButBiggerThan32 = 1 to 64
            val numberOfBeatsIsZero = 0 to 1
            val numberOfBeatsIs33 = 33 to 1
            return denominatorInValidRangeButNotPowerOfTwo + denominatorIsZero + denominatorIs33 +
                    denominatorIsPowerOfTwoButBiggerThan32 + numberOfBeatsIs33 + numberOfBeatsIsZero
        }

        @JvmStatic
        fun generateValidTimeSignatures() = allValidValuesForTimeSignature()
    }
}