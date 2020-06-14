package aprocraft.eq;

/**
 * Klasa implementuj�ca przedmioty
 * Obiekt Item ma swoje id, nazw� i ilo�� danego przedmiotu
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
     * Metoda zwraca ilo�c przedmiot�w przypisanych do danego obiektu Item
     *
     * @return ilo�� przedmiot�w
     */
    public int getSize() {
        return size;
    }

    /**
     * Metoda ustawiaj�ca ilo�c przedmiot�w przypisanych do danego obiektu Item
     *
     * @param size ilo�� przedmiot�w jak� chcem ustawi�
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * Metoda zwi�kszaj�ca parametr size o jeden
     */
    public void AddOne() {
        size += 1;
    }


}
