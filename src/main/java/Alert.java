public class Alert {

    private String message;
    private Auction source;

    public Alert() {
    }

    public Alert(Auction source, String message) {
        this.message = message;
        this.source = source;
    }

    public String getMessage() {
        return this.message;
    }

}
