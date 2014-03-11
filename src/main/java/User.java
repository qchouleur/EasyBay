import time.RealClock;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {


    private final String name;

    public User() {
        this.name = "";
    }

    public User(String name) {
        this.name = name;
    }

    private List<Alert> pendingAlerts = new ArrayList<Alert>();

    public void bid(Auction auction, BigDecimal price) {

        auction.makeOffer(new Offer(this, price));
    }

    public Auction createAuction(Item item, Date auctionEndingDate, BigDecimal reservePrice, BigDecimal minimalOffer) {
        return new Auction(this, new RealClock(), item, auctionEndingDate, reservePrice, minimalOffer);
    }

    public void AddAlert(Alert alert) {
        pendingAlerts.add(alert);
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
}



