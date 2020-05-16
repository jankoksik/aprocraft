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

    private int time;
    private int skyTime;
    private int skyTimeDir;

    public World() {
        generator = new Generator(new Random().nextLong(), 24, 12);

        chunks = new Chunk[SIZE][SIZE];

        time = 0;
        skyTime = 3000;
        skyTimeDir = 1;

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                chunks[i][j] = new Chunk(i, j, generator);

        //generateTrees(6 * SIZE * SIZE);
        generateStructures();
        generateClouds(1 * SIZE * SIZE);

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                chunks[i][j].create(this);
    }

    public Generator getGenerator() {
        return generator;
    }

    public void generateTrees(int n) {
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int x = r.nextInt(SIZE * Chunk.SIZE);
            int z = r.nextInt(SIZE * Chunk.SIZE);
            int y = getMaxHeight(x, z);

            /*if(getBlock(x, y, z) == Blocks.GRASS)
                Structures.OAK_TREE.spawn(this, x, y+1, z);*/
            Chunk c = getChunk(x, z);
            if (c == null) continue;
            for(Structure s : c.getBiome().getStructures())
                if(getBlock(x, y, z) == Blocks.GRASS || getBlock(x, y, z) == Blocks.SAND)
                    s.spawn(this, x, y + 1, z);
        }
    }

    public void generateStructures() {
        Random r = new Random();

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                Biome b = chunks[i][j].getBiome();
                for(int k = 0; k < b.getStructures().size(); k ++) {
                    for(int l = 0; l < b.getStructureOccurrence(k); l ++) {
                        int x = i * Chunk.SIZE + r.nextInt(Chunk.SIZE);
                        int z = j * Chunk.SIZE + r.nextInt(Chunk.SIZE);
                        int y = getMaxHeight(x, z);
                        if(getBlock(x, y, z) == Blocks.GRASS || getBlock(x, y, z) == Blocks.SAND)
                            b.getStructures().get(k).spawn(this, x, y + 1, z);
                    }
                }
            }
    }

    public void generateClouds(int n) {
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int x = r.nextInt(SIZE * Chunk.SIZE);
            int z = r.nextInt(SIZE * Chunk.SIZE);
            int y = Chunk.SIZE - 2;

            for (int j = 0; j < 6; j++) {
                Structures.CLOUD.spawn(this, x + r.nextInt(5) - 2, y + r.nextInt(2) - 1, z + r.nextInt(5) - 2);
            }
        }
    }

    public int getMaxHeight(int x, int z) {
        int xx = x / Chunk.SIZE;
        int zz = z / Chunk.SIZE;
        if (xx < 0 || zz < 0 || xx >= SIZE || zz >= SIZE) return 0;
        Chunk c = chunks[xx][zz];
        return c.getMaxHeight(x % Chunk.SIZE, z % Chunk.SIZE);
    }

    public Chunk getChunk(int x, int z) {
        int xx = x / Chunk.SIZE;
        int zz = z / Chunk.SIZE;
        if (xx < 0 || zz < 0 || xx >= SIZE || zz >= SIZE) return null;
        return chunks[xx][zz];
    }

    public Block getBlock(int x, int y, int z) {
        int xx = x / Chunk.SIZE;
        int zz = z / Chunk.SIZE;
        if (xx < 0 || zz < 0 || xx >= SIZE || zz >= SIZE) return null;
        Chunk c = chunks[xx][zz];
        return c.getBlock(x % Chunk.SIZE, y, z % Chunk.SIZE);
    }

    public void setBlock(int x, int y, int z, Block block) {
        int xx = x / Chunk.SIZE;
        int zz = z / Chunk.SIZE;
        if (xx < 0 || zz < 0 || xx >= SIZE || zz >= SIZE) return;
        Chunk c = chunks[xx][zz];
        c.setBlock(x % Chunk.SIZE, y, z % Chunk.SIZE, block, this);
    }

    public void update() {
        time++;
        skyTime += skyTimeDir;

        if (time >= 7200)
            time = 0;

        if (skyTime == 3600 || skyTime == 0)
            skyTimeDir *= -1;
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

    public int getTime() {
        return time;
    }

    public float getSkyColorMultiplier() {
        return ((float) skyTime / 3600f);
    }
}
