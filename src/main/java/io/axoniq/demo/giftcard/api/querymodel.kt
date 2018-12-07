package io.axoniq.demo.giftcard.api

import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.NamedQueries
import javax.persistence.NamedQuery

/**
 *
 */
@Entity
@NamedQueries(
        NamedQuery(name = "CardSummary.fetch",
                query = "SELECT c FROM CardSummary c WHERE c.id LIKE CONCAT(:idStartsWith, '%') ORDER BY c.id"),
        NamedQuery(name = "CardSummary.count",
                query = "SELECT COUNT(c) FROM CardSummary c WHERE c.id LIKE CONCAT(:idStartsWith, '%')"))
data class CardSummary(@Id var id: String, var initialValue: Int, var remainingValue: Int) {
    constructor() : this("", 0, 0)
}
