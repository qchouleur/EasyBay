package alerts;

import auction.Alert;
import auction.Auction;

public class ReservePriceReachedAlert extends Alert {

    public ReservePriceReachedAlert(Auction source) {
        super(source, String.format("Le prix de reserve est atteint sur la vente référence %s", source.id()));

    }
}
