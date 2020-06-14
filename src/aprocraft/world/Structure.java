package aprocraft.world;

import aprocraft.world.Block;
import aprocraft.world.World;

/**
 * Klasa implementująca struktury
 */

public abstract class Structure {
    public int sizeX, sizeY, sizeZ;

    protected Block[][][] blocks;

    /**
     * Konstruktor struktury
     * @param sizeX współrzędna x
     * @param sizeY współrzędna y
     * @param sizeZ współrzędna z
     */
    public Structure(int sizeX, int sizeY, int sizeZ) {
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        this.sizeZ = sizeZ;

        blocks = new Block[sizeX][sizeY][sizeZ];
    }

    /*public Structure(String filename) {
        blocks = new Block[sizeX][sizeY][sizeZ];

        //TODO: wczytywanie do tablicy z pliku
    }*/

    /**
     * Konsturtor tworzacy strukture ze wskazanej tablicy blokow
     * @param blocks
     */
    public Structure(Block[][][] blocks) {
        this.blocks = blocks;
        sizeX = this.blocks.length;
        sizeY = this.blocks[0].length;
        sizeZ = this.blocks[0][0].length;
    }

    /**
     * Powoduje zbudowanie struktury w wybranym swiecie na wybranych wspolrzednych
     * @param world swiat
     * @param x pozycja x
     * @param y pozycja y
     * @param z pozycja z
     */
    public void spawn(World world, int x, int y, int z) {
        for(int i = 0; i < sizeX; i ++)
            for(int j = 0; j < sizeY; j ++)
                for(int k = 0; k < sizeZ; k ++) {
                    int xx = x + i - (int)(sizeX / 2);
                    int yy = y + j; //- (int)(sizeY / 2);
                    int zz = z + k - (int)(sizeZ / 2);
                    if(this.blocks[i][j][k] != null)
                        world.setBlock(xx, yy, zz, blocks[i][j][k]);
                        /*if(xx >= 0 && yy >= 0 && zz >= 0 && xx < blocks.length && yy < blocks[0].length && zz < blocks[0][0].length)
                            blocks[xx][yy][zz] = this.blocks[i][j][k];*/
                }
    }
}
