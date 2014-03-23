package auction;
import time.Clock;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import observable.Observable;
import observable.Observateur;



public class Auction implements Observable{

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    
    private AuctionState state;

    private final Seller seller;
    private final Date auctionEndingDate;
    private final BigDecimal reservePrice;
    private final BigDecimal minimalOffer;
    private final List<Offer> offers;
    private final Clock clock;
    private final Item item;
    
    private ArrayList<Observateur> listObservateur = new ArrayList<Observateur>();

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
        this.offers = new ArrayList<Offer>();
        this.clock = clock;
        this.item = item;
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
        Alert alert = new Alert(this,"Auction is published");
        this.updateObservateur(alert);
    }

    public void cancel() {
        this.state = AuctionState.CANCELED;
        Alert alert = new Alert(this,"Auction is canceled");
        this.updateObservateur(alert);
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

        if (offer.getBidder() == this.seller) {
            throw new IllegalArgumentException("You cannot placeBid on your own auction");
        }

        if (this.state != AuctionState.PUBLISHED) {
            throw new IllegalStateException("You cannot placeBid on an auction that is not in the published state");
        }

        offers.add(offer);

        this.getPublisher().AddAlert(new Alert(this, offer.getBidder().name() + " a fait une offre de " + offer.getPrice() + " sur la vente " + this.item.id()));
    }

    public boolean isReservePriceReached() {
        return getHighestOffer().getPrice().compareTo(this.reservePrice) >= 0;
    }

    public Seller getPublisher() {
        return this.seller;
    }
    
    //Ajoute un observateur � la liste
    public void addObservateur(Observateur obs) {
      this.listObservateur.add(obs);
    }
    //Retire tous les observateurs de la liste
    public void delObservateur() {
      this.listObservateur = new ArrayList<Observateur>();
    }
    //Avertit les observateurs que l'objet observable a chang� et invoque la m�thode update() de chaque observateur
    public void updateObservateur() {
    	for(Observateur obs : this.listObservateur )
		{
    		User user = (User) seller;
	        obs.update(user.getPendingAlerts().get(0));
	    }	
    }
}