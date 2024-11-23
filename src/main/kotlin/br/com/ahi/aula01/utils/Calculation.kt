package br.com.ahi.aula01.utils

class Calculation {

    fun sum(a: Int, b: Int): Int {
        if(a < 0 || b < 0) throw IllegalArgumentException("numbers must be positive")
        return a + b
    }
}