import java.math.BigDecimal
import java.math.MathContext
import java.time.Duration
import NoteType.*

data class Notes(private val notes: List<Note>) : Lengthy {
    override fun lengthIn(tempo: Tempo): Length =
        notes
            .map { it.lengthIn(tempo) }
            .reduce { acc, length -> acc + length }

    operator fun plus(note: Note): Notes = Notes(notes + note)
    operator fun plus(other: Notes): Notes = Notes(notes + other.notes)
}

data class Note(val noteType: NoteType, val pitch: Pitch) : Lengthy by noteType

enum class NoteType(private val lengthInBeats: BigDecimal) : Lengthy {
    WholeNote(4.toBigDecimal()),
    HalfNote(2.toBigDecimal()),
    QuarterNote(1.toBigDecimal()),
    EighthNote("0.5".toBigDecimal()),
    SixteenthNote("0.25".toBigDecimal()),
    ThirtySecondNote("0.125".toBigDecimal());

    override fun lengthIn(tempo: Tempo): Length = Length(
        MILLISECONDS_IN_MINUTE.multiply(lengthInBeats).divide(tempo.bpmValue.toBigDecimal(), MathContext.DECIMAL32)
    )

    fun toNote(pitchStringRepresentation: String) = Note(this, Pitch.valueOf(pitchStringRepresentation))
    fun toNote(pitch: Pitch) = Note(this, pitch)

    companion object {
        private val MILLISECONDS_IN_MINUTE = Duration.ofMinutes(1L).toSeconds().toBigDecimal()
    }
}

enum class Pitch {
    C0, C1, C2, C3, C4, C5, C6, C7, C8, D0, D1, D2, D3, D4, D5, D6, D7, D8, E0, E1, E2, E3, E4, E5,
    E6, E7, E8, F0, F1, F2, F3, F4, F5, F6, F7, F8, G0, G1, G2, G3, G4, G5, G6, G7, G8, A0, A1, A2,
    A3, A4, A5, A6, A7, A8, H0, H1, H2, H3, H4, H5, H6, H7, H8;

    fun toWholeNote(): Note = toNote(WholeNote)
    fun toHalfNote(): Note = toNote(HalfNote)
    fun toQuarterNote(): Note = toNote(QuarterNote)
    fun toEighthNote(): Note = toNote(EighthNote)
    fun toSixteenthNote(): Note = toNote(SixteenthNote)
    fun toThirtySecondNote(): Note = toNote(ThirtySecondNote)

    private fun toNote(noteType: NoteType) = Note(noteType, this)
}