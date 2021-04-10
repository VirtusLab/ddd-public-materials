class Bar(
    private val ordinal: Ordinal,
    private val notes: Notes,
    private val timeSignature: ValidTimeSignature
) {
    fun id(): Ordinal = ordinal

    fun equal(other: Bar): Boolean {
        return id() == other.id()
    }

    override fun toString(): String {
        return "Bar(ordinalNumber=$ordinal, notes=$notes, timeSignature=$timeSignature)"
    }
}

data class Ordinal(val value: Int) {
    init {
        require(value > 0) { "ordinal must be positive integer" }
    }
}
