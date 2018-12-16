@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence", "NAME_SHADOWING")

package lesson7.task1

import java.io.File
import kotlin.math.min

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    var currentLineLength = 0
    for (line in File(inputName).readLines()) {
        if (line.isEmpty()) {
            outputStream.newLine()
            if (currentLineLength > 0) {
                outputStream.newLine()
                currentLineLength = 0
            }
            continue
        }
        for (word in line.split(" ")) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    outputStream.newLine()
                    currentLineLength = 0
                } else {
                    outputStream.write(" ")
                    currentLineLength++
                }
            }
            outputStream.write(word)
            currentLineLength += word.length
        }
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val sb = StringBuilder()
    for (i in File(inputName).readLines()) sb.append(i.toLowerCase() + "\n")
    val text = sb.toString()
    val answ = mutableMapOf<String, Int>()
    for (i in substrings) {
        answ[i] = 0
        for (c in Regex(i.toLowerCase()).findAll(text, 0)) {
            answ[i] = answ[i]!! + 1
        }
    }
    return answ

}


/**
 * Средняя
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    val fixing = mapOf(Pair('ы', 'и'), Pair('Ы', 'И'), Pair('я', 'а'),
            Pair('Я', 'А'), Pair('ю', 'у'), Pair('Ю', 'У'))
    for (str in File(inputName).readLines()) {
        val out = StringBuilder()
        for (i in str.split(" ")) {
            if (i.toLowerCase() in listOf("жюри", "брошюра", "парашют")) {
                out.append("$i ")
                continue
            } else {
                if (i.isEmpty()) {
                    out.append(' ')
                    continue
                }
                out.append(i[0])
                for (iter in 1 until i.length) {
                    if (i[iter - 1] in "ЖЧШЩжчшщ" && i[iter] in fixing.keys)
                        out.append(fixing[i[iter]])
                    else out.append(i[iter])
                }
                out.append(' ')
            }
        }
        outputStream.write(out.deleteCharAt(out.length - 1).toString())
        outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    var maxLen = 0
    for (i in File(inputName).readLines()) {
        maxLen = maxOf(maxLen, i.trim().length)
    }
    val outputStream = File(outputName).bufferedWriter()
    for (i in File(inputName).readLines()) {
        val out = StringBuilder()
        for (c in 0 until (maxLen - i.trim().length) / 2) out.append(' ')
        out.append(i.trim())
        outputStream.write(out.toString())
        outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Сложная
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val input = File(inputName).readLines().toMutableList()
    var maxLen = 0
    for (i in 0 until input.size) {
        input[i] = input[i].trim()
        Regex(".* *.*").replace(input[i], " ")
        maxLen = maxOf(maxLen, input[i].length)
    }
    val outputStream = File(outputName).bufferedWriter()
    for (i in input) {
        if (i.isEmpty()) {
            outputStream.newLine()
            continue
        }
        val addLen = maxLen - i.length
        val i = i.split(" ")
        val out = StringBuilder()
        out.append(i[0])
        for (c in 1 until i.size) {
            out.append(" ") //потерянный при split'е пробел
            for (count in 0 until (addLen / (i.size - 1))) out.append(" ")
            if (c <= addLen % (i.size - 1)) out.append(" ")
            out.append(i[c])
        }
        outputStream.write(out.toString())
        outputStream.newLine()
    }
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> {
    val dict = mutableMapOf<String, Int>()
    val wordRegex = Regex("[А-ЯЁа-яёA-Za-z][А-ЯЁа-яёA-Za-z]*")
    for (str in File(inputName).readLines()) {
        for (match in wordRegex.findAll(str, 0)) {
            val currKey = match.value.toLowerCase()
            dict[currKey] = dict.getOrDefault(currKey, 0) + 1
        }
    }
    if (dict.keys.size == 0) return emptyMap()
    val answ = mutableMapOf<String, Int>()
    for (i in 0 until min(20, dict.keys.size)) {
        val max = dict.maxBy { it.value }!!
        answ[max.key] = max.value
        dict.remove(max.key)
    }
    return answ
}

/**
 * Средняя
 *
 * Реализовать транслитерацию текста из входного файла в выходной файл посредством динамически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    val dict = mutableMapOf<Char, String>()
    dictionary.forEach { dict[it.key.toLowerCase()] = it.value.toLowerCase() }
    val outputStream = File(outputName).bufferedWriter()
    var sb = StringBuilder()
    for (i in File(inputName).readLines()) sb.append(i + '\n')
    val text = sb.removeSuffix("\n").toString()
    sb = StringBuilder()
    for (ch in text) {
        val add = dict.getOrDefault(ch.toLowerCase(), ch.toString())
        if (ch == ch.toUpperCase() && ch.toUpperCase() != ch.toLowerCase()) {
            sb.append(add.capitalize())
        } else {
            sb.append(dict.getOrDefault(ch, ch.toString()).toLowerCase())
        }
    }
    outputStream.write(sb.toString())
    outputStream.close()
}

/**
 * Средняя
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    var max = "" to 0
    for (i in File(inputName).readLines()) {
        if (i.toLowerCase().toSet().size == i.length) {
            when {
                i.length > max.second -> max = i to i.length
                i.length == max.second -> max = max.first + ", $i" to max.second
            }
        }
    }
    val outputStream = File(outputName).bufferedWriter()
    outputStream.write(max.first)
    outputStream.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun findMarkUpBody(list: MutableList<String>, start: Int): MutableList<String> { //for further use in impossible
    val unList = Regex("\\*.*")
    val numList = Regex("\\d+\\..*")
    val markUpList = mutableListOf<String>()
    for (i in start until list.size)
        if (!list[i].matches(unList) && !list[i].matches(numList)) markUpList.add(list[i]) else break
    return markUpList
}

fun markUp(list: MutableList<String>): String { //for further use in impossible
    val sb = StringBuilder("<p>\n")
    var iOpened = false
    var bOpened = false
    var sOpened = false
    for (i in list) {
        if (i.isEmpty()) sb.append("</p>\n\n<p>")
        var index = 0
        while (index < i.length - 1) {
            when {
                i[index] == '*' && i[index + 1] == '*' -> {
                    sb.append(if (bOpened) "</b>" else "<b>")
                    bOpened = !bOpened
                    index += 2
                }
                i[index] == '*' && i[index + 1] != '*' -> {
                    sb.append(if (iOpened) "</i>" else "<i>")
                    iOpened = !iOpened
                    index += 1
                }
                i[index] == '~' && i[index + 1] == '~' -> {
                    sb.append(if (sOpened) "</s>" else "<s>")
                    sOpened = !sOpened
                    index += 2
                }
                else -> {
                    sb.append(i[index])
                    index += 1
                }
            }
        }
        if (index != i.length) sb.append(i[i.length - 1])
    }
    return sb.append("</p>\n").toString()
}

fun markdownToHtmlSimple(inputName: String, outputName: String) {
    val outputStream = File(outputName).bufferedWriter()
    outputStream.write("<html>\n<body>\n")
    val list = File(inputName).readLines().toMutableList()
    outputStream.write(markUp(list))
    outputStream.write("</body>\n</html>")
    outputStream.close()
}

/**
 * Сложная
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body>...</body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>
Или колбаса
</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>
Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ul>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun startSpaceCount(str: String): Int {
    var count = 0
    for (i in 0 until str.length) if (str[i] == ' ') count += 1 else break
    return count
}

fun findList(lists: MutableList<String>, startIndex: Int, spaces: Int): MutableList<String> {
    val curList = mutableListOf<String>()
    val unList = Regex(" *\\*.*")
    val numList = Regex(" *\\d+\\..*")
    for (i in startIndex until lists.size)
        if (startSpaceCount(lists[i]) >= spaces && (lists[i].matches(unList) || lists[i].matches(numList)))
            curList.add(lists[i]) else break
    return curList
}

fun unListIter(list: MutableList<String>, spaces: Int): String { //unNumberedLists - for further use in impos.
    val sb = StringBuilder()
    for (i in 0 until spaces) sb.append(" ")
    sb.append("<ul>\n")
    var i = 0
    while (i < list.size) {
        for (iter in 0 until spaces + 4) sb.append(" ")
        when {
            startSpaceCount(list[i]) == spaces -> {
                val str = list[i].drop(startSpaceCount(list[i]) + 1)
                sb.append("<li>$str</li>\n")
                i += 1
            }
            startSpaceCount(list[i]) - spaces == 4 -> {
                if (list[i][startSpaceCount(list[i])] == '*') {
                    val str = unListIter(findList(list, i, spaces + 4), spaces + 4)
                    for (iter in 0 until 9 + spaces) sb.deleteCharAt(sb.length - 2) //removing </li>
                    sb.append("\n")
                    sb.append(str)
                } else {
                    val str = numListIter(findList(list, i, spaces + 4), spaces + 4)
                    for (iter in 0 until 9 + spaces) sb.deleteCharAt(sb.length - 2) //removing </li>
                    sb.append("\n")
                    sb.append(str)
                }
                for (iter in 0 until spaces + 4) sb.append(" ")
                sb.append("</li>\n") //adding removed </li> at correct place
                i += findList(list, i, spaces + 4).size
            }
        }
    }
    for (i in 0 until spaces) sb.append(" ")
    sb.append("</ul>\n")
    return sb.toString()
}

fun numListIter(list: MutableList<String>, spaces: Int): String { //numberedLists - for further use in impos.
    val sb = StringBuilder()
    for (i in 0 until spaces) sb.append(" ")
    sb.append("<ol>\n")
    var i = 0
    while (i < list.size) {
        for (iter in 0 until spaces + 4) sb.append(" ")
        when {
            startSpaceCount(list[i]) == spaces -> {
                val str = list[i].drop(list[i].indexOf('.') + 1)
                sb.append("<li>$str</li>\n")
                i += 1
            }
            startSpaceCount(list[i]) - spaces == 4 -> {
                if (list[i][startSpaceCount(list[i])] == '*') {
                    val str = unListIter(findList(list, i, spaces + 4), spaces + 4)
                    for (iter in 0 until 9 + spaces) sb.deleteCharAt(sb.length - 2) //removing </li>
                    sb.append("\n")
                    sb.append(str)
                } else {
                    val str = numListIter(findList(list, i, spaces + 4), spaces + 4)
                    for (iter in 0 until 9 + spaces) sb.deleteCharAt(sb.length - 2) //removing </li>
                    sb.append("\n")
                    sb.append(str)
                }
                i += findList(list, i, spaces + 4).size
                for (iter in 0 until spaces + 4) sb.append(" ")
                sb.append("</li>\n")
            }
        }
    }
    for (i in 0 until spaces) sb.append(" ")
    sb.append("</ol>\n")
    return sb.toString()
}

fun markdownToHtmlLists(inputName: String, outputName: String) {
    val lists = mutableListOf<String>()
    for (i in File(inputName).readLines()) lists.add(i)
    val sb = StringBuilder()
    if (lists[0][0] == '*') {
        val str = unListIter(lists, 0)
        sb.append(str)
    } else {
        val str = numListIter(lists, 0)
        sb.append(str)
    }
    val outputStream = File(outputName).bufferedWriter()
    outputStream.write("<html>\n<body>\n")
    outputStream.write(sb.toString())
    outputStream.write("\n</body>\n</html>")
    outputStream.close()
}

/**
 * Очень сложная
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    val list = File(inputName).readLines().toMutableList()
    val unList = Regex(" *\\*.*")
    val numList = Regex(" *\\d+\\..*")
    val sb = StringBuilder("<html>\n<body>\n")
    var i = 0
    while (i < list.size) {
        when {
            list[i].matches(unList) -> {
                sb.append(unListIter(findList(list, i, 0), 0))
                i += findList(list, i, 0).size
            }
            list[i].matches(numList) -> {
                sb.append(numListIter(findList(list, i, 0), 0))
                i += findList(list, i, 0).size
            }
            else -> {
                val str = markUp(findMarkUpBody(list, i))
                sb.append("$str\n")
                i += findMarkUpBody(list, i).size
            }
        }
    }
    sb.append("</body>\n</html>")
    val outputStream = File(outputName).bufferedWriter()
    outputStream.write(sb.toString())
    print(sb.toString())
    outputStream.close()
}

/**
 * Средняя
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}


/**
 * Сложная
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    TODO()
}

