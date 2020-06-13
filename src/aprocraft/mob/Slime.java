package aprocraft.mob;

import aprocraft.world.World;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glColor3f;

public class Slime extends Mob {
    public Slime(World world, float x, float y, float z, float size) {
        super(world);

        this.x = x;
        this.y = y;
        this.z = z;

        this.s = size;
    }

    @Override
    public void render() {
        float s = this.s*0.5f;

        glColor3f(0.4f, 1, 0.4f);
        glBegin(GL_QUADS);

        glVertex3f(x - s, y - s, z - s);
        glVertex3f(x + s, y - s, z - s);
        glVertex3f(x + s, y + s, z - s);
        glVertex3f(x - s, y + s, z - s);

        glVertex3f(x + s, y - s, z + s);
        glVertex3f(x - s, y - s, z + s);
        glVertex3f(x - s, y + s, z + s);
        glVertex3f(x + s, y + s, z + s);

        glVertex3f(x - s, y - s, z - s);
        glVertex3f(x + s, y - s, z - s);
        glVertex3f(x + s, y - s, z + s);
        glVertex3f(x - s, y - s, z + s);

        glVertex3f(x + s, y + s, z - s);
        glVertex3f(x - s, y + s, z - s);
        glVertex3f(x - s, y + s, z + s);
        glVertex3f(x + s, y + s, z + s);

        glVertex3f(x - s, y - s, z - s);
        glVertex3f(x - s, y + s, z - s);
        glVertex3f(x - s, y + s, z + s);
        glVertex3f(x - s, y - s, z + s);

        glVertex3f(x + s, y + s, z - s);
        glVertex3f(x + s, y - s, z - s);
        glVertex3f(x + s, y - s, z + s);
        glVertex3f(x + s, y + s, z + s);

        glEnd();
        glColor3f(1, 1, 1);
    }
}
