enum class Direction {
    Away, Into
}

data class bidi_link(val point1: String, val Point2: String, val weight: Double, val direction: Direction);

class PaymentsManager() {
    data class Payee(val name: String, val adjacent: MutableList<bidi_link>) {
        fun getTotalPayable(): Double {
            var total: Double = 0;
            for (it in adjacent) {
                if (it.direction == Direction.Away) {
                    total += it.weight;
                }
            }
            return total;
        }

        fun getTotalReceivable(): Double {
            var total: Double = 0;
            for (it in adjacent) {
                if (it.direction == Direction.Into) {
                    total += it.weight;
                }
            }
            return total;
        }
    }

    init {
        // Read from the database and initialize the links to determine how much each person owes
    }
}