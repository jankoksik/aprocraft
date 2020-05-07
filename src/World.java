public class World {
    private Chunk[][] chunks;

    public World() {
        chunks = new Chunk[4][4];

        for(int i = 0; i < 4; i ++)
            for(int j = 0; j < 4; j ++)
                chunks[i][j] = new Chunk(i, j);
    }

    public void update() {

    }

    public void render() {
        for(int i = 0; i < 4; i ++)
            for(int j = 0; j < 4; j ++)
                chunks[i][j].render();
    }
}
