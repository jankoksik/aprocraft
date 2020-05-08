import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.HashMap;

public class SaveNReadJson {


    // Klasa na szybko nie jest dopracowana
    public static <E> void save (String[] name, E[] key, String filename) throws FileNotFoundException {
        JSONObject jo = new JSONObject();
        if(name.length != key.length)
        {
            System.err.println("Invalid Input! : size of name array  must be as big as key is");
            System.exit(1);
        }
        for(int i=0; i < key.length; i++){
            jo.put(name[i], key[i]);
        }
        PrintWriter pw = new PrintWriter("./data/"+filename+".json");
        pw.write(jo.toJSONString());

        pw.flush();
        pw.close();
}

    public static HashMap<String, Integer> readControls (String filename) throws IOException, ParseException {
        HashMap<String, Integer> controls = new HashMap<>();
        Object obj = new JSONParser().parse(new FileReader("./data/"+filename+".json"));
        JSONObject jo = (JSONObject) obj;
        Field fld[] = Controls.class.getDeclaredFields();
        for(int i=0; i< fld.length; i++)
        {
            long val =  (Long) jo.get(fld[i].getName());
            controls.put( fld[i].getName(), (int)val);
        }
        return  controls;
    }

    public static void applyCOntrols(HashMap<String, Integer> ControlsMap){
        Controls.setUse(ControlsMap.get("use"));
        Controls.setDrop(ControlsMap.get("drop"));
        Controls.setForward(ControlsMap.get("forward"));
        Controls.setSprint(ControlsMap.get("sprint"));
        Controls.setDestroy(ControlsMap.get("destroy"));
        Controls.setRight(ControlsMap.get("right"));
        Controls.setInventory(ControlsMap.get("inventory"));
        Controls.setDown(ControlsMap.get("down"));
        Controls.setNextItemInInventory(ControlsMap.get("NextItemInInventory"));
        Controls.setPrevItemInInventory(ControlsMap.get("PrevItemInInventory"));
        Controls.setCrouch(ControlsMap.get("crouch"));
        Controls.setLeft(ControlsMap.get("left"));
        Controls.setAttack(ControlsMap.get("attack"));
        Controls.setBackward(ControlsMap.get("backward"));
        Controls.setUp(ControlsMap.get("up"));
        Controls.setPlace(ControlsMap.get("place"));
        Controls.setJump(ControlsMap.get("jump"));
    }
    public static void SaveControls(String filename) throws IllegalAccessException, FileNotFoundException {
        JSONObject jo = new JSONObject();
        Field fld[] = Controls.class.getDeclaredFields();
      //  String[] names = new String[fld.length];
      //  for(int i=0; i< fld.length; i++)
      //  {
      //      names[i] = fld[i].getName();
      //  }
        for(int i=0; i < fld.length; i++){
            jo.put(fld[i].getName(), fld[i].getInt(null));
        }
        PrintWriter pw = new PrintWriter("./data/"+filename+".json");
        pw.write(jo.toJSONString());

        pw.flush();
        pw.close();



    }




}
