import org.assertj.core.api.Assertions
import org.assertj.core.data.Offset
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import java.math.MathContext
import java.time.Duration
import kotlin.random.Random
import NoteValue.*

class Test {

    @ParameterizedTest
    @MethodSource("generateNoteValuesTemposAndLength")
    fun testIfCreatingAllValidTimeSignaturesWillNotThrowException(
        noteValue: NoteValue,
        tempo: Tempo,
        expectedLength: Length,
        beatBase: NoteValue,
    ) {
        val length = noteValue.lengthIn(tempo, beatBase)

        Assertions.assertThat(length.duration.toMillis())
            .withFailMessage("The actual $length is not enough close to $expectedLength")
            .isCloseTo(expectedLength.duration.toMillis(), Offset.offset(10L))
    }

    companion object {
        private val SECONDS_IN_MINUTE = Duration.ofMinutes(1L).toSeconds().toBigDecimal()

        private val random = Random(System.currentTimeMillis())
        private val staticExamples = listOf(
            Arguments.of(WholeNote, Tempo(120), Length(2.toBigDecimal()), QuarterNote),
            Arguments.of(HalfNote, Tempo(120), Length(1.toBigDecimal()), QuarterNote),
            Arguments.of(HalfNote, Tempo(60), Length(1.toBigDecimal()), HalfNote),
            Arguments.of(HalfNote, Tempo(240), Length(1.toBigDecimal()), EighthNote),
            Arguments.of(QuarterNote, Tempo(120), Length("0.5".toBigDecimal()), QuarterNote),
            Arguments.of(QuarterNote, Tempo(240), Length("0.5".toBigDecimal()), EighthNote),
            Arguments.of(EighthNote, Tempo(120), Length("0.25".toBigDecimal()), QuarterNote),
            Arguments.of(SixteenthNote, Tempo(120), Length("0.125".toBigDecimal()), QuarterNote),
            Arguments.of(ThirtySecondNote, Tempo(120), Length("0.0625".toBigDecimal()), QuarterNote),
            Arguments.of(WholeNote, Tempo(60), Length(4.toBigDecimal()), QuarterNote),
            Arguments.of(HalfNote, Tempo(60), Length(2.toBigDecimal()), QuarterNote),
            Arguments.of(QuarterNote, Tempo(60), Length(1.toBigDecimal()), QuarterNote),
            Arguments.of(EighthNote, Tempo(60), Length("0.5".toBigDecimal()), QuarterNote),
            Arguments.of(SixteenthNote, Tempo(60), Length("0.25".toBigDecimal()), QuarterNote),
            Arguments.of(SixteenthNote, Tempo(60), Length(1.toBigDecimal()), SixteenthNote),
            Arguments.of(ThirtySecondNote, Tempo(60), Length("0.125".toBigDecimal()), QuarterNote),
            Arguments.of(ThirtySecondNote, Tempo(60), Length(1.toBigDecimal()), ThirtySecondNote),
        )

        @JvmStatic
        fun generateNoteValuesTemposAndLength(): List<Arguments> =
            noteValuesWithRandomTempo().map { (noteValue, tempo) ->
                val beatValue = beatValue(noteValue)
                val tempoValue = tempo.bpm.toBigDecimal()
                val length = Length(SECONDS_IN_MINUTE.multiply(beatValue).divide(tempoValue, MathContext.DECIMAL32))
                Arguments.of(noteValue, tempo, length, QuarterNote)
            } + staticExamples


        private fun noteValuesWithRandomTempo() =
            values().flatMap { randomTempos().map { tempo -> it to tempo } }

        private fun randomTempos() = randomTempo().take(random.nextInt(2, 8)).toList()

        private fun randomTempo(): Sequence<Tempo> = generateSequence { Tempo(random.nextInt(10, 390)) }

        private fun beatValue(noteValue: NoteValue) = when (noteValue) {
            WholeNote -> 4.toBigDecimal()
            HalfNote -> 2.toBigDecimal()
            QuarterNote -> 1.toBigDecimal()
            EighthNote -> "0.5".toBigDecimal()
            SixteenthNote -> "0.25".toBigDecimal()
            ThirtySecondNote -> "0.125".toBigDecimal()
        }
    }

}
