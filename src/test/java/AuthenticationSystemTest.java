import org.junit.Assert;
import org.junit.Test;

public class AuthenticationSystemTest {

    @Test
    public void AuthenticationWithGoodUserShouldSucceed() {

        AuthenticationSystem authSystem = new AuthenticationSystem();

        String login = "Nero";
        String password = "chou78200";

        Assert.assertTrue("authentication supposed to succeed failed",
                authSystem.authenticate(login, password));
    }

    @Test
    public void AuthenticationWithBadUserShouldFail() {
        AuthenticationSystem authSystem = new AuthenticationSystem();

        String login = "Chou";
        String password = "nero75016";

        Assert.assertFalse("authentication supposed to fail succeeded",
                authSystem.authenticate(login, password));
    }

}
