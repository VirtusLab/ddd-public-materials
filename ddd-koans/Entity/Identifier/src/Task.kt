interface Entity {
    fun id(): Any
}

class Bar(
    private val ordinalNumber: OrdinalNumber,
    private val notes: Notes,
    private val timeSignature: ValidTimeSignature
) : Entity {
    override fun id(): Any = ordinalNumber

    override fun toString(): String {
        return "Bar(ordinalNumber=$ordinalNumber, notes=$notes, timeSignature=$timeSignature)"
    }
}

data class OrdinalNumber(val value: Int)
