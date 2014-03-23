import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import time.RealClock;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class UserTest {


    private User author;
    private User bidder;
    private Alert alert;


    @Before
    public void setUp() {
        this.author = new User("toto le sri");
        this.bidder = new User("tutu le babtou");
        this.alert = new Alert();
    }

    @Test
    public void UserCanReceiveAnAlert() {

        author.AddAlert(alert);
        List<Alert> pendingAlerts = author.getPendingAlerts();

        Assert.assertEquals(pendingAlerts.size(), 1);
        Assert.assertEquals(pendingAlerts.get(0), alert);
    }


    @Test
    public void UserCanRemoveAlert() {

        User user = new User();
        Alert alert = new Alert();
        user.AddAlert(alert);

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
        Item item = new Item("Lampadaire215", "Lampadaire respectant les normes environnementales et l'écologie dans son ensemble");


        Auction auction = new Auction(author, new RealClock(), item, endingDate, reservePrice, minimumOffer);
        auction.publish();

        bidder.bid(auction, new BigDecimal(200));

        Alert newBidAlert = author.getPendingAlerts().get(0);

        Assert.assertNotNull(newBidAlert);
        Assert.assertEquals("tutu le babtou a fait une offre de 200 sur la vente Lampadaire215", newBidAlert.getMessage());
    }

}