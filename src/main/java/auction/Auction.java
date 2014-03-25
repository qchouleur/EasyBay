package auction;

import alerts.CanceledAuctionAlert;
import alerts.HigherBidAlert;
import alerts.NewBidAlert;
import alerts.ReservePriceReachedAlert;
import observable.BuyerObserver;
import observable.Observable;
import observable.ObservableByBuyer;
import observable.Observer;
import time.Clock;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Auction {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    
    private AuctionState state;

    private final Seller seller;
    private final Date auctionEndingDate;
    private final BigDecimal reservePrice;
    private final BigDecimal minimalOffer;
    private Offer winningOffer;
    private final Clock clock;
    private final Item item;

    private final Observable sellerNotifier;
    private final ObservableByBuyer buyerNotifier;

    public Auction(Seller seller, Clock clock, Item item, Date auctionEndingDate, BigDecimal reservePrice, BigDecimal minimalOffer) {
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

        this.seller = seller;
        this.state = AuctionState.CREATED;
        this.auctionEndingDate = auctionEndingDate;
        this.reservePrice = reservePrice;
        this.minimalOffer = minimalOffer;
        this.clock = clock;
        this.item = item;


        buyerNotifier = new AuctionBuyerNotifier();
        sellerNotifier = new AuctionSellerNotifier();
        sellerNotifier.add(seller);
    }

    public AuctionState getState() {
        if (auctionEndingDate.before(clock.now())
                || clock.now().equals(auctionEndingDate)) {
            this.state = AuctionState.CLOSED;
        }
        return this.state;
    }

    public void publish() {
        this.state = AuctionState.PUBLISHED;
    }

    public void cancel() {

        if (this.isReservePriceReached()) {
            throw new IllegalStateException("An auction shall not be canceled if the reserve price is reached");
        }

        this.state = AuctionState.CANCELED;
        this.buyerNotifier.notify(new CanceledAuctionAlert(this));

    }

    public Offer getHighestOffer() {
        return this.winningOffer;
    }

    public void makeOffer(Offer offer) {
        if (offer.getPrice().compareTo(this.minimalOffer) < 0) {
            throw new IllegalArgumentException("Minimal offer is " + minimalOffer +
                    " yours is " + offer.getPrice());
        }

        if (offer.getBidder() == this.seller) {
            throw new IllegalArgumentException("You cannot place a bid on your own auction");
        }

        if (this.state != AuctionState.PUBLISHED) {
            throw new IllegalStateException("You cannot place a bid on an auction that is not in the published state");
        }


        if (winningOffer == null) {
            this.winningOffer = offer;
        } else if (offer.isGreaterOrEqual(getPriceToReach())) {
            this.winningOffer = offer;
        } else {
            throw new IllegalArgumentException("Your offer should at least be of " + getPriceToReach() +
                    " yours is " + offer.getPrice());
        }

        this.buyerNotifier.add(offer.getBidder());

        this.sellerNotifier.notify(new NewBidAlert(this, offer));
        this.buyerNotifier.notify(new HigherBidAlert(this), offer.getBidder());

        if (this.isReservePriceReached()) {
            this.buyerNotifier.notify(new ReservePriceReachedAlert(this));
        }

    }

    public BigDecimal getPriceToReach() {

        BigDecimal currentPrice = winningOffer.getPrice();
        return currentPrice.add(BidIncrement.incrementForPrice(winningOffer.getPrice()));
    }

    public boolean isReservePriceReached() {
        if (getHighestOffer() == null) {
            return false;
        }
        if(getHighestOffer().getPrice().compareTo(this.reservePrice) >= 0)
        {
        	return true;
        }
        else
        	return false;
    }

    public Seller getPublisher() {
        return this.seller;
    }

    public String id() {
        return this.item.id();
    }

    public String description() {
        return item.description();
    }

    public final class AuctionSellerNotifier implements Observable {

        private List<Observer> observers = new ArrayList<Observer>();

        @Override
        public void add(Observer observer) {
            if (!this.observers.contains(observer)) {
                this.observers.add(observer);
            }
        }

        @Override
        public void notify(Alert alert) {
            for (Observer observer : observers) {
                observer.receiveAlert(alert);
            }
        }

        @Override
        public void notify(Alert alert, Observer... ignoredObservers) {
            for (Observer observer : observers) {
                if (!ArrayContainsElement(ignoredObservers, observer)) {
                    observer.receiveAlert(alert);
                }
            }
        }
    }

    public final class AuctionBuyerNotifier implements ObservableByBuyer {

        private List<BuyerObserver> observers = new ArrayList<BuyerObserver>();


        @Override
        public void add(BuyerObserver observer) {
            if (!this.observers.contains(observer)) {
                this.observers.add(observer);
            }
        }

        @Override
        public void notify(Alert alert) {
            for (BuyerObserver observer : observers) {
                if (observer.hasSubscriptionTo(alert.getClass())) {
                    observer.receiveAlert(alert);
                }
            }
        }

        @Override
        public void notify(Alert alert, Observer... ignoredObservers) {
            for (BuyerObserver observer : observers) {
                if (!ArrayContainsElement(ignoredObservers, observer) &&
                        observer.hasSubscriptionTo(alert.getClass())) {
                    observer.receiveAlert(alert);
                }
            }
        }
    }

    private static <T> boolean ArrayContainsElement(T[] array, T searchedElement) {
        for (T element : array) {
            if (element == searchedElement) {
                return true;
            }
        }

        return false;
    }


}
