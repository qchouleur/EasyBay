package alerts;

import auction.Alert;
import auction.Auction;


public class CanceledAuctionAlert extends Alert {

    public CanceledAuctionAlert(Auction source) {
        super(source, String.format("la vente reférence %s a été annulée", source.id()));
    }

}