import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Raycast {
    private List<Vector3f> points;
    private Player player;

    public Raycast(Player player) {
        this.player = player;

        points = new ArrayList<Vector3f>();

        for(int i = 0; i < 16*10; i ++)
            points.add(new Vector3f());
    }

    public void update() {
        int i = 0;
        for(Vector3f v : points) {
            Vector3f pos = new Vector3f(-player.getX(), -player.getY(), -player.getZ()).add(new Vector3f(player.getXRot(), 0, player.getYRot()).mul(i/16.0f));
            v.set(pos);
            i ++;
        }
    }

    public Vector3f getBlockPosition() {
        for(Vector3f v : points) {
            int x = (int)v.x;
            int y = (int)v.y;
            int z = (int)v.z;

            if(player.getWorld().getBlock(x, y, z) != null)
                return new Vector3f(x, y, z);
        }
        return null;
    }
}
