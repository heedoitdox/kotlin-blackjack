package blackjack.domain

class User(
    override val name: Name,
    override val cards: Cards = Cards(),
    val betAmount: BetAmount
) : Player {
    constructor(name: Name, betAmount: () -> Int) : this(name = name, betAmount = BetAmount(betAmount.invoke()))
    override fun copy(name: Name, cards: Cards): User {
        return User(name = name, cards = cards, betAmount = betAmount)
    }

    override fun drawInitialCards(deck: Deck): User {
        return this.copy(cards = deck.drawInitCards())
    }
}
