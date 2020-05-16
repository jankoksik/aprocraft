public abstract class Structures {
    public static final Structure OAK_TREE = new OakTree();
    public static final Structure SPRUCE_TREE = new SpruceTree();
    public static final Structure CLOUD = new Cloud();
    public static final Structure CACTUS = new Cactus();
}

class OakTree extends Structure {
    public OakTree() {
        super(5, 6, 5);

        for(int i = 0; i < sizeX; i ++)
            for(int j = 0; j < sizeY; j ++)
                for(int k = 0; k < sizeZ; k ++) {
                    if(j < 2) {
                        if (i == 2 && k == 2)
                            blocks[i][j][k] = Blocks.LOG;
                    } else if (j < 5) {
                        blocks[i][j][k] = Blocks.LEAVES;
                    } else {
                        if(i >= 1 && i < 4 && k >= 1 && k < 4)
                            blocks[i][j][k] = Blocks.LEAVES;
                    }

                }
    }
}

class SpruceTree extends Structure {
    public SpruceTree() {
        super(5, 8, 5);

        for(int i = 0; i < sizeX; i ++)
            for(int j = 0; j < sizeY; j ++)
                for(int k = 0; k < sizeZ; k ++) {
                    if(j < 2) {
                        if (i == 2 && k == 2)
                            blocks[i][j][k] = Blocks.LOG;
                    } else if (j < 4) {
                        blocks[i][j][k] = Blocks.DARK_LEAVES;
                    } else if (j < 7) {
                        if(i >= 1 && i < 4 && k >= 1 && k < 4)
                            blocks[i][j][k] = Blocks.DARK_LEAVES;
                    } else {
                        if (i == 2 && k == 2)
                            blocks[i][j][k] = Blocks.DARK_LEAVES;
                    }

                }
    }
}

class Cloud extends Structure {
    public Cloud() {
        super(5, 1, 5);

        for(int i = 0; i < sizeX; i ++)
            for(int j = 0; j < sizeZ; j ++)
                blocks[i][0][j] = Blocks.CLOUD;

    }
}

class Cactus extends Structure {
    public Cactus() {
        super(1, 3, 1);

        for(int i = 0; i < sizeY; i ++)
            blocks[0][i][0] = Blocks.LEAVES;

    }
}
