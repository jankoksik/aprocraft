public abstract class Blocks {
    public static final Block GRASS = new GrassBlock();
    public static final Block DIRT = new DirtBlock();
    public static final Block STONE = new StoneBlock();
}

class GrassBlock extends Block {
    public GrassBlock() {
        super(new RGB(0.2f, 1f, 0.2f));
    }
}

class DirtBlock extends Block {
    public DirtBlock() {
        super(new RGB(0.4f, 0.3f, 0.1f));
    }
}

class StoneBlock extends Block {
    public StoneBlock() {
        super(new RGB(0.3f, 0.3f, 0.3f));
    }
}
