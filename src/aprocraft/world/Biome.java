package aprocraft.world;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Klasa implementująca biomy
 */

public abstract class Biome {
    private String name;

    private int occurrence;

    private int amplitude;
    private int octave;

    private Block[] layers;

    private List<Block> ores;
    private List<Integer> oreOccurrence;
    private int totalOres;

    private List<Structure> structures;
    private List<Integer> structureOccurrence;
    private int totalStructures;

    /**
     * Konstruktor klasy Biome
     *
     * @param name       nazwa biomu
     * @param occurrence czestotliwosc wystepowania
     */
    public Biome(String name, int occurrence) {
        this.name = name;

        this.occurrence = occurrence;

        totalOres = 0;
        totalStructures = 0;

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

    /**
     * Metoda ustawiająca wartyw terenu
     *
     * @param lv1 typ bloków tworzących warstwę 1
     * @param lv2 typ bloków tworzących warstwę 2
     * @param lv3 typ bloków tworzących warstwę 3
     */
    public void setLayers(Block lv1, Block lv2, Block lv3) {
        layers[0] = lv1;
        layers[1] = lv2;
        layers[2] = lv3;
    }

    /**
     * Metoda dodająca rudę
     *
     * @param block      typ rudy
     * @param occurrence szansa występowani ustawionej rudy
     */
    public void addOre(Block block, int occurrence) {
        ores.add(block);
        oreOccurrence.add(occurrence);
        totalOres += occurrence;
    }

    /**
     * Metod adodająca struktue
     *
     * @param type       typ struktury
     * @param occurrence szansa występowani ustawionej struktury
     */
    public void addStructure(Structure type, int occurrence) {
        structures.add(type);
        structureOccurrence.add(occurrence);
        totalStructures += occurrence;
    }

    /**
     * Metoda zwracająca ustawione warstwy
     *
     * @return tablica zawierająca typy bloków tworzących warstwy
     */
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


    /**
     * Metoda wybierająca struktórę do dodania ze zbioru struktór
     *
     * @return wybrana struktura
     */
    public Structure chooseStructure() {
        if (totalStructures == 0) return null;

        int index = new Random().nextInt(totalStructures);
        int sum = 0;
        int i = 0;
        while (sum < index) {
            sum = sum + structureOccurrence.get(i++);
        }

        return structures.get(Math.max(0, i - 1));
    }

    /**
     * Metoda wybierająca rudę do dodania ze zbioru rud
     *
     * @return wybrana ruda
     */
    public Block chooseOre() {
        if (totalOres == 0) return null;

        int index = new Random().nextInt(totalOres);
        int sum = 0;
        int i = 0;
        while (sum < index) {
            sum = sum + oreOccurrence.get(i++);
        }

        return ores.get(Math.max(0, i - 1));
    }

    /**
     * Metoda sumująca ilośc wszystkich bloków rudy
     *
     * @return ilość bloków
     */
    public int getTotalOres() {
        return totalOres;
    }
}
