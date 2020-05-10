public class Recipe {
    int w=0;
    int [] craft;

    public Recipe(int width, int height)
    {
        craft = new int[width*height];
        w = width;
    }


    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int[] getCraft() {
        return craft;
    }

    public void setCraft(int[] craft) {
        this.craft = craft;
    }
}
