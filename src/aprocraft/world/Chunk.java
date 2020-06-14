package aprocraft.world;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

import org.lwjgl.BufferUtils;

import java.nio.FloatBuffer;
import java.util.Random;

/**
 * Metoda implementująca strukturę chunka
 */

public class Chunk {
    public static final int SIZE = 32;

    //private Biome biome;
    private Generator generator;
    private Generator biomeGenerator;
    private int x, y, z;

    private static FloatBuffer fb, cb, lb;
    private int vbo, col, light;
    int fbsize;

    private Block[][][] blocks;

    public boolean hasStructures, hasClouds;

    /**
     * Konstruktor
     * @param x
     * @param y
     * @param z
     * @param generator
     * @param biomeGenerator
     */
    public Chunk(int x, int y, int z, Generator generator, Generator biomeGenerator) {
        this.generator = generator;
        this.biomeGenerator = biomeGenerator;

        //boolean r = new Random().nextBoolean();
        //this.biome = r ? aprocraft.world.Biomes.FOREST : aprocraft.world.Biomes.DESERT;
        //this.biome = aprocraft.world.Biomes.choose();
        //generator.setAmplitude(biome.getAmplitude());
        //generator.setOctave(biome.getOctave());

        this.x = x;
        this.y = y;
        this.z = z;

        hasStructures = false;
        hasClouds = false;

        blocks = new Block[SIZE][SIZE][SIZE];

        fbsize = 0;

        //generate(12);
    }

    /**
     * Metoda pobierająca jaki biom występuje na danej współrzędnej
     * @param x współrzędna x
     * @param z współrzędna z
     * @return typ biomu
     */
    public Biome getBiome(int x, int z) {
        Biome biome;
        int bbb = (int)biomeGenerator.getHeight(x, z);

        //int bbb = (int)(8*(Math.max(generator.getHeight(z, x)/generator.getAmplitude(), biomeGenerator.getHeight(x, z)/biomeGenerator.getAmplitude())));
        //int bbb = (int)(16*(generator.getHeight(x, z)+biomeGenerator.getHeight(x, z))/(generator.getAmplitude()+biomeGenerator.getAmplitude()));
        //int bbb = (int)(8*Math.abs((generator.getHeight(x, z)/generator.getAmplitude())-(2*biomeGenerator.getHeight(x, z)/(biomeGenerator.getAmplitude()))));

        /*if(bbb == 1 || bbb == 2 || bbb == 8)
            biome = aprocraft.world.Biomes.DEFAULT;
        else if(bbb == 3 || bbb == 7)
            biome = aprocraft.world.Biomes.CANYON;
        else if (bbb == 4 || bbb == 12)
            biome = aprocraft.world.Biomes.DESERT;
        else if (bbb == 6 || bbb == 14 || bbb == 16)
            biome = aprocraft.world.Biomes.PLAINS;
        else if (bbb == 15)
            biome = aprocraft.world.Biomes.ICEBERG;
        else
            biome = aprocraft.world.Biomes.FOREST;*/

        /*if(bbb < 3)
            biome = aprocraft.world.Biomes.CANYON;
        else if (bbb < 4)
            biome = aprocraft.world.Biomes.DEFAULT;
        else if (bbb < 5)
            biome = aprocraft.world.Biomes.DESERT;
        else if (bbb < 6)
            biome = aprocraft.world.Biomes.PLAINS;
        else
            biome = aprocraft.world.Biomes.FOREST;*/

        if(bbb < 2)
            biome = Biomes.CANYON;
        else if (bbb < 3)
            biome = Biomes.DESERT;
        else if (bbb < 4)
            biome = Biomes.DESERT;
        else if (bbb < 6)
            biome = Biomes.PLAINS;
        else if (bbb < 7)
            biome = Biomes.DEFAULT;
        else
            biome = Biomes.FOREST;

        return biome;
    }

    /*public aprocraft.world.Biome getBiome() {
        return biome;
    }*/

    /**
     * Metoda generująca ..
     * @param height
     */
    public void generate(int height) {
        Random r = new Random();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                for (int k = 0; k < SIZE; k++) {
                    int xw = SIZE*x+i;
                    int yw = SIZE*y+j;
                    int zw = SIZE*z+k;
                    int h = (int)generator.getHeight(xw, zw); //(biome.getAmplitude()/generator.getAmplitude()))

                    Biome biome = getBiome(xw, zw);

                    //h += (int)biomeGenerator.getHeight(xw, zw);
                    //h /= 2;
                    h *= (biome.getAmplitude()/generator.getAmplitude());

                    /*if(j == 0) {
                        Block b = Blocks.BEDROCK;
                        blocks[i][j][k] = b;
                    } else */
                    if(j < h+height-4) {
                        Block b = (r.nextInt(j*2+8) == 0 && biome.getTotalOres() != 0) ? biome.chooseOre() : biome.getLayers()[2];
                        blocks[i][j][k] = b;
                    } else if(j < h+height-1) {
                        Block b = biome.getLayers()[1];
                        blocks[i][j][k] = b;
                    } else if(j < h+height) {
                        /*boolean hh = (i == 0 || i == SIZE-1 || k == 0 || k == SIZE-1);
                        aprocraft.world.Block b = (new Random().nextBoolean() && hh) ? aprocraft.world.Blocks.GRASS : biome.getLayers()[0];*/
                        Block b = biome.getLayers()[0];
                        blocks[i][j][k] = b;
                    }
                }
    }

    /**
     *Metoda generująca ...
     */
    public void generateStone() {
        Random r = new Random();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                for (int k = 0; k < SIZE; k++) {
                    int xw = SIZE*x+i;
                    int zw = SIZE*z+k;

                    Biome biome = getBiome(xw, zw);

                    if(j == 0) {
                        Block b = Blocks.BEDROCK;
                        blocks[i][j][k] = b;
                    } else {
                        Block b = (r.nextInt(j*2+8) == 0 && biome.getTotalOres() != 0) ? biome.chooseOre() : biome.getLayers()[2];
                        blocks[i][j][k] = b;
                    }
                }
    }

    /**
     * Metoda zwracająca maksymalną wysokość
     * @param x współrzędna x
     * @param z współrzędna y
     * @return maksymalna wysokość
     */
    public int getMaxHeight(int x, int z) {
        for(int i = SIZE-1; i >= 0; i --)
            if (getBlock(x, i, z) != null)
                return i;

        return 0;
    }

    /**
     * Metoda tworząca buffor
     */
    private void createBuffer() {
        vbo = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        col = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, col);
        glBufferData(GL_ARRAY_BUFFER, cb, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);

        light = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, light);
        glBufferData(GL_ARRAY_BUFFER, lb, GL_STATIC_DRAW);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    /**
     * Metoda odśiwerzająca zawartość buffora
     */
    private void updateBuffer() {
        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
        //glBufferSubData(GL_ARRAY_BUFFER, 0, fb);
        //glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, col);
        glBufferData(GL_ARRAY_BUFFER, cb, GL_STATIC_DRAW);
        //glBufferSubData(GL_ARRAY_BUFFER, 0, cb);
        //glBindBuffer(GL_ARRAY_BUFFER, 0);

        glBindBuffer(GL_ARRAY_BUFFER, light);
        glBufferData(GL_ARRAY_BUFFER, lb, GL_STATIC_DRAW);
    }

    /**
     * Metoda zwracająca informacje o położeniu bloku
     * @param x współrzędna x
     * @param y współrzędna y
     * @param z współrzędna z
     * @return wskazany blok
     */
    public Block getBlock(int x, int y, int z) {
        if(x < 0 || y < 0 || z < 0 || x >= SIZE || y >= SIZE || z >= SIZE) return null;
        return blocks[x][y][z];
    }

    /**
     * Metoda dodająca blok
     * @param x współrzędna x
     * @param y współrzędna y
     * @param z współrzędna z
     * @param block typ dodanego bloku
     * @param world obiekt wygnerowanego świata
     */
    public void placeBlock(int x, int y, int z, Block block, World world) {
        if(x < 0 || y < 0 || z < 0 || x >= SIZE || y >= SIZE || z >= SIZE) return;
        blocks[x][y][z] = block;

        if(fb != null)
            updateChunk(world);
    }

    /**
     * Metoda ustawiająca typ bloku
     * @param x współrzędna x
     * @param y współrzędna y
     * @param z współrzędna z
     * @param block typ bloku
     */
    public void setBlock(int x, int y, int z, Block block) {
        if(x < 0 || y < 0 || z < 0 || x >= SIZE || y >= SIZE || z >= SIZE) return;
        blocks[x][y][z] = block;
    }

    public void create(World world) {
        fb = BufferUtils.createFloatBuffer(SIZE * SIZE * SIZE * 6 * 4 * 3);
        cb = BufferUtils.createFloatBuffer(SIZE * SIZE * SIZE * 6 * 4 * 2);
        lb = BufferUtils.createFloatBuffer(SIZE * SIZE * SIZE * 6 * 4 * 4);

        fbsize = 0;

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                for (int k = 0; k < SIZE; k++) {
                    int xw = SIZE*x+i;
                    int yw = SIZE*y+j;
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
                    cb.put(b.getTextureData());
                    lb.put(b.getLightData());
                    fbsize += 6*4;

                }

        fb.flip();
        cb.flip();
        lb.flip();
        createBuffer();
    }

    /**
     * Metoda odświerzająca chunka
     * @param world obiekt wygnerowanego świata
     */
    public void updateChunk(World world) {
        fbsize = 0;
        fb.clear();
        cb.clear();
        lb.clear();
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                for (int k = 0; k < SIZE; k++) {
                    int xw = SIZE*x+i;
                    int yw = SIZE*y+j;
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
                    cb.put(b.getTextureData());
                    lb.put(b.getLightData());
                    fbsize += 6*4;

                }

        fb.flip();
        cb.flip();
        lb.flip();
        updateBuffer();
    }

    /**
     * Metoda wyśiwetlająca chunka
     */
    public void render() {
        glEnable(GL_TEXTURE_2D);
        Blocks.TEXTURE_PACK.bind(0);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexPointer(3, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, col);
        glTexCoordPointer(2, GL_FLOAT, 0, 0L);
        //glColorPointer(4, GL_FLOAT, 0, 0L);

        glBindBuffer(GL_ARRAY_BUFFER, light);
        glColorPointer(4, GL_FLOAT, 0, 0L);

        glEnableClientState(GL_VERTEX_ARRAY);
        glEnableClientState(GL_TEXTURE_COORD_ARRAY);//GL_COLOR_ARRAY
        glEnableClientState(GL_COLOR_ARRAY);
        //GL_TEXTURE_COORD_ARRAY

        glDrawArrays(GL_QUADS, 0, fbsize);//SIZE * SIZE * SIZE * 6 * 4 * 4

        glDisableClientState(GL_COLOR_ARRAY);
        glDisableClientState(GL_TEXTURE_COORD_ARRAY);
        glDisableClientState(GL_VERTEX_ARRAY);

        glDisable(GL_TEXTURE_2D);
        /*glEnableVertexAttribArray(0);
        glEnableVertexAttribArray(1);

        glBindBuffer(GL_ARRAY_BUFFER, vbo);
        glVertexAttribPointer(0, 3, GL_FLOAT, false, 7 * 4, 0);
        glVertexAttribPointer(1, 4, GL_FLOAT, false, 7 * 4, 12);

        glDrawArrays(GL_QUADS, 0, fbSize);

        glDisableVertexAttribArray(0);
        glDisableVertexAttribArray(1);*/
    }

    public int getX() {
        return x;
    }

    public int getY() { return y; }

    public int getZ() {
        return z;
    }

    /**
     * Metoda zwracająca tablicę bloków zawarych w chunku
     * @return tablica bloków
     */
    public Block[][][] getBlocks() {
        return blocks;
    }
}
