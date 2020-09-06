import org.assertj.core.api.Assertions.assertThatCode
import org.assertj.core.api.Assertions.assertThatExceptionOfType
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource


class Test {

    @ParameterizedTest
    @MethodSource("generateValidTimeSignatures")
    fun testIfCreatingAllValidTimeSignaturesWillNotThrowException(arguments: Pair<Int, Int>) {
        val (numberOfBeats, noteValue) = arguments

        assertThatCode { TimeSignature(numberOfBeats, noteValue) }
            .`as`("Creating TimeSignatures using values ($numberOfBeats/$noteValue) should not throw exception on creation")
            .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @MethodSource("generateInvalidTimeSignatures")
    fun testIfCreatingInvalidTimeSignaturesWillThrowException(arguments: Pair<Int, Int>) {
        val (numberOfBeats, noteValue) = arguments

        assertThatExceptionOfType(Exception::class.java)
            .`as`("Invalid values ($numberOfBeats/$noteValue) should cause throwing exception when creating TimeSignatures")
            .isThrownBy { TimeSignature(numberOfBeats, noteValue) }
    }

    companion object {
        @JvmStatic
        fun generateInvalidTimeSignatures(): List<Pair<Int, Int>> {
            val noteValueInValidRangeButNotPowerOfTwo = (1..32).flatMap { numberOfBeats ->
                (1..32).filterNot { it.isPowerOfTwo() }.map { numberOfBeats to it }
            }
            val noteValueIsZero = 1 to 0
            val noteValueIs33 = 1 to 33
            val noteValueIsPowerOfTwoButBiggerThan32 = 1 to 64
            val numberOfBeatsIsZero = 0 to 1
            val numberOfBeatsIs33 = 33 to 1
            return noteValueInValidRangeButNotPowerOfTwo + noteValueIsZero + noteValueIs33 +
                    noteValueIsPowerOfTwoButBiggerThan32 + numberOfBeatsIs33 + numberOfBeatsIsZero
        }

        @JvmStatic
        fun generateValidTimeSignatures() = allValidValuesForTimeSignature()
    }
}