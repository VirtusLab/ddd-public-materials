import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Test {
    @ParameterizedTest
    @MethodSource("generateValidTimeSignatures")
    fun testIfOfFactoryMethodCreatesAllValidTimeSignaturesUsingAllValidStrings(arguments: Pair<Int, Int>) {
        val (numberOfBeats, noteValue) = arguments
        val timeSignatureString = "$numberOfBeats/$noteValue"

        assertThatCode {
            val fromStringTimeSignature = TimeSignature.of(timeSignatureString)
            val fromValuesTimeSignature = TimeSignature(numberOfBeats, noteValue)

            assertThat(fromStringTimeSignature)
                .`as`("TimeSignature created using \"$timeSignatureString\" should be equal to $fromValuesTimeSignature")
                .withFailMessage("Created $fromStringTimeSignature from \"$timeSignatureString\" does not match expected $fromValuesTimeSignature")
                .isEqualTo(fromValuesTimeSignature)
        }
            .withFailMessage("TimeSignature created using \"$timeSignatureString\" should be created, but exception was thrown")
            .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @MethodSource("generateInvalidTimeSignatureStrings")
    fun testIfOfFactoryMethodCreatesInvalidTimeSignaturesOnInvalidStrings(invalidTimeSignature: String) {
        assertThatCode {
            assertThatCode { TimeSignature.of(invalidTimeSignature) }
                .isExactlyInstanceOf(InvalidTimeSignatureException::class.java)
                .`as`("Creating TimeSignature using \"$invalidTimeSignature\" should fail")
                .withFailMessage("Creating TimeSignature using \"$invalidTimeSignature\" did not fail")
        }
            .withFailMessage("\"$invalidTimeSignature\" is invalid therefore only InvalidTimeSignatureException should have been thrown")
            .doesNotThrowAnyException()
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
            val zeroNumberOfBeatsAndNoteValue = "0/0"
            val threeNumbers = "2/2/2"
            val timeSignatureAndExtraWhitespace = " 1/12      "
            val slashOnly = "/"
            val timeSignatureUsingLetters = "a/b"
            val timeSignatureUsingSpecialCharacters = "&/@"
            val numberOfBeatsOnly = "1/"
            val noteValueOnly = "/1"
            return invalidTimeSignatureStrings + empty + blank + blankWithSlash + zeroNumberOfBeatsAndNoteValue +
                    threeNumbers + timeSignatureAndExtraWhitespace + slashOnly + timeSignatureUsingLetters +
                    numberOfBeatsOnly + noteValueOnly + timeSignatureUsingSpecialCharacters
        }
    }
}