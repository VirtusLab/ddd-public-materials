fun allValidValuesForTimeSignature() =
    (1..32).flatMap { numerator ->
        allValidDenominatorValues()
            .map { denominator -> numerator to denominator }
    }

private fun allValidDenominatorValues() =
    (1..32).filter { it.isPowerOfTwo() }