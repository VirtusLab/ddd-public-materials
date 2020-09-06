import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Test {
    @ParameterizedTest
    @MethodSource("generateValidTimeSignatures")
    fun testIfCreatingAllValidTimeSignaturesWillGiveValidTimeSignatureInstance(arguments: Pair<Int, Int>) {
        val (numberOfBeats, noteValue) = arguments

        val timeSignature = TimeSignature.create(numberOfBeats, noteValue)

        assertThat(timeSignature)
            .`as`("Creating TimeSignatures using values ($numberOfBeats/$noteValue) should provide ValidTimeSignature")
            .isExactlyInstanceOf(ValidTimeSignature::class.java)
    }

    @ParameterizedTest
    @MethodSource("generateInvalidTimeSignatures")
    fun testIfCreatingInvalidTimeSignaturesWillGiveInvalidTimeSignatureObject(arguments: Pair<Int, Int>) {
        val (numberOfBeats, noteValue) = arguments

        val timeSignature = TimeSignature.create(numberOfBeats, noteValue)

        assertThatCode {
            assertThat(timeSignature)
                .`as`("Creating TimeSignature ($numberOfBeats/$noteValue) should return InvalidTimeSignature and exception should not be thrown")
                .isExactlyInstanceOf(InvalidTimeSignature::class.java)
        }
            .withFailMessage("Creating invalid TimeSignature($numberOfBeats/$noteValue) should not throw exception, but it had")
            .doesNotThrowAnyException()

    }

    @ParameterizedTest
    @MethodSource("generateValidTimeSignatures")
    fun testIfOfFactoryMethodCreatesAllValidTimeSignaturesUsingAllValidStrings(arguments: Pair<Int, Int>) {
        val (numberOfBeats, noteValue) = arguments
        val timeSignatureString = "$numberOfBeats/$noteValue"

        val fromStringTimeSignature = TimeSignature.of(timeSignatureString)
        val fromValuesTimeSignature = TimeSignature.create(numberOfBeats, noteValue)

        assertThat(fromStringTimeSignature)
            .`as`("TimeSignature created using \"$timeSignatureString\" should be equal to $fromValuesTimeSignature")
            .isEqualTo(fromValuesTimeSignature)
    }

    @ParameterizedTest
    @MethodSource("generateInvalidTimeSignatureStrings")
    fun testIfOfFactoryMethodCreatesInvalidTimeSignaturesOnInvalidStrings(invalidTimeSignature: String) {
        val timeSignature = TimeSignature.of(invalidTimeSignature)

        assertThat(timeSignature).`as`("Creating TimeSignatures using values \"$invalidTimeSignature\" should return InvalidTimeSignature")
            .isExactlyInstanceOf(InvalidTimeSignature::class.java)
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

        @JvmStatic
        fun generateInvalidTimeSignatureStrings(): List<String> {
            val invalidTimeSignatureStrings = generateInvalidTimeSignatures().map { (n, d) -> "$n/$d" }
            val empty = ""
            val blank = "    "
            val blankWithSlash = "  /  "
            val zeroNominatorAndNoteValue = "0/0"
            val threeNumbers = "2/2/2"
            val timeSignatureAndExtraWhitespace = " 1/12      "
            val slashOnly = "/"
            val timeSignatureUsingLetters = "a/b"
            val timeSignatureUsingSpecialCharacters = "&/@"
            val nominatorOnly = "1/"
            val noteValueOnly = "/1"
            return invalidTimeSignatureStrings + empty + blank + blankWithSlash + zeroNominatorAndNoteValue +
                    threeNumbers + timeSignatureAndExtraWhitespace + slashOnly + timeSignatureUsingLetters +
                    nominatorOnly + noteValueOnly + timeSignatureUsingSpecialCharacters
        }
    }
}