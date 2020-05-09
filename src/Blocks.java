public abstract class Blocks {
    public static final Block GRASS = new GrassBlock();
    public static final Block DIRT = new DirtBlock();
    public static final Block STONE = new StoneBlock();
    public static final Block LOG = new LogBlock();
    public static final Block LEAVES = new LeavesBlock();
    public static final Block BEDROCK = new BedrockBlock();
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

class LogBlock extends Block {
    public LogBlock() {
        super(new RGB(0.3f, 0.2f, 0.0f));
    }
}

class LeavesBlock extends Block {
    public LeavesBlock() {
        super(new RGB(0.0f, 0.4f, 0.0f));
    }
}

class BedrockBlock extends Block {
    public BedrockBlock() {
        super(new RGB(0.1f, 0.1f, 0.1f));
    }
}
