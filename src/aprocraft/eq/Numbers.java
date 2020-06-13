package aprocraft.eq;

import java.util.HashMap;

public class Numbers {
    public static HashMap<Integer, int[]> Nmbrs = new HashMap<>(); // float[] :  [0] - x, [1] - y, [2] - w, [3] - h
    public static int w = 512, h = 512;

    public Numbers() {
        Nmbrs.put(0, new int[]{400, 91, 40, 67});
        Nmbrs.put(1, new int[]{44, 355, 24, 65});
        Nmbrs.put(2, new int[]{244, 158, 41, 66});
        Nmbrs.put(3, new int[]{315, 91, 42, 67});
        Nmbrs.put(4, new int[]{285, 158, 45, 66});
        Nmbrs.put(5, new int[]{330, 158, 40, 66});
        Nmbrs.put(6, new int[]{370, 158, 40, 66});
        Nmbrs.put(7, new int[]{68, 355, 39, 65});
        Nmbrs.put(8, new int[]{357, 91, 43, 67});
        Nmbrs.put(9, new int[]{410, 158, 40, 66});
    }

    public static HashMap<Integer, int[]> getNmbrs() {
        return Nmbrs;
    }

    public static int getW() {
        return w;
    }

    public static int getH() {
        return h;
    }
}
