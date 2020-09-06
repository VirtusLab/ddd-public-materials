import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import org.junit.jupiter.params.provider.ValueSource
import java.lang.reflect.Constructor
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.jvmErasure

class Test {

    @ParameterizedTest
    @ValueSource(strings = ["NumberOfBeats", "NoteValue"])
    fun testIfNumberOfBeatsAndNoteValueClassAreAvailableInClassPathAndAreDataClasses(className: String) {
        assertThatCode { Class.forName(className) }
            .`as`("Class $className should be available in classpath").doesNotThrowAnyException()
        assertThat(Class.forName(className).kotlin.isData)
            .`as`("$className as small value object should be declared as data class")
            .isTrue()
    }

    @Test
    fun testIfValidTimeSignatureHasOnlyTwoAttributesNoteValueAndNumberOfBeats() {
        val declaredPropertiesNames = TimeSignature::class.declaredMemberProperties.map { it.name }

        assertThat(declaredPropertiesNames)
            .`as`("TimeSignature should have expected properties")
            .overridingErrorMessage("TimeSignature should have two only two properties: numberOfBeats, noteValue")
            .containsExactlyInAnyOrder("numberOfBeats", "noteValue")
    }

    @Test
    fun testIfValidTimeSignaturePropertiesAreDeclaredWithExpectedTinyTypes() {
        TimeSignature::class.declaredMemberProperties.forEach { property ->
            assertThat(property).`as`("${property.name} should be declared as val not var")
                .isNotInstanceOf(KMutableProperty::class.java)

            val returnType = property.returnType
            val typeName = property.name.capitalize()

            assertThat(returnType.jvmErasure)
                .`as`("${property.name} should be declared with tiny type")
                .overridingErrorMessage("${property.name} should be declared with tiny type $typeName")
                .isEqualTo(Class.forName(typeName).kotlin)

            assertThat(returnType.isMarkedNullable)
                .`as`("${property.name} should not be marked as nullable")
                .overridingErrorMessage("${property.name} should not be nullable")
                .isFalse()
        }
    }

    @ParameterizedTest
    @MethodSource("generateValidTimeSignatures")
    fun testIfCreatingAllValidTimeSignaturesWillGiveValidTimeSignatureInstance(arguments: Pair<Int, Int>) {
        val (numberOfBeats, noteValue) = arguments

        val timeSignature = TimeSignature.create(numberOfBeats, noteValue)

        assertThat(timeSignature)
            .`as`("Creating TimeSignatures using values ($numberOfBeats/$noteValue) should provide TimeSignature")
            .isExactlyInstanceOf(TimeSignature::class.java)
    }

    @ParameterizedTest
    @MethodSource("generateInvalidTimeSignatures")
    fun testIfCreatingInvalidTimeSignaturesWillGiveInvalidTimeSignatureObject(arguments: Pair<Int, Int>) {
        val (numberOfBeats, noteValue) = arguments

        assertThatCode { TimeSignature.create(numberOfBeats, noteValue) }
            .isExactlyInstanceOf(InvalidTimeSignatureException::class.java)
            .`as`("Creating TimeSignatures using values ($numberOfBeats/$noteValue)")
            .withFailMessage("Creating TimeSignatures using values ($numberOfBeats/$noteValue) end up throwing InvalidTimeSignatureException")
    }

    @ParameterizedTest
    @MethodSource("testIfNoteValueAcceptsOnlyIntegersThatArePowerOfTwoAndBetween1And32")
    fun testIfNoteValueAcceptsOnlyIntegersThatArePowerOfTwoAndBetween1And32(value: Int) {
        val constructor = checkIfTypesExistsWithConstructor("NoteValue")

        assertThatCode { constructor.newInstance(value) }
            .`as`("NoteValue accepts only value that is power of two and between 1 and 32, $value is not one of them")
            .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @MethodSource("testIfNoteValueDoesNotAcceptIntegersThatAreNotPowerOfTwoOrBetween1And32")
    fun testIfNoteValueDoesNotAcceptIntegersThatAreNotPowerOfTwoOrBetween1And32(value: Int) {
        val constructor = checkIfTypesExistsWithConstructor("NoteValue")

        assertThatExceptionOfType(Exception::class.java)
            .`as`("NoteValue cannot accept value of $value, because it is not power of two and between 1 and 32")
            .isThrownBy { constructor.newInstance(value) }
    }
    @ParameterizedTest
    @MethodSource("testIfNumberOfBeatsAcceptsOnlyIntegersThatAreBetween1And32")
    fun testIfNumberOfBeatsAcceptsOnlyIntegersThatAreBetween1And32(value: Int) {
        val constructor = checkIfTypesExistsWithConstructor("NumberOfBeats")

        assertThatCode { constructor.newInstance(value) }
            .`as`("NumberOfBeats accepts only value that is between 1 and 32, $value is not one of them")
            .doesNotThrowAnyException()
    }

    @ParameterizedTest
    @MethodSource("testIfNumberOfBeatsDoesNotAcceptIntegersThatAreNotBetween1And32")
    fun testIfNumberOfBeatsDoesNotAcceptIntegersThatAreNotBetween1And32(value: Int) {
        val constructor = checkIfTypesExistsWithConstructor("NumberOfBeats")

        assertThatExceptionOfType(Exception::class.java)
            .`as`("NumberOfBeats cannot accept value of $value, because it is not between 1 and 32")
            .isThrownBy { constructor.newInstance(value) }
    }

    private fun checkIfTypesExistsWithConstructor(type: String): Constructor<out Any> {
        assertThatCode { Class.forName(type) }
            .overridingErrorMessage("$type does not exists.")
            .doesNotThrowAnyException()
        assertThatCode { Class.forName(type).getConstructor(Int::class.java) }
            .overridingErrorMessage("$type does not exists.")
            .doesNotThrowAnyException()
        return Class.forName(type).getConstructor(Int::class.java)
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
        fun testIfNoteValueAcceptsOnlyIntegersThatArePowerOfTwoAndBetween1And32() =
            (1..32).filter { it.isPowerOfTwo() }.toList()

        @JvmStatic
        fun testIfNoteValueDoesNotAcceptIntegersThatAreNotPowerOfTwoOrBetween1And32() =
            (-1000..1000).filterNot { it.isPowerOfTwo() && it in 1..32 }
                .toList() + Int.MAX_VALUE + Int.MIN_VALUE

        @JvmStatic
        fun testIfNumberOfBeatsDoesNotAcceptIntegersThatAreNotBetween1And32() =
            (-1000..1000).filterNot { it in 1..32 }.toList() + Int.MAX_VALUE + Int.MIN_VALUE

        @JvmStatic
        fun testIfNumberOfBeatsAcceptsOnlyIntegersThatAreBetween1And32() = (1..32).toList()

    }
}