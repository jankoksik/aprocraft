package aprocraft.mob;

import aprocraft.eq.Item;
import aprocraft.player.Player;
import org.joml.Vector3f;
import aprocraft.world.Block;
import aprocraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_FILL;

public class Mob {
    private int hp;
    private int attack;
    private Item[] Drop;
    float x, y, z;
    float MoveTox, MoveToy, MoveToz;
    float s; //size
    float xSpeed, ySpeed, zSpeed;
    int mobH = 2;
    World world;
    List<Vector3f> route = new ArrayList<>();

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

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public Item[] getDrop() {
        return Drop;
    }

    public void setDrop(Item[] drop) {
        Drop = drop;
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }


    public void SetDest(int x, int y, int z) {
        Thread thread = new Thread(() -> {
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
        thread.start();
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
        SetDest(x,y,z);
        GoTo();
        // ur code here plz
    }

    public void GoTo() {
        if (route == null) {

        }
       else{
            Vector3f move = route.get(0);
            route.remove(0);
            MoveTox = move.x;
            MoveToz = move.z;
        }
    }

    public void GoTo(Block block) {
        // ur code here plz
    }

    public void Attack(Player player) {
        //type code here
    }

    public void Attack(Mob mob) {
        //type code here
    }

    public boolean checkCollision(float x, float y, float z) {

        if (world.getBlock((int) x, (int) y, (int) z) != null) return true;

        return false;
    }

    public void update(World world) {
        x += xSpeed;
        z += zSpeed;
        y += ySpeed;

        //if(y-s < world.getMaxHeight((int)x, (int)z))
        if(world.getBlock((int)x, (int)(y-s*0.5f), (int)z) != null)
            ySpeed = 0.3f;

        ySpeed -= 0.01f;

        //xSpeed *= 0.96f;
        ySpeed *= 0.96f;
        //zSpeed *= 0.96f;

        if(world.getTime()%240==0) {
            Random r = new Random();
            xSpeed = (r.nextInt(20)-10)*0.01f;
            zSpeed = (r.nextInt(20)-10)*0.01f;
        }
    }

    public void render() {
    }

}
