package aprocraft.player;

/**
 * Klsa odpoiwadająca za poruszanie się avatarem gracza i inne akcje jakie może wykonać gracz.
 * W pliku JSON zawarte są informacje jaki klawisz odpowiada za jaką akcję
 */
public abstract class Controls {
    private static int forward =0;
    private static int backward =0;
    private static int left = 0;
    private static int right=0;
    private static int up=0;
    private static int down=0;
    private static int jump=0;
    private static int crouch=0;
    private static int inventory=0;
    private static int sprint=0;
    private static int drop=0;
    private static int attack=0;
    private static int destroy=0;
    private static int place=0;
    private static int use=0;
    private static int NextItemInInventory=0;
    private static int PrevItemInInventory=0;

    /**
     * Metoda zwracająca jaki klawisz jest przypisany do ruchu do przodu
     * @return przypisany klawisz
     */
    public static int getForward() {
        return forward;
    }

    /**
     * Metoda ustawiająca jaki klawisz będzie powodowął ruch do przodu
     * @param forward  klawisz, który przypisaliśmy
     */
    public static void setForward(int forward) {
        Controls.forward = forward;
    }
    /**
     * Metoda zwracająca jaki klawisz jest przypisany do ruchu do tyłu
     * @return przypisany klawisz
     */
    public static int getBackward() {
        return backward;
    }

    /**
     * Metoda ustawiająca jaki klawisz będzie powodowął ruch do tyłu
     * @param backward klawisz, który przypisaliśmy
     */
    public static void setBackward(int backward) {
        Controls.backward = backward;
    }

    /**
     * Metoda zwracająca jaki klawisz jest przypisany do ruchu do lewo
     * @return przypisany klawisz
     */
    public static int getLeft() {
        return left;
    }

    /**
     * Metoda ustawiająca jaki klawisz będzie powodował ruch w lewo
     * @param left klawisz, który przypisaliśmy
     */
    public static void setLeft(int left) {
        Controls.left = left;
    }

    /**
     * Mrtoda zwracająca jaki klawisz jest przypisany do ruchu w prawo
     * @return przypisany klawisz
     */
    public static int getRight() {
        return right;
    }

    /**
     * Metoda ustawiająca jaki klawisz będzie powodował ruch w prawo
     * @param right klawisz, który przypisaliśmy
     */
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

    /**
     * Metoda zwracająca jaki klawisz jest przypisany do skoku
     * @return przypisany klawisz
     */
    public static int getJump() {
        return jump;
    }

    /**
     * Metoda ustawiająca jaki klawisz będzie powodował skok
     * @param jump klawisz, który przypisaliśmy
     */
    public static void setJump(int jump) {
        Controls.jump = jump;
    }
    /**
     * Metoda zwracająca jaki klawisz jest przypisany do kucania
     * @return przypisany klawisz
     */
    public static int getCrouch() {
        return crouch;
    }

    /**
     * Metoda ustawiająca jaki klawisz będzie powodował kucanie
     * @param crouch klawisz, który przypisaliśmy
     */
    public static void setCrouch(int crouch) {
        Controls.crouch = crouch;
    }

    /**
     * Metoda zwracająca jaki klawisz jest przypisany do otwierania ekwipunku
     * @return przypisany klawisz
     */
    public static int getInventory() {
        return inventory;
    }

    /**
     * Metoda ustawiająca jaki klawisz będzie powodował otwarcie ekwipunku
     * @param inventory klawisz, który przypisaliśmy
     */
    public static void setInventory(int inventory) {
        Controls.inventory = inventory;
    }

    /**
     * Metoda zwracająca jaki klawisz jest przypisany do sprintowania
     * @return przypisany klawisz
     */
    public static int getSprint() {
        return sprint;
    }

    /**
     * Metoda ustawiająca jaki klawisz będzie powodował sprint
     * @param sprint klawisz, który przypisaliśmy
     */
    public static void setSprint(int sprint) {
        Controls.sprint = sprint;
    }

    /**
     * Metoda zwracająca jaki klawisz jest przypisany do wyrzucania przedmiotów
     * @return przypisany klawisz
     */
    public static int getDrop() {
        return drop;
    }

    /**
     * Metoda ustawiająca jaki klawisz będzie powodowała wyrzucenie przedmiotu
     * @param drop klawisz, który przypisaliśmy
     */
    public static void setDrop(int drop) {
        Controls.drop = drop;
    }

    /**
     * Metoda zwracająca jaki klawisz jest przypisany do wykonania ataku
     * @return przypisany klawisz
     */
    public static int getAttack() {
        return attack;
    }

    /**
     * Metoda ustawiająca jaki klawisz będzie powodował akcję ataku
     * @param attack klawisz, który przypisaliśmy
     */
    public static void setAttack(int attack) {
        Controls.attack = attack;
    }

    /**
     * Metoda zwracająca jaki klawisz jest przypisany do wykonania akcji zniszczenia bloku
     * @return przypisany klawisz
     */
    public static int getDestroy() {
        return destroy;
    }

    /**
     * Metoda ustawiająca jaki klawisz będzie powodował akcję zniszczenia bloku
     * @param destroy klawisz, który przypisaliśmy
     */
    public static void setDestroy(int destroy) {
        Controls.destroy = destroy;
    }

    /**
     * Metoda zwracająca jaki klawisz jest przypisany do wykonania akcji postawienia bloku
     * @return przypisany klawisz
     */
    public static int getPlace() {
        return place;
    }

    /**
     * Metoda ustawiająca jaki klawisz będzie powodował akcję postawienia bloku
     * @param place  klawisz, który przypisaliśmy
     */
    public static void setPlace(int place) {
        Controls.place = place;
    }

    /**
     * Metoda zwracająca jaki klawisz jest przypisany do życia przedmiotu
     * @return przypisany klawisz
     */
    public static int getUse() {
        return use;
    }

    /**
     * Metoda ustawiająca jaki klawisz będzie powodował użycie przedmiotu
     * @param use klawisz, który przypisaliśmy
     */
    public static void setUse(int use) {
        Controls.use = use;
    }

    /**
     * Metoda zwracająca jaki klawisz jest przypisany do zmiany używanego przedmiotu  o jeden w przód w ekwipunku
     * @return przypisany klawisz
     */
    public static int getNextItemInInventory() {
        return NextItemInInventory;
    }

    /**
     * Metoda ustawiająca  jaki klawisz będzie powodował zmianę używanego przedmiotu o jeden w przód w ekwipunku
     * @param nextItemInInventory klawisz, który przypisaliśmy
     */
    public static void setNextItemInInventory(int nextItemInInventory) {
        NextItemInInventory = nextItemInInventory;
    }
    /**
     * Metoda zwracająca jaki klawisz jest przypisany do zmiany używanego przedmiotu  o jeden w tył w ekwipunku
     * @return przypisany klawisz
     */
    public static int getPrevItemInInventory() {
        return PrevItemInInventory;
    }

    /**
     * Metoda ustawiająca  jaki klawisz będzie powodował zmianę używanego przedmiotu  o jeden w tył w ekwipunku
     * @param prevItemInInventory klawisz, który przypisaliśmy
     */
    public static void setPrevItemInInventory(int prevItemInInventory) {
        PrevItemInInventory = prevItemInInventory;
    }

}
