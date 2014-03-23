package auction;
import java.math.BigDecimal;
import java.util.Date;

public interface Seller {

    Auction createAuction(Item item, Date auctionEndingDate, BigDecimal reservePrice, BigDecimal minimalOffer);
    void AddAlert(Alert alert);

}
