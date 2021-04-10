import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.util.*

class Test {

    @ParameterizedTest(name = "Two bars are not equal, when their ids are not the same")
    @MethodSource("notEqualTestData")
    fun `Two bars are not equal, when their ids are not the same`(timeSignature: ValidTimeSignature, notes: Notes) {
        val firstBar = Bar(Ordinal(5), notes, timeSignature)
        val secondBar = Bar(Ordinal(6), notes, timeSignature)

        Assertions.assertThat(firstBar.equal(secondBar))
            .`as`("The first bar should not be equal to the second bar")
            .overridingErrorMessage("Two bars are equal, however, they shouldn't be!")
            .isFalse()
        Assertions.assertThat(secondBar.equal(firstBar))
            .overridingErrorMessage("Two bars are not equal, however, they shouldn't be!")
            .`as`("Second bar should not be equal to the first bar")
            .isFalse()
    }

    @ParameterizedTest(name = "Two bars are equal, when their id is the same")
    @MethodSource("equalTestData")
    fun `Two bars are equal, when their ids are the same`(
        firstTimeSignature: ValidTimeSignature,
        firstNotes: Notes,
        secondTimeSignature: ValidTimeSignature,
        secondNotes: Notes
    ) {
        val ordinal = Ordinal(3)

        val firstBar = Bar(ordinal, firstNotes, firstTimeSignature)
        val secondBar = Bar(ordinal, secondNotes, secondTimeSignature)

        Assertions.assertThat(firstBar.equal(secondBar))
            .`as`("First bar should be equal to the second bar")
            .overridingErrorMessage("Two bars are not equal, however, they should be!")
            .isTrue()
        Assertions.assertThat(secondBar.equal(firstBar))
            .overridingErrorMessage("Two bars are not equal, however, they should be!")
            .`as`("Second bar should be equal to the first bar")
            .isTrue()
    }

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

    companion object {
        private val random = Random()

        @JvmStatic
        fun notEqualTestData() =
            hundredRandomTimeSignaturesAndNotes()
                .map { (timeSignature, notes) -> Arguments.of(timeSignature, notes) }

        @JvmStatic
        fun equalTestData() =
            hundredRandomTimeSignaturesAndNotes()
                .zip(hundredRandomTimeSignaturesAndNotes())
                .map { (firstPair, secondPair) ->
                    Arguments.of(
                        firstPair.first,
                        firstPair.second,
                        secondPair.first,
                        secondPair.second
                    )
                }

        private fun hundredRandomTimeSignaturesAndNotes() = (1..100)
            .map { ValidTimeSignature(NumberOfBeats(it % 32 + 1), NoteValue.values().random()) }
            .map { it to randomNotes() }

        private fun randomNotes(): Notes = Notes(
            generateSequence { randomNote() }
                .take(random.nextInt(10) + 2)
                .toList()
        )

        private fun randomNote(): Note = NoteValue.values().random()(Pitch.values().random())
    }
}
