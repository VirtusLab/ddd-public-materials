import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Test
import kotlin.reflect.KMutableProperty
import kotlin.reflect.KVisibility
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.jvmErasure

class Test {
    @Test
    fun testIfTimeSignatureIsImmutable() {
        TimeSignature::class.declaredMemberProperties.forEach {
            assertFalse(it is KMutableProperty<*>, "${it.name} should be declared as val not var")
            assertEquals(Int::class, it.returnType.jvmErasure, "${it.name} should be declared with type Int")
            assertFalse(it.returnType.isMarkedNullable, "${it.name} should not be marked as nullable")
        }
    }

    @Test
    fun testIfTimeSignatureHasOnlyTwoAttributesOfDenominatorAndNumerator() {
        val expectedPropertiesNames = setOf("numerator", "denominator")

        val declaredMemberProperties = TimeSignature::class.declaredMemberProperties
        val declaredPropertiesNames = declaredMemberProperties.map { it.name }.toSet()

        assertEquals(2, declaredMemberProperties.size, "TimeSignature should has exactly two properties")
        assertEquals(
            expectedPropertiesNames,
            declaredPropertiesNames,
            "TimeSignature's properties should have names: numerator, denominator"
        )
    }
}