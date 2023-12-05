package org.rgoussey.aoc2023.day3

import java.io.File
import kotlin.math.max
import kotlin.math.min

fun main() {
    val lines = File({}.javaClass.getResource("/day3/input.txt")!!.toURI()).readLines();

    process(lines);
}

fun process(lines: List<String>) {
    val characters = mutableListOf<MutableList<Char>>()
    val symbols = mutableListOf<MutableList<Boolean>>()
    val numbers = mutableListOf<MutableList<Boolean>>()
    val closeToSymbol = mutableListOf<MutableList<Boolean>>()
    for (i in lines.indices) {
        characters.add(i, mutableListOf())
        symbols.add(i, mutableListOf())
        numbers.add(i, mutableListOf())
        closeToSymbol.add(i, mutableListOf())
        for (j in lines[i].indices) {
            val currentChar = lines[i][j]
            characters[i].add(j, currentChar)
            val isSymbol = isSymbol(currentChar)
            symbols[i].add(j, isSymbol)
            numbers[i].add(j, isNumber(currentChar))
        }
    }

    for (i in lines.indices) {
        for (j in lines[i].indices) {
            closeToSymbol[i].add(j, adjacentToSymbol(symbols, i, j))
        }
    }

    printMap(symbols)
    printMap(numbers)
    printMap(closeToSymbol)

    var sum = 0;
    for (i in characters.indices) {
        var currentNumber = "";
        var lastNumberIndex = 0;
        var numberIsValid = false;
        for (j in characters[i].indices) {
            val isNumber = numbers[i][j]
            if (isNumber) {
                numberIsValid = numberIsValid or adjacentToSymbol(symbols, i, j)
                lastNumberIndex = j
                currentNumber += characters[i][j]
                val endOfLine = j == characters[i].size-1
                if (endOfLine) {
                    val number = Integer.parseInt(currentNumber)
                    if (numberIsValid) {
//                        println("Valid number $number")
                        sum += number;
                    } else {
//                        println("     Not valid number $number")
                    }
                    currentNumber = ""
                    lastNumberIndex = 0;
                }
            } else {
                val numberEnded = lastNumberIndex + 1 == j
                if (numberEnded && currentNumber != "") {
                    val number = Integer.parseInt(currentNumber)
//                    println("Number is detected %s".format(number))
                    if (numberIsValid) {
//                        println("Valid number $number")
                        sum += number;
                    } else {
//                        println("      Not valid number $number")
                    }
                    currentNumber = "";
                    numberIsValid=false
                }
                lastNumberIndex = 0;
            }
        }
    }
    println("Sum is $sum")
}

fun printMap(map: MutableList<MutableList<Boolean>>) {
    for (i in map.indices) {
        for (j in map[i].indices) {
            if (map[i][j]) {
                print('x')
            } else {
                print('.')
            }
        }
        print("\n");
    }
    print("\n");

}

fun adjacentToSymbol(symbols: MutableList<MutableList<Boolean>>, x: Int, y: Int): Boolean {
    for (i in max(x - 1, 0)..min(x + 1, symbols.size - 1)) {
        for (j in max(y - 1, 0)..min(y + 1, symbols[x].size - 1)) {
            if (symbols[i][j]) {
                return true
            }
        }
    }
    return false;
}


fun isSymbol(char: Char): Boolean {
    return !isNumber(char) && char != '.';
}

fun isNumber(char: Char): Boolean {
    return char in '0'..'9';
}
