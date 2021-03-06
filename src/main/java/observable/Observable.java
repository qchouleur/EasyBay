package observable;

import auction.Alert;

public interface Observable {

    public void add(Observer observer);

    public void notify(Alert alert);

    public void notify(Alert alert, Observer... ignoredObservers);

}
