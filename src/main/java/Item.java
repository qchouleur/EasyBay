public class Item {

    private String id;
    private String description;

    public Item()  {

    }

    public Item(String id, String description) {
        this.id = id;
        this.description = description;
    }


    public String id() {
        return this.id;
    }
}
