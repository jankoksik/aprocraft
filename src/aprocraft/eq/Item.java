package aprocraft.eq;

/**
 * Klasa implementuj¹ca przedmioty
 * Obiekt Item ma swoje id, nazwê i iloœæ danego przedmiotu
 */
public class Item {
    private int id = 0;
    private String Name;
    private int size = 0;

    /**
     * Konstruktor obiektu Item
     *
     * @param id idetyfikator obiektu Item
     */
    public Item(int id) {
        this.id = id;
    }

    /**
     * Metoda zwraca id danego obiektu Item
     *
     * @return id Danrgo obiktu Item
     */
    public int getId() {
        return id;
    }

    /**
     * Metoda zwraca iloœc przedmiotów przypisanych do danego obiektu Item
     *
     * @return iloœæ przedmiotów
     */
    public int getSize() {
        return size;
    }

    /**
     * Metoda ustawiaj¹ca iloœc przedmiotów przypisanych do danego obiektu Item
     *
     * @param size iloœæ przedmiotów jak¹ chcem ustawiæ
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Metoda zwiêkszaj¹ca parametr size o jeden
     */
    public void AddOne() {
        size += 1;
    }


}
