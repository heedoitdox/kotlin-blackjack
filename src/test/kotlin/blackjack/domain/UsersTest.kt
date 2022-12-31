package blackjack.domain

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeInstanceOf

internal class UsersTest : StringSpec({
    "플레이어들에게 2장씩 카드를 지급한다." {
        val users = Users("플레이어1", "플레이어2", "플레이어3")
        val drawnPlayers = users.drawInitCards(deck = Deck())

        drawnPlayers.values.forEach {
            it.cards.values.size shouldBe 2
        }
    }

    "플레이어들의 승부 결과를 반환한다." {
        val users = Users("플레이어1", "플레이어2", "플레이어3")
        val dealer = Dealer()

        val results = users.calculateResult(dealer)
        results.forEach {
            it.shouldBeInstanceOf<PlayerResult>()
        }
    }
})
