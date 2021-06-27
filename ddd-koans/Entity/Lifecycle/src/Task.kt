sealed class Bar(private val ordinal: Ordinal, val notes: Notes, val timeSignature: ValidTimeSignature) {
    protected val beatsLimit = beatsLimit(timeSignature)
    protected val beats = beats(notes, timeSignature.noteValue)

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

    companion object {
        fun of(ordinal: Ordinal, notes: Notes, timeSignature: ValidTimeSignature): Bar {
            val beats = notes.beats(timeSignature.noteValue)
            val beatsLimit = timeSignature.numberOfBeats.asBigDecimal
            return when {
                beats < beatsLimit -> IncompleteBar(ordinal, notes, timeSignature)
                beats > beatsLimit -> throw BarWithTooManyNotesException()
                else -> CompleteBar(ordinal, notes, timeSignature)
            }
        }

        private fun beats(notes: Notes, noteValue: NoteValue) =
            notes.beats(noteValue)

        private fun beatsLimit(timeSignature: ValidTimeSignature) =
            timeSignature.numberOfBeats.asBigDecimal
    }
}

class IncompleteBar(ordinal: Ordinal, notes: Notes, timeSignature: ValidTimeSignature) :
    Bar(ordinal, notes, timeSignature) {
    init {
        require(beats < beatsLimit) { "Incomplete bar should have less beats than ${timeSignature.numberOfBeats}" }
    }
}

class CompleteBar(ordinal: Ordinal, notes: Notes, timeSignature: ValidTimeSignature) :
    Bar(ordinal, notes, timeSignature) {
    init {
        require(beats.compareTo(beatsLimit) == 0) { "Complete bar should include exactly ${timeSignature.numberOfBeats}" }
    }
}

data class Ordinal(val value: Int) {
    init {
        require(value > 0) { "ordinal must be positive integer" }
    }
}

class BarWithTooManyNotesException :
    RuntimeException("Bar has too many notes and sum of beats is bigger than declared in the time signature.")
