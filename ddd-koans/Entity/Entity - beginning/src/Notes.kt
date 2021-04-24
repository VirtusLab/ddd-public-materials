import NoteValue.*
import java.math.BigDecimal
import java.math.MathContext

data class Notes(private val notes: List<Note>) : LengthInTempo {
    override fun lengthIn(tempo: Tempo, beatBase: NoteValue): Length {
        return notes
            .map { it.lengthIn(tempo, beatBase) }
            .fold(Length(BigDecimal.ZERO)) { sum, length -> sum + length }
    }

    fun beats(beatBase: NoteValue): BigDecimal =
        notes.map { note -> note.noteValue.lengthInBeats(beatBase) }
            .fold(BigDecimal.ZERO) { first, second ->
                first.add(second)
            }

    operator fun plus(note: Note): Notes {
        return Notes(notes + note)
    }

    operator fun plus(other: Notes): Notes {
        return Notes(notes + other.notes)
    }
}

data class Note(val noteValue: NoteValue, val pitch: Pitch) : LengthInTempo by noteValue

enum class NoteValue(private val relativeValue: Int) : LengthInTempo {
    WholeNote(1),
    HalfNote(2),
    QuarterNote(4),
    EighthNote(8),
    SixteenthNote(16),
    ThirtySecondNote(32);

    fun lengthInBeats(beatBase: NoteValue): BigDecimal =
        beatBase.relativeValue.toBigDecimal().divide(relativeValue.toBigDecimal())

    override fun lengthIn(tempo: Tempo, beatBase: NoteValue): Length {
        val minuteInSeconds = 60.toBigDecimal()
        val durationInSeconds = minuteInSeconds
            .multiply(lengthInBeats(beatBase)).divide(tempo.bpm.toBigDecimal(), MathContext.DECIMAL32)
        return Length(durationInSeconds)
    }

    operator fun invoke(pitchStringRepresentation: String): Note {
        return runCatching { Note(this, Pitch.valueOf(pitchStringRepresentation)) }
            .getOrElse { throw InvalidPitchRepresentationException(pitchStringRepresentation) }
    }

    operator fun invoke(pitch: Pitch) = Note(this, pitch)

    companion object {
        fun fromRelativeValue(relativeValue: Int) = NoteValue.values().find { it.relativeValue == relativeValue }
    }
}

enum class Pitch {
    C0, C1, C2, C3, C4, C5, C6, C7, C8, D0, D1, D2, D3, D4, D5, D6, D7, D8, E0, E1, E2, E3, E4, E5,
    E6, E7, E8, F0, F1, F2, F3, F4, F5, F6, F7, F8, G0, G1, G2, G3, G4, G5, G6, G7, G8, A0, A1, A2,
    A3, A4, A5, A6, A7, A8, H0, H1, H2, H3, H4, H5, H6, H7, H8;

    fun wholeNote(): Note = toNote(WholeNote)
    fun halfNote(): Note = toNote(HalfNote)
    fun quarterNote(): Note = toNote(QuarterNote)
    fun eighthNote(): Note = toNote(EighthNote)
    fun sixteenthNote(): Note = toNote(SixteenthNote)
    fun thirtySecondNote(): Note = toNote(ThirtySecondNote)

    private fun toNote(noteValue: NoteValue): Note = Note(noteValue, this)
}

class InvalidPitchRepresentationException(invalidPitchStringRepresentation: String) :
    Exception("The provided pitch representation '$invalidPitchStringRepresentation' is invalid.")
