import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Test {
    @ParameterizedTest
    @MethodSource("generateDifferentNumerators")
    fun testShouldNotBeEqualIfNumeratorsAreDifferent(pairOfNumerators: Pair<Int, Int>) {
        val (n1, n2) = pairOfNumerators

        val timeSignature = TimeSignature(n1, 1)
        val otherTimeSignature = TimeSignature(n2, 1)

        assertThat(timeSignature)
            .`as`("TimeSignature created with different numerators should not be equal")
            .isNotEqualTo(otherTimeSignature)
    }

    @ParameterizedTest
    @MethodSource("generateDifferentDenominators")
    fun testShouldNotBeEqualIfDenominatorsAreDifferent(pairOfDenominators: Pair<Int, Int>) {
        val (d1, d2) = pairOfDenominators

        val timeSignature = TimeSignature(1, d1)
        val otherTimeSignature = TimeSignature(1, d2)

        assertThat(timeSignature)
            .`as`("TimeSignatures with different denominators should not be equal.")
            .isNotEqualTo(otherTimeSignature)
    }

    @ParameterizedTest
    @MethodSource("generateValidTimeSignatures")
    fun testTimeSignaturesWithSameNumeratorAndDenominatorsAreEqual(numeratorAndDenominator: Pair<Int, Int>) {
        val (numerator, denominator) = numeratorAndDenominator

        val timeSignature = TimeSignature(numerator, denominator)
        val otherTimeSignature = TimeSignature(numerator, denominator)

        assertThat(timeSignature)
            .`as`("TimeSignatures of same numerators and denominators should be equal")
            .isEqualTo(otherTimeSignature)
    }

    @Test
    fun testTimeSignatureWithBothDifferentNumeratorAndDenominatorAreNotEqual() {
        val timeSignature = TimeSignature(1, 1)
        val otherTimeSignature = TimeSignature(32, 32)

        assertThat(timeSignature)
            .`as`("TimeSignatures with different both numerators and denominators are not equal")
            .isNotEqualTo(otherTimeSignature)
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