public abstract class Block {
    public static final Block GRASS = new BlockGrass();

    private RGB color;
    private float size;

    public Block(RGB color) {
        this.color = color;
        size = 1f;
    }

    public float[] getData(float x, float y, float z) {
        return new float[] {
                x, y, z,                            color.r, color.g, color.b, 1,
                x + size, y, z,                     color.r, color.g, color.b, 1,
                x + size, y + size, z,              color.r, color.g, color.b, 1,
                x, y + size, z,                     color.r, color.g, color.b, 1,

                x, y, z + size,                     color.r, color.g, color.b, 1,
                x + size, y, z + size,              color.r, color.g, color.b, 1,
                x + size, y + size, z + size,       color.r, color.g, color.b, 1,
                x, y + size, z + size,              color.r, color.g, color.b, 1,

                x, y, z,                            color.r, color.g, color.b, 1,
                x, y + size, z,                     color.r, color.g, color.b, 1,
                x, y + size, z + size,              color.r, color.g, color.b, 1,
                x, y, z + size,                     color.r, color.g, color.b, 1,

                x + size, y, z,                     color.r, color.g, color.b, 1,
                x + size, y + size, z,              color.r, color.g, color.b, 1,
                x + size, y + size, z + size,       color.r, color.g, color.b, 1,
                x + size, y, z + size,              color.r, color.g, color.b, 1,

                x, y, z,                            color.r, color.g, color.b, 1,
                x + size, y, z,                     color.r, color.g, color.b, 1,
                x + size, y, z + size,              color.r, color.g, color.b, 1,
                x, y, z + size,                     color.r, color.g, color.b, 1,

                x, y + size, z,                     color.r, color.g, color.b, 1,
                x + size, y + size, z,              color.r, color.g, color.b, 1,
                x + size, y + size, z + size,       color.r, color.g, color.b, 1,
                x, y + size, z + size,              color.r, color.g, color.b, 1
        };
    }
}
