/**
 * Klasa implementująca przedmioty
 * Obiekt Item ma swoje id, nazwę i ilość danego przedmiotu
 */
public class Item {
    private int id = 0;
    private String Name;
    private int size = 0;

    /**
     * Konstruktor obiektu Item
     * @param id idetyfikator obiektu Item
     */
    public Item(int id) {
        this.id = id;
    }

    /**
     * Metoda zwraca id danego obiektu Item
     * @return zwraca id Danrgo obiktu Item
     */
    public int getId() {
        return id;
    }

    /**
     * Metoda zwraca ilośc przedmiotów przypisanych do danego obiektu Item
     * @return ilość przedmiotów
     */
    public int getSize() {
        return size;
    }

    /**
     * Metoda ustawiająca ilośc przedmiotów przypisanych do danego obiektu Item
     * @param size ilość przedmiotów jaką chcem ustawić
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Metoda zwiększająca parametr size o jeden
     *
     */
    public void AddOne() {
        size += 1;
    }


}
