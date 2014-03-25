package auction;

import observable.BuyerObserver;

import java.math.BigDecimal;

public interface Buyer extends BuyerObserver {

    void placeBid(Auction auction, BigDecimal bid);
    String name();
}
