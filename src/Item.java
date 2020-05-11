public class Item {
    private int id = 0;
    private String Name;
    private int size = 0;

    public Item(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void AddOne() {
        size += 1;
    }


}
