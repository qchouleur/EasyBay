package alerts;

import auction.Alert;
import auction.Auction;

public class HigherBidAlert extends Alert {

    public HigherBidAlert(Auction source) {
        super(source, String.format("Une offre plus importante a été effectuée sur la vente référence %s", source.id()));
    }

}
