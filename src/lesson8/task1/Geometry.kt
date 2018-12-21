@file:Suppress("UNUSED_PARAMETER")

package lesson8.task1

import lesson1.task1.sqr
import kotlin.Double.Companion.POSITIVE_INFINITY
import kotlin.math.*

/**
 * Точка на плоскости
 */
data class Point(val x: Double, val y: Double) {

    fun center(other: Point): Point = Point(((x + other.x) / 2), (y + other.y) / 2)

    /**
     * Пример
     *
     * Рассчитать (по известной формуле) расстояние между двумя точками
     */
    fun distance(other: Point): Double = sqrt(sqr(x - other.x) + sqr(y - other.y))
}

/**
 * Треугольник, заданный тремя точками (a, b, c, см. constructor ниже).
 * Эти три точки хранятся в множестве points, их порядок не имеет значения.
 */
class Triangle private constructor(private val points: Set<Point>) {

    private val pointList = points.toList()

    val a: Point get() = pointList[0]

    val b: Point get() = pointList[1]

    val c: Point get() = pointList[2]

    constructor(a: Point, b: Point, c: Point) : this(linkedSetOf(a, b, c))

    /**
     * Пример: полупериметр
     */
    fun halfPerimeter() = (a.distance(b) + b.distance(c) + c.distance(a)) / 2.0

    /**
     * Пример: площадь
     */
    fun area(): Double {
        val p = halfPerimeter()
        return sqrt(p * (p - a.distance(b)) * (p - b.distance(c)) * (p - c.distance(a)))
    }

    /**
     * Пример: треугольник содержит точку
     */
    fun contains(p: Point): Boolean {
        val abp = Triangle(a, b, p)
        val bcp = Triangle(b, c, p)
        val cap = Triangle(c, a, p)
        return abp.area() + bcp.area() + cap.area() <= area()
    }

    override fun equals(other: Any?) = other is Triangle && points == other.points

    override fun hashCode() = points.hashCode()

    override fun toString() = "Triangle(a = $a, b = $b, c = $c)"
}

/**
 * Окружность с заданным центром и радиусом
 */
data class Circle(val center: Point, val radius: Double) {
    /**
     * Простая
     *
     * Рассчитать расстояние между двумя окружностями.
     * Расстояние между непересекающимися окружностями рассчитывается как
     * расстояние между их центрами минус сумма их радиусов.
     * Расстояние между пересекающимися окружностями считать равным 0.0.
     */
    fun distance(other: Circle): Double {
        val centerDist = center.distance(other.center)
        return when {
            (centerDist - (radius + other.radius) <= 0) -> 0.0
            else -> centerDist - (radius + other.radius)
        }
    }

    /**
     * Тривиальная
     *
     * Вернуть true, если и только если окружность содержит данную точку НА себе или ВНУТРИ себя
     */
    fun contains(p: Point): Boolean = center.distance(p) <= radius
}

/**
 * Отрезок между двумя точками
 */
data class Segment(val begin: Point, val end: Point) {
    fun length() = begin.distance(end)

    fun center(): Point {
        val xCenter = (begin.x + end.x) / 2.0
        val yCenter = (begin.y + end.y) / 2.0
        return Point(xCenter, yCenter)
    }

    override fun equals(other: Any?) =
            other is Segment && (begin == other.begin && end == other.end || end == other.begin && begin == other.end)

    override fun hashCode() =
            begin.hashCode() + end.hashCode()
}

/**
 * Средняя
 *
 * Дано множество точек. Вернуть отрезок, соединяющий две наиболее удалённые из них.
 * Если в множестве менее двух точек, бросить IllegalArgumentException
 */
fun diameter(vararg points: Point): Segment {
    if (points.size < 2) throw IllegalArgumentException()
    var seg = Segment(points[0], points[1])
    for (p1 in 0 until points.size) {
        for (p2 in p1 + 1 until points.size) {
            if (points[p1].distance(points[p2]) > seg.length())
                seg = Segment(points[p1], points[p2])
        }
    }
    return seg
}

/**
 * Простая
 *
 * Построить окружность по её диаметру, заданному двумя точками
 * Центр её должен находиться посередине между точками, а радиус составлять половину расстояния между ними
 */
fun circleByDiameter(diameter: Segment): Circle {
    val center = diameter.center()
    val radius = diameter.length() / 2.0
    return Circle(center, radius)
}

/**
 * Прямая, заданная точкой point и углом наклона angle (в радианах) по отношению к оси X.
 * Уравнение прямой: (y - point.y) * cos(angle) = (x - point.x) * sin(angle)
 * или: y * cos(angle) = x * sin(angle) + b, где b = point.y * cos(angle) - point.x * sin(angle).
 * Угол наклона обязан находиться в диапазоне от 0 (включительно) до PI (исключительно).
 */
class Line private constructor(val b: Double, val angle: Double) {
    init {
        require(angle >= 0 && angle < PI) { "Incorrect line angle: $angle" }
    }

    constructor(point: Point, angle: Double) : this(point.y * cos(angle) - point.x * sin(angle), angle)

    /**
     * Средняя
     *
     * Найти точку пересечения с другой линией.
     * Для этого необходимо составить и решить систему из двух уравнений (каждое для своей прямой)
     */
    fun crossPoint(other: Line): Point {
        val x = ((other.b * cos(angle)) - (b * cos(other.angle))) /
                ((sin(angle) * cos(other.angle)) - (sin(other.angle) * cos(angle)))
        val y = ((b * sin(other.angle)) - (other.b * sin(angle))) /
                ((cos(angle) * sin(other.angle)) - (cos(other.angle) * sin(angle)))
        return Point(x, y)
    }

    override fun equals(other: Any?) = other is Line && angle == other.angle && b == other.b

    override fun hashCode(): Int {
        var result = b.hashCode()
        result = 31 * result + angle.hashCode()
        return result
    }

    override fun toString() = "Line(${cos(angle)} * y = ${sin(angle)} * x + $b)"
}

/**
 * Средняя
 *
 * Построить прямую по отрезку
 */
fun correctAngle(angle: Double): Double {
    var corAngle = angle % PI
    if (corAngle < 0) corAngle += PI
    return if (corAngle == PI) 0.0 else corAngle
}

fun lineBySegment(s: Segment): Line {
    val yLength = s.begin.y - s.end.y
    val xLength = s.begin.x - s.end.x
    val angle = correctAngle(atan(yLength / xLength))
    return Line(s.begin, angle)
}

/**
 * Средняя
 *
 * Построить прямую по двум точкам
 */
fun lineByPoints(a: Point, b: Point): Line = lineBySegment(Segment(a, b))


/**
 * Сложная
 *
 * Построить серединный перпендикуляр по отрезку или по двум точкам
 */
fun bisectorByPoints(a: Point, b: Point): Line {
    val angle = if (a.y == b.y) PI / 2.0
    else correctAngle(atan((b.x - a.x) / (a.y - b.y)))
    return Line(a.center(b), angle)
}

/**
 * Средняя
 *
 * Задан список из n окружностей на плоскости. Найти пару наименее удалённых из них.
 * Если в списке менее двух окружностей, бросить IllegalArgumentException
 */
fun findNearestCirclePair(vararg circles: Circle): Pair<Circle, Circle> {
    if (circles.size < 2) throw IllegalArgumentException()
    var distance = POSITIVE_INFINITY
    var nearest = circles[0] to circles[1]
    for (i1 in 0 until circles.size) {
        for (i2 in i1 + 1 until circles.size) {
            val c1 = circles[i1]
            val c2 = circles[i2]
            val curDistance = c1.distance(c2)
            if (curDistance < distance) {
                nearest = c1 to c2
                distance = curDistance
            }
        }
    }
    return nearest
}

/**
 * Сложная
 *
 * Дано три различные точки. Построить окружность, проходящую через них
 * (все три точки должны лежать НА, а не ВНУТРИ, окружности).
 * Описание алгоритмов см. в Интернете
 * (построить окружность по трём точкам, или
 * построить окружность, описанную вокруг треугольника - эквивалентная задача).
 */
fun circleByThreePoints(a: Point, b: Point, c: Point): Circle {
    val center = bisectorByPoints(a, b).crossPoint(bisectorByPoints(b, c))
    val radius = a.distance(center)
    return Circle(center, radius)
}

/**
 * Очень сложная
 *
 * Дано множество точек на плоскости. Найти круг минимального радиуса,
 * содержащий все эти точки. Если множество пустое, бросить IllegalArgumentException.
 * Если множество содержит одну точку, вернуть круг нулевого радиуса с центром в данной точке.
 *
 * Примечание: в зависимости от ситуации, такая окружность может либо проходить через какие-либо
 * три точки данного множества, либо иметь своим диаметром отрезок,
 * соединяющий две самые удалённые точки в данном множестве.
 */
fun minContainingCircle(vararg points: Point): Circle = TODO()

fun comission(examResults: String, treshold: Int, required: List<String>): List<String> {
    val pass = mutableListOf<String>()
    var allExams: Boolean
    var sum: Int
    var temp: List<String>
    var name: String
    var marks: MutableMap<String, Int>
    var examMark: List<String>
    for (str in examResults.split("\n")) {
        temp = Regex(".* *- *.*").split(str)
        if (temp.size != 2) throw IllegalArgumentException("format incorrect: $str")
        marks = mutableMapOf() //обнуляем карту экзамен-оценка и сумму баллов
        sum = 0
        name = temp[0]
        if (!name.matches(Regex("\\w* \\w*")))
            throw IllegalArgumentException("name incorrect: $name")
        examMark = temp[1].split(Regex(","))
        for (pair in examMark) {
            if (!pair.matches(Regex("\\w* *100|\\d\\d|\\d")))
                throw IllegalArgumentException("exam pair incorrect $pair")
            marks[pair.split(Regex(" *"))[0]] = pair.split(Regex(" *"))[1].toInt()
        }
        allExams = true
        for (exam in required) { //считаем сумму баллов по нужным экзаменам
            if (marks[exam] != null) sum += marks[exam]!!
            else {
                allExams = false
                break
            }
        }
        if (sum >= treshold && allExams) pass.add(name)
    }
    return pass
}