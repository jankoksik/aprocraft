public abstract class Block {
    public static final Block GRASS = new BlockGrass();

    private RGB color;
    private float size;

    private float[] colorData;

    public Block(RGB color) {
        this.color = color;
        size = 1f;

        colorData = new float[] {
                //back
                color.r*0.7f, color.g*0.7f, color.b*0.7f, 1,
                color.r*0.7f, color.g*0.7f, color.b*0.7f, 1,
                color.r*0.7f, color.g*0.7f, color.b*0.7f, 1,
                color.r*0.7f, color.g*0.7f, color.b*0.7f, 1,

                //right
                color.r*0.6f, color.g*0.6f, color.b*0.6f, 1,
                color.r*0.6f, color.g*0.6f, color.b*0.6f, 1,
                color.r*0.6f, color.g*0.6f, color.b*0.6f, 1,
                color.r*0.6f, color.g*0.6f, color.b*0.6f, 1,

                //left
                color.r*0.9f, color.g*0.9f, color.b*0.9f, 1,
                color.r*0.9f, color.g*0.9f, color.b*0.9f, 1,
                color.r*0.9f, color.g*0.9f, color.b*0.9f, 1,
                color.r*0.9f, color.g*0.9f, color.b*0.9f, 1,

                //front
                color.r*0.8f, color.g*0.8f, color.b*0.8f, 1,
                color.r*0.8f, color.g*0.8f, color.b*0.8f, 1,
                color.r*0.8f, color.g*0.8f, color.b*0.8f, 1,
                color.r*0.8f, color.g*0.8f, color.b*0.8f, 1,

                //bottom
                color.r*0.5f, color.g*0.5f, color.b*0.5f, 1,
                color.r*0.5f, color.g*0.5f, color.b*0.5f, 1,
                color.r*0.5f, color.g*0.5f, color.b*0.5f, 1,
                color.r*0.5f, color.g*0.5f, color.b*0.5f, 1,

                //top
                color.r, color.g, color.b, 1,
                color.r, color.g, color.b, 1,
                color.r, color.g, color.b, 1,
                color.r, color.g, color.b, 1
        };
    }

    public float[] getData(float x, float y, float z) {
        return new float[] {
                x, y, z,
                x + size, y, z,
                x + size, y + size, z,
                x, y + size, z,

                x, y, z + size,
                x + size, y, z + size,
                x + size, y + size, z + size,
                x, y + size, z + size,

                x, y, z,
                x, y + size, z,
                x, y + size, z + size,
                x, y, z + size,

                x + size, y, z,
                x + size, y + size, z,
                x + size, y + size, z + size,
                x + size, y, z + size,

                x, y, z,
                x + size, y, z,
                x + size, y, z + size,
                x, y, z + size,

                x, y + size, z,
                x + size, y + size, z,
                x + size, y + size, z + size,
                x, y + size, z + size
        };
    }

    public float[] getColorData() {
        return colorData;
    }
}
