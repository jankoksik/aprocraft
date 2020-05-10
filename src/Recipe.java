import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

public class Recipe {
    int w=0;
    int [] craft;
    int result = 0;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }



    public Recipe(int width, int height)
    {
        craft = new int[width*height];
        w = width;
    }


    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int[] getCraft() {
        return craft;
    }

    public void setCrafting(int[] craft) {
        this.craft = craft;
    }

    public void savceRecipe(String file) throws IllegalAccessException {

        JSONArray ja = new JSONArray();

        JSONObject jo = new JSONObject();
        Gson gson = new Gson();
        jo.put("width", w);
        jo.put("crafting", gson.toJson(craft));
        jo.put("result", result);
        ja.add(jo);
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new FileWriter("./recipes/"+file+".json", true));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        pw.write(ja.toJSONString());

        pw.flush();
        pw.close();
    }


    @Override
    public boolean equals(Object o) {
        if(o==this)
            return true;
        if (!(o instanceof Recipe)) {
            return false;
        }
        Recipe c = (Recipe) o;

        return Integer.compare(w, c.w) == 0
                && Arrays.compare(craft, c.craft) == 0;
    }


    }
