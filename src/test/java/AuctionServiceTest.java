import org.junit.Before;
import org.junit.Test;
import time.RealClock;
import junit.framework.Assert;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class AuctionServiceTest {

    private Date endingDate;
    private BigDecimal reservePrice;
    private BigDecimal minimumOffer;
    private Item item;

    private User lambdaUser;
    private Auction auction;
    private AuctionService auctionService;

    @Before
    public void setUp() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.JANUARY, 9);
        endingDate = calendar.getTime();

        reservePrice = new BigDecimal(400);
        minimumOffer = new BigDecimal(20);
        item = new Item();

        User publisher = new User();
        lambdaUser = new User();

        auctionService = new AuctionService();
        auction = new Auction(publisher, new RealClock(), item, endingDate, reservePrice, minimumOffer);
    }

    @Test
    public void UsersShouldNotSeeUnpublishedAuction() {

        auctionService.createAuction(auction);
        List<Auction> auctions = auctionService.getAvailableAuctionsForUser(lambdaUser);

        Assert.assertNotNull(auctions);
        Assert.assertTrue(auctions.isEmpty());

    }

    @Test
    public void UserShouldOnlyBeAbleToSeeItsOwnUnpublishedAuctions() {


        auctionService.createAuction(auction);

        Auction auctionLambda = new Auction(lambdaUser, new RealClock(), item, endingDate, reservePrice, minimumOffer);
        auctionService.createAuction(auctionLambda);

        List<Auction> auctions = auctionService.getAvailableAuctionsForUser(lambdaUser);

        Assert.assertNotNull(auctions);
        Assert.assertTrue(auctions.size() == 1);
        Assert.assertTrue(auctions.get(0).getState() == AuctionState.CREATED);
        Assert.assertTrue(auctions.get(0) == auctionLambda);
    }

    @Test
    public void UserShouldSeeAllPublishedAuctions() {

        auctionService.createAuction(auction);
        auction.publish();

        List<Auction> auctions = auctionService.getAvailableAuctionsForUser(lambdaUser);

        Assert.assertNotNull(auctions);
        Assert.assertFalse(auctions.isEmpty());

    }


}
