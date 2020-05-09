import java.util.Random;

public class World {
    public static final int SIZE = 4;

    private Generator generator;

    private Chunk[][] chunks;

    public World() {
        generator = new Generator(new Random().nextLong(), 16, 8);

        chunks = new Chunk[SIZE][SIZE];

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                chunks[i][j] = new Chunk(i, j, generator);

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                chunks[i][j].create(this);
    }

    public Block getBlock(int x, int y, int z) {
        int xx = x / Chunk.SIZE;
        int zz = z / Chunk.SIZE;
        if(xx < 0 || zz < 0 || xx >= SIZE || zz >= SIZE) return null;
        Chunk c = chunks[xx][zz];
        return c.getBlock(x % Chunk.SIZE, y, z % Chunk.SIZE);
    }

    public void update() {

    }

    public void render() {
        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                chunks[i][j].render();
    }
}
