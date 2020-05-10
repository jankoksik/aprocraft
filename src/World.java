import java.util.Random;

public class World {
    public static final int SIZE = 6;

    private Generator generator;

    private Chunk[][] chunks;

    public World() {
        generator = new Generator(new Random().nextLong(), 24, 12);

        chunks = new Chunk[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                chunks[i][j] = new Chunk(i, j, generator);

        generateTrees(50);

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
                Structures.OAK_TREE.spawn(this, x, y, z);
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
        c.setBlock(x % Chunk.SIZE, y, z % Chunk.SIZE, block);
    }

    public void update() {

    }

    public void render() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                chunks[i][j].render();
    }
}
