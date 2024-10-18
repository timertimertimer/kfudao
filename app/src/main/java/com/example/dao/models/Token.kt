package com.example.dao.models

import org.web3j.utils.Convert
import java.math.BigDecimal
import java.math.BigInteger

data class Token(
    var balanceInWei: BigInteger?,
    val symbol: String? = "KDT",
    val balanceInEther: BigDecimal = Convert.fromWei(BigDecimal(balanceInWei), Convert.Unit.ETHER)
) {
    override fun toString(): String {
        return "$balanceInEther"
    }
}

