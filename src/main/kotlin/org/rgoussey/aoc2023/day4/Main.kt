package org.rgoussey.aoc2023.day4

import java.io.File
import java.util.stream.Collectors

fun main() {
    val lines = File({}.javaClass.getResource("/day4/input.txt")!!.toURI()).readLines();

    process(lines);
}

fun process(lines: List<String>) {
    val cards = lines.stream().map { line ->

        Card(line)

    }.toList()
    var sum = 0;
    cards.forEach { card ->
        run {
            val score = card.getScore()
            println("score $score")
            sum += score
        }
    }
    print(sum)
}

class Card(line: String) {
    private var duplicateNumbers: Set<Int>
    private val losingNumbers: Set<Int>

    private val winningNumbers: Set<Int>


    init {
        val numbers = line.split(":")[1].split("|")
        winningNumbers = parseNumbers(numbers[0])
        losingNumbers = parseNumbers(numbers[1])
        duplicateNumbers =
            losingNumbers.stream().filter(winningNumbers::contains).collect(Collectors.toSet())
    }

    fun getScore(): Int {
        val size = duplicateNumbers.size
        return if (size <= 1) {
            size;
        } else {
            1 shl size - 1
        }
    }


    private fun parseNumbers(line: String): Set<Int> {
        val result: MutableSet<Int> = HashSet();
        val numbers = line.split(" ")
        numbers.forEach { number ->
            run {
                try {
                    result.add(number.toInt())
                } catch (e: Exception) {
                    //Ignore
                }
            }
        }
        return result;
    }


}
