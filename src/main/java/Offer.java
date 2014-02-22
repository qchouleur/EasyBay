import java.math.BigDecimal;

public class Offer implements Comparable<Offer> {

    private final User bidder;
    private final BigDecimal price;

    public Offer(User bidder, BigDecimal price) {

        this.bidder = bidder;
        this.price = price;
    }

    @Override
    public int compareTo(Offer offer) {
        return -this.price.compareTo(offer.price);
    }

    public User getBidder() {
        return this.bidder;
    }

    public BigDecimal getPrice() {

        return price;
    }
}
