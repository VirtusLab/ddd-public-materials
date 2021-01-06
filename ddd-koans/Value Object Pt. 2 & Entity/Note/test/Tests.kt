import org.assertj.core.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import org.junit.jupiter.params.provider.MethodSource
import kotlin.reflect.KFunction0
import kotlin.reflect.KFunction1

class Test {

    @ParameterizedTest
    @MethodSource("generateNoteValuesAndPitches")
    fun testIfNoteIsCreatedProperlyUsingValidPitchStringRepresentationInvokedOnNoteValue(arguments: Pair<NoteValue, Pitch>) {
        val (noteValue, pitch) = arguments
        val noteFactory = { noteValue(pitch.toString()) }

        assertThatCode { noteFactory() }
            .`as`("Creating note using valid pitch string representation invoked on note value $noteValue should not thrown exception")
            .doesNotThrowAnyException()

        val note = noteFactory()
        assertThat(note.pitch).`as`("Created note $note should contain expected pitch $pitch")
            .overridingErrorMessage("Created note $note doesn't contain expected pitch $pitch")
            .isEqualTo(pitch)
    }

    @ParameterizedTest
    @MethodSource("generateNoteValuesAndPitches")
    fun testIfNoteIsNotCreatedProperlyUsingInvalidPitchStringRepresentationInvokedOnNoteValue(arguments: Pair<NoteValue, Pitch>) {
        val (noteValue, pitch) = arguments
        val pitchRepresentation = pitch.toString() + "test"

        assertThatExceptionOfType(Exception::class.java).isThrownBy { noteValue(pitchRepresentation) }
            .`as`("Creating note using invalid pitch string representation '$pitchRepresentation' invoked on note value $noteValue should thrown exception")
            .overridingErrorMessage("Creating note using invalid pitch string representation '$pitchRepresentation' invoked on note value $noteValue should thrown exception")
    }

    @ParameterizedTest
    @MethodSource("generatePitchItsTransformationFunctionsAndNoteValueOfFunction")
    fun testIfExpectedNoteIsCreated(arguments: Triple<Pitch, KFunction1<Pitch, Note>, NoteValue>) {
        val (pitch, toNote, value) = arguments
        assertThatCode { toNote(pitch) }
            .`as`("'$toNote' should not throw exception with given $pitch")
            .overridingErrorMessage("'$toNote' should not throw exception with given $pitch")
            .doesNotThrowAnyException()

        val note = toNote(pitch)
        assertThat(note.noteValue).`as`("The created note: $note should have expected note value: $value")
            .overridingErrorMessage("The created note: $note has not expected note value: $value")
            .isEqualTo(value)
    }

    companion object {
        private val toNoteMethods = listOf(
            Pitch::wholeNote to NoteValue.WholeNote,
            Pitch::halfNote to NoteValue.HalfNote,
            Pitch::quarterNote to NoteValue.QuarterNote,
            Pitch::eighthNote to NoteValue.EighthNote,
            Pitch::sixteenthNote to NoteValue.SixteenthNote,
            Pitch::thirtySecondNote to NoteValue.ThirtySecondNote
        )

        @JvmStatic
        fun generateNoteValuesAndPitches(): List<Pair<NoteValue, Pitch>> =
            NoteValue.values().flatMap { noteValue -> Pitch.values().map { pitch -> noteValue to pitch } }

        @JvmStatic
        fun generatePitchItsTransformationFunctionsAndNoteValueOfFunction(): List<Triple<Pitch, KFunction1<Pitch, Note>, NoteValue>> =
            Pitch.values().flatMap { pitch -> pitchFunctionsAndValues(pitch) }

        private fun pitchFunctionsAndValues(pitch: Pitch) =
            toNoteMethods.map { (toNoteMethod, noteValue) -> Triple(pitch, toNoteMethod, noteValue) }

    }

}
