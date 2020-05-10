public abstract class Structures {
    public static final Structure OAK_TREE = new OakTree();
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