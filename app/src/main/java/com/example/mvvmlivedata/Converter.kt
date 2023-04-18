package com.example.mvvmlivedata

object Converter {
    fun convert(value: String): Int? {
        return try {
            Integer.parseInt(value)
        } catch (_:Exception) {
            null
        }
    }
 }