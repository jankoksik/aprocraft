import java.util.ArrayList;
import java.util.List;

public abstract class Biome {
    private String name;

    private int occurrence;

    private int amplitude;
    private int octave;

    private Block[] layers;

    private List<Block> ores;
    private List<Integer> oreOccurrence;

    private List<Structure> structures;
    private List<Integer> structureOccurrence;

    public Biome(String name, int occurrence) {
        this.name = name;

        this.occurrence = occurrence;

        amplitude = 12;
        octave = 24;

        layers = new Block[3];
        setLayers(Blocks.GRASS, Blocks.DIRT, Blocks.STONE);

        ores = new ArrayList<>();
        oreOccurrence = new ArrayList<>();

        structures = new ArrayList<>();
        structureOccurrence = new ArrayList<>();

        Biomes.registerBiome(this);
    }

    public void setAmplitude(int amplitude) {
        this.amplitude = amplitude;
    }

    public void setOctave(int octave) {
        this.octave = octave;
    }

    public void setLayers(Block lv1, Block lv2, Block lv3) {
        layers[0] = lv1;
        layers[1] = lv2;
        layers[2] = lv3;
    }

    public void addOre(Block block, int occurrence) {
        ores.add(block);
        oreOccurrence.add(occurrence);
    }

    public void addStructure(Structure type, int occurrence) {
        structures.add(type);
        structureOccurrence.add(occurrence);
    }

    public Block[] getLayers() {
        return layers;
    }

    public List<Structure> getStructures() {
        return structures;
    }

    public int getStructureOccurrence(int i) {
        return structureOccurrence.get(i);
    }

    public int getOccurrence() {
        return occurrence;
    }

    public int getAmplitude() {
        return amplitude;
    }

    public int getOctave() {
        return octave;
    }
}
