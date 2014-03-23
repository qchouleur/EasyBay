package auction;
import java.util.HashMap;

public class AuthenticationSystem {


    private static final HashMap<String, String> users = new HashMap<String, String>();


    public AuthenticationSystem() {
        initUser();
    }

    public static void initUser() {
        users.put("Nero", "chou78200");
        users.put("Chat", "nero91300");
        users.put("Godffroy", "ilovecocks");
        users.put("Jean", "iwinulose");
        users.put("Lucky", "f4sterThanSh4d0w");
    }

    public boolean authenticate(String login, String password) {

        if (!users.containsKey(login)) {
            return false;
        }
        String storedPassword = users.get(login);
        return storedPassword.equals(password);
    }
}
