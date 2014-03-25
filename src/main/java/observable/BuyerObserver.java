package observable;

import auction.Alert;

public interface BuyerObserver extends Observer {

    <T extends Alert> void unsuscribe(Class<T> alert);

    <T extends Alert> void subscribe(Class<T> alert);

    <T extends Alert> boolean hasSubscriptionTo(Class<T> alert);
}
