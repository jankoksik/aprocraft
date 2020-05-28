package aprocraft.mob;

import aprocraft.world.World;

public class Slime extends Mob {
    public Slime(World world, float x, float y, float z, float size) {
        super(world);

        this.x = x;
        this.y = y;
        this.z = z;

        this.s = size;
    }
}
