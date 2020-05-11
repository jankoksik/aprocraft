public abstract class Blocks {
    public static final Block AIR = null;
    public static final Block GRASS = new GrassBlock();
    public static final Block DIRT = new DirtBlock();
    public static final Block STONE = new StoneBlock();
    public static final Block LOG = new LogBlock();
    public static final Block LEAVES = new LeavesBlock();
    public static final Block BEDROCK = new BedrockBlock();
    public static final Block CLOUD = new CloudBlock();
}

class GrassBlock extends Block {
    public GrassBlock() {
        super("Grass", new RGB(0.35f, 1f, 0.15f));
    }
}

class DirtBlock extends Block {
    public DirtBlock() {
        super("Dirt", new RGB(0.4f, 0.3f, 0.1f));
    }
}

class StoneBlock extends Block {
    public StoneBlock() {
        super("Stone", new RGB(0.3f, 0.3f, 0.3f));
    }
}

class LogBlock extends Block {
    public LogBlock() {
        super("Oak Log", new RGB(0.3f, 0.2f, 0.0f));
    }
}

class LeavesBlock extends Block {
    public LeavesBlock() {
        super("Oak Leaves", new RGB(0.1f, 0.6f, 0.1f));
    }
}

class BedrockBlock extends Block {
    public BedrockBlock() {
        super("Bedrock", new RGB(0.1f, 0.1f, 0.1f));
    }
}

class CloudBlock extends Block {
    public CloudBlock() {
        super("Cloud", new RGB(1f, 1f, 1f));
    }
}
