package aprocraft.io;

import aprocraft.world.Blocks;
import aprocraft.world.World;
import com.google.gson.Gson;
import org.json.simple.JSONObject;
import aprocraft.world.Chunk;

import java.io.*;

/**
 * Klasa implemetująca możliwość wczytywania i zapisywania obecnego stanu rozgrywki
 */
public class WorldSaveNRead {

    /**
     * Metoda odpowiadająca za wczytanie stanu rozgrywki, poprez wczytanie poszczególnych chunków
     * @param gameName nazwa rozgrywki, którą chcemy wczytać
     * @param world obiekt wygnerowanego świata
     * @throws IOException
     */
    public static void load(String gameName, World world) throws IOException {
        boolean success = (new File("./Saves/" + gameName)).mkdirs();

        try {
            FileReader fr = new FileReader("./Saves/" + gameName + "/" + "world.sav");

            world.getGenerator().setSeed(fr.read());
            world.getBiomeGenerator().setSeed(fr.read());

            fr.close();
        } catch (FileNotFoundException e) {
            System.out.println("No save file found");
            return;
        }

        for (int x = 0; x < World.SIZE; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {
                for (int z = 0; z < World.SIZE; z++) {
                    world.setChunk(x, y, z, loadChunk(gameName, world, x, y, z));
                }
            }
        }
    }

    /**
     * Metoda wczytująca konkretny chunk wczytywanej rozgrywki
     * @param gameName nazwa wczytywanej rozgrywki
     * @param world obiekt wygnerowanego świata
     * @param x współrzędna x chunka
     * @param y współrzędna y chunka
     * @param z współrzędna z chunka
     * @return ładuje wskazanego chunka
     * @throws IOException
     */
    public static Chunk loadChunk(String gameName, World world, int x, int y, int z) throws IOException {
        Chunk chunk = new Chunk(x, y, z, world.getGenerator(), world.getBiomeGenerator());

        try {
            FileReader br = new FileReader("./Saves/" + gameName + "/" + chunk.getX() + "_" + chunk.getY() + "_" + chunk.getZ() + ".chk");

            for (int i = 0; i < Chunk.SIZE; i++) {
                for (int j = 0; j < Chunk.SIZE; j++) {
                    for (int k = 0; k < Chunk.SIZE; k++) {
                        int id = br.read();

                        if(id != 0)
                            chunk.setBlock(i, j, k, Blocks.searchByID(id));
                    }
                }
            }

            br.close();
        } catch (FileNotFoundException e) {
            return null;
        }

        chunk.hasClouds = true;
        chunk.hasStructures = true;
        chunk.create(world);

        return chunk;
    }

    /**
     * Metoda zapisująca stan rozgrywki, zapisując poszczególne chunki
     * @param gameName nazwa pod jaką chcemy zapisać rozgrywkę
     * @param world obiekt wygnerowanego świata
     * @throws IOException
     */
    public static void save(String gameName, World world) throws IOException {
        boolean success = (new File("./Saves/" + gameName)).mkdirs();

        FileWriter bw = new FileWriter("./Saves/" + gameName + "/" + "world.sav");

        bw.write(world.getGenerator().getSeed());
        bw.write(world.getBiomeGenerator().getSeed());

        bw.close();

        for (int x = 0; x < World.SIZE; x++) {
            for (int y = 0; y < World.HEIGHT; y++) {
                for (int z = 0; z < World.SIZE; z++) {
                    if (world.getChunks()[x][y][z] == null)
                        continue;

                    saveChunk(gameName, world.getChunks()[x][y][z]);

                    System.out.println("Chunk " + x + "_" + y + "_" + z + " saved.");
                }
            }
        }
    }

    /**
     * Metoda zapisująca konkretny chunk
     * @param gameName nazwa pod jaką chcemy zapisać rozgrywkę
     * @param chunk chunk który chcemy zapisać
     * @throws IOException
     */
    public static void saveChunk(String gameName, Chunk chunk) throws IOException {
        boolean success = (new File("./Saves/" + gameName)).mkdirs();

        if(chunk == null)
            return;

        FileWriter bw = new FileWriter("./Saves/" + gameName + "/" + chunk.getX() + "_" + chunk.getY() + "_" + chunk.getZ() + ".chk");

        for (int x = 0; x < Chunk.SIZE; x++) {
            for (int y = 0; y < Chunk.SIZE; y++) {
                for (int z = 0; z < Chunk.SIZE; z++) {
                    int id = 0;

                    if(chunk.getBlock(x,y,z) != null)
                        id = chunk.getBlock(x,y,z).getID();

                    bw.write(id);
                }
            }
        }

        bw.close();
    }


    public static void Update() {


    }
}


