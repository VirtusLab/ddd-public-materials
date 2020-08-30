import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatCode
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class Test {
    @ParameterizedTest
    @MethodSource("generateValidTimeSignatures")
    fun testIfOfFactoryMethodCreatesAllValidTimeSignaturesUsingAllValidStrings(arguments: Pair<Int, Int>) {
        val (numerator, denominator) = arguments
        val timeSignatureString = "$numerator/$denominator"

        assertThatCode {
            val fromStringTimeSignature = TimeSignature.of(timeSignatureString)
            val fromValuesTimeSignature = TimeSignature(numerator, denominator)

            assertThat(fromStringTimeSignature)
                .`as`("TimeSignature created using \"$timeSignatureString\" should be equal to $fromValuesTimeSignature")
                .isEqualTo(fromValuesTimeSignature)
        }
            .withFailMessage("TimeSignature created using \"$timeSignatureString\" should be created, but exception was thrown")
            .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @MethodSource("generateInvalidTimeSignatureStrings")
    fun testIfOfFactoryMethodCreatesInvalidTimeSignaturesOnInvalidStrings(invalidTimeSignature: String) {
        assertThatCode { TimeSignature.of(invalidTimeSignature) }
            .`as`("Creating TimeSignatures using values \"$invalidTimeSignature\"")
            .withFailMessage("Creating TimeSignatures using values \"$invalidTimeSignature\" ends in exception")
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

        @JvmStatic
        fun generateInvalidTimeSignatureStrings(): List<String> {
            val invalidTimeSignatureStrings = generateInvalidTimeSignatures().map { (n, d) -> "$n/$d" }
            val empty = ""
            val blank = "    "
            val blankWithSlash = "  /  "
            val zeroNominatorAndDenominator = "0/0"
            val threeNumbers = "2/2/2"
            val timeSignatureAndExtraWhitespace = " 1/12      "
            val slashOnly = "/"
            val timeSignatureUsingLetters = "a/b"
            val timeSignatureUsingSpecialCharacters = "&/@"
            val nominatorOnly = "1/"
            val denominatorOnly = "/1"
            return invalidTimeSignatureStrings + empty + blank + blankWithSlash + zeroNominatorAndDenominator +
                    threeNumbers + timeSignatureAndExtraWhitespace + slashOnly + timeSignatureUsingLetters +
                    nominatorOnly + denominatorOnly + timeSignatureUsingSpecialCharacters
        }
    }
}