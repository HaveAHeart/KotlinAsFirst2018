@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson4.task1

import lesson1.task1.discriminant
import lesson3.task1.numberToList
import kotlin.math.ceil
import kotlin.math.sqrt

/**
 * Пример
 *
 * Найти все корни уравнения x^2 = y
 */
fun sqRoots(y: Double) =
        when {
            y < 0 -> listOf()
            y == 0.0 -> listOf(0.0)
            else -> {
                val root = sqrt(y)
                // Результат!
                listOf(-root, root)
            }
        }

/**
 * Пример
 *
 * Найти все корни биквадратного уравнения ax^4 + bx^2 + c = 0.
 * Вернуть список корней (пустой, если корней нет)
 */
fun biRoots(a: Double, b: Double, c: Double): List<Double> {
    if (a == 0.0) {
        return if (b == 0.0) listOf()
        else sqRoots(-c / b)
    }
    val d = discriminant(a, b, c)
    if (d < 0.0) return listOf()
    if (d == 0.0) return sqRoots(-b / (2 * a))
    val y1 = (-b + sqrt(d)) / (2 * a)
    val y2 = (-b - sqrt(d)) / (2 * a)
    return sqRoots(y1) + sqRoots(y2)
}

/**
 * Пример
 *
 * Выделить в список отрицательные элементы из заданного списка
 */
fun negativeList(list: List<Int>): List<Int> {
    val result = mutableListOf<Int>()
    for (element in list) {
        if (element < 0) {
            result.add(element)
        }
    }
    return result
}

/**
 * Пример
 *
 * Изменить знак для всех положительных элементов списка
 */
fun invertPositives(list: MutableList<Int>) {
    for (i in 0 until list.size) {
        val element = list[i]
        if (element > 0) {
            list[i] = -element
        }
    }
}

/**
 * Пример
 *
 * Из имеющегося списка целых чисел, сформировать список их квадратов
 */
fun squares(list: List<Int>) = list.map { it * it }

/**
 * Пример
 *
 * Из имеющихся целых чисел, заданного через vararg-параметр, сформировать массив их квадратов
 */
fun squares(vararg array: Int) = squares(array.toList()).toTypedArray()

/**
 * Пример
 *
 * По заданной строке str определить, является ли она палиндромом.
 * В палиндроме первый символ должен быть равен последнему, второй предпоследнему и т.д.
 * Одни и те же буквы в разном регистре следует считать равными с точки зрения данной задачи.
 * Пробелы не следует принимать во внимание при сравнении символов, например, строка
 * "А роза упала на лапу Азора" является палиндромом.
 */
fun isPalindrome(str: String): Boolean {
    val lowerCase = str.toLowerCase().filter { it != ' ' }
    for (i in 0..lowerCase.length / 2) {
        if (lowerCase[i] != lowerCase[lowerCase.length - i - 1]) return false
    }
    return true
}

/**
 * Пример
 *
 * По имеющемуся списку целых чисел, например [3, 6, 5, 4, 9], построить строку с примером их суммирования:
 * 3 + 6 + 5 + 4 + 9 = 27 в данном случае.
 */
fun buildSumExample(list: List<Int>) = list.joinToString(separator = " + ", postfix = " = ${list.sum()}")

/**
 * Простая
 *
 * Найти модуль заданного вектора, представленного в виде списка v,
 * по формуле abs = sqrt(a1^2 + a2^2 + ... + aN^2).
 * Модуль пустого вектора считать равным 0.0.
 */
fun abs(v: List<Double>): Double = sqrt(v.fold(0.0) { prev, curr ->
    prev + Math.pow(curr, 2.0)
})

/**
 * Простая
 *
 * Рассчитать среднее арифметическое элементов списка list. Вернуть 0.0, если список пуст
 */
fun mean(list: List<Double>): Double = if (list.isEmpty()) 0.0
else list.sum() / list.size

/**
 * Средняя
 *
 * Центрировать заданный список list, уменьшив каждый элемент на среднее арифметическое всех элементов.
 * Если список пуст, не делать ничего. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun center(list: MutableList<Double>): MutableList<Double> {
    var mean = mean(list)
    for (i in 0 until list.size) list[i] -= mean
    return list
}

/**
 * Средняя
 *
 * Найти скалярное произведение двух векторов равной размерности,
 * представленные в виде списков a и b. Скалярное произведение считать по формуле:
 * C = a1b1 + a2b2 + ... + aNbN. Произведение пустых векторов считать равным 0.0.
 */
fun times(a: List<Double>, b: List<Double>): Double = a.zip(b).fold(0.0) { prev, curr ->
    prev + (curr.toList()[0] * curr.toList()[1])
}

/**
 * Средняя
 *
 * Рассчитать значение многочлена при заданном x:
 * p(x) = p0 + p1*x + p2*x^2 + p3*x^3 + ... + pN*x^N.
 * Коэффициенты многочлена заданы списком p: (p0, p1, p2, p3, ..., pN).
 * Значение пустого многочлена равно 0.0 при любом x.
 */
fun polynom(p: List<Double>, x: Double): Double {
    var p = p.toMutableList()
    for ((i, value) in p.withIndex()) {
        p[i] = value * Math.pow(x, i.toDouble())
    }
    return p.fold(0.0) { prev, curr ->
        prev + curr
    }
}

/**
 * Средняя
 *
 * В заданном списке list каждый элемент, кроме первого, заменить
 * суммой данного элемента и всех предыдущих.
 * Например: 1, 2, 3, 4 -> 1, 3, 6, 10.
 * Пустой список не следует изменять. Вернуть изменённый список.
 *
 * Обратите внимание, что данная функция должна изменять содержание списка list, а не его копии.
 */
fun accumulate(list: MutableList<Double>): MutableList<Double> {
    var sum = 0.0
    for (i in 0 until list.size) {
        sum += list[i]
        list[i] = sum
    }
    return list
}

/**
 * Средняя
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде списка множителей, например 75 -> (3, 5, 5).
 * Множители в списке должны располагаться по возрастанию.
 */
fun factorize(n: Int): List<Int> {
    var n = n
    var list = mutableListOf<Int>()
    while (n > 1) {
        for (i in 2..n) {
            if (n % i == 0) {
                list.add(i)
                n /= i
                break
            }
        }
    }
    return list
}

/**
 * Сложная
 *
 * Разложить заданное натуральное число n > 1 на простые множители.
 * Результат разложения вернуть в виде строки, например 75 -> 3*5*5
 * Множители в результирующей строке должны располагаться по возрастанию.
 */
fun factorizeToString(n: Int): String = factorize(n).joinToString(separator = "*")

/**
 * Средняя
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием base > 1.
 * Результат перевода вернуть в виде списка цифр в base-ичной системе от старшей к младшей,
 * например: n = 100, base = 4 -> (1, 2, 1, 0) или n = 250, base = 14 -> (1, 3, 12)
 */
fun convert(n: Int, base: Int): List<Int> {
    var n = n
    var list = mutableListOf<Int>()
    while (n > 0) {
        list.add(0, n % base)
        n = (n - (n % base)) / base
    }
    return if (list.isEmpty()) listOf(0)
    else list
}

/**
 * Сложная
 *
 * Перевести заданное целое число n >= 0 в систему счисления с основанием 1 < base < 37.
 * Результат перевода вернуть в виде строки, цифры более 9 представлять латинскими
 * строчными буквами: 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: n = 100, base = 4 -> 1210, n = 250, base = 14 -> 13c
 */
fun convertToString(n: Int, base: Int): String {
    var sb = StringBuilder()
    val alphabet = "abcdefghijklmnopqrstuvwxyz"
    var list = convert(n, base)
    for (i in list) {
        sb.append(if (i >= 10) alphabet[i - 10]
        else i)
    }
    return if (sb.isBlank()) "0"
    else sb.toString()
}

/**
 * Средняя
 *
 * Перевести число, представленное списком цифр digits от старшей к младшей,
 * из системы счисления с основанием base в десятичную.
 * Например: digits = (1, 3, 12), base = 14 -> 250
 */
fun decimal(digits: List<Int>, base: Int): Int {
    var answ = 0
    val digits = digits.reversed()
    for (i in 0 until digits.size) {
        answ += digits[i] * Math.pow(base.toDouble(), i.toDouble()).toInt()
    }
    return answ
}

/**
 * Сложная
 *
 * Перевести число, представленное цифровой строкой str,
 * из системы счисления с основанием base в десятичную.
 * Цифры более 9 представляются латинскими строчными буквами:
 * 10 -> a, 11 -> b, 12 -> c и так далее.
 * Например: str = "13c", base = 14 -> 250
 */
fun decimalFromString(str: String, base: Int): Int {
    val fullAlphabet = "0123456789abcdefghijklmnopqrstuvwxyz"
    var answ = 0
    var currNum = 0
    val str = str.reversed()
    for (i in 0 until str.length) {
        currNum = fullAlphabet.indexOf(str[i])
        answ += currNum * Math.pow(base.toDouble(), i.toDouble()).toInt()
    }
    return answ
}

/**
 * Сложная
 *
 * Перевести натуральное число n > 0 в римскую систему.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: 23 = XXIII, 44 = XLIV, 100 = C
 */
fun roman(n: Int): String {
    var n = n
    val listNum = listOf(1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1)
    val listStr = listOf("M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I")
    var answ = ""
    while (n > 0) {
        for (i in 0 until listNum.size) {
            if (n >= listNum[i]) {
                answ += listStr[i]
                n -= listNum[i]
                break
            }
        }
    }
    return answ
}

/**
 * Очень сложная
 *
 * Записать заданное натуральное число 1..999999 прописью по-русски.
 * Например, 375 = "триста семьдесят пять",
 * 23964 = "двадцать три тысячи девятьсот шестьдесят четыре"
 */
fun threeDigsToStr(n: Int, thousands: Boolean): String {
    var n = n
    var sb = StringBuilder()
    val listDigits = listOf("", "один", "два", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")
    val listDigitsEnd = listOf("", "одна", "две", "три", "четыре", "пять", "шесть", "семь", "восемь", "девять")
    val listSpecial = listOf("", "одиннадцать", "двенадцать", "тринадцать", "четырнадцать",
            "пятнадцать", "шестнадцать", "семнадцать", "восемнадцать", "девятнадцать")
    val listTenth = listOf("", "десять", "двадцать", "тридцать", "сорок",
            "пятьдесят", "шестьдесят", "семьдесят", "восемьдесят", "девяносто")
    val listHundreds = listOf("", "сто", "двести", "триста", "четыреста",
            "пятьсот", "шестьсот", "семьсот", "восемьсот", "девятьсот")
    val listThousands = listOf("тысяча", "тысячи", "тысяч")
    sb.append(listHundreds[n / 100])                                                       //разбираемся с сотнями
    n %= 100
    if (thousands) {
        when (n) {                                                                        //разбираемся с десятками
            0 -> return sb.append(" " + listThousands[2]).toString()
            in 11..19 -> return sb.append(
                    if (sb.isBlank()) listSpecial[n % 10] + " " + listThousands[2]                    //отсекаем 11-19
                    else " " + listSpecial[n % 10] + " " + listThousands[2]).toString()
        }
        if (n > 9) {
            sb.append(if (sb.isBlank()) listTenth[n / 10]
            else " " + listTenth[n / 10])
            n %= 10
        }
        return if (sb.isBlank()) {                                                       //разбираемся с единицами
            when (n) {
                0 -> listThousands[2]
                1 -> listDigitsEnd[n] + " " + listThousands[0]
                in 2..4 -> listDigitsEnd[n] + " " + listThousands[1]
                else -> listDigitsEnd[n] + " " + listThousands[2]
            }
        } else {
            when (n) {
                0 -> sb.append(" ", listThousands[2]).toString()
                1 -> sb.append(" ", listDigitsEnd[n], " ", listThousands[0]).toString()
                in 2..4 -> sb.append(" ", listDigitsEnd[n], " ", listThousands[1]).toString()
                else -> sb.append(" ", listDigitsEnd[n], " ", listThousands[2]).toString()
            }
        }
    } else {
        when (n) {                                                                        //разбираемся с десятками
            0 -> return sb.toString()
            in 11..19 -> return sb.append(if (sb.isBlank()) listSpecial[n % 10]
            else " " + listSpecial[n % 10]).toString()                                    //отсекаем 11-19
        }
        if (n > 9) {
            sb.append(if (sb.isBlank()) listTenth[n / 10]
            else " " + listTenth[n / 10])
            n %= 10
        }
        return if (sb.isBlank()) {                                                      //разбираемся с единицами
            listDigits[n]
        } else {
            sb.append(" ", listDigits[n]).toString()
        }
    }
}

fun russian(n: Int): String {
    var n = n
    return if (n < 1000) threeDigsToStr(n, false)
    else {
        val lesserThree = n % 1000
        val greaterThree = n / 1000
        val lesserStr = threeDigsToStr(lesserThree, false)
        val greaterStr = threeDigsToStr(greaterThree, true)
        if (lesserStr != "" && greaterStr != "") "$greaterStr $lesserStr".trim()
        else "$greaterStr$lesserStr".trim()

    }
}