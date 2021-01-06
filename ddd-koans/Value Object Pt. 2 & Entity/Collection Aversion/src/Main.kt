import NoteValue.*
import java.math.BigDecimal
import java.math.MathContext
import java.time.Duration

data class Notes(private val notes: List<Note>) : LengthInTempo {
    override fun lengthIn(tempo: Tempo): Length {
        return notes
            .map { it.lengthIn(tempo) }
            .fold(Length(0.toBigDecimal())) { sum, length -> sum + length }
    }
}

data class Note(val noteValue: NoteValue, val pitch: Pitch) : LengthInTempo by noteValue

enum class NoteValue(private val relativeValue: Int): LengthInTempo {
    WholeNote(1),
    HalfNote(2),
    QuarterNote(4),
    EighthNote(8),
    SixteenthNote(16),
    ThirtySecondNote(32);

    private val lengthInBeats: BigDecimal = 4.toBigDecimal().divide(relativeValue.toBigDecimal())

    override fun lengthIn(tempo: Tempo): Length {
        val minuteAsSeconds = Duration.ofMinutes(1L).toSeconds().toBigDecimal()
        val durationInSeconds = minuteAsSeconds
            .multiply(lengthInBeats).divide(tempo.bpm.toBigDecimal(), MathContext.DECIMAL32)
        return Length(durationInSeconds)
    }

    operator fun invoke(pitchStringRepresentation: String) = Note(this, Pitch.valueOf(pitchStringRepresentation))
    operator fun invoke(pitch: Pitch) = Note(this, pitch)
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

    private fun toNote(noteValue: NoteValue) = Note(noteValue, this)
}
