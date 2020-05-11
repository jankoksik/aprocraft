import org.joml.Vector2f;

import java.util.Random;

public class World {
    public static final int SIZE = 4;

    private Generator generator;

    //TODO: Poziomy chunkow

    public static final int HELL = 0;
    public static final int CAVES = 1;
    public static final int LAND = 2;
    public static final int CLOUDS = 3;
    public static final int SPACE = 4;

    private Chunk[][] chunks;

    public World() {
        generator = new Generator(new Random().nextLong(), 24, 12);

        chunks = new Chunk[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                chunks[i][j] = new Chunk(i, j, generator);

        generateTrees(6*SIZE*SIZE);
        generateClouds(1*SIZE*SIZE);

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                chunks[i][j].create(this);
    }

    public Generator getGenerator() {
        return generator;
    }

    public void generateTrees(int n) {
        Random r = new Random();
        for(int i = 0; i < n; i ++) {
            int x = r.nextInt(SIZE*Chunk.SIZE);
            int z = r.nextInt(SIZE*Chunk.SIZE);
            int y = getMaxHeight(x, z);

            if(getBlock(x, y, z) == Blocks.GRASS)
                Structures.OAK_TREE.spawn(this, x, y+1, z);
        }
    }

    public void generateClouds(int n) {
        Random r = new Random();
        for(int i = 0; i < n; i ++) {
            int x = r.nextInt(SIZE*Chunk.SIZE);
            int z = r.nextInt(SIZE*Chunk.SIZE);
            int y = Chunk.SIZE-2;

            for(int j = 0; j < 6; j ++) {
                Structures.CLOUD.spawn(this, x+r.nextInt(5)-2, y+r.nextInt(2)-1, z+r.nextInt(5)-2);
            }
        }
    }

    public int getMaxHeight(int x, int z) {
        int xx = x / Chunk.SIZE;
        int zz = z / Chunk.SIZE;
        if(xx < 0 || zz < 0 || xx >= SIZE || zz >= SIZE) return 0;
        Chunk c = chunks[xx][zz];
        return c.getMaxHeight(x % Chunk.SIZE, z % Chunk.SIZE);
    }

    public Block getBlock(int x, int y, int z) {
        int xx = x / Chunk.SIZE;
        int zz = z / Chunk.SIZE;
        if(xx < 0 || zz < 0 || xx >= SIZE || zz >= SIZE) return null;
        Chunk c = chunks[xx][zz];
        return c.getBlock(x % Chunk.SIZE, y, z % Chunk.SIZE);
    }

    public void setBlock(int x, int y, int z, Block block) {
        int xx = x / Chunk.SIZE;
        int zz = z / Chunk.SIZE;
        if(xx < 0 || zz < 0 || xx >= SIZE || zz >= SIZE) return;
        Chunk c = chunks[xx][zz];
        c.setBlock(x % Chunk.SIZE, y, z % Chunk.SIZE, block, this);
    }

    public void update() {

    }

    public void render(Player player) {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                float dist = Vector2f.distance(player.getX(), player.getZ(), (i + 0.5f) * Chunk.SIZE, (j + 0.5f) * Chunk.SIZE);
                //System.out.println(dist);
                if (dist <= 128)
                    chunks[i][j].render();
            }
    }
}
