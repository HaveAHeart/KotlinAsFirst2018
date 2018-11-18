@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence", "NAME_SHADOWING", "UNUSED_EXPRESSION")

package lesson6.task1

import lesson5.task1.canBuildFrom
import java.lang.NullPointerException

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main(args: Array<String>) {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
fun dateStrToDigit(str: String): String {
    val str = str.split(" ")
    val months = listOf("января", "ферваля", "марта", "апреля", "мая",
            "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
    return if ((str.size != 3 || str[1] !in months) ||
            (str[1] in "январямартамаяиюляавгустаоктябрядекабря" && str[0].toInt() !in 1..31) ||
            (str[1] in "апреляиюнясентябряноября" && str[0].toInt() !in 1..30) ||
            (str[1] == "февраля" &&
                    (str[2].toInt() % 400 == 0 ||
                            (str[2].toInt() % 4 == 0 && str[2].toInt() % 100 != 0)) &&
                    str[0].toInt() !in 1..29) ||
            (str[1] == "февраля" && str[0].toInt() !in 1..28)

    ) ""
    else twoDigitStr(str[0].toInt()) + "." + twoDigitStr(months.indexOf(str[1]) + 1) + "." + str[2]
}

/**
 * Средняя
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */
fun dateDigitToStr(digital: String): String {
    val dig = mutableListOf<Int>()
    for (i in digital.split(".")) {
        try {
            dig.add(i.toInt())
        } catch (e: NumberFormatException) {
            return ""
        }
    }
    val months = listOf("января", "ферваля", "марта", "апреля", "мая",
            "июня", "июля", "августа", "сентября", "октября", "ноября", "декабря")
    return if ((dig.size != 3 || dig[1] !in 1..12) ||
            (dig[1] in listOf(1, 3, 5, 7, 8, 10, 12) && dig[0] !in 1..31) ||
            (dig[1] in listOf(4, 6, 9, 11) && dig[0] !in 1..30) ||
            (dig[1] == 2 &&
                    (dig[2] % 400 == 0 ||
                            (dig[2] % 4 == 0 && dig[2] % 100 != 0)) &&
                    dig[0] !in 1..29) ||
            (dig[1] == 2 && dig[0] !in 1..28)
    ) ""
    else dig[0].toString() + " " + months[dig[1] - 1] + " " + dig[2]
}

/**
 * Средняя
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -98 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку
 */
fun flattenPhoneNumber(phone: String): String {
    val phone = phone.replace(" ", "").replace("-", "")
    if ((phone.count { it == '(' } > 1 || phone.count { it == ')' } > 1 ||
                    (phone.count { it == '(' } != phone.count { it == ')' })) ||
            (phone.count { it == '+' } > 1) ||
            ((phone.count { it == '+' } == 1) && phone[0] != '+')) return ""
    val sb = StringBuilder()
    var isOpen = false
    for (i in phone) {
        if (i == '+' && sb.isEmpty()) sb.append(i)
        else if (i == '(') {
            isOpen = true
            continue
        } else if (i == ')' && !isOpen &&
                (phone.indexOf(')') - phone.indexOf('(') == 1)) return ""
        else if (i == ')' && isOpen) continue
        else {
            try {
                i.toString().toInt() //ну, я пытался :c
            } catch (e: NumberFormatException) {
                return ""
            }
            sb.append(i)
        }
    }
    return sb.toString()
}

/**
 * Средняя
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int = try {
    jumps.split(" ").onEach { it ->
        if (it == "-" || it == "%") ""
        else it.toInt()
    }.max()!!.toInt()
} catch (e: NumberFormatException) {
    -1
}


/**
 * Сложная
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    val jumps = jumps.split(" ")
    if (jumps.size % 2 != 0) return -1
    for (i in jumps.size - 1 downTo 0 step 2) {
        if (!canBuildFrom(listOf('+', '%', '-'), jumps[i])) return -1
        if ('+' in jumps[i]) {
            return try {
                jumps[i - 1].toInt()
            } catch (e: NumberFormatException) {
                -1
            }
        }
    }
    return -1
}

/**
 * Сложная
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expr: String): Int {
    val expr = expr.split(" ")
    if (expr.size % 2 != 1) throw IllegalArgumentException()
    for (i in 0 until expr.size) {
        if (i % 2 == 0) {
            if (!canBuildFrom(listOf('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'), expr[i]))
                throw IllegalArgumentException()

        } else {
            if (expr[i] != "+" && expr[i] != "-") throw IllegalArgumentException()
        }
    }
    var answ = 0
    for (i in 0 until expr.size step 2) {
        when {
            i == 0 -> answ += expr[i].toInt()
            expr[i - 1] == "-" -> answ -= expr[i].toInt()
            else -> answ += expr[i].toInt()
        }
    }
    return answ
}

/**
 * Сложная
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val words = str.split(" ")
    var index = -1
    for (i in 1 until words.size) {
        if (words[i].toLowerCase() == words[i - 1].toLowerCase()) index = i - 1
    }
    return when (index) {
        -1 -> -1
        0 -> 0
        else -> {
            var length = 0
            for (i in 0 until index) {
                length += words[i].length
            }
            length + index
        }
    }
}

/**
 * Сложная
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше либо равны нуля.
 */
fun mostExpensive(description: String): String {
    val description = description.split("; ")
    val pairs: MutableList<Pair<String, Double>> = mutableListOf()
    for (i in description) {
        if (' ' !in i) return ""
        try {
            pairs.add(Pair(i.split(" ")[0], i.split(" ")[1].toDouble()))
        } catch (e: NumberFormatException) {
            return ""
        }
    }
    return try {
        pairs.maxBy { it.second }!!.first
    } catch (e: NullPointerException) {
        ""
    }
}

/**
 * Сложная
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if (!canBuildFrom(listOf('I', 'V', 'X', 'L', 'C', 'D', 'M'), roman)) return -1
    val roman = roman.toMutableList()
    val map = mapOf(
            Pair("I", 1), Pair("IV", 4), Pair("V", 5), Pair("IX", 9), Pair("X", 10),
            Pair("XL", 40), Pair("L", 50), Pair("XC", 90), Pair("C", 100), Pair("CD", 400),
            Pair("D", 500), Pair("CM", 900), Pair("M", 1000))
    if (roman.size == 1) return map[roman[0].toString()] ?: -1
    val romNums = "IVXLCDM"
    var answ = 0
    var currChar = 0
    while (currChar < roman.size) {
        if (roman[currChar] !in romNums) return -1
        if (currChar == roman.size - 1) {
            answ += map[roman[currChar].toString()] ?: return -1
            break
        }
        if (roman[currChar + 1] !in romNums) return -1
        if (romNums.indexOf(roman[currChar]) < romNums.indexOf(roman[currChar + 1])) {
            answ += map[roman[currChar].toString() + roman[currChar + 1].toString()] ?: return -1
            currChar += 2
            continue
        }
        answ += map[roman[currChar].toString()] ?: return -1
        currChar += 1
    }
    return answ
}

/**
 * Очень сложная
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun getLoopBody(loopStart: Int, commands: String): String {
    var openCount = 0
    var closeCount = 0
    val loopBody = StringBuilder()
    for (i in loopStart until commands.length) {
        if (commands[i] == '[') openCount++
        else if (commands[i] == ']') closeCount++
        loopBody.append(commands[i])
        if (openCount == closeCount) break
    }
    return loopBody.deleteCharAt(0).deleteCharAt(loopBody.lastIndex).toString()
}
fun process(el: MutableList<Int>, commands: String, curPos: Int, limit: Int): Pair<MutableList<Int>, Pair<Int, Int>> {
    var el = el
    var limit = limit
    var curPos = curPos
    var i = 0
    while (i < commands.length) {
        if (limit <= 0) break
        when {
            commands[i] == '+' -> {
                limit--
                el[curPos]++
            }
            commands[i] == '-' -> {
                limit--
                el[curPos]--
            }
            commands[i] == '>' -> {
                limit--
                if (curPos == el.size - 1) throw IllegalStateException()
                else curPos++
            }
            commands[i] == '<' -> {
                limit--
                if (curPos == 0) throw IllegalStateException()
                else curPos--
            }
            commands[i] == '[' -> {
                limit--
                val loopBody = getLoopBody(i, commands)
                println(loopBody)
                println(curPos)
                val loopRes = looping(el, loopBody, curPos, limit)
                el = loopRes.first
                curPos = loopRes.second.first
                limit = loopRes.second.second
                i += loopBody.length
            }
            commands[i] == ' ' -> limit--
            commands[i] == ']' -> {/*ничего, limit учтён в looping*/}
            else -> throw IllegalArgumentException()
        }
        if (limit <= 0) break
        i++
    }
    return Pair(el, Pair(curPos, limit))
}
fun looping(el: MutableList<Int>, loopBody: String, curPos: Int, limit: Int): Pair<MutableList<Int>, Pair<Int, Int>> {
    var el = el
    var curPos = curPos
    var limit = limit
    while (el[curPos] != 0 && limit > 0) {
        if (limit <= 0) break
        val temp = process(el, loopBody, curPos, limit)
        println(temp)
        el = temp.first
        curPos = temp.second.first
        limit = temp.second.second
        limit--
    }
    return Pair(el, Pair(curPos, limit))
}

fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> {
    // проверка введённых данных на корректность
    var openCount = 0
    var closeCount = 0
    for (i in commands) {
        if (i == '[') openCount += 1
        if (i == ']') closeCount += 1
        if (i !in "+-><[] " || closeCount > openCount) throw IllegalArgumentException()
    }
    if (openCount != closeCount) throw IllegalArgumentException()
    // задание счётчика действий, положения, списка состояния элементов
    val limit = limit
    val curPos = cells / 2
    var el = mutableListOf<Int>()
    // работа с данными
    for (i in 0 until cells) el.add(0)
    el = process(el, commands, curPos, limit).first
    return el
}