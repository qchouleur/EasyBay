package observable;

import auction.Alert;

public interface Observable {
	
  public void addObserver(ObserverSeller obs);
  public void addObserver(ObserverBuyer obs);
  public void updateOberverBuyer(Alert alert);
  public void updateOberverSeller(Alert alert);
  public void delObserverBuyer();
  public void delObserverSeller();
  
}
