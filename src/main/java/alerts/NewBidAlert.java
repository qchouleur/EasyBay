package alerts;

import auction.Alert;
import auction.Auction;
import auction.Offer;

public class NewBidAlert extends Alert {

    public NewBidAlert(Auction source, Offer offer) {
        super(source, String.format("%s a fait une offre de %09.2f sur la vente reférence %s",
                offer.getBidder().name(), offer.getPrice(), source.id()));
    }

}
