package auction;

import alerts.CanceledAuctionAlert;
import alerts.HigherBidAlert;
import alerts.ReservePriceReachedAlert;
import observable.BuyerObserver;
import time.RealClock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class User implements Buyer, Seller, BuyerObserver {


    private final String name;

    public User() {
        this.name = "";
    }

    public User(String name) {
        this.name = name;
    }

    private List<Alert> pendingAlerts = new ArrayList<Alert>();

    public void placeBid(Auction auction, BigDecimal price) {

        auction.makeOffer(new Offer(this, price));
    }

    public Auction createAuction(Item item, Date auctionEndingDate, BigDecimal reservePrice, BigDecimal minimalOffer) {
        return new Auction(this, new RealClock(), item, auctionEndingDate, reservePrice, minimalOffer);
    }

    public List<Alert> getPendingAlerts() {
        return pendingAlerts;

    }

    public void removeAlert(Alert alert) {
        if(pendingAlerts.contains(alert)) {
            pendingAlerts.remove(alert);
        }
    }

    public String name() {
        return this.name;
    }

    @Override
    public void receiveAlert(Alert alert) {
        pendingAlerts.add(alert);
    }

    private List<Class> subscribedAlerts = new ArrayList<Class>(Arrays.asList(HigherBidAlert.class, CanceledAuctionAlert.class, ReservePriceReachedAlert.class));

    @Override
    public <T extends Alert> void unsuscribe(Class<T> alert) {
        subscribedAlerts.remove(alert.getClass());
    }

    @Override
    public <T extends Alert> void subscribe(Class<T> alert) {
        subscribedAlerts.add(alert.getClass());
    }

    @Override
    public <T extends Alert> boolean hasSubscriptionTo(Class<T> alert) {
        return subscribedAlerts.contains(alert.getClass());
    }
}



