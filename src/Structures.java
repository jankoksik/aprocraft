public abstract class Structures {
    public static final Structure OAK_TREE = new OakTree();
    public static final Structure CLOUD = new Cloud();
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

class Cloud extends Structure {
    public Cloud() {
        super(5, 1, 5);

        for(int i = 0; i < sizeX; i ++)
            for(int j = 0; j < sizeZ; j ++)
                blocks[i][0][j] = Blocks.CLOUD;

    }
}
