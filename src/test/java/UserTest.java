import alerts.ReservePriceReachedAlert;
import auction.Alert;
import auction.Auction;
import auction.Item;
import auction.User;
import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import time.RealClock;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserTest {


    private static User author;
    private static User bidder;
    private static Alert alert;


    @BeforeClass
    public static void setUp() {
        author = new User("toto le sri");
        bidder = new User("tutu le babtou");
        alert = new Alert();
    }

    @Test
    public void UserCanReceiveAnAlert() {

        author.receiveAlert(alert);
        List<Alert> pendingAlerts = author.getPendingAlerts();

        Assert.assertEquals(pendingAlerts.size(), 1);
        Assert.assertEquals(pendingAlerts.get(0), alert);
    }


    @Test
    public void UserCanRemoveAlert() {

        User user = new User();
        Alert alert = new Alert();
        user.receiveAlert(alert);

        user.removeAlert(alert);

        List<Alert> pendingAlerts = user.getPendingAlerts();

        Assert.assertNotNull(pendingAlerts);
        Assert.assertEquals(pendingAlerts.size(), 0);

    }


    @Test
    public void UserShouldReceiveAnAlertWhenSomeoneBidOnHisAuction() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.JANUARY, 9);
        Date endingDate = calendar.getTime();

        BigDecimal reservePrice = new BigDecimal(400);
        BigDecimal minimumOffer = new BigDecimal(20);
        Item item = new Item("Lampadaire215", "Lampadaire respectant les normes environnementales et l'ecologie dans son ensemble");


        Auction auction = new Auction(author, new RealClock(), item, endingDate, reservePrice, minimumOffer);
        auction.publish();
        

        bidder.placeBid(auction, new BigDecimal(200));

        Alert newBidAlert = author.getPendingAlerts().get(0);

        Assert.assertNotNull(newBidAlert);
        //Assert.assertEquals("tutu le babtou a fait une offre de 200 sur la vente Lampadaire215", newBidAlert.getMessage());
    }

    @Test
    public void UserShouldNotReceiveAlertWhenUnsuscribed() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.JANUARY, 9);
        Date endingDate = calendar.getTime();

        BigDecimal reservePrice = new BigDecimal(400);
        BigDecimal minimumOffer = new BigDecimal(20);
        Item item = new Item("Lampadaire215", "Lampadaire respectant les normes environnementales et l'ecologie dans son ensemble");

        Auction auction = new Auction(author, new RealClock(), item, endingDate, reservePrice, minimumOffer);
        auction.publish();

        User firstBuyer = new User();

        firstBuyer.unsuscribe(ReservePriceReachedAlert.class);
        firstBuyer.placeBid(auction, reservePrice);


        Assert.assertEquals(0, firstBuyer.getPendingAlerts().size());

    }


    public void update(Alert alert) {
		alert.getMessage();
	}
}
