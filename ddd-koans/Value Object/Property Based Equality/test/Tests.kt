import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Test {
    @ParameterizedTest
    @MethodSource("generateDifferentNumberOfBeats")
    fun testShouldNotBeEqualIfNumberOfBeatsAreDifferent(pairOfNumberOfBeats: Pair<Int, Int>) {
        val (n1, n2) = pairOfNumberOfBeats

        val timeSignature = TimeSignature(n1, 1)
        val otherTimeSignature = TimeSignature(n2, 1)

        assertThat(timeSignature)
            .`as`("TimeSignature created with different numberOfBeatss should not be equal")
            .isNotEqualTo(otherTimeSignature)
    }

    @ParameterizedTest
    @MethodSource("generateDifferentNoteValues")
    fun testShouldNotBeEqualIfNoteValuesAreDifferent(pairOfNoteValues: Pair<Int, Int>) {
        val (d1, d2) = pairOfNoteValues

        val timeSignature = TimeSignature(1, d1)
        val otherTimeSignature = TimeSignature(1, d2)

        assertThat(timeSignature)
            .`as`("TimeSignatures with different noteValues should not be equal.")
            .isNotEqualTo(otherTimeSignature)
    }

    @ParameterizedTest
    @MethodSource("generateValidTimeSignatures")
    fun testTimeSignaturesWithSameNumeratorAndNoteValuesAreEqual(numberOfBeatsAndNoteValue: Pair<Int, Int>) {
        val (numberOfBeats, noteValue) = numberOfBeatsAndNoteValue

        val timeSignature = TimeSignature(numberOfBeats, noteValue)
        val otherTimeSignature = TimeSignature(numberOfBeats, noteValue)

        assertThat(timeSignature)
            .`as`("TimeSignatures of same numberOfBeatss and noteValues should be equal")
            .isEqualTo(otherTimeSignature)
    }

    @Test
    fun testTimeSignatureWithBothDifferentNumeratorAndNoteValueAreNotEqual() {
        val timeSignature = TimeSignature(1, 1)
        val otherTimeSignature = TimeSignature(32, 32)

        assertThat(timeSignature)
            .`as`("TimeSignatures with different both numberOfBeatss and noteValues are not equal")
            .isNotEqualTo(otherTimeSignature)
    }

    companion object {
        @JvmStatic
        fun generateDifferentNumberOfBeats() =
            (1..32).flatMap { n1 -> (1..32).filter { n2 -> n1 != n2 }.map { n2 -> n1 to n2 } }

        @JvmStatic
        fun generateDifferentNoteValues() =
            (1..32).filter { it.isPowerOfTwo() }
                .flatMap { d1 -> (1..32).filter { it.isPowerOfTwo() }.filter { d2 -> d1 != d2 }.map { d2 -> d1 to d2 } }

        @JvmStatic
        fun generateValidTimeSignatures() = allValidValuesForTimeSignature()
    }
}