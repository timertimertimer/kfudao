package com.example.dao.models

import java.math.BigInteger
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

data class Proposal(
    val id: BigInteger? = null,
    val proposer: String? = null,
    val description: String? = null,
    val voteStartBlock: BigInteger? = null,
    val voteEndBlock: BigInteger? = null,
    val voteStartBlockTimestamp: Timestamp? = null,
    val voteEndBlockTimestamp: Timestamp? = null,
    val votesFor: Token? = null,
    val votesAgainst: Token? = null,
    val votesAbstain: Token? = null
) {
    override fun toString(): String {
        val formatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.UK)
        val voteStartDateTime = voteStartBlockTimestamp?.let { formatter.format(it) } ?: "N/A"
        val voteEndDateTime = voteEndBlockTimestamp?.let { formatter.format(it) } ?: "N/A"

        return "Создатель: $proposer\n" +
                "Описание: $description\n" +
                "Дата начала: $voteStartDateTime\n" +
                "Дата конца: $voteEndDateTime\n" +
                "За: ${votesFor?.toString()}\n" +
                "Против: ${votesAgainst?.toString()}\n" +
                "Воздержались: ${votesAbstain?.toString()}"
    }
}