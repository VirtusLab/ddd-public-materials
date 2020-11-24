import org.assertj.core.api.Assertions
import org.assertj.core.data.Offset
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import java.math.MathContext
import java.time.Duration
import kotlin.random.Random

class Test {

    @ParameterizedTest
    @MethodSource("generateNoteValuesTemposAndLength")
    fun testIfCreatingAllValidTimeSignaturesWillNotThrowException(arguments: Triple<NoteValue, Tempo, Length>) {
        val (noteValue, tempo, expectedLength) = arguments

        val length = noteValue.lengthIn(tempo)

        Assertions.assertThat(length.duration.toMillis())
            .withFailMessage("The actual $length is not enough close to $expectedLength")
            .isCloseTo(expectedLength.duration.toMillis(), Offset.offset(10L))
    }

    companion object {
        private val SECONDS_IN_MINUTE = Duration.ofMinutes(1L).toSeconds().toBigDecimal()

        private val random = Random(System.currentTimeMillis())
        private val staticExamples = listOf(
            Triple(NoteValue.WholeNote, Tempo(120), Length(2.toBigDecimal())),
            Triple(NoteValue.HalfNote, Tempo(120), Length(1.toBigDecimal())),
            Triple(NoteValue.QuarterNote, Tempo(120), Length("0.5".toBigDecimal())),
            Triple(NoteValue.EighthNote, Tempo(120), Length("0.25".toBigDecimal())),
            Triple(NoteValue.SixteenthNote, Tempo(120), Length("0.125".toBigDecimal())),
            Triple(NoteValue.ThirtySecondNote, Tempo(120), Length("0.0625".toBigDecimal())),
            Triple(NoteValue.WholeNote, Tempo(60), Length(4.toBigDecimal())),
            Triple(NoteValue.HalfNote, Tempo(60), Length(2.toBigDecimal())),
            Triple(NoteValue.QuarterNote, Tempo(60), Length(1.toBigDecimal())),
            Triple(NoteValue.EighthNote, Tempo(60), Length("0.5".toBigDecimal())),
            Triple(NoteValue.SixteenthNote, Tempo(60), Length("0.25".toBigDecimal())),
            Triple(NoteValue.ThirtySecondNote, Tempo(60), Length("0.125".toBigDecimal()))
        )

        @JvmStatic
        fun generateNoteValuesTemposAndLength(): List<Triple<NoteValue, Tempo, Length>> =
            noteValuesWithRandomTempo().map { (noteValue, tempo) ->
                val beatValue = beatValue(noteValue)
                val tempoValue = tempo.bpm.toBigDecimal()
                val length = Length(SECONDS_IN_MINUTE.multiply(beatValue).divide(tempoValue, MathContext.DECIMAL32))
                Triple(noteValue, tempo, length)
            } + staticExamples


        private fun noteValuesWithRandomTempo() =
            NoteValue.values().flatMap { randomTempos().map { tempo -> it to tempo } }

        private fun randomTempos() = randomTempo().take(random.nextInt(2, 8)).toList()

        private fun randomTempo(): Sequence<Tempo> = generateSequence { Tempo(random.nextInt(10, 390)) }

        private fun beatValue(noteValue: NoteValue) = when(noteValue) {
            NoteValue.WholeNote -> 4.toBigDecimal()
            NoteValue.HalfNote -> 2.toBigDecimal()
            NoteValue.QuarterNote -> 1.toBigDecimal()
            NoteValue.EighthNote -> "0.5".toBigDecimal()
            NoteValue.SixteenthNote -> "0.25".toBigDecimal()
            NoteValue.ThirtySecondNote -> "0.125".toBigDecimal()
        }
    }

}