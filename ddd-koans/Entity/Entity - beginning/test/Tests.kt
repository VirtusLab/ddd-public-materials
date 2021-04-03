import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

class Test {
    @Test
    fun `Bar should have properly defined id method`() {
        val expectedId = Ordinal(1)
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

    @Test
    fun `Bar should have id of expected type`() {
        val idMemberClassifier = Bar::class.members.find { it.name == "id" }!!.returnType.classifier
        Assertions.assertThat(idMemberClassifier)
            .`as`("Bar's id should be of expected type")
            .withFailMessage("Provided id=$idMemberClassifier is not the expected id type.")
            .isEqualTo(Ordinal::class)
    }
}
