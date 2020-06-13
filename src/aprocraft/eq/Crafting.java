package aprocraft.eq;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Klasa odpowiedzialna za tworzenie przedmiotów.
 * Przedmioty tworzone są poprez umieszczanie ich w kwadracie 3x3.
 * Do stowrzenia danego przedmiotu odpowiedni skłądnick musi być dodany w opdowiednie pole
 */
public class Crafting {
    ArrayList<Recipe> recipes = new ArrayList<>();
    String Fn; // Json
    int w = 0;
    public int[] craft;

    public Crafting(int width, int height, String RecipFile) {
        craft = new int[width * height];
        w = width;
        Fn = RecipFile;
    }

    private Size GetDim(Recipe r) {
        Size n = new Size(r.getW(), r.getCraft().length / r.getW());
        return n;
    }

    /* eg.
    0	1	2

0	0	1	2

1	3	4	5

2	6	7	8
     */

    /**
     * Metoda odpowedziala za dodanie składnika we wskazanym przez gracza polu
     *
     * @param x      pierwsza współzędna pola w którym został dodany składnik
     * @param y      pierwsza współzędna pola w którym został dodany składnik
     * @param itemId id dodanego składnika
     * @param Eq     ekwipunek gracza, z którego dodany zostął skałdnik
     */
    public void PlaceItemInCrafting(int x, int y, int itemId, Inventory Eq) {
        if (Eq.removeOne(itemId)) {
            if (craft[w * y + x] != 0)
                Eq.addItem(craft[w * y + x]);

            craft[w * y + x] = itemId;
        }
    }

    public void GetItemFromCrafting(int x, int y, Inventory Eq) {
        if (craft[w * y + x] != 0) {
            Eq.addItem(craft[w * y + x]);
            craft[w * y + x] = 0;
        }
    }

    public Size GetCurrDim() {
        boolean found = false;
        int craftH = craft.length / w;
        int CornerX = 0, CornerY = 0, LX = 0, Ly = 0;
        for (int y = 0; y < craftH; y++) {
            for (int x = 0; x < w; x++) {
                if (craft[w * y + x] != 0) {
                    CornerY = y;
                    CornerX = x;
                    found = true;
                    break;

                }
            }
            if (found) {
                break;
            }
        }
        for (int y = 0; y < craftH; y++) {
            for (int x = 0; x < w; x++) {
                if (craft[w * y + x] != 0) {
                    LX = y;
                    Ly = x;
                }
            }
        }

        return new Size(LX - CornerX, Ly - CornerY);
    }

    /**
     * metoda konwertująca ArrayList do tablicy int
     *
     * @param integers Arrayslist integer
     * @return tablica int
     */
    private static int[] convertIntegers(ArrayList<Integer> integers) {
        int[] ret = new int[integers.size()];
        for (int i = 0; i < ret.length; i++) {
            ret[i] = integers.get(i).intValue();
        }
        return ret;
    }

    public int[] getSeperated() {
        ArrayList<Integer> curr = new ArrayList<>();
        int craftH = craft.length / w;
        boolean found = false;
        // aprocraft.eq.Size n = GetCurrDim();
        // int [] curr = new int[n.getW() * n.getH()];
        int CornerX = 0, CornerY = 0, LX = 0, Ly = 0;
        for (int y = 0; y < craftH; y++) {
            for (int x = 0; x < w; x++) {
                if (craft[w * y + x] != 0) {
                    CornerY = y;
                    CornerX = x;
                    found = true;
                    break;

                }
            }
            if (found) {
                break;
            }
        }
        for (int y = 0; y < craftH; y++) {
            for (int x = 0; x < w; x++) {
                if (craft[w * y + x] != 0) {
                    LX = x;
                    Ly = y;
                }
            }
        }
        for (int y = CornerY; y <= Ly; y++) {
            for (int x = CornerX; x <= LX; x++) {
                curr.add(craft[w * y + x]);
                //System.out.print(curr[iter] + " ");
            }
        }
        // System.out.println();
        return convertIntegers(curr);
    }

    /**
     * Metoda wczytująca z pliku JSon przepisy opisujące, jak należy stworzyć poszczególne przedmioty
     */
    public void LoadRecipes() {
        Object obj = null;
        try {
            obj = new JSONParser().parse(new FileReader("./recipes/" + Fn + ".json"));

            JSONObject jsonObject = (JSONObject) obj;

            JSONArray solutions = (JSONArray) jsonObject.get("recipes");

            Iterator iterator = solutions.iterator();
            while (iterator.hasNext()) {
                long val = 0;
                JSONObject jo = (JSONObject) iterator.next();
                //int[] craft = (int[]) jo.get("crafting");
                JSONArray jsonArray = (JSONArray) jo.get("crafting");
                int[] craft = new int[jsonArray.size()];
                for (int i = 0; i < jsonArray.size(); ++i) {
                    val = (Long) jsonArray.get(i);
                    craft[i] = (int) val;
                }
                val = (Long) jo.get("result");
                int ResId = (int) val;
                val = (Long) jo.get("Rn");
                int Rn = (int) val;
                val = (Long) jo.get("width");
                int w = (int) val;
                Recipe n = new Recipe(w, craft.length / w);
                n.setResult(ResId);
                n.setnR(Rn);
                n.setCrafting(craft);
                recipes.add(n);
                //iterator.next();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void unLoadRecipes() {
        recipes.clear();
    }

    private boolean CompareInt(int[] arr) {
        int[] cr = getSeperated();
        if (arr.length != cr.length)
            return false;
        for (int i = 0; i < cr.length; i++) {
            if (arr[i] != cr[i])
                return false;
        }
        return true;
    }

    public Recipe checkPattern() {
        if (!recipes.isEmpty()) {
            Size curr = GetCurrDim();
            int w = curr.w + 1;//
            int h = curr.h + 1;//
            Recipe r = new Recipe(w, h);
            int[] ready = getSeperated();
            r.setCrafting(ready);
            r.setW(w);
            for (Recipe recS : recipes) {
                if (recS.getW() == r.getW()) {
                    if (CompareInt(recS.getCraft()))
                        return recS;
                }
            }
        }
        return null;
    }

    public int ShowPatternMatchinResult() {
        Recipe r = checkPattern();
        if (r != null)
            return r.result;
        else
            return -1;
    }

    /**
     * Metoda dodająca do ekwipunku gracza stworzony przedmiot
     *
     * @param Eq ekwipunek gracza do ktrego dodany ma zostać przedmiot
     */
    public void Craft(Inventory Eq) {
        Eq.addItem(ShowPatternMatchinResult());
    }


}
