import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Random;

public class Chunk {
    public static final int SIZE = 32;

    private Biome biome;
    private Generator generator;
    private int x, z;

    private static FloatBuffer fb, cb;
    private int vbo, col;
    int fbsize;

    private Block[][][] blocks;

    public Chunk(int x, int z, Generator generator) {
        this.generator = generator;

        boolean r = new Random().nextBoolean();
        this.biome = r ? Biomes.DEFAULT : Biomes.DESERT;

        this.x = x;
        this.z = z;

        blocks = new Block[SIZE][SIZE][SIZE];

        fbsize = 0;

        generate(12);
    }

    private void generate(int height) {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                for (int k = 0; k < SIZE; k++) {
                    int xw = SIZE*x+i;
                    int yw = j;
                    int zw = SIZE*z+k;
                    int h = (int)generator.getHeight(xw, zw);

                    if(j == 0) {
                        Block b = Blocks.BEDROCK;
                        blocks[i][j][k] = b;
                    } else if(j < h+height-4) {
                        Block b = biome.getLayers()[2];
                        blocks[i][j][k] = b;
                    } else if(j < h+height-1) {
                        Block b = biome.getLayers()[1];
                        blocks[i][j][k] = b;
                    } else if(j < h+height) {
                        Block b = biome.getLayers()[0];
                        blocks[i][j][k] = b;
                    }
                }
    }

    public int getMaxHeight(int x, int z) {
        for(int i = SIZE-1; i >= 0; i --)
            if (getBlock(x, i, z) != null)
                return i;

        return 0;
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

    private void updateBuffer() {
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
        //glBufferSubData(GL_ARRAY_BUFFER, 0, fb);
        //glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, col);
        glBufferData(GL_ARRAY_BUFFER, cb, GL_STATIC_DRAW);
        //glBufferSubData(GL_ARRAY_BUFFER, 0, cb);
        //glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    public Biome getBiome() {
        return biome;
    }

    public Block getBlock(int x, int y, int z) {
        if(x < 0 || y < 0 || z < 0 || x >= SIZE || y >= SIZE || z >= SIZE) return null;
        return blocks[x][y][z];
    }

    public void setBlock(int x, int y, int z, Block block, World world) {
        if(x < 0 || y < 0 || z < 0 || x >= SIZE || y >= SIZE || z >= SIZE) return;
        blocks[x][y][z] = block;

        if(fb != null)
            updateChunk(world);
    }

    public void create(World world) {
        fb = BufferUtils.createFloatBuffer(SIZE * SIZE * SIZE * 6 * 4 * 3);
        cb = BufferUtils.createFloatBuffer(SIZE * SIZE * SIZE * 6 * 4 * 4);

        fbsize = 0;

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                for (int k = 0; k < SIZE; k++) {
                    int xw = SIZE*x+i;
                    int yw = j;
                    int zw = SIZE*z+k;

                    boolean up = world.getBlock(xw, yw+1, zw) == null;
                    boolean down = world.getBlock(xw, yw-1, zw) == null;
                    boolean left = world.getBlock(xw-1, yw, zw) == null;
                    boolean right = world.getBlock(xw+1, yw, zw) == null;
                    boolean front = world.getBlock(xw, yw, zw-1) == null;
                    boolean back = world.getBlock(xw, yw, zw+1) == null;

                    if(!up && !down && !left && !right && !front && ! back) continue;
                    if(blocks[i][j][k] == null) continue;

                    Block b = blocks[i][j][k];

                    fb.put(b.getData(xw, yw, zw));
                    cb.put(b.getColorData());
                    fbsize += 6*4;

                }

        fb.flip();
        cb.flip();
        createBuffer();
    }

    public void updateChunk(World world) {
        fbsize = 0;
        fb.clear();
        cb.clear();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                for (int k = 0; k < SIZE; k++) {
                    int xw = SIZE*x+i;
                    int yw = j;
                    int zw = SIZE*z+k;

                    boolean up = world.getBlock(xw, yw+1, zw) == null;
                    boolean down = world.getBlock(xw, yw-1, zw) == null;
                    boolean left = world.getBlock(xw-1, yw, zw) == null;
                    boolean right = world.getBlock(xw+1, yw, zw) == null;
                    boolean front = world.getBlock(xw, yw, zw-1) == null;
                    boolean back = world.getBlock(xw, yw, zw+1) == null;

                    if(!up && !down && !left && !right && !front && ! back) continue;
                    if(blocks[i][j][k] == null) continue;

                    Block b = blocks[i][j][k];

                    fb.put(b.getData(xw, yw, zw));
                    cb.put(b.getColorData());
                    fbsize += 6*4;

                }

        fb.flip();
        cb.flip();
        updateBuffer();
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
