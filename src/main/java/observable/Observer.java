package observable;

import auction.Alert;

public interface Observer {
    public void receiveAlert(Alert alert);
}
