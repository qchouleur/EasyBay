import junit.framework.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class AuctionTest {

    private static Item item;
    private static Date auctionEndingDate;
    private static BigDecimal reservePrice;
    private static BigDecimal minimalOffer;
    private static User auctionCreator;


    @BeforeClass
    public static void setUp() throws ParseException {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

        item = new Item();
        auctionEndingDate = dateFormat.parse("20501212");
        reservePrice = new BigDecimal(1.3);
        minimalOffer = new BigDecimal(1.3);

        auctionCreator = new User();

    }

    @Test
    public void AuctionCreationShouldSucceed() {

        Auction auction = auctionCreator.createAuction(item, auctionEndingDate, reservePrice, minimalOffer);
        Assert.assertEquals(auction.getState(), AuctionState.CREATED);
    }

    @Test(expected = IllegalArgumentException.class)
    public void AuctionCreationWithInvalidEndingDateShouldFail() throws ParseException {


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Date auctionEndingDate = simpleDateFormat.parse("15/06/1989");

        Auction auction = auctionCreator.createAuction(item, auctionEndingDate, reservePrice, minimalOffer);

    }

    @Test(expected = IllegalArgumentException.class)
    public void AuctionCreationWithInvalidReservePriceShouldFail() throws ParseException {

        // A reserve price of 0 is valid, it is optional
        BigDecimal reservePrice = new BigDecimal(-0.1);

        Auction auction = auctionCreator.createAuction(item, auctionEndingDate, reservePrice, minimalOffer);

    }

    @Test(expected = IllegalArgumentException.class)
    public void AuctionCreationWithInvalidMinimalOfferShouldFail() throws ParseException {
        // A minimalOffer of 0 is valid, it means that the vendor accept any price
        BigDecimal minimalOffer = new BigDecimal(-0.1);

        Auction auction = auctionCreator.createAuction(item, auctionEndingDate, reservePrice, minimalOffer);
    }

    @Test
    public void AuctionPublicationShouldChangeState() {

        Auction auction = auctionCreator.createAuction(item, auctionEndingDate, reservePrice, minimalOffer);
        auction.publish();


        Assert.assertEquals(auction.getState(), AuctionState.PUBLISHED);
    }

    @Test
    public void AuctionCancellationShouldChangeState() {
        Auction auction = auctionCreator.createAuction(item, auctionEndingDate, reservePrice, minimalOffer);
        auction.cancel();

        Assert.assertEquals(auction.getState(), AuctionState.CANCELED);
    }

    @Test
    public void AuctionWithReachedEndingDateShouldBeClosed() {

        // The provided ending date is expired by the time we get to the
        // get state method
        Auction auction = auctionCreator.createAuction(item, new Date(), reservePrice, minimalOffer);
        Assert.assertEquals(auction.getState(), AuctionState.CLOSED);
    }

    @Test
    public void HighestBidderShouldWinTheAuction() {
        User loser = new User();
        User winner = new User();
        Auction auction = auctionCreator.createAuction(item, new Date(), reservePrice, minimalOffer);

        loser.bid(auction, minimalOffer);
        winner.bid(auction, new BigDecimal(400));

        Assert.assertEquals(winner, auction.getHighestOffer().getBidder());
    }

    @Test(expected = IllegalArgumentException.class)
    public void BidWithPriceUnderAuctionReservePriceShouldFail() {
        User bidder = new User();

        Auction auction = auctionCreator.createAuction(item, new Date(), reservePrice, minimalOffer);

        bidder.bid(auction, new BigDecimal(0));

    }

    @Test
    public void auctionReservePriceShouldBeReachable() {
        User bidder = new User();

        Auction auction = auctionCreator.createAuction(item, new Date(), new BigDecimal(500), minimalOffer);

        bidder.bid(auction, new BigDecimal(400));
        Assert.assertFalse(auction.isReservePriceReached());
        bidder.bid(auction, new BigDecimal(500));
        Assert.assertTrue(auction.isReservePriceReached());
    }

    @Test(expected = IllegalStateException.class)
    public void UserCannotBidOnUnpublishedOffer() {

        User lambdaUser = new User();

        Auction auction = new Auction(auctionCreator, item, auctionEndingDate, reservePrice, minimalOffer);

        Offer offer = new Offer(lambdaUser, new BigDecimal(400));
        auction.makeOffer(offer);

    }

    @Test(expected = IllegalArgumentException.class)
    public void UserCannotBidHisOwnAuction() {

        Auction auction = new Auction(auctionCreator, item, auctionEndingDate, reservePrice, minimalOffer);
        Offer offer = new Offer(auctionCreator, new BigDecimal(400));
        auction.makeOffer(offer);
    }

    @Test(expected = IllegalStateException.class)
    public void UserCannotBidOnCanceledAuction() {
        User lambdaUser = new User();

        Auction auction = auctionCreator.createAuction(item, auctionEndingDate, reservePrice, minimalOffer);
        auction.cancel();

        Assert.assertEquals(auction.getState(), AuctionState.CANCELED);
        auction.makeOffer(new Offer(lambdaUser, minimalOffer));
    }

    @Test(expected = IllegalStateException.class)
    public void UserCannotBidOnClosedAuction() {
        User lambdaUser = new User();

        Auction auction = auctionCreator.createAuction(item, new Date(), reservePrice, minimalOffer);

        Assert.assertEquals(auction.getState(), AuctionState.CLOSED);
        auction.makeOffer(new Offer(lambdaUser, minimalOffer));
    }


}
