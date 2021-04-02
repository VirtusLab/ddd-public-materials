interface Entity {
    fun id(): Any
}

class Bar(
    private val ordinal: Ordinal,
    private val notes: Notes,
    private val timeSignature: ValidTimeSignature
) : Entity {
    override fun id(): Any = ordinal

    override fun toString(): String {
        return "Bar(ordinalNumber=$ordinal, notes=$notes, timeSignature=$timeSignature)"
    }
}

data class Ordinal(val value: Int) {
    init {
        require(value > 0) { "ordinal must be positive integer" }
    }
}
