import java.math.BigDecimal;
import java.util.Date;

public class User {


    public void bid(Auction auction, BigDecimal price) {

        auction.addOffer(new Offer(this, price));
    }

    public Auction createAuction(Item item, Date auctionEndingDate, BigDecimal reservePrice, BigDecimal minimalOffer) {
        return new Auction(this, item, auctionEndingDate, reservePrice, minimalOffer);
    }
}



