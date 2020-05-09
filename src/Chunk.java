import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.ArrayList;

public class Chunk {
    public static final int SIZE = 32;

    private Generator generator;
    private int x, z;

    private static FloatBuffer fb, cb;
    private int vbo, col;
    int fbsize;

    private Block[][][] blocks;

    public Chunk(int x, int z, Generator generator) {
        this.generator = generator;

        this.x = x;
        this.z = z;

        blocks = new Block[SIZE][SIZE][SIZE];

        fbsize = 0;

        generate();

    }

    private void generate() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                for (int k = 0; k < SIZE; k++) {
                    int xw = SIZE*x+i;
                    int yw = j;
                    int zw = SIZE*z+k;
                    if(j-4 < generator.getHeight(xw, zw)) {
                        Block b = Block.GRASS;
                        blocks[i][j][k] = b;
                    }
                }
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

    public Block getBlock(int x, int y, int z) {
        if(x < 0 || y < 0 || z < 0 || x >= SIZE || y >= SIZE || z >= SIZE) return null;
        return blocks[x][y][z];
    }

    public void create(World world) {
        fb = BufferUtils.createFloatBuffer(SIZE * SIZE * SIZE * 6 * 4 * 3);
        cb = BufferUtils.createFloatBuffer(SIZE * SIZE * SIZE * 6 * 4 * 4);

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                for (int k = 0; k < SIZE; k++) {
                    boolean up = getBlock(i, j+1, k) == null;
                    boolean down = getBlock(i, j-1, k) == null;
                    boolean left = getBlock(i-1, j, k) == null;
                    boolean right = getBlock(i+1, j, k) == null;
                    boolean front = getBlock(i, j, k-1) == null;
                    boolean back = getBlock(i, j, k+1) == null;

                    if(!up && !down && !left && !right && !front && ! back) continue;
                    if(blocks[i][j][k] == null) continue;

                    Block b = blocks[i][j][k];

                    fb.put(b.getData(SIZE*x+i, j, SIZE*z+k));
                    cb.put(b.getColorData());
                    fbsize += 6*4;

                }

        fb.flip();
        cb.flip();
        createBuffer();
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

        glDrawArrays(GL_QUADS, 0, fbsize);//SIZE * SIZE * SIZE * 6 * 4 * 4

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
