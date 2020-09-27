package com.gomezrondon.awsexamgenerator.utils

fun getAlfabetList(): List<String> {
    val charRange = 'A'..'Z'
    val optionsList = charRange.map { it.toString() }.toList()
    return optionsList
}
