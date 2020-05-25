public class Mob {
   private int hp;
   private int attack;
   private Item[] Drop;
   float x,y,z;
   World map;

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
        float x = player.getX();
        float y = player.getY();
        float z = player.getZ();

        // ur code here plz
    }

    public void GoTo(float DesX, float DesY, float DesZ) {
        float DX = DesX - x;
        float DY = DesY - y;
        float DZ = DesZ - z;



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
