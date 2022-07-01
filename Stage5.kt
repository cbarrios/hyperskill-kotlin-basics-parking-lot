package parkinglot

class ParkingLot {
    private var spots = mutableListOf<ParkingSpot>()
    private val isCreated: Boolean
        get() = spots.isNotEmpty()
    private val isEmpty: Boolean
        get() = spots.all { it.isAvailable }

    fun create(size: Int) {
        spots = MutableList(size) { index -> ParkingSpot(index + 1) }
        println("Created a parking lot with $size spots.")
    }

    fun park(car: Car) {
        if (notCreated()) return
        val first = spots.firstOrNull { it.isAvailable }
        if (first == null) {
            println("Sorry, the parking lot is full.")
            return
        }
        val updated = first.copy(car = car)
        spots[first.id - 1] = updated
        println("${updated.car!!.color} car parked in spot ${updated.id}.")
    }

    fun leave(spotId: Int) {
        if (notCreated()) return
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

    fun status() {
        if (notCreated()) return
        if (isEmpty) {
            println("Parking lot is empty.")
            return
        }
        val list = spots.filter { !it.isAvailable }
        for (spot in list) {
            println("${spot.id} ${spot.car!!.registrationId} ${spot.car.color}")
        }
    }

    private fun notCreated(): Boolean {
        if (!isCreated) println("Sorry, a parking lot has not been created.")
        return !isCreated
    }

    // Stage 5
    fun getRegsByColor(color: String) {
        if (notCreated()) return
        val list = spots.filter { it.car?.color?.uppercase() == color }
        if (list.isEmpty()) {
            println("No cars with color $color were found.")
            return
        }
        val mapped = list.map { it.car?.registrationId }
        println(mapped.joinToString())
    }

    fun getSpotsByColor(color: String) {
        if (notCreated()) return
        val list = spots.filter { it.car?.color?.uppercase() == color }
        if (list.isEmpty()) {
            println("No cars with color $color were found.")
            return
        }
        val mapped = list.map { it.id }
        println(mapped.joinToString())
    }

    fun getSpotByReg(registrationId: String) {
        if (notCreated()) return
        val spot = spots.firstOrNull { it.car?.registrationId == registrationId }
        if (spot == null) {
            println("No cars with registration number $registrationId were found.")
            return
        }
        println(spot.id)
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
    mainLoop@ while (true) {
        val input = readLine()!!.split(" ")
        when (input.first()) {
            "create" -> {
                val size = input[1].toInt()
                parkingLot.create(size)
            }
            "park" -> {
                val car = Car(input[1], input[2])
                parkingLot.park(car)
            }
            "leave" -> {
                val spotId = input[1].toInt()
                parkingLot.leave(spotId)
            }
            "status" -> {
                parkingLot.status()
            }
            "reg_by_color" -> {
                val color = input[1].uppercase()
                parkingLot.getRegsByColor(color)
            }
            "spot_by_color" -> {
                val color = input[1].uppercase()
                parkingLot.getSpotsByColor(color)
            }
            "spot_by_reg" -> {
                val registrationId = input[1]
                parkingLot.getSpotByReg(registrationId)
            }
            "exit" -> break@mainLoop
        }
    }
}