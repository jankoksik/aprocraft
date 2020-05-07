import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

public class Chunk {
    public static final int SIZE = 16;
    public static final int HEIGHT = 128;

    private static FloatBuffer fb;
    private int vbo;

    private List<Block> blocks;

    public Chunk() {
        blocks = new ArrayList<Block>();
        generate();
        createBuffer();
    }

    private void generate() {
        fb = BufferUtils.createFloatBuffer(SIZE*SIZE*HEIGHT*6*4*3);

        for(int i = 0; i < SIZE; i ++)
            for(int j = 0; j < SIZE; j ++)
                for(int k = 0; k < HEIGHT; k ++) {
                    Block b = Block.GRASS;
                    blocks.add(b);
                    fb.put(b.getData(i, j, k));
                }

        fb.flip();
    }

    private void createBuffer() {
        vbo = glGenBuffers();

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
    }

    public void update() {

    }

    public void render() {
        glEnableVertexAttribArray(0);
    }
}
