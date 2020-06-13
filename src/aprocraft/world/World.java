package aprocraft.world;

import aprocraft.mob.Slime;
import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import aprocraft.player.*;
import aprocraft.APROCraft;
import aprocraft.io.WorldSaveNRead;

public class World {
    public static final int SIZE = 32;
    public static final int HEIGHT = 3;
    public static final int RENDER_DISTANCE = 128; //192

    private Generator generator;
    private Generator biomeGenerator;

    //TODO: Poziomy chunkow

    /*public static final int HELL = 0;
    public static final int CAVES = 1;
    public static final int LAND = 2;
    public static final int CLOUDS = 3;
    public static final int SPACE = 4;*/

    public static final int CAVES = 0;
    public static final int LAND = 1;
    public static final int CLOUDS = 2;

    private Chunk[][][] chunks;

    private int time;
    private int skyTime;
    private int skyTimeDir;

    private int seed, biomeSeed;

    List<Slime> slimes;

    public World() {
        Random rand = new Random();
        seed = rand.nextInt();
        biomeSeed = rand.nextInt();

        generator = new Generator(seed, 32, 12);
        biomeGenerator = new Generator(biomeSeed, 96, 10);

        System.out.println("Seed: " + seed + "\nBiome Seed: " + biomeSeed);

        chunks = new Chunk[SIZE][HEIGHT][SIZE];

        time = 0;
        skyTime = 3000;
        skyTimeDir = 1;

        slimes = new ArrayList<>();

        for(int i = 0; i < 200; i ++)
            slimes.add(new Slime(this, rand.nextInt(1024), 32, rand.nextInt(1024), 1+rand.nextInt(30)*0.1f));

        /*for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                float dist = Vector2f.distance(0, 0, (i + 0.5f) * aprocraft.world.Chunk.SIZE, (j + 0.5f) * aprocraft.world.Chunk.SIZE);
                if (dist <= RENDER_DISTANCE)
                    chunks[i][j] = new aprocraft.world.Chunk(i, j, generator, biomeGenerator);
            }*/

        //blendBiomes();
        //generateTrees(6 * SIZE * SIZE);
        //generateStructures();
        //generateClouds(1 * SIZE * SIZE);

        /*for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++)
                if(chunks[i][j] != null)
                    chunks[i][j].create(this);*/
    }

//    public void init() {
//        slime = new Slime(this, 512, 32,512, 4);
//    }

    public Generator getGenerator() {
        return generator;
    }

    public Generator getBiomeGenerator() {
        return biomeGenerator;
    }

    /*public void blendBiomes() {
        for (int i = 0; i < SIZE*aprocraft.world.Chunk.SIZE; i++)
            for (int j = 0; j < SIZE*aprocraft.world.Chunk.SIZE; j++) {
                Random r = new Random();
                if(r.nextBoolean() == true) {
                    setBlock(i * aprocraft.world.Chunk.SIZE, j * aprocraft.world.Chunk.SIZE, getMaxHeight(i * aprocraft.world.Chunk.SIZE, j * aprocraft.world.Chunk.SIZE),
                            getBlock(i * aprocraft.world.Chunk.SIZE + 1 - r.nextInt(1), j * aprocraft.world.Chunk.SIZE + 1 - r.nextInt(1),
                                    getMaxHeight(i * aprocraft.world.Chunk.SIZE + 1 - r.nextInt(1), j * aprocraft.world.Chunk.SIZE + 1 - r.nextInt(1))));
                    setBlock(aprocraft.world.Chunk.SIZE*(i%aprocraft.world.Chunk.SIZE), getMaxHeight(aprocraft.world.Chunk.SIZE*(i%aprocraft.world.Chunk.SIZE), aprocraft.world.Chunk.SIZE*(j%aprocraft.world.Chunk.SIZE)), aprocraft.world.Chunk.SIZE*(j%aprocraft.world.Chunk.SIZE), aprocraft.world.Blocks.DARK_LEAVES);
                }
            }
    }*/

    /*public void generateTrees(int n) {
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            int x = r.nextInt(SIZE * aprocraft.world.Chunk.SIZE);
            int z = r.nextInt(SIZE * aprocraft.world.Chunk.SIZE);
            int y = getMaxHeight(x, z);

            //if(getBlock(x, y, z) == aprocraft.world.Blocks.GRASS)
            //    aprocraft.world.Structures.OAK_TREE.spawn(this, x, y+1, z);
            aprocraft.world.Chunk c = getChunk(x, z);
            if (c == null) continue;
            for(aprocraft.world.Structure s : c.getBiome().getStructures())
                if(getBlock(x, y, z) == aprocraft.world.Blocks.GRASS || getBlock(x, y, z) == aprocraft.world.Blocks.SAND)
                    s.spawn(this, x, y + 1, z);
        }
    }*/

    public void generateStructures() {
        Random r = new Random();

        /*for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                aprocraft.world.Biome b = chunks[i][j].getBiome();
                for(int k = 0; k < b.getStructures().size(); k ++) {
                    for(int l = 0; l < b.getStructureOccurrence(k); l ++) {
                        int x = i * aprocraft.world.Chunk.SIZE + r.nextInt(aprocraft.world.Chunk.SIZE);
                        int z = j * aprocraft.world.Chunk.SIZE + r.nextInt(aprocraft.world.Chunk.SIZE);
                        int y = getMaxHeight(x, z);
                        if(getBlock(x, y, z) == aprocraft.world.Blocks.GRASS || getBlock(x, y, z) == aprocraft.world.Blocks.SAND)
                            b.getStructures().get(k).spawn(this, x, y + 1, z);
                    }
                }
            }*/

        /*for(int i = 0; i < SIZE*aprocraft.world.Chunk.SIZE; i ++)
            for(int j = 0; j < SIZE*aprocraft.world.Chunk.SIZE; j ++) {
                if(r.nextInt(100) == 1) {
                    aprocraft.world.Biome b = getBiome(i, j);
                    int y = getMaxHeight(i, j);
                    if (getBlock(i, y, j) == aprocraft.world.Blocks.GRASS || getBlock(i, y, j) == aprocraft.world.Blocks.SAND) {
                        aprocraft.world.Structure s = b.chooseStructure();
                        //if(b.getStructures().size() > 0) {
                        //    aprocraft.world.Structure s = b.getStructures().get(r.nextInt(b.getStructures().size()));
                            if (s != null)
                                s.spawn(this, i, y + 1, j);
                        //}
                    }
                }
            }*/

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                if (chunks[i][LAND][j] == null) continue;
                if (!chunks[i][LAND][j].hasStructures) {
                    for (int a = 0; a < 10; a++) {
                        int x = i * Chunk.SIZE + r.nextInt(Chunk.SIZE - 1);
                        int z = j * Chunk.SIZE + r.nextInt(Chunk.SIZE - 1);
                        Biome b = getBiome(x, z);
                        int y = getMaxHeight(x, z);
                        if (getBlock(x, y, z) == Blocks.GRASS || getBlock(x, y, z) == Blocks.SAND) {
                            Structure s = b.chooseStructure();
                            //if(b.getStructures().size() > 0) {
                            //    aprocraft.world.Structure s = b.getStructures().get(r.nextInt(b.getStructures().size()));
                            if (s != null)
                                s.spawn(this, x, y + 1, z);
                            //}
                        }
                    }

                    chunks[i][LAND][j].hasStructures = true;
                }
            }
    }

    /*public void generateStructures(aprocraft.world.Chunk chunk) {
        Random r = new Random();

        for (int i = 0; i < aprocraft.world.Chunk.SIZE; i++)
            for (int j = 0; j < aprocraft.world.Chunk.SIZE; j++) {
                if (r.nextInt(100) == 1) {
                    aprocraft.world.Biome b = getBiome(chunk.getX() * aprocraft.world.Chunk.SIZE + i, chunk.getX() * aprocraft.world.Chunk.SIZE + j);
                    int y = getMaxHeight(chunk.getX() * aprocraft.world.Chunk.SIZE + i, chunk.getX() * aprocraft.world.Chunk.SIZE + j);
                    if (getBlock(chunk.getX() * aprocraft.world.Chunk.SIZE + i, y, chunk.getX() * aprocraft.world.Chunk.SIZE + j) == aprocraft.world.Blocks.GRASS || getBlock(chunk.getX() * aprocraft.world.Chunk.SIZE + i, y, chunk.getX() * aprocraft.world.Chunk.SIZE + j) == aprocraft.world.Blocks.SAND) {
                        aprocraft.world.Structure s = b.chooseStructure();
                        //if(b.getStructures().size() > 0) {
                        //    aprocraft.world.Structure s = b.getStructures().get(r.nextInt(b.getStructures().size()));
                        if (s != null)
                            s.spawn(this, chunk.getX() * aprocraft.world.Chunk.SIZE + i, y + 1, chunk.getZ() * aprocraft.world.Chunk.SIZE + j);
                        //}
                    }
                }
            }
    }*/

    public void generateClouds(int n) {
        Random r = new Random();
        /*for (int i = 0; i < n; i++) {
            int x = r.nextInt(SIZE * aprocraft.world.Chunk.SIZE);
            int z = r.nextInt(SIZE * aprocraft.world.Chunk.SIZE);
            int y = aprocraft.world.Chunk.SIZE - 2;

            for (int j = 0; j < 6; j++) {
                aprocraft.world.Structures.CLOUD.spawn(this, x + r.nextInt(5) - 2, y + r.nextInt(2) - 1, z + r.nextInt(5) - 2);
            }
        }*/

        for (int i = 0; i < SIZE; i++)
            for (int j = 0; j < SIZE; j++) {
                if (chunks[i][CLOUDS][j] == null) continue;
                if (!chunks[i][CLOUDS][j].hasClouds) {
                    for (int a = 0; a < n; a++) {
                        int x = i * Chunk.SIZE + r.nextInt(Chunk.SIZE - 1);
                        int z = j * Chunk.SIZE + r.nextInt(Chunk.SIZE - 1);

                        for (int q = 0; q < 3; q++)
                            Structures.CLOUD.spawn(this, x + r.nextInt(5) - 2, (CLOUDS + 1) * Chunk.SIZE - 12 + r.nextInt(2) - 1, z + r.nextInt(5) - 2);
                    }

                    chunks[i][CLOUDS][j].hasClouds = true;
                }
            }
    }

    public int getMaxHeight(int x, int z) {
        int xx = x / Chunk.SIZE;
        int zz = z / Chunk.SIZE;
        if (xx < 0 || zz < 0 || xx >= SIZE || zz >= SIZE) return 0;
        Chunk c = chunks[xx][LAND][zz];
        if (c == null) return 0;
        return c.getMaxHeight(x % Chunk.SIZE, z % Chunk.SIZE) + LAND * Chunk.SIZE;
    }

    public Chunk getChunk(int x, int y, int z) {
        int xx = x / Chunk.SIZE;
        int yy = y / Chunk.SIZE;
        int zz = z / Chunk.SIZE;
        if (xx < 0 || zz < 0 || yy < 0 || xx >= SIZE || zz >= SIZE || yy >= HEIGHT) return null;
        return chunks[xx][yy][zz];
    }

    public Block getBlock(int x, int y, int z) {
        /*int xx = x / Chunk.SIZE;
        int zz = z / Chunk.SIZE;
        if (xx < 0 || zz < 0 || xx >= SIZE || zz >= SIZE) return null;
        Chunk c = chunks[xx][zz];*/
        Chunk c = getChunk(x, y, z);
        if (c == null) return null;
        return c.getBlock(x % Chunk.SIZE, y % Chunk.SIZE, z % Chunk.SIZE);
    }

    public Biome getBiome(int x, int z) {
        int xx = x / Chunk.SIZE;
        int zz = z / Chunk.SIZE;
        if (xx < 0 || zz < 0 || xx >= SIZE || zz >= SIZE) return null;
        Chunk c = chunks[xx][LAND][zz];
        if (c == null) return null;
        return c.getBiome(x, z);
    }

    public void setChunk(int x, int y, int z, Chunk c) {
        chunks[x][y][z] = c;
    }

    public void setBlock(int x, int y, int z, Block block) {
        /*int xx = x / Chunk.SIZE;
        int zz = z / Chunk.SIZE;
        if (xx < 0 || zz < 0 || xx >= SIZE || zz >= SIZE) return;
        Chunk c = chunks[xx][zz];*/
        Chunk c = getChunk(x, y, z);
        if (c == null) return;
        c.setBlock(x % Chunk.SIZE, y % Chunk.SIZE, z % Chunk.SIZE, block);
    }

    public void placeBlock(int x, int y, int z, Block block) {
        /*int xx = x / Chunk.SIZE;
        int zz = z / Chunk.SIZE;
        if (xx < 0 || zz < 0 || xx >= SIZE || zz >= SIZE) return;
        Chunk c = chunks[xx][zz];*/
        Chunk c = getChunk(x, y, z);
        if (c == null) return;
        c.placeBlock(x % Chunk.SIZE, y % Chunk.SIZE, z % Chunk.SIZE, block, this);
    }

    public void update(Player player) {
        time++;
        skyTime += skyTimeDir;

        if (time >= 7200)
            time = 0;

        if (skyTime == 3600 || skyTime == 0)
            skyTimeDir *= -1;

        if (time % 20 == 0) {
            //for (int l = 0; l < HEIGHT; l++) {
            for (int i = 0; i < SIZE; i++)
                for (int j = 0; j < SIZE; j++) {
                    float dist = Vector2f.distance(player.getX(), player.getZ(), (i + 0.5f) * Chunk.SIZE, (j + 0.5f) * Chunk.SIZE);
                    if (dist <= RENDER_DISTANCE)
                        if (chunks[i][LAND][j] == null) {
                            chunks[i][LAND][j] = new Chunk(i, LAND, j, generator, biomeGenerator);
                            chunks[i][LAND][j].generate(16);
                            chunks[i][CLOUDS][j] = new Chunk(i, CLOUDS, j, generator, biomeGenerator);
                            chunks[i][CAVES][j] = new Chunk(i, CAVES, j, generator, biomeGenerator);
                            chunks[i][CAVES][j].generateStone();
                            generateStructures();
                            generateClouds(2);
                            chunks[i][LAND][j].create(this);
                            chunks[i][CLOUDS][j].create(this);
                            chunks[i][CAVES][j].create(this);
                            //chunks[i][j].updateChunk(this);
                        /*if(i > 0 && i < SIZE && j > 0 && j < SIZE) {
                            if(chunks[i - 1][j] != null)
                                chunks[i - 1][j].updateChunk(this);
                            if(chunks[i + 1][j] != null)
                                chunks[i + 1][j].updateChunk(this);
                            if(chunks[i][j - 1] != null)
                                chunks[i][j - 1].updateChunk(this);
                            if(chunks[i][j + 1] != null)
                                chunks[i][j + 1].updateChunk(this);
                        }*/
                        }
                }
            //}
        }

        if (time % 60 == 0) {
            Chunk c = getChunk((int) player.getX(), (int) player.getY(), (int) player.getZ());
            if (c != null)
                c.updateChunk(this);

            c = getChunk((int) player.getX()+Chunk.SIZE, (int) player.getY(), (int) player.getZ());
            if (c != null)
                c.updateChunk(this);

            c = getChunk((int) player.getX()-Chunk.SIZE, (int) player.getY(), (int) player.getZ());
            if (c != null)
                c.updateChunk(this);

            c = getChunk((int) player.getX(), (int) player.getY(), (int) player.getZ()+Chunk.SIZE);
            if (c != null)
                c.updateChunk(this);

            c = getChunk((int) player.getX(), (int) player.getY(), (int) player.getZ()-Chunk.SIZE);
            if (c != null)
                c.updateChunk(this);

            c = getChunk((int) player.getX(), (int) player.getY()-Chunk.SIZE, (int) player.getZ());
            if (c != null)
                c.updateChunk(this);
        }

        for(Slime slime : slimes)
            if(Vector2f.distance(player.getX(), player.getZ(), slime.getX(), slime.getZ()) <= RENDER_DISTANCE)
                slime.update(this);
    }

    public void render(Player player) {
        for (int l = 1; l < HEIGHT; l++)
            for (int i = 0; i < SIZE; i++)
                for (int j = 0; j < SIZE; j++) {
                    float dist = Vector2f.distance(player.getX(), player.getZ(), (i + 0.5f) * Chunk.SIZE, (j + 0.5f) * Chunk.SIZE);
                    //System.out.println(dist);
                    if (dist <= RENDER_DISTANCE / 3) {
                        if (chunks[i][l][j] != null)
                            chunks[i][l][j].render();
                    } else if (dist <= RENDER_DISTANCE) {
                        float angle = clampRot(getAngle(player, (i + 0.5f) * Chunk.SIZE, (j + 0.5f) * Chunk.SIZE) - (clampRot(player.getYRot())));
                        //System.out.println(angle);
                        if (angle >= 30 && angle <= 150)
                            if (chunks[i][l][j] != null)
                                chunks[i][l][j].render();
                    }
                }

        Chunk c = getChunk((int) player.getX(), CAVES, (int) player.getZ());
        if (c != null)
            c.render();
        c = getChunk((int) player.getX()+Chunk.SIZE, CAVES, (int) player.getZ());
        if (c != null)
            c.render();
        c = getChunk((int) player.getX()-Chunk.SIZE, CAVES, (int) player.getZ());
        if (c != null)
            c.render();
        c = getChunk((int) player.getX(), CAVES, (int) player.getZ()+Chunk.SIZE);
        if (c != null)
            c.render();
        c = getChunk((int) player.getX(), CAVES, (int) player.getZ()-Chunk.SIZE);
        if (c != null)
            c.render();

        for(Slime slime : slimes)
            if(Vector2f.distance(player.getX(), player.getZ(), slime.getX(), slime.getZ()) <= RENDER_DISTANCE)
                slime.render();
    }

    private float clampRot(float angle) {
        float res = angle;
        while (res < 0) {
            res += 360;
        }

        res %= 360;

        return res;
    }

    private float getAngle(Player p, float x, float z) {
        float angle = (float) Math.toDegrees(Math.atan2(p.getZ() - z, p.getX() - x));

        while (angle < 0) {
            angle += 360;
        }

        angle %= 360;

        return angle;
    }

    public int getTime() {
        return time;
    }

    public float getSkyColorMultiplier() {
        return ((float) skyTime / 3600f);
    }

    public Chunk[][][] getChunks() {
        return chunks;
    }

    public void AutoSave() {

        /*Thread thread = new Thread(() -> {
            System.out.println("AutoSave");
            WorldSaveNRead.Save(APROCraft.GAME_NAME, this.getChunks());
        });
        thread.setName("AutoSave_World");
        thread.start();*/
    }
}
