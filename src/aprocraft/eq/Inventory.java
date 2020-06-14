package aprocraft.eq;

import aprocraft.APROCraft;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;


/**
 * Klasa implementująca ekwipunku gracza
 */
public class Inventory {

    //stack - liczba blok�w w stacku
    // space - ilosc kratek na przedmioty

    private ArrayList<Item> eq = new ArrayList<>();
    private int stack;
    private int space;
    private int w;
    private ArrayList<Item> map = new ArrayList<>();

    /**
     * Konstruktor klasy
     * @param stack ile przedmiotów może zawierać jeden obiekt
     * @param w szerokośc ekwipunku
     * @param h wysokość ewkipunku
     */
    public Inventory(int stack, int w, int h) {
        this.stack = stack;
        this.space = w * h;
        this.w = w;
    }

    //malo optymalne

    /**
     * zwraca  listę przedmiotów zawartych w ekwipunku
     * @return lista obiektów Irem
     */
    public List<Item> getEq() {
        ArrayList<Item> sum = new ArrayList<>();
        int pos = 0;
        for (Item i : eq) {
            i.setSize(stack);
            //i.pos = pos++;
            sum.add(i);
            //getEq(i.getId()).setSize(stack);
        }
        for (Item i : map) {
            // Item add = new Item(i.getId());
            // add.setSize(i.getSize());
            sum.add(i);
        }
        return sum;
    }

    /**
     * Metoda weryfikująca czyw ekwpipunku znajduje się dany obiket ( sprawdzenie po typie obiektu)
     * @param n typ obiektu
     * @return wartość boolean cy taki przedmiot został znaleziony
     */
    private boolean contain(Item n) {
        for (Item k : map) {
            if (k.getId() == n.getId())
                return true;
        }
        return false;
    }

    /**
     * Metoda weryfikująca czyw ekwpipunku znajduje się dany obiket ( sprawdzenie po id obiektu)
     * @param id identyfikator obiektu
     * @return wartość boolean cy taki przedmiot został znaleziony
     */
    private boolean contain(int id) {
        for (Item k : map) {
            if (k.getId() == id)
                return true;
        }
        return false;
    }

    private Item get(Item n) {
        for (Item k : map) {
            if (k.getId() == n.getId())
                return k;
        }
        return null;
    }

    /**
     * Meotda zwracająca wielkość ( pojemność ekwipunku)
     * @return wielkość ekwipunku
     */
    public int GetSpace() {
        return space;
    }

    /**
     * Meotda zwracająca szerokość ekwipunku
     * @return szrokość
     */
    public int GetW() {
        return w;
    }

    /**
     *
     * @param id
     * @return
     */
    private Item get(int id) {
        for (Item k : map) {
            if (k.getId() == id)
                return k;
        }
        return null;
    }

    private boolean removeStack(Item i) {
        for (Item k : map) {
            if (k.getId() == i.getId()) {
                map.remove(k);
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param id
     * @return
     */
    private boolean removeStack(int id) {
        for (Item k : map) {
            if (k.getId() == id) {
                map.remove(k);
                return true;
            }
        }
        return false;
    }

    /**
     * Metoda usuwająca stack przedmiotów z ewkipunku
     * @param id identyfokator usuanwgo obiektu
     * @return wartość boolean informująca czy operacja powiodła się
     */
    private boolean removeStackEq(int id) {
        for (Item k : eq) {
            if (k.getId() == id) {
                eq.remove(k);
                return true;
            }
        }
        return false;
    }


    /**
     * Metoda usuwająca pojedyńczy przedmiot z obiektu
     * @param id id obiektu
     * @return wartość boolean informująca czy operacja powiodła się
     */
    public boolean removeOne(int id) {
        for (Item k : map) {
            if (k.getId() == id) {
                int nmbr = k.getSize() - 1;
                if (nmbr == 0)
                    map.remove(k);
                else
                    k.setSize(nmbr);
                return true;
            }
        }
        for (Item k : eq) {
            if (k.getId() == id) {
                eq.remove(k);
                Item n = new Item(id);
                n.setSize(stack - 1);
                map.add(n);
                return true;
            }
        }

        return false;
    }

    private Item getEq(int id) {
        for (Item k : eq) {
            if (k.getId() == id)
                return k;
        }
        return null;
    }

    /**
     * Metoda zwracająca ilość przemitoów wchodzących w skałd danego obiektu w ekwipunku
     * @param id identyfikator przedmiotu
     * @return zwraca ilość przemiotów
     */
    public int getNmbrOfItems(int id) {
        int nmbr = 0;
        for (Item i : eq) {
            if (i.getId() == id) {
                nmbr += stack;
            }
        }
        for (Item i : map) {
            if (i.getId() == id) {
                nmbr += i.getSize();
            }
        }

        return nmbr;
    }



    /**
     * Metoda odpowiadająca za usuwanie obiektów z ekwipunku
     * @param id identyfikator obiektu
     * @param nmbr ilość danych obiektów
     * @param force
     * @return force -> true - usun tyle ile sie da /  false - nie usunie itemow jesli niema wystarczajacej ilosci
     */
    public boolean remove(int id, int nmbr, boolean force) {
        if (nmbr > getNmbrOfItems(id) && !force)
            return false;
        int br = nmbr / stack;
        int del = 0;
        for (; br > 0; br--, del++) {
            if (!removeStackEq(id))
                break;
        }
        int left = nmbr - del * stack;
        if (contain(id) && left > 0) {
            int v = get(id).getSize() - left;
            if (v <= 0) {
                map.remove(get(id));
            } else {
                get(id).setSize(v);
            }
        }
        return true;
    }

    /*private int findFreeSlot() {
        int res = 0;
        for (Item i : map) {
            if(i.pos != res) {
                for (Item j : eq) {
                    if(j.pos != res)
                        return res;
                }
            }
            res++;
        }

        return res;
    }*/

    /**
     * Metoda odpowiadająca za dodawanie obiektów do ekwipunku
     * @param itemId identyfikator dodanego obiektu
     * @return wartość boolean informująca czy w ekwipunku było miejsce na dodanie przedmiotu,
     *  tzn. czy dodanie przedmiotu zakończyło się powodzeniem
     */
    public boolean addItem(int itemId) {
        Item n = new Item(itemId);
        if (!map.isEmpty() && contain(n)) {
            get(n.getId()).AddOne();
            if (get(n.getId()).getSize() == stack) {
                //int p = findFreeSlot();
                //n.setPosition(p);
                eq.add(n);
                eq.get(eq.indexOf(n)).setSize(stack);
                removeStack(n.getId());
            }
            return true;
        } else {
            if (eq.size() + map.size() < space) {
                n.AddOne();
                //int p = findFreeSlot();
                //n.setPosition(p);
                map.add(n);
                return true;
            } else {
                return false;
            }

        }


    }

    //

    /**
     * Metoda dodająca obiekt do ekwipunku
     * @param itemId identyfikator dodawanego obiektu
     * @param NmbrOfItems ilość przedmiotów wchodzących w skałd obiektu
     * @param force force -> true - dodaj tyle ile sie da /  false - nie doda itemow jesli niema miejsca
     * @return wartość boolean informująca czy operacja powiodła się
     */
    public boolean addItem(int itemId, int NmbrOfItems, boolean force) {
        Item n = new Item(itemId);
        int stacks = NmbrOfItems / stack;
        if ((stacks + eq.size() + map.size() > space) && !force)
            return false;

        int miejsce = space - eq.size() - map.size();
        if (miejsce < stacks + 1 && !force)
            return false;
        n.setSize(stack);
        //int p = findFreeSlot();
        //n.setPosition(p);
        int added = 0;
        for (int i = stacks; i > 0 && miejsce > 0; i--, miejsce--, added++) {
            //p = findFreeSlot();
            //n.setPosition(p);
            eq.add(n);
        }
        int left = (NmbrOfItems - (added * stack));
        if (left > 0 && get(n.getId()) != null) {
            while (left > 0 && get(n.getId()) != null) {
                get(n.getId()).AddOne();
                left--;
            }
            if (left > 0 && eq.size() + map.size() < space) {
                n.setSize(left);
                //n.pos = left;
                //p = findFreeSlot();
                //n.setPosition(p);
                map.add(n);
                return true;
            }
        } else if (eq.size() + map.size() < space && left > 0) {
            Item k = new Item(itemId);
            k.setSize(left);
            //p = findFreeSlot();
            //k.setPosition(p);
            //k.pos = left;
            map.add(k);
            return true;
        }
        return true;
    }

    //przekaz itemy do innego eq
    public boolean passItem(Inventory other, int id, int nmbr) {
        if (remove(id, nmbr, false)) {
            if (other.addItem(id, nmbr, false)) {
                return true;
            } else {
                addItem(id, nmbr, false);
                return false;
            }
        }
        return false;
    }

    /**
     * Metoda odpoiwadająca za zapisanie stanu ekwpiunku
     * @param filename nazwa pliku typu Json, w którym zapisana zostanie informacja o stanie ekwipunku
     * @return
     */
    public boolean saveEq(String filename) {
        try {
            Gson gson = new Gson();
            PrintWriter pw = null;

            pw = new PrintWriter("./data/" + filename + ".json");

            pw.write(gson.toJson(getEq()));

            pw.flush();
            pw.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }


    }

    public void readEq(String filename) {
        try {
            Gson gson = new Gson();
            boolean success = (new File("./Saves/" + APROCraft.GAME_NAME)).mkdirs();
            FileReader obj = new FileReader("./Saves/" + APROCraft.GAME_NAME + "/" + filename + ".json");
            Type type = new TypeToken<ArrayList<Item>>() {
            }.getType();
            ArrayList<Item> lista = gson.fromJson(obj, type);

            for (Item i : lista) {
                if (i.getSize() == stack) {
                    eq.add(i);
                } else {
                    map.add(i);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
