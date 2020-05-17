import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Test {
    @ParameterizedTest
    @MethodSource("generateDifferentNumerators")
    fun testShouldNotBeEqualIfNumeratorsAreDifferent(pairOfNumerators: Pair<Int, Int>) {
        val (n1, n2) = pairOfNumerators
        assertNotEquals(
            TimeSignature(n1, 1), TimeSignature(n2, 1),
            "TimeSignatures with different numerators $n1 and $n2 should not be equal."
        )
    }

    @ParameterizedTest
    @MethodSource("generateDifferentDenominators")
    fun testShouldNotBeEqualIfDenominatorsAreDifferent(pairOfDenominators: Pair<Int, Int>) {
        val (d1, d2) = pairOfDenominators
        assertNotEquals(
            TimeSignature(1, d2), TimeSignature(1, d1),
            "TimeSignatures with different denominators $d1 and $d2 should not be equal."
        )
    }

    @ParameterizedTest
    @MethodSource("generateValidTimeSignatures")
    fun testTimeSignaturesWithSameNumeratorAndDenominatorsAreEqual(numeratorAndDenominator: Pair<Int, Int>) {
        val (numerator, denominator) = numeratorAndDenominator
        assertEquals(
            TimeSignature(numerator, denominator), TimeSignature(numerator, denominator),
            "TimeSignatures of same numerators and denominators should be equal"
        )
    }

    @Test
    fun testTimeSignatureWithBothDifferentNumeratorAndDenominatorAreNotEqual() {
        assertNotEquals(
            TimeSignature(1, 1),
            TimeSignature(32, 32),
            "TimeSignatures with different both numerators and denominators are not equal"
        )
    }

    companion object {
        @JvmStatic
        fun generateDifferentNumerators() =
            (1..32).flatMap { n1 -> (1..32).filter { n2 -> n1 != n2 }.map { n2 -> n1 to n2 } }

        @JvmStatic
        fun generateDifferentDenominators() =
            (1..32).filter { it.isPowerOfTwo() }
                .flatMap { d1 -> (1..32).filter { it.isPowerOfTwo() }.filter { d2 -> d1 != d2 }.map { d2 -> d1 to d2 } }

        @JvmStatic
        fun generateValidTimeSignatures() = allValidValuesForTimeSignature()
    }
}