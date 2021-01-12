import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import kotlin.random.Random

class Test {

    @ParameterizedTest
    @MethodSource("generatedListOfNotes")
    fun testIfAddingNotesWorks(noteList: List<Note>) {
        val length = noteList.size
        val halfOfLength = noteList.size / 2

        val firstHalf = Notes(noteList.subList(0, halfOfLength))
        val secondHalf = Notes(noteList.subList(halfOfLength, length))

        val fullNotes = Notes(noteList)

        assertThat(firstHalf + secondHalf)
            .`as`("Two Notes objects should add one to another and produce third notes")
            .overridingErrorMessage("The added Notes objects doesn't produced expected value")
            .isEqualTo(fullNotes)

        assertThat(secondHalf + firstHalf)
            .`as`("Two Notes objects should add one to another and produce third list, however this is not commutative.")
            .overridingErrorMessage("Adding first notes to second notes produced the same result as second notes to first notes.")
            .isNotEqualTo(fullNotes)
    }

    @ParameterizedTest
    @MethodSource("generatedListOfNotes")
    fun testIfAddingNoteToNotesWorks(noteList: List<Note>) {
        val note = noteList.last()
        val restOfNotes = Notes(noteList.dropLast(1))

        val fullNotes = Notes(noteList)

        assertThat(restOfNotes + note)
            .`as`("When Note is added to Notes it should produce expected result.")
            .overridingErrorMessage("Adding Note to Notes produced not expected value.")
            .isEqualTo(fullNotes)
    }

    companion object {
        private val random = Random(System.currentTimeMillis())

        @JvmStatic
        fun generatedListOfNotes(): List<List<Note>> {
            return generateSequence {
                generateSequence { NoteValue.values().random()(Pitch.A0) }
                    .take(random.nextInt(10, 30))
                    .toList()
            }
                .take(100)
                .toList()
        }
    }

}
