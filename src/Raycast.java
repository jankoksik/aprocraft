import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Raycast {
    private List<Vector3f> points;
    private Player player;

    public Raycast(Player player) {
        this.player = player;

        points = new ArrayList<Vector3f>();

        for(int i = 0; i < 16*4; i ++)
            points.add(new Vector3f());
    }

//    private Vector3f getDir() {
//        float cosY = (float)Math.cos(Math.toRadians(player.getYRot()-90));
//        float sinY = (float)Math.sin(Math.toRadians(player.getYRot()-90));
//        float cosP = (float)Math.cos(Math.toRadians(-player.getXRot()));
//        float sinP = (float)Math.sin(Math.toRadians(-player.getXRot()));
//
//        Vector3f r = new Vector3f(cosY*cosP, sinP, sinY*cosP);
//
//        r.normalize();
//
//        return r;
//    }

    public void update() {
        int i = 0;
        for(Vector3f v : points) {
            Vector3f pos = new Vector3f(player.getX(), player.getY(), player.getZ()).add(
                    (float)(Math.cos(Math.toRadians(-player.getXRot()))*Math.sin(Math.toRadians(player.getYRot())))*(i/16.0f),
                    (float)Math.sin(Math.toRadians(-player.getXRot()))*(i/16.0f),
                    (float)(-Math.cos(Math.toRadians(player.getXRot()))*Math.cos(Math.toRadians(player.getYRot())))*(i/16.0f));
            v.set(pos);
            i ++;
        }
    }

    public Vector3f getBlockPosition() {
        for(Vector3f v : points) {
            int x = (int)v.x;
            int y = (int)v.y;
            int z = (int)v.z;

            //System.out.println(x + " " + y + " " + z);

            if(player.getWorld().getBlock(x, y, z) != null) {
                //System.out.println(player.getWorld().getBiome(x,z));
                return new Vector3f(x, y, z);
            }
        }
        return null;
    }
}
