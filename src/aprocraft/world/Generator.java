package aprocraft.world;

import org.joml.Vector2f;

import java.util.Random;

/**
 * Klasa implementujaca generator szumu uzywany do generacji mapy wysokosciwej swiata oraz mapy biomow
 */
public class Generator {
    private Random rand;

    private int seed;
    private int octave;
    private float amplitude;

    public Generator(int seed, int octave, float amplitude) {
        this.seed = seed;
        this.octave = octave;
        this.amplitude = amplitude;

        rand = new Random();
    }

    public float getHeight(float x, float z) {
        int xmin = (int) (double) x / octave;
        int xmax = xmin + 1;
        int zmin = (int) (double) z / octave;
        int zmax = zmin + 1;

        Vector2f a = new Vector2f(xmin, zmin);
        Vector2f b = new Vector2f(xmax, zmin);
        Vector2f c = new Vector2f(xmax, zmax);
        Vector2f d = new Vector2f(xmin, zmax);

        float ra = (float) noise(a);
        float rb = (float) noise(b);
        float rc = (float) noise(c);
        float rd = (float) noise(d);

        float t1 = (x - xmin * octave) / octave;
        float t2 = (z - zmin * octave) / octave;

        float i1 = interpolate(ra, rb, t1);
        float i2 = interpolate(rd, rc, t1);
        float h = interpolate(i1, i2, t2);

        return h * amplitude;
    }

    private float interpolate(float a, float b, float t) {
        float ft = (float) (t * Math.PI);
        float f = (float) ((1f - Math.cos(ft)) * 0.5f);

        return a * (1f - f) + b * f;
    }

    private double noise(Vector2f coord) {
        rand.setSeed((long) ((Math.sin(coord.x + Math.cos(coord.y)) + Math.tan(seed)) * 10000));
        return rand.nextDouble();
    }

    public int getSeed() {
        return seed;
    }

    public void setSeed(int seed) { this.seed = seed; }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    public void setAmplitude(int amplitude) {
        this.amplitude = amplitude;
    }

    public float getAmplitude() {
        return amplitude;
    }
}
