import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Auction {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    private AuctionState state;

    private final User creator;
    private final Date auctionEndingDate;
    private final BigDecimal reservePrice;
    private final BigDecimal minimalOffer;
    private final List<Offer> offers;

    public Auction(User creator, Item item, Date auctionEndingDate, BigDecimal reservePrice, BigDecimal minimalOffer) {
        if (!dateFormat.format(auctionEndingDate).equals(dateFormat.format(new Date())) &&
                auctionEndingDate.before(new Date())) {
            throw new IllegalArgumentException("invalid auction ending date");
        }

        if (reservePrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("reserve price should be positive");
        }

        if (minimalOffer.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("minimal offer should be positive");
        }

        this.creator = creator;
        this.state = AuctionState.CREATED;
        this.auctionEndingDate = auctionEndingDate;
        this.reservePrice = reservePrice;
        this.minimalOffer = minimalOffer;
        this.offers = new ArrayList<Offer>();
    }

    // TODO : Meilleur gestion date expiration
    public AuctionState getState() {
        if (auctionEndingDate.before(new Date())) {
            this.state = AuctionState.CLOSED;
        }
        return this.state;
    }

    public void publish() {
        this.state = AuctionState.PUBLISHED;
    }

    public void cancel() {
        this.state = AuctionState.CANCELED;
    }

    public Offer getHighestOffer() {

        Collections.sort(offers);
        return offers.get(0);
    }

    public void makeOffer(Offer offer) {
        if (offer.getPrice().compareTo(this.minimalOffer) < 0) {
            throw new IllegalArgumentException("Minimal offer is " + minimalOffer +
                    " yours is " + offer.getPrice());
        }

        if (offer.getBidder() == this.creator) {
            throw new IllegalArgumentException("You cannot bid on your own auction");
        }

        if (this.state != AuctionState.PUBLISHED) {
            throw new IllegalStateException("You cannot bid on an auction that is not in the published state");
        }

        offers.add(offer);
    }

    public boolean isReservePriceReached() {
        return getHighestOffer().getPrice().compareTo(this.reservePrice) >= 0;
    }

    public User getPublisher() {
        return this.creator;
    }

}
