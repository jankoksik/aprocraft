package aprocraft.world;

/**
 * Klasa implementująca obiekt bloków
 */

public abstract class Block {
    public static final int NORMAL = 0;
    public static final int BOUNCY = 1;
    public static final int STICKY = 2;
    public static final int SLIPPY = 3;
    public static final int HURTING = 4;

    private int id;
    private String name;

    private RGB color;
    private int size;
    private int coordX, coordY;

    private int drop;
    private float durability;
    private int material;

    private float[] textureData, lightData;

    /**
     * Konstruktor obiektu
     * @param name nazwa bloku
     * @param id identyfikator bloku
     */
    public Block(String name, int id) {
        this.name = name;

        this.id = id;

        drop = id;
        durability = 20;
        material = NORMAL;

        size = 1;

        color = new RGB(1, 1, 1);

        coordX = (id-1) % 16;
        coordY = (id-1) / 16;

        float tx = coordX / 16.0f;
        float ty = coordY / 16.0f;
        float s = 1f / 16.0f;

        textureData = new float[]{
                tx, ty, tx + s, ty, tx + s, ty + s, tx, ty + s,
                tx, ty, tx + s, ty, tx + s, ty + s, tx, ty + s,
                tx, ty, tx, ty + s, tx + s, ty + s, tx + s, ty,
                tx, ty, tx, ty + s, tx + s, ty + s, tx + s, ty,
                tx, ty, tx + s, ty, tx + s, ty + s, tx, ty + s,
                tx, ty, tx + s, ty, tx + s, ty + s, tx, ty + s
        };

        lightData = new float[]{
                //back
                color.r * 0.65f, color.g * 0.65f, color.b * 0.65f, 1,
                color.r * 0.65f, color.g * 0.65f, color.b * 0.65f, 1,
                color.r * 0.65f, color.g * 0.65f, color.b * 0.65f, 1,
                color.r * 0.65f, color.g * 0.65f, color.b * 0.65f, 1,

                //front
                color.r * 0.75f, color.g * 0.75f, color.b * 0.75f, 1,
                color.r * 0.75f, color.g * 0.75f, color.b * 0.75f, 1,
                color.r * 0.75f, color.g * 0.75f, color.b * 0.75f, 1,
                color.r * 0.75f, color.g * 0.75f, color.b * 0.75f, 1,

                //left
                color.r * 0.85f, color.g * 0.85f, color.b * 0.85f, 1,
                color.r * 0.85f, color.g * 0.85f, color.b * 0.85f, 1,
                color.r * 0.85f, color.g * 0.85f, color.b * 0.85f, 1,
                color.r * 0.85f, color.g * 0.85f, color.b * 0.85f, 1,

                //right
                color.r * 0.5f, color.g * 0.5f, color.b * 0.5f, 1,
                color.r * 0.5f, color.g * 0.5f, color.b * 0.5f, 1,
                color.r * 0.5f, color.g * 0.5f, color.b * 0.5f, 1,
                color.r * 0.5f, color.g * 0.5f, color.b * 0.5f, 1,

                //bottom
                color.r * 0.4f, color.g * 0.4f, color.b * 0.4f, 1,
                color.r * 0.4f, color.g * 0.4f, color.b * 0.4f, 1,
                color.r * 0.4f, color.g * 0.4f, color.b * 0.4f, 1,
                color.r * 0.4f, color.g * 0.4f, color.b * 0.4f, 1,

                //top
                color.r * 0.95f, color.g * 0.95f, color.b * 0.95f, 1,
                color.r * 0.95f, color.g * 0.95f, color.b * 0.95f, 1,
                color.r * 0.95f, color.g * 0.95f, color.b * 0.95f, 1,
                color.r * 0.95f, color.g * 0.95f, color.b * 0.95f, 1
        };

        Blocks.registerBlock(this);
    }

    /**
     * Metoda zwracająca informacje o położeniu bloku
     * @param x współrzędna x
     * @param y współrzędna y
     * @param z współrzędna z
     * @return talica współrzędnych
     */
    public float[] getData(float x, float y, float z) {
        return new float[]{
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

    /**
     * Metoda ustawiająca jaki przedmiot otrzymuje gracz po zniszczeniu bloku
     * @param drop otrzymywany blok
     */
    public void setDrop(int drop) {
        this.drop = drop;
    }

    /**
     * Metoda ustawiająca materiał bloku
     * @param material materiał bloku
     */
    public void setMaterial(int material) {
        this.material = material;
    }

    /**
     * Metoda ustawiająca wytrzymałość bloku
     * @param durability wytrzymałość bloku
     */
    public void setDurability(float durability) {
        this.durability = durability;
    }

    /**
     * Metoda zwracająca teksturę bloku
     * @return tekstura bloku
     */
    public float[] getTextureData() {
        return textureData;
    }

    /**
     * Metoda zwracająca oświetlenie bloku
     * @return oświetlenie bloku
     */
    public float[] getLightData() {
        return lightData;
    }

    /**
     * Metoda zwracająca kolor bloku
     * @return klor wyrażony w notacji RGB
     */
    public RGB getColor() {
        return color;
    }

    /**
     * Metoda zwracająca materiał bloku
     * @return materiał bloku
     */
    public int getMaterial() {
        return material;
    }

    /**
     * Metoda zwracająca wytrzymałość bloku
     * @return wytrzxymałość
     */
    public float getDurability() {
        return durability;
    }

    /**
     * Metoda zwracająca jaki przedmiot otrzyma gracz po zniszczeniu bloku
     * @return otrzymywany przedmiot
     */
    public int getDrop() {
        return drop;
    }

    /**
     * Metoda zwracająca identyfiaktor bloku
     * @return idetyfikator
     */
    public int getID() {
        return id;
    }

    @Override
    public String toString() {
        return name + " (" + id + ")";
    }
}
