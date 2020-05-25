import org.joml.Vector2f;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.List;

public class Mob {
   private int hp;
   private int attack;
   private Item[] Drop;
   float x,y,z;
   int mobH = 2;
   World map;
   List<Vector3f> route = new ArrayList<>();


    public enum Behaviour {
        Attack, Escape, Follow, Search, Idle, Goto
    };
    private boolean Change = false;
    private Behaviour actBehaviour = Behaviour.Idle;
    public void ChangeAction(Behaviour beh) {
        actBehaviour = beh;
        Change = true;
    }

    public Mob(World world){
        map = world;

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


    public void SetDest (int x, int y, int z){
        Thread thread = new Thread(() -> {
            Pathfind path = new Pathfind(map, mobH);
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
        int x = (int)  player.getX();
        int y =  (int) player.getY();
        int z =  (int) player.getZ();

        GoTo(x,y,z);
        // ur code here plz
    }

    public void GoTo(int DesX, int DesY, int DesZ) {
        if(route == null) {
            SetDest(DesX, DesY, DesZ);

        }
        if(route != null)
        {
            Vector3f move = route.get(0);
            route.remove(0);
            x = move.x;
            z = move.z;
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

        if (map.getBlock((int)x, (int)y, (int)z) != null) return true;

        return false;
    }



}
