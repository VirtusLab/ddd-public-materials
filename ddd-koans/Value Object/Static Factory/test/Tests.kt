import org.junit.jupiter.api.Assertions.assertEquals
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

    @ParameterizedTest
    @MethodSource("generateValidTimeSignatures")
    fun testIfOfFactoryMethodCreatesAllValidTimeSignaturesUsingAllValidStrings(arguments: Pair<Int, Int>) {
        val (numerator, denominator) = arguments
        assertEquals(
            TimeSignature.of("$numerator/$denominator"),
            TimeSignature.create(numerator, denominator)
        )
    }

    @ParameterizedTest
    @MethodSource("generateInvalidTimeSignatureStrings")
    fun testIfOfFactoryMethodCreatesInvalidTimeSignaturesOnInvalidStrings(invalidTimeSignature: String) {
        assertTrue(
            TimeSignature.of(invalidTimeSignature) is InvalidTimeSignature,
            "Creating TimeSignatures using values \"$invalidTimeSignature\" should return InvalidTimeSignature"
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