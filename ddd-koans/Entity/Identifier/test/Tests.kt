import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Test {
    @Test
    fun `Bar should have properly defined id method`() {
        val expectedId = OrdinalNumber(1)
        val bar = Bar(
            expectedId,
            Notes(listOf(Pitch.A0.eighthNote())),
            ValidTimeSignature(NumberOfBeats(3), NoteValue.QuarterNote)
        )

        val id = bar.id()
        Assertions.assertThat(id)
            .`as`("$bar should have id defined as expected to provide its identification in context of music piece.")
            .withFailMessage("Provided id=$id is not the expected id.")
            .isEqualTo(expectedId)
    }
}
