import java.util.ArrayList;
import java.util.List;

public class AuctionService {

    private final List<Auction> auctions = new ArrayList<Auction>();

    public void createAuction(Auction auction) {

        auctions.add(auction);
    }

    public List<Auction> getAvailableAuctionsForUser(User lambdaUser) {

        List<Auction> visibleAuctions = new ArrayList<Auction>();

        for (Auction auction : auctions) {
            if (auction.getState() == AuctionState.CREATED &&
                    auction.getPublisher() != lambdaUser) {
                continue;
            }

            visibleAuctions.add(auction);
        }

        return visibleAuctions;
    }

}
