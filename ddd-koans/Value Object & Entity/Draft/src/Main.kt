import NoteType.*
import Pitch.*

fun main() {
    val notes = Notes(
        listOf(
            WholeNote("F2"),
            QuarterNote("A1"),
            EighthNote("H1"),
            ThirtySecondNote("D5"),
            WholeNote(F2),
            ThirtySecondNote(C2),
            ThirtySecondNote(D5),
            F2.wholeNote(),
            A1.sixteenthNote(),
            D2.thirtySecondNote()
        )
    )

    println((notes + G2.sixteenthNote()).lengthIn(Tempo(123)))
}
