package com.example.dao.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.dao.models.Proposal
import com.example.dao.models.Token
import com.example.dao.viewmodels.MainViewModel
import kotlinx.coroutines.delay
import java.math.BigInteger
import java.sql.Timestamp

@Composable
fun LazyProposals(proposals: List<Proposal>, mvm: MainViewModel) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(1),
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(proposals) { proposal ->
            ProposalCard(proposal, mvm)
        }
    }
}

@Composable
fun ProposalCard(proposal: Proposal, mvm: MainViewModel) {
    var showDialog by remember { mutableStateOf(false) }
    var timeLeft by remember { mutableLongStateOf(0L) }
    var hasVoted by remember { mutableStateOf(false) }
    var progress by remember { mutableFloatStateOf(0f) }

    val startBlock = proposal.voteStartBlock ?: BigInteger.ZERO
    val endBlock = proposal.voteEndBlock ?: BigInteger.ZERO

    LaunchedEffect(key1 = mvm.connected, key2 = proposal.id) {
        if (mvm.connected) {
            val voted = mvm.hasVoted(proposal.id!!)
            hasVoted = voted
        } else {
            hasVoted = false
        }
    }

    LaunchedEffect(key1 = proposal.voteEndBlockTimestamp) {
        while (true) {
            timeLeft = (proposal.voteEndBlockTimestamp?.time?.minus(System.currentTimeMillis())
                ?: 0) / 1000
            if (timeLeft < 0) timeLeft = 0

            val totalBlocks = endBlock - startBlock
            val passedBlocks = mvm.currentBlock.value?.blockNumber?.minus(startBlock) ?: 0
            progress = if (totalBlocks > BigInteger.ZERO) {
                (passedBlocks.toFloat() / totalBlocks.toFloat()).coerceIn(0f, 1f)
            } else {
                0f
            }

            delay(1000L)
        }
    }

    ProposalCardContent(
        proposal = proposal,
        hasVoted = hasVoted,
        progress = progress,
        timeLeft = timeLeft
    ) { showDialog = true }

    if (showDialog) {
        ProposalDialog(proposal, mvm, hasVoted) { showDialog = false }
    }
}

fun getCardColor(progress: Float): Color {
    return when {
        progress < 1f / 3f -> Color.Green
        progress < 2f / 3f -> Color.Yellow
        progress < 1f -> Color(0xFFFFA500)
        else -> Color.Red
    }
}

@Composable
fun ProposalCardContent(
    proposal: Proposal,
    hasVoted: Boolean,
    progress: Float,
    timeLeft: Long,
    onClick: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        colors = if (hasVoted) CardDefaults.elevatedCardColors() else CardDefaults.cardColors(
            containerColor = getCardColor(progress)
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Описание: ${proposal.description}",
                fontWeight = FontWeight.Bold,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Оставшееся время: $timeLeft секунд",
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProposalCardContentPreview() {
    val voteStartBlock: BigInteger = BigInteger.ZERO
    val voteEndBlock: BigInteger = BigInteger.TEN
    val currentTime = System.currentTimeMillis()
    ProposalCardContent(
        proposal = Proposal(
            id = BigInteger.ONE,
            proposer = "0x54fd18a25591a716affed85356cbd89b050db7e2",
            description = "Считаете ли вы важным введение дополнительных мер по поддержке здоровья студентов?",
            voteStartBlock = voteStartBlock,
            voteEndBlock = voteEndBlock,
            voteStartBlockTimestamp = Timestamp(currentTime),
            voteEndBlockTimestamp = Timestamp(
                currentTime + (voteEndBlock - voteStartBlock).multiply(
                    BigInteger("100")
                ).toLong()
            ),
            votesFor = Token(BigInteger.ONE),
            votesAbstain = Token(BigInteger.ZERO),
            votesAgainst = Token(BigInteger.ZERO)
        ),
        hasVoted = false, progress = 0f, timeLeft = 0L
    ) {

    }
}