package org.laolittle.plugin.draw

internal fun String.split(): List<String>? {
    val words =
        when {
            this.length == 1 -> listOf(this, " ")
            contains("\n") -> split(Regex("\n+"))
            else -> split(Regex("[\\s　]+"))
        }.toMutableList()

    if (words.isEmpty()) return null
    if (words.size == 1) {
        words.apply {
            val sentence = words[0]
            clear()
            val left = if (sentence.isNotEmpty())
                (sentence.length shr 1) else 1
            add(sentence.substring(0, left))
            if (sentence.length == 1)
                add(" ")
            else
                add(sentence.substring(left, sentence.length))
        }
    }
    return words
}