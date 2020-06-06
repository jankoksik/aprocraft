package aprocraft.eq;

import java.util.Comparator;

/**
 * Klasa implementująca przedmioty
 * Obiekt aprocraft.eq.Item ma swoje id, nazwę i ilość danego przedmiotu
 */
public class Item implements Comparable {
    private int id = 0;
    private String Name;
    private int size = 0;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    private int x =0;

    /**
     * Konstruktor obiektu aprocraft.eq.Item
     * @param id idetyfikator obiektu aprocraft.eq.Item
     */
    public Item(int id) {
        this.id = id;
    }

    /**
     * Metoda zwraca id danego obiektu aprocraft.eq.Item
     * @return zwraca id Danrgo obiktu aprocraft.eq.Item
     */
    public int getId() {
        return id;
    }

    /**
     * Metoda zwraca ilośc przedmiotów przypisanych do danego obiektu aprocraft.eq.Item
     * @return ilość przedmiotów
     */
    public int getSize() {
        return size;
    }

    /**
     * Metoda ustawiająca ilośc przedmiotów przypisanych do danego obiektu aprocraft.eq.Item
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
        size ++;
    }

    public String getName() {
        return Name;
    }

    public static Comparator<Item> ItemComp = new Comparator<Item>() {

        @Override
        public int compare(Item e1, Item e2) {
            return Integer.compare(e1.getX(), e2.getX());
        }
    };

   @Override
   public int compareTo(Object o) {
       Item i = (Item)o;
       if(i.getX() == this.getX())
       return  0;
       else if(this.getX() > i.getX())
           return  1;
       else
           return  -1;
   }
}
