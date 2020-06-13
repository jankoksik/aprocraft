package aprocraft.eq;

/**
 * Klasa implementuj�ca przedmioty
 * Obiekt Item ma swoje id, nazw� i ilo�� danego przedmiotu
 */
public class Item {
    private int id = 0;
    private String Name;
    private int size = 0;
    //public int pos;

    /**
     * Konstruktor obiektu Item
     *
     * @param id idetyfikator obiektu Item
     */
    public Item(int id) {
        this.id = id;
        //pos = -1;
    }

    /*public void setPosition(int pos) {
        if(this.pos == -1)
            this.pos = pos;
    }*/

    /**
     * Metoda zwraca id danego obiektu Item
     *
     * @return zwraca id Danrgo obiktu Item
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
