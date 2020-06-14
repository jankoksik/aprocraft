package aprocraft.mob;

import aprocraft.world.World;
import org.joml.Vector3f;

import java.util.ArrayList;

/**
 * Klasa odpiwadając za wyznaczanie ścieżek po których wyznaczają się moby
 */
public class Pathfind {

    World map;
    float sx, sy, sz;
    float dx, dy, dz;
    int cx, cy, cz;
    int h = 2;
    boolean check = false;

    public void setSx(float sx) {
        this.sx = sx;
    }

    public void setSy(float sy) {
        this.sy = sy;
    }

    public void setSz(float sz) {
        this.sz = sz;
    }

    public void setDx(float dx) {
        this.dx = dx;
    }

    public void setDy(float dy) {
        this.dy = dy;
    }

    public void setDz(float dz) {
        this.dz = dz;
    }

    public void setH(int h) {
        this.h = h;
    }

    public Pathfind(World map, int h) {
        this.map = map;
        this.h = h;
    }

    /**
     * Meotda odpowiadająca za wyznaczanie ścieżek w dwóch wyiarach
     *
     * @return tablica zawerającawyznaczoną ścieżkę
     */
    public ArrayList<Vector3f> FindRoute2D() {
        ArrayList<Vector3f> route = new ArrayList<>();

        cx = (int) sx;
        cz = (int) sz;
        while (cx != (int) dx && cz != (int) dz) {
            check = false;
            if (cx != dx) {
                int d = (int) dx - cx;
                if (d > 0 && !Colision(cx + 1, cy, cz)) {
                    cx += 1;
                    route.add(new Vector3f(cx, cy, cz));
                    check = true;
                } else if (d < 0 && !Colision(cx - 1, cy, cz)) {
                    cx -= 1;
                    route.add(new Vector3f(cx, cy, cz));
                    check = true;
                }

            }
            if (cz != dz) {
                int d = (int) dz - cz;
                if (d > 0 && !Colision(cx, cy, cz + 1)) {
                    cz += 1;
                    route.add(new Vector3f(cx, cy, cz));
                    check = true;
                } else if (d < 0 && !Colision(cx, cy, cz - 1)) {
                    cz -= 1;
                    route.add(new Vector3f(cx, cy, cz));
                    check = true;
                }

            }
            if (!check && (cx != (int) dx || cz != (int) dz)) {
                return null;
            }
        }
        return route;
    }


    private float max(float a, float b) {
        if (a > b)
            return a;
        else return b;
    }

    private float min(float a, float b) {
        if (a < b)
            return a;
        else return b;
    }

    /**
     * Metoda odpowiadająca za wykrywanie kolizji
     *
     * @param x współrzędna x moba
     * @param y współrzędna y moba
     * @param z współrzędna z moba
     * @return wartość bolean czy na danej współrzędnej znajduje się blok
     */
    private boolean Colision(int x, int y, int z) {
        for (int i = 0; i < h; i++) {
            if (map.getBlock(x, y + i, z) != null) return true;
        }
        if (map.getBlock(x, y - 1, z) == null) return true;
        return false;
    }


}
