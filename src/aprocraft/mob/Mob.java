package aprocraft.mob;

import aprocraft.eq.Item;
import aprocraft.player.Player;
import org.joml.Vector3f;
import aprocraft.world.Block;
import aprocraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Klasa implemetująca moby - postaci niezależne od gracza zasiedlające generowany świat
 * Mob zdefiniowany jest przez: punkty życia, siłę ataku, przedmioty zdobywane po jego pokonaniu,
 * współrzedne jego przebywania, prędkość z którą się porusza, rozmiary moba, zacowanie
 */
public class Mob {
    private int hp;
    private int attack;
    private Item[] Drop;
    public float x, y, z;
    private float MoveTox, MoveToy, MoveToz;
    public float s; //size
    public float xSpeed, ySpeed, zSpeed;
    private int mobH = 2;
    private World world;
    private List<Vector3f> route = new ArrayList<>();

    public enum Behaviour {
        Attack, Escape, Follow, Search, Idle, Goto
    }

    private boolean Change = false;
    private Behaviour actBehaviour = Behaviour.Idle;

    public void ChangeAction(Behaviour beh) {
        actBehaviour = beh;
        Change = true;
    }

    public Mob(World world) {
        world = world;

    }

    /**
     * Metoda zwracająca punkty żywotnośc moba
     *
     * @return punkty życiaa
     */
    public int getHp() {
        return hp;
    }

    /**
     * Metoda ustawiająca mobowi punkty życia
     *
     * @param hp punkty życia
     */
    public void setHp(int hp) {
        this.hp = hp;
    }

    /**
     * Metoda zwracająca punkty ataku moba
     *
     * @return punkty ataku
     */
    public int getAttack() {
        return attack;
    }

    /**
     * Metoda ustawiająca punkty ataku mona
     *
     * @param attack punkty ataku
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }

    /**
     * Metoda zqracająca jake przedmiotu otrzymuje się po pokonaniu moba
     *
     * @return otrzymywane przedmioty
     */
    public Item[] getDrop() {
        return Drop;
    }

    /**
     * Metoda ustawiająca jake przedmiotu otrzymuje się po pokonaniu moba
     *
     * @param drop otrzymywane przedmioty
     */
    public void setDrop(Item[] drop) {
        Drop = drop;
    }

    /**
     * Metoda zwracająca współrzędną z moba
     *
     * @return współrzędna x
     */
    public float getX() {
        return x;
    }

    /**
     * Metoda ustawiająca pozycję moba
     *
     * @param x współrzena x
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Metoda zwracająca współrzędną z moba
     * y@return współrzędna x
     */
    public float getY() {
        return y;
    }

    /**
     * Metoda ustawiająca pozycję moba
     *
     * @param y współrzena y
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Metoda zwracająca współrzędną z moba
     * z@return współrzędna x
     */
    public float getZ() {
        return z;
    }

    /**
     * Metoda ustawiająca pozycję moba
     *
     * @param z współrzena z
     */
    public void setZ(float z) {
        this.z = z;
    }


    public void SetDest(int x, int y, int z) {
        /*Thread thread = new Thread(() -> {
            Pathfind path = new Pathfind(world, mobH);
            path.setDx(x);
            path.setDy(y);
            path.setDz(z);
            path.setSx(this.x);
            path.setSy(this.y);
            path.setSz(this.z);
            route = path.FindRoute2D();
        });
        thread.setName("Pathfind");
        thread.start();*/
    }

    public void Do() {

        if (Change) {
            Change = false;
            switch (actBehaviour) {
                case Goto:
                    break;

                case Idle:
                    break;

                case Attack:
                    break;

                case Escape:
                    break;

                case Follow:
                    break;

                case Search:
                    break;

            }

        }

    }


    public void GoTo(Player player) {
        int x = (int) player.getX();
        int y = (int) player.getY();
        int z = (int) player.getZ();
        SetDest(x, y, z);
        GoTo();
        // ur code here plz
    }


    public void GoTo() {
        if (route == null) {

        } else {
            Vector3f move = route.get(0);
            route.remove(0);
            MoveTox = move.x;
            MoveToz = move.z;
        }
    }

    public void GoTo(Block block) {

    }

    public void Attack(Player player) {

    }

    public void Attack(Mob mob) {

    }

    public boolean checkCollision(float x, float y, float z) {

        if (world.getBlock((int) x, (int) y, (int) z) != null) return true;

        return false;
    }

    /**
     * Metoda odświerzająca obecną pozycję moba
     *
     * @param world obiekt wygnerowanego świata
     */
    public void update(World world) {
        x += xSpeed;
        z += zSpeed;
        y += ySpeed;

        //if(y-s < world.getMaxHeight((int)x, (int)z))
        if (world.getBlock((int) x, (int) (y - s * 0.5f), (int) z) != null)
            ySpeed = 0.3f;

        ySpeed -= 0.01f;

        //xSpeed *= 0.96f;
        ySpeed *= 0.96f;
        //zSpeed *= 0.96f;

        if (world.getTime() % 240 == 0) {
            Random r = new Random();
            xSpeed = (r.nextInt(20) - 10) * 0.01f;
            zSpeed = (r.nextInt(20) - 10) * 0.01f;
        }
    }

    public void render() {
    }

}
