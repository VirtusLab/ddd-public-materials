import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test

fun allValidValuesForTimeSignature() =
    (1..32).flatMap { numerator ->
        allValidDenominatorValues()
            .map { denominator -> numerator to denominator }
    }

private fun allValidDenominatorValues() =
    (1..32).filter { it.isPowerOfTwo() }

abstract class QuizTest<T>(private val expectedAnswer: T, private val answerProvider: () -> T) {

    @Test
    fun testIfTheAnswerIsCorrect() {
        Assertions.assertThatCode {
            answerProvider()
        }
            .`as`("Question method that returns answer should not throw exception.")
            .doesNotThrowAnyException()

        Assertions.assertThat(clearAnswer(answerProvider()))
            .`as`("Answer(s) should be as expected.")
            .withFailMessage("Answer(s) should be as expected.")
            .isEqualTo(clearAnswer(expectedAnswer))
    }

    protected abstract fun clearAnswer(answers: T): T
}

abstract class SingleQuizTest(expectedAnswer: String, answerProvider: () -> String) :
    QuizTest<String>(expectedAnswer, answerProvider) {
    override fun clearAnswer(answers: String) =
        answers
            .toLowerCase()
            .trim()
}

abstract class MultiQuizTest(expectedAnswers: Set<String>, answerProvider: () -> Set<String>) :
    QuizTest<Set<String>>(expectedAnswers, answerProvider) {

    override fun clearAnswer(answers: Set<String>): Set<String> =
        answers
            .map(String::toLowerCase)
            .map(String::trim)
            .toSet()
}