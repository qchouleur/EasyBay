package auction;

import observable.Observer;

import java.math.BigDecimal;
import java.util.Date;

public interface Seller extends Observer {

    Auction createAuction(Item item, Date auctionEndingDate, BigDecimal reservePrice, BigDecimal minimalOffer);

}
