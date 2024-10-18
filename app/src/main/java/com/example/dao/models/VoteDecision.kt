package com.example.dao.models

enum class VoteDecision(val value: Int) {
    FOR(0),
    AGAINST(1),
    ABSTAIN(2);

    override fun toString(): String {
        return when (this) {
            FOR -> "За"
            AGAINST -> "Против"
            ABSTAIN -> "Воздерживаюсь"
        }
    }
}