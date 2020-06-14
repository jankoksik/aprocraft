package aprocraft.world;

/**
 * Implementacja poszczególnych struktur
 */

public abstract class Structures {
    public static final Structure OAK_TREE = new DefaultTree(Blocks.LEAVES, Blocks.LOG);
    public static final Structure BIRCH_TREE = new DefaultTree(Blocks.BIRCH_LEAVES, Blocks.BIRCH_LOG);
    public static final Structure SPRUCE_TREE = new SpruceTree();
    public static final Structure CLOUD = new Cloud();
    public static final Structure CACTUS = new Cactus();
}

/**
 * Implementacja struktury - podstawowe drewno
 */
class DefaultTree extends Structure {
    public DefaultTree(Block leaves, Block log) {
        super(5, 6, 5);

        for(int i = 0; i < sizeX; i ++)
            for(int j = 0; j < sizeY; j ++)
                for(int k = 0; k < sizeZ; k ++) {
                    if(j < 2) {

                    } else if (j < 5) {
                        blocks[i][j][k] = leaves;
                    } else {
                        if(i >= 1 && i < 4 && k >= 1 && k < 4)
                            blocks[i][j][k] = leaves;
                    }

                    if(j < 5) {
                        if (i == 2 && k == 2)
                            blocks[i][j][k] = log;
                    }
                }
    }
}
/**
 * Implementacja struktury - świerk
 */
class SpruceTree extends Structure {
    public SpruceTree() {
        super(5, 8, 5);

        for(int i = 0; i < sizeX; i ++)
            for(int j = 0; j < sizeY; j ++)
                for(int k = 0; k < sizeZ; k ++) {
                    if(j < 2) {

                    } else if (j < 4) {
                        blocks[i][j][k] = Blocks.SPRUCE_LEAVES;
                    } else if (j < 7) {
                        if(i >= 1 && i < 4 && k >= 1 && k < 4)
                            blocks[i][j][k] = Blocks.SPRUCE_LEAVES;
                    } else {
                        if (i == 2 && k == 2)
                            blocks[i][j][k] = Blocks.SPRUCE_LEAVES;
                    }

                    if(j < 7) {
                        if (i == 2 && k == 2)
                            blocks[i][j][k] = Blocks.SPRUCE_LOG;
                    }
                }
    }
}
/**
 * Implementacja struktury - chmura
 */
class Cloud extends Structure {
    public Cloud() {
        super(5, 1, 5);

        for(int i = 0; i < sizeX; i ++)
            for(int j = 0; j < sizeZ; j ++)
                blocks[i][0][j] = Blocks.CLOUD;

    }
}
/**
 * Implementacja struktury - kaktus
 */
class Cactus extends Structure {
    public Cactus() {
        super(1, 3, 1);

        for(int i = 0; i < sizeY; i ++)
            blocks[0][i][0] = Blocks.CACTUS;

    }
}
