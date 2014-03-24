package auction;

import java.math.BigDecimal;


/*
    Permet d'obtenir l'augmentation de prix pour une vente aux enchères
    en fonction de l'offre actuelle.

    Cf. http://pages.ebay.com/help/buy/bid-increments.html

 */
public class BidIncrement {


    public BigDecimal incrementForPrice(BigDecimal price) {
        for (BidIncrementRange range : BidIncrementRange.values()) {
            if (range.contains(price)) {
                return range.increment();
            }
        }

        return BigDecimal.ZERO;
    }

}
