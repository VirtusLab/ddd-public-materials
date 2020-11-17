import NoteType.*
import Pitch.*

fun main() {
    val notes = Notes(
        listOf(
            WholeNote.toNote("F2"),
            QuarterNote.toNote("A1"),
            EighthNote.toNote("H1"),
            ThirtySecondNote.toNote("D5"),
            WholeNote.toNote(F2),
            ThirtySecondNote.toNote(C2),
            ThirtySecondNote.toNote(D5),
            F2.toWholeNote(),
            A1.toSixteenthNote(),
            D2.toThirtySecondNote()
        )
    )

    println((notes + G2.toSixteenthNote()).lengthIn(Tempo(123)))
}
