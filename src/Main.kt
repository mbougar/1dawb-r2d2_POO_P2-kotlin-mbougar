import kotlin.random.Random

enum class Direccion {
    PositiveY, NegativeX, NegativeY, PositiveX
}

fun girarR2D2(dir: Int): Int {
    if (dir == 3) {
        return 0
    } else {
        return (dir + 1)
    }
}

fun girarDAW1A(posX: Int, dir: Int): Int {
    if (posX >= 0){
        if (dir == 3) {
            return 1
        } else {
            return (dir + 2)
        }
    } else {
        if (dir == 0) {
            return 3
        } else {
            return (dir - 1)
        }
    }
}

fun girarDAW1B(posY: Int, dir: Int): Int {
    if (posY < 0){
        if (dir == 3) {
            return 1
        } else {
            return (dir + 2)
        }
    } else {
        if (dir == 0) {
            return 3
        } else {
            return (dir - 1)
        }
    }
}

fun girarDAM1(dir: Int): Int {
    var nuevaDir: Int
    do {
        nuevaDir = Random.nextInt(0, 3)
    } while (nuevaDir == dir)

    return nuevaDir
}

class Robot(private val nombre: String, private var posX: Int = 0, private var posY: Int = 0, private var dir: Int = 0, private val girar: (posX: Int, posY: Int, dir: Int) -> Int) {

    init {
        require(nombre.isNotEmpty()) { "Nombre no puede ser vacío." }
        require(dir in 0..3) { "La direccion no puede ser menor que 0 o mayor que 3" }
    }

    // Change the parameter type to accept a lambda
    fun moverRobot(movimientos: IntArray) {
        for (elemento in movimientos) {
            when (dir) {
                0 -> posY += elemento
                1 -> posX -= elemento
                2 -> posY -= elemento
                3 -> posX += elemento
            }
            dir = girar(posX, posY, dir)
        }
    }

    private fun obtenerDireccion(): String {
        return when (dir) {
            0 -> Direccion.PositiveY.name
            1 -> Direccion.NegativeX.name
            2 -> Direccion.NegativeY.name
            3 -> Direccion.PositiveX.name
            else -> throw IllegalArgumentException("Direccion no válida: $dir")
        }
    }

    fun mostrarPosicion() {
        println(" - $nombre está en ($posX, $posY) ${obtenerDireccion()}")
    }
}

fun main() {
    val robot1 = Robot("R2D2") { _, _, dir -> girarR2D2(dir) }
    val robot2 = Robot("DAW1A", Random.nextInt(-5, 5), 0, 1) { posX, _, dir -> girarDAW1A(posX ,dir) }
    val robot3 = Robot("DAW1B", 0, Random.nextInt(-10, 10), Random.nextInt(0, 3)) { _, posY, dir -> girarDAW1B(posY ,dir) }
    val robot4 = Robot("DAM1", Random.nextInt(-5, 5), Random.nextInt(-5, 5), Random.nextInt(0, 3)) { _, _, dir -> girarDAM1(dir) }

    val listaMovimientos = listOf(
        intArrayOf(10, 5, -2),
        intArrayOf(0, 0, 0),
        intArrayOf(),
        intArrayOf(-10, -5, 2),
        intArrayOf(-10, -5, 2, 4, -8),
        intArrayOf(3, 3, 5, 6, 1, 0, 0, -7),
        intArrayOf(1, -5, 0, -9),
        intArrayOf(2, 1, 0, -1, 1, 1, -4),
        intArrayOf(3, 5),
        intArrayOf(4)
    )

    var contadorMovimiento = 1

    for (movimiento in listaMovimientos) {
        println("Movimiento $contadorMovimiento:")
        contadorMovimiento++
        robot1.moverRobot(movimiento)
        robot1.mostrarPosicion()
        robot2.moverRobot(movimiento)
        robot2.mostrarPosicion()
        robot3.moverRobot(movimiento)
        robot3.mostrarPosicion()
        robot4.moverRobot(movimiento)
        robot4.mostrarPosicion()
    }
}
