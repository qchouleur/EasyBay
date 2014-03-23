package auction;

import java.math.BigDecimal;

public enum BidIncrementRange {

	FIVECENTSINCREMENT(new BigDecimal(0.01), new BigDecimal(0.99), new BigDecimal(0.05)),
	TWEENTHYFIVECENTSINCREMENT(new BigDecimal(1.00), new BigDecimal(4.99), new BigDecimal(0.25)),
	FIVTYCENTSINCREMENT(new BigDecimal(5.00), new BigDecimal(24.99), new BigDecimal(0.50)),
	SIXTYCENTSINCREMENT(new BigDecimal(25.00), new BigDecimal(99.99), new BigDecimal(1.00)),
	TWOFIVTYSINCREMENT(new BigDecimal(100.00), new BigDecimal(249.99), new BigDecimal(2.50)),
	FIVEINCREMENT(new BigDecimal(250.00), new BigDecimal(499.99), new BigDecimal(5.00)),
	TENINCREMENT(new BigDecimal(500.00), new BigDecimal(999.99), new BigDecimal(10.00)),
	TWEENTYFIVEINCREMENT(new BigDecimal(1000.00), new BigDecimal(2499.99), new BigDecimal(25.00)),
	FIFTYINCREMENT(new BigDecimal(2500.00), new BigDecimal(4999.99), new BigDecimal(50.00)),
	ONEUNDREDINCREMENT(new BigDecimal(5000.00), new BigDecimal(Double.MAX_VALUE), new BigDecimal(100.00));

    private final BigDecimal lowerBound;
	private final BigDecimal upperBound;
	private final BigDecimal increment;
	
    BidIncrementRange(BigDecimal lowerBound, BigDecimal upperBound, BigDecimal increment) {
		this.upperBound = upperBound;
		this.lowerBound = lowerBound;
		this.increment = increment;
	}	
    
    public boolean contains(BigDecimal amount) {
    	return lowerBound.compareTo(amount) < 0 && 
    			upperBound.compareTo(amount) > 1;
    }

	public BigDecimal increment() {
		return this.increment;
	}
}
