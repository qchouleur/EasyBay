import java.math.BigDecimal;

public class Offer implements Comparable<Offer> {

    private final Buyer bidder;
    private final BigDecimal price;

    public Offer(User bidder, BigDecimal price) {

        this.bidder = bidder;
        this.price = price;
    }

    @Override
    public int compareTo(Offer offer) {
        return -this.price.compareTo(offer.price);
    }

    public Buyer getBidder() {
        return this.bidder;
    }

    public BigDecimal getPrice() {

        return price;
    }
}
