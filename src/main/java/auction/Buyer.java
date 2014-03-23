package auction;
import java.math.BigDecimal;

public interface Buyer {

    void placeBid(Auction auction, BigDecimal bid);
    String name();
}
