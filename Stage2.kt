package parkinglot

class ParkingLot(size: Int = 2) {
    private val spots = MutableList(size) { index ->
        when (index) {
            0 -> ParkingSpot(1, Car("CC-01-BB-3300", "Black"))
            else -> ParkingSpot(index + 1)
        }
    }

    fun park(car: Car) {
        val first = spots.firstOrNull { it.isAvailable }
        if (first == null) {
            println("Parking lot is full.")
            return
        }
        val updated = first.copy(car = car)
        spots[first.id - 1] = updated
        println("${updated.car!!.color} car parked in spot ${updated.id}.")
    }

    fun leave(spotId: Int) {
        val spot = spots.getOrNull(spotId - 1)
        if (spot == null) {
            println("Parking spot not found.")
            return
        }
        if (spot.isAvailable) {
            println("There is no car in spot $spotId.")
            return
        }
        val updated = spot.copy(car = null)
        spots[spotId - 1] = updated
        println("Spot $spotId is free.")
    }
}

data class ParkingSpot(
    val id: Int,
    val car: Car? = null
) {
    val isAvailable: Boolean
        get() = car == null
}

data class Car(val registrationId: String, val color: String)

fun main() {
    val parkingLot = ParkingLot()
    val input = readln().split(" ")
    when(input.first()) {
        "park" -> {
            val car = Car(input[1], input[2])
            parkingLot.park(car)
        }
        "leave" -> {
            val spotId = input[1].toInt()
            parkingLot.leave(spotId)
        }
    }
}
