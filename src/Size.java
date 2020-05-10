public class Size {
    public Size(int w, int h){
        this.w =w;
        this.h = h;
    }
    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    @Override
    public String toString(){
        return "w : " + w + "   | h : " + h;
    }

    int w,h;

}
