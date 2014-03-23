package time;

import java.util.Date;

public class FakeClock implements Clock {

    private final Date date;

    public FakeClock(Date date) {

        this.date = date;
    }

    public Date now() {
        return this.date;
    }
}
