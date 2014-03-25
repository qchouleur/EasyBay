package observable;

import auction.Alert;

public interface ObservableByBuyer {
    public void add(BuyerObserver observer);

    public void notify(Alert alert);

    public void notify(Alert alert, Observer... ignoredObservers);
}
