package modell;

public class Shoppinglist {
    private int id;
    private String item;
    private int quantily;

    public Shoppinglist() {
    }

    public Shoppinglist(String item, int quantily) {
        this.item = item;
        this.quantily = quantily;
    }

    public Shoppinglist(int id, String item, int quantily) {
        this.id = id;
        this.item = item;
        this.quantily = quantily;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public int getQuantily() {
        return quantily;
    }

    public void setQuantily(int quantily) {
        this.quantily = quantily;
    }

    public int getId() {
        return this.id;
    }

}
