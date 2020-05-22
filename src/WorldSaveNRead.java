import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;

public class WorldSaveNRead{

    String Seed = "";
    Chunk[][] chunks;
    public static void Read(){


    }
    public static void Save(String GameName, Chunk[][] chunk){
        boolean success = (new File("./Saves/"+GameName)).mkdirs();
        PrintWriter pw = null;
        JSONObject jo = new JSONObject();
        Gson gson = new Gson();
        for(int z=0; z<chunk.length; z++) {
            for (int x = 0; x < chunk[z].length; x++) {
                jo.put("x", x);
                jo.put("z", z);
                jo.put("blocks", gson.toJson(chunk[x][z].getBlocks()));
                try {
                    pw = new PrintWriter(new FileWriter("./Saves/"+GameName + "/"+x+"_"+z+".json", false));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pw.write(jo.toJSONString());

                pw.flush();
                pw.close();
            }
        }
    }

    public static void SaveChunk (String GameName, Chunk[][] chunk, int x, int z){
        PrintWriter pw = null;
        JSONObject jo = new JSONObject();
        Gson gson = new Gson();
                jo.put("x", x);
                jo.put("z", z);
                jo.put("blocks", gson.toJson(chunk[x][z].getBlocks()));
                try {
                    pw = new PrintWriter(new FileWriter("./Saves/"+GameName + "/"+x+"_"+z+".json", false));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pw.write(jo.toJSONString());

                pw.flush();
                pw.close();

    }




    public static void Update(){


    }
}


