@file:Suppress("UNUSED_PARAMETER")

package lesson3.task1

import kotlinx.html.InputType
import kotlin.math.*

/**
 * Пример
 *
 * Вычисление факториала
 */
fun factorial(n: Int): Double {
    var result = 1.0
    for (i in 1..n) {
        result = result * i // Please do not fix in master
    }
    return result
}

/**
 * Пример
 *
 * Проверка числа на простоту -- результат true, если число простое
 */
fun isPrime(n: Int): Boolean {
    if (n < 2) return false
    if (n == 2) return true
    if (n % 2 == 0) return false
    for (m in 3..sqrt(n.toDouble()).toInt() step 2) {
        if (n % m == 0) return false
    }
    return true
}

/**
 * Пример
 *
 * Проверка числа на совершенность -- результат true, если число совершенное
 */
fun isPerfect(n: Int): Boolean {
    var sum = 1
    for (m in 2..n / 2) {
        if (n % m > 0) continue
        sum += m
        if (sum > n) break
    }
    return sum == n
}

/**
 * Пример
 *
 * Найти число вхождений цифры m в число n
 */
fun digitCountInNumber(n: Int, m: Int): Int =
        when {
            n == m -> 1
            n < 10 -> 0
            else -> digitCountInNumber(n / 10, m) + digitCountInNumber(n % 10, m)
        }

/**
 * Тривиальная
 *
 * Найти количество цифр в заданном числе n.
 * Например, число 1 содержит 1 цифру, 456 -- 3 цифры, 65536 -- 5 цифр.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun digitNumber(n: Int): Int {
    var n = abs(n)
    var count = 0
    do {
        count += 1
        n /= 10
    } while (n > 0)
    return count
}

/**
 * Простая
 *
 * Найти число Фибоначчи из ряда 1, 1, 2, 3, 5, 8, 13, 21, ... с номером n.
 * Ряд Фибоначчи определён следующим образом: fib(1) = 1, fib(2) = 1, fib(n+2) = fib(n) + fib(n+1)
 */
fun fib(n: Int): Int = if (n == 1 || n == 2) 1
    else {
        var fib = 1
        var fib2 = 1
        var answer = 0
        for (i in 3..n) {
            answer = fib + fib2
            fib = fib2
            fib2 = answer
        }
        answer
    }

/**
 * Простая
 *
 * Для заданных чисел m и n найти наименьшее общее кратное, то есть,
 * минимальное число k, которое делится и на m и на n без остатка
 */
fun lcm(m: Int, n: Int): Int = if (m == n) m
    else {
        var num1 = m
        var num2 = n
        while (num1 != num2) {
            if (num1 > num2) num1 -= num2
            else num2 -= num1
        }
        m * (n / num1)
    }

/**
 * Простая
 *
 * Для заданного числа n > 1 найти минимальный делитель, превышающий 1
 */
fun minDivisor(n: Int): Int {
    val rootn = sqrt(n.toDouble()).toInt()
    for (i in 2..rootn) {
        if (n % i == 0) return i
    }
    return n
}

/**
 * Простая
 *
 * Для заданного числа n > 1 найти максимальный делитель, меньший n
 */
fun maxDivisor(n: Int): Int = n / minDivisor(n)

/**
 * Простая
 *
 * Определить, являются ли два заданных числа m и n взаимно простыми.
 * Взаимно простые числа не имеют общих делителей, кроме 1.
 * Например, 25 и 49 взаимно простые, а 6 и 8 -- нет.
 */
fun isCoPrime(m: Int, n: Int): Boolean {
    if (m % n == 0 || n % m == 0) return (m == 1 || n == 1)
    else {
        val lower = min(m, n)
        val root = sqrt(lower.toDouble()).toInt()
        for (i in 2..root) {
            if (m % i == 0 && n % i == 0) return false
        }
        return true
    }
}

/**
 * Простая
 *
 * Для заданных чисел m и n, m <= n, определить, имеется ли хотя бы один точный квадрат между m и n,
 * то есть, существует ли такое целое k, что m <= k*k <= n.
 * Например, для интервала 21..28 21 <= 5*5 <= 28, а для интервала 51..61 квадрата не существует.
 */
fun squareBetweenExists(m: Int, n: Int): Boolean {
    if ((sqrt(m.toDouble()) % 1.0) == 0.0 ||
            (sqrt(n.toDouble()) % 1.0) == 0.0) return true
    val rootm = sqrt(m.toDouble()).toInt()
    val rootn = sqrt(n.toDouble()).toInt()
    return rootm != rootn
}

/**
 * Средняя
 *
 * Гипотеза Коллатца. Рекуррентная последовательность чисел задана следующим образом:
 *
 *   ЕСЛИ (X четное)
 *     Xслед = X /2
 *   ИНАЧЕ
 *     Xслед = 3 * X + 1
 *
 * например
 *   15 46 23 70 35 106 53 160 80 40 20 10 5 16 8 4 2 1 4 2 1 4 2 1 ...
 * Данная последовательность рано или поздно встречает X == 1.
 * Написать функцию, которая находит, сколько шагов требуется для
 * этого для какого-либо начального X > 0.
 */
fun collatzSteps(x: Int): Int {
    var x = x
    var i = 0
    while (x != 1) {
        i += 1
        if (x % 2 == 0) x /= 2
        else x = 3 * x + 1
    }
    return i
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * sin(x) = x - x^3 / 3! + x^5 / 5! - x^7 / 7! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun sin(x: Double, eps: Double): Double {
    val x = x % (2 * PI)
    var count = 2
    var answ = x
    var member = Math.pow(x, 3.0) / factorial(3)
    while (abs(member) >= eps) {
        if (count % 2 == 0) answ -= member
        else answ += member
        count += 1
        member = member * Math.pow(x, 2.0) / ((2 * count) - 1) / ((2 * count) - 2)
    }
    return answ
}

/**
 * Средняя
 *
 * Для заданного x рассчитать с заданной точностью eps
 * cos(x) = 1 - x^2 / 2! + x^4 / 4! - x^6 / 6! + ...
 * Нужную точность считать достигнутой, если очередной член ряда меньше eps по модулю
 */
fun cos(x: Double, eps: Double): Double {
    val x = x % (2 * PI)
    var count = 2
    var answ = 1.0
    var member = Math.pow(x, 2.0) / 2
    while (abs(member) >= eps) {
        if (count % 2 == 0) answ -= member
        else answ += member
        member = member * Math.pow(x, 2.0) / ((2 * count) - 1) / (2 * count)
        count += 1
    }
    return answ
}

/**
 * Средняя
 *
 * Поменять порядок цифр заданного числа n на обратный: 13478 -> 87431.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun revert(n: Int): Int {
    var n = n
    var answ = 0
    while (n > 0) {
        answ = (answ * 10) + (n % 10)
        n /= 10
    }
    return answ
}

/**
 * Средняя
 *
 * Проверить, является ли заданное число n палиндромом:
 * первая цифра равна последней, вторая -- предпоследней и так далее.
 * 15751 -- палиндром, 3653 -- нет.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun isPalindrome(n: Int): Boolean = revert(n) == n

/**
 * Средняя
 *
 * Для заданного числа n определить, содержит ли оно различающиеся цифры.
 * Например, 54 и 323 состоят из разных цифр, а 111 и 0 из одинаковых.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun hasDifferentDigits(n: Int): Boolean {
    var n = n
    val temp = n % 10
    while (n > 0) {
        if (temp != (n % 10)) return true
        n /= 10
    }
    return false
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из квадратов целых чисел:
 * 149162536496481100121144...
 * Например, 2-я цифра равна 4, 7-я 5, 12-я 6.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */
fun numberToList(n: Int): List<Int> {
    var n = n
    val digitsArr = mutableListOf<Int>()
    while (n > 0) {
        digitsArr.add(n % 10)
        n /= 10
    }
    return digitsArr
}
fun squareSequenceDigit(n: Int): Int {
    var length = 0
    var i = 0
    var sqr: Int
    while (true) {
        i += 1
        sqr = i * i
        if (length + digitNumber(sqr) >= n) {
            val digitsArr = numberToList(sqr)
            return digitsArr[digitsArr.size - (n - length)]
        }
        length += digitNumber(sqr)
    }
}

/**
 * Сложная
 *
 * Найти n-ю цифру последовательности из чисел Фибоначчи (см. функцию fib выше):
 * 1123581321345589144...
 * Например, 2-я цифра равна 1, 9-я 2, 14-я 5.
 *
 * Использовать операции со строками в этой задаче запрещается.
 */

fun fibSequenceDigit(n: Int): Int {
    if (n == 1 || n == 2) return 1
    else {
        var fib1 = 1
        var fib2 = 1
        var fib3: Int
        var length = 2
        while (true) {
            fib3 = fib1 + fib2
            if (length + digitNumber(fib3) >= n) {
                val digitsArr = numberToList(fib3)
                return digitsArr[digitsArr.size - (n - length)]
            }
            length += digitNumber(fib3)
            fib1 = fib2
            fib2 = fib3
        }
    }
}
