package observable;

import auction.Alert;

public interface Observateur {
	public void update(Alert alert);
}
