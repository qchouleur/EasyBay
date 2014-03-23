package observable;

import auction.Alert;

public interface Observable {
	
  public void addObserver(ObserverSeller obs);
  public void addObserver(ObserverBuyer obs);
  public void updateObserverBuyer(Alert alert);
  public void updateObserverSeller(Alert alert);
  public void delObserverBuyer();
  public void delObserverSeller();
  
}
