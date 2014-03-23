package time;

import java.util.Date;

public class RealClock implements Clock {

    public Date now() {
        return new Date();
    }
}
