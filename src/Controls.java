
public class Controls {
    static int forward =0;
    static int backward =0;
    static int left = 0;
    static int right=0;
    static int up=0;
    static int down=0;
    static int jump=0;
    static int crouch=0;
    static int inventory=0;
    static int sprint=0;
    static int drop=0;
    static int attack=0;
    static int destroy=0;
    static int place=0;
    static int use=0;
    static int NextItemInInventory=0;
    static int PrevItemInInventory=0;

    public static int getForward() {
        return forward;
    }

    public static void setForward(int forward) {
        Controls.forward = forward;
    }

    public static int getBackward() {
        return backward;
    }

    public static void setBackward(int backward) {
        Controls.backward = backward;
    }

    public static int getLeft() {
        return left;
    }

    public static void setLeft(int left) {
        Controls.left = left;
    }

    public static int getRight() {
        return right;
    }

    public static void setRight(int right) {
        Controls.right = right;
    }

    public static int getUp() {
        return up;
    }

    public static void setUp(int up) {
        Controls.up = up;
    }

    public static int getDown() {
        return down;
    }

    public static void setDown(int down) {
        Controls.down = down;
    }

    public static int getJump() {
        return jump;
    }

    public static void setJump(int jump) {
        Controls.jump = jump;
    }

    public static int getCrouch() {
        return crouch;
    }

    public static void setCrouch(int crouch) {
        Controls.crouch = crouch;
    }

    public static int getInventory() {
        return inventory;
    }

    public static void setInventory(int inventory) {
        Controls.inventory = inventory;
    }

    public static int getSprint() {
        return sprint;
    }

    public static void setSprint(int sprint) {
        Controls.sprint = sprint;
    }

    public static int getDrop() {
        return drop;
    }

    public static void setDrop(int drop) {
        Controls.drop = drop;
    }

    public static int getAttack() {
        return attack;
    }

    public static void setAttack(int attack) {
        Controls.attack = attack;
    }

    public static int getDestroy() {
        return destroy;
    }

    public static void setDestroy(int destroy) {
        Controls.destroy = destroy;
    }

    public static int getPlace() {
        return place;
    }

    public static void setPlace(int place) {
        Controls.place = place;
    }

    public static int getUse() {
        return use;
    }

    public static void setUse(int use) {
        Controls.use = use;
    }

    public static int getNextItemInInventory() {
        return NextItemInInventory;
    }

    public static void setNextItemInInventory(int nextItemInInventory) {
        NextItemInInventory = nextItemInInventory;
    }

    public static int getPrevItemInInventory() {
        return PrevItemInInventory;
    }

    public static void setPrevItemInInventory(int prevItemInInventory) {
        PrevItemInInventory = prevItemInInventory;
    }

}
