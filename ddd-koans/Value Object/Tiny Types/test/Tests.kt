import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.jvmErasure

class Test {

    @ParameterizedTest
    @ValueSource(strings = ["Numerator", "Denominator"])
    fun testIfNumeratorAndDenominatorClassAreAvailableInClassPathAndAreDataClasses(className: String) {
        assertThatCode { Class.forName(className) }
            .`as`("Class $className should be available in classpath").doesNotThrowAnyException()
        assertThat(Class.forName(className).kotlin.isData)
            .`as`("$className as small value object should be declared as data class")
            .isTrue()
    }

    @Test
    fun testIfValidTimeSignatureHasOnlyTwoAttributesDenominatorAndNumerator() {
        val declaredPropertiesNames = ValidTimeSignature::class.declaredMemberProperties.map { it.name }

        assertThat(declaredPropertiesNames)
            .`as`("ValidTimeSignature should have two properties: numerator, denominator")
            .containsExactlyInAnyOrder("numerator", "denominator")
    }

    @Test
    fun testIfValidTimeSignaturePropertiesAreDeclaredWithExpectedTinyTypes() {
        ValidTimeSignature::class.declaredMemberProperties.forEach { property ->
            assertThat(property).`as`("${property.name} should be declared as val not var")
                .isNotInstanceOf(KMutableProperty::class.java)

            assertThat(property.visibility).`as`("${property.name} should be declared as private")
                .isEqualTo(KVisibility.PRIVATE)

            val returnType = property.returnType
            val typeName = property.name.capitalize()

            assertThat(returnType.jvmErasure)
                .`as`("${property.name} should be declared with tiny type $typeName")
                .isEqualTo(Class.forName(typeName).kotlin)

            assertThat(returnType.isMarkedNullable)
                .`as`("${property.name} should not be marked as nullable")
                .isFalse()
        }
    }

    @ParameterizedTest
    @MethodSource("generateValidTimeSignatures")
    fun testIfCreatingAllValidTimeSignaturesWillGiveValidTimeSignatureInstance(arguments: Pair<Int, Int>) {
        val (numerator, denominator) = arguments

        val timeSignature = TimeSignature.create(numerator, denominator)

        assertThat(timeSignature)
            .`as`("Creating TimeSignatures using values ($numerator/$denominator) should provide ValidTimeSignature")
            .isExactlyInstanceOf(ValidTimeSignature::class.java)
    }

    @ParameterizedTest
    @MethodSource("generateInvalidTimeSignatures")
    fun testIfCreatingInvalidTimeSignaturesWillGiveInvalidTimeSignatureObject(arguments: Pair<Int, Int>) {
        val (numerator, denominator) = arguments

        val timeSignature = TimeSignature.create(numerator, denominator)

        assertThat(timeSignature)
            .`as`("Creating TimeSignatures using values ($numerator/$denominator) should provide ValidTimeSignature")
            .isExactlyInstanceOf(InvalidTimeSignature::class.java)
    }

    @ParameterizedTest
    @MethodSource("testIfDenominatorAcceptsOnlyIntegersThatArePowerOfTwoAndBetween1And32")
    fun testIfDenominatorAcceptsOnlyIntegersThatArePowerOfTwoAndBetween1And32(value: Int) {
        assertThatCode { Denominator(value) }
            .`as`("Denominator accepts only value that is power of two and between 1 and 32, $value is not one of them")
            .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @MethodSource("testIfDenominatorDoesNotAcceptIntegersThatAreNotPowerOfTwoOrBetween1And32")
    fun testIfDenominatorDoesNotAcceptIntegersThatAreNotPowerOfTwoOrBetween1And32(value: Int) {
        assertThatExceptionOfType(Exception::class.java)
            .`as`("Denominator cannot accept value of $value, because it is not power of two and between 1 and 32")
            .isThrownBy { Denominator(value) }
    }

    @ParameterizedTest
    @MethodSource("testIfNumeratorAcceptsOnlyIntegersThatAreBetween1And32")
    fun testIfNumeratorAcceptsOnlyIntegersThatAreBetween1And32(value: Int) {
        assertThatCode { Numerator(value) }
            .`as`("Numerator accepts only value that is between 1 and 32, $value is not one of them")
            .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @MethodSource("testIfNumeratorDoesNotAcceptIntegersThatAreNotBetween1And32")
    fun testIfNumeratorDoesNotAcceptIntegersThatAreNotBetween1And32(value: Int) {
        assertThatExceptionOfType(Exception::class.java)
            .`as`("Denominator cannot accept value of $value, because it is not between 1 and 32")
            .isThrownBy { Numerator(value) }
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
        fun testIfDenominatorAcceptsOnlyIntegersThatArePowerOfTwoAndBetween1And32() =
            (1..32).filter { it.isPowerOfTwo() }.toList()

        @JvmStatic
        fun testIfDenominatorDoesNotAcceptIntegersThatAreNotPowerOfTwoOrBetween1And32() =
            (-1000..1000).filterNot { it.isPowerOfTwo() && it in 1..32 }
                .toList() + Int.MAX_VALUE + Int.MIN_VALUE

        @JvmStatic
        fun testIfNumeratorDoesNotAcceptIntegersThatAreNotBetween1And32() =
            (-1000..1000).filterNot { it in 1..32 }.toList() + Int.MAX_VALUE + Int.MIN_VALUE

        @JvmStatic
        fun testIfNumeratorAcceptsOnlyIntegersThatAreBetween1And32() = (1..32).toList()

    }
}