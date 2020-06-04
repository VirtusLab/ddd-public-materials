import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.reflect.KMutableProperty
import kotlin.reflect.full.declaredMemberProperties
import kotlin.reflect.jvm.jvmErasure

class Test {
    @Test
    fun testIfTimeSignatureIsImmutable() {
        TimeSignature::class.declaredMemberProperties.forEach { property ->
            assertThat(property).`as`("${property.name} should be declared as val not var")
                .isNotInstanceOf(KMutableProperty::class.java)

            val returnType = property.returnType
            assertThat(returnType.jvmErasure).`as`("${property.name} should be declared with type Int")
                .isEqualTo(Int::class)
            assertThat(returnType.isMarkedNullable).`as`("${property.name} should not be marked as nullable")
                .isFalse()
        }
    }

    @Test
    fun testIfTimeSignatureHasOnlyTwoAttributesDenominatorAndNumerator() {
        val declaredPropertiesNames = TimeSignature::class.declaredMemberProperties.map { it.name }

        assertThat(declaredPropertiesNames)
            .`as`("TimeSignature's should have two properties: numerator, denominator")
            .containsExactlyInAnyOrder("numerator", "denominator")
    }
}