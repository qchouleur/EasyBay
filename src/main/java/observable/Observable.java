package observable;

import auction.Alert;

public interface Observable {
	
	  public void addObservateur(Observateur obs);
	  public void updateObservateur(Alert alert);
	  public void delObservateur();
	  
	}
