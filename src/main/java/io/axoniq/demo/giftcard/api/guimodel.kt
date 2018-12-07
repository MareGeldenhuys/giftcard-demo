package io.axoniq.demo.giftcard.api

/**
 *
 */
data class CardSummaryFilter(val idStartsWith: String = "")

class CountCardSummariesQuery(val filter: CardSummaryFilter = CardSummaryFilter()) {
    override fun toString(): String = "CountCardSummariesQuery"
}

data class CountCardSummariesResponse(val count: Int, val lastEvent: Long)
data class FetchCardSummariesQuery(val offset: Int, val limit: Int, val filter: CardSummaryFilter)

class CountChangedUpdate()
