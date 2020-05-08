import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Chunk {
    public static final int SIZE = 32;
    public static final int HEIGHT = 32;

    private int x, y;

    private static FloatBuffer fb, cb;
    private int vbo, col;
    private int fbSize;

    private List<Block> blocks;

    public Chunk(int x, int y) {
        this.x = x;
        this.y = y;

        blocks = new ArrayList<Block>();

        generate();
        createBuffer();
    }

    private void generate() {
        fb = BufferUtils.createFloatBuffer(SIZE*SIZE*HEIGHT*6*4*3);
        cb = BufferUtils.createFloatBuffer(SIZE*SIZE*HEIGHT*6*4*4);
        Random r = new Random();

        for(int i = 0; i < SIZE; i ++)
            for(int j = 0; j < SIZE; j ++)
                for(int k = 0; k < HEIGHT; k ++) {
                    if(r.nextBoolean()) {
                        Block b = Block.GRASS;
                        blocks.add(b);
                        fb.put(b.getData(i, j, k));
                        cb.put(b.getColorData());
                        fbSize += 6 * 4;
                    }
                }

        fb.flip();
        cb.flip();
    }

    private void createBuffer() {
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        col = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, col);
        glBufferData(GL_ARRAY_BUFFER, cb, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public void update() {

    }

    public void render() {
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexPointer(3, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, col);
        glColorPointer(4, GL_FLOAT, 0, 0L);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_COLOR_ARRAY);

        glDrawArrays(GL_QUADS, 0, SIZE*SIZE*HEIGHT*6*4*4);

        glDisableClientState(GL_COLOR_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);

        /*glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 7 * 4, 0);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 7 * 4, 12);

        glDrawArrays(GL_QUADS, 0, fbSize);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);*/
    }
}
