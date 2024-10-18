package com.example.dao.models

data class Account(
    val email: String,
    val institute: String,
    val instituteAbbreviation: String,
    val faculty: String,
    var address: String?
) {
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        address?.let { stringBuilder.append("Адрес: $it\n") }
        return stringBuilder.toString()
    }
}
