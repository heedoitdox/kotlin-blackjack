package blackjack.domain

data class Dealer(
    override val name: Name = DEALER_NAME,
    override var cards: Cards = Cards(),
    val profit: Profit = Profit()
) : Player {
    var results: MutableList<ResultStatus> = mutableListOf()

    override fun isHit() = this.score <= DEALER_HIT_SCORE

    override fun copy(name: Name, cards: Cards): Player {
        return Dealer(cards = cards)
    }

    private fun isBust() = this.score > BLACKJACK_SCORE
    fun getMatchResult(user: User): PlayerResult {
        val playerResult = getPlayerResult(user)
        calculateResult(playerResult)

        return PlayerResult(user, playerResult)
    }

    fun calculateResult(playerResult: ResultStatus) {
        val dealerResult = when (playerResult) {
            ResultStatus.WIN -> ResultStatus.LOSE
            ResultStatus.LOSE -> ResultStatus.WIN
            else -> ResultStatus.DRAW
        }
        results.add(dealerResult)
    }

    fun calculateProfit(playerResults: List<PlayerResult>): Dealer {
        val profit = Profit(
            playerResults.sumOf {
                profit + (-it.profit)
            }
        )
        return this.copy(profit = profit)
    }

    private fun getPlayerResult(player: Player): ResultStatus {
        if (this.isBlackJack()) return getPlayerResultWhenDealerBlackJack(player)
        if (this.isBust() && player.isHit()) return ResultStatus.WIN
        if (!player.isHit()) return ResultStatus.LOSE

        return player.match(this.score)
    }

    private fun getPlayerResultWhenDealerBlackJack(player: Player): ResultStatus {
        if (player.isBlackJack()) return ResultStatus.DRAW
        return ResultStatus.LOSE
    }

    companion object {
        private const val DEALER_HIT_SCORE = 16
        private const val BLACKJACK_SCORE = 21
        private val DEALER_NAME = Name("딜러")
    }
}
