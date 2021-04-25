import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.math.BigDecimal
import kotlin.random.Random

class Test {

    @ParameterizedTest
    @MethodSource("generatedListOfNotes")
    fun testIfNotesReturnExpectedLength(noteList: List<Note>) {
        val note = Notes(noteList)
        val tempo = Tempo(120)

        val length = noteList
            .map { it.lengthIn(tempo, NoteValue.QuarterNote) }
            .fold(Length(BigDecimal.ZERO)) { acc, length -> acc + length }

        assertThat(note.lengthIn(tempo, NoteValue.QuarterNote))
            .`as`("The length calculated for Notes object should be equal to sum of lengths of its notes")
            .overridingErrorMessage("The length calculated for Notes object wasn't equal to sum of lengths of its notes")
            .isEqualTo(length)
    }

    companion object {
        private val random = Random(System.currentTimeMillis())

        @JvmStatic
        fun generatedListOfNotes(): List<List<Note>> {
            return generateSequence {
                generateSequence { NoteValue.values().random()(Pitch.A0) }
                    .take(random.nextInt(0, 30))
                    .toList()
            }
                .take(100)
                .toList()
        }
    }

}
