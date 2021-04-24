class Bar(private val ordinal: Ordinal, val notes: Notes, val timeSignature: ValidTimeSignature) {
    init {
        validateState()
    }

    fun state(): State {
        val beats = notes.beats(timeSignature.noteValue)
        val beatsLimit = timeSignature.numberOfBeats.asBigDecimal
        return when {
            beats < beatsLimit -> State.Incomplete
            beats > beatsLimit -> throw BarWithTooManyNotesException()
            else -> State.Complete
        }
    }

    fun id(): Ordinal = ordinal

    fun equal(other: Bar): Boolean {
        return id() == other.id()
    }

    override fun equals(other: Any?): Boolean = when (other) {
        null -> false
        is Bar -> equal(other)
        else -> false
    }

    override fun hashCode(): Int = ordinal.hashCode()

    override fun toString(): String {
        return "Bar(ordinalNumber=$ordinal, notes=$notes, timeSignature=$timeSignature)"
    }

    private fun validateState() {
        state()
    }

    enum class State {
        Complete, Incomplete
    }
}

data class Ordinal(val value: Int) {
    init {
        require(value > 0) { "ordinal must be positive integer" }
    }
}

class BarWithTooManyNotesException :
    RuntimeException("Bar has too many notes and sum of beats is bigger than declared in the time signature.")
