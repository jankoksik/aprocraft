public class Crafting {
    String Fn; // Json
    int w=0;
    int [] craft;
    public Crafting(int width, int height, String RecipFile)
    {
        craft = new int[width*height];
        w = width;
        Fn = RecipFile;
    }

    private Size GetDim(Recipe r){
        Size n = new Size(r.getW(), r.getCraft().length/r.getW() );
    return n;
    }

    /* eg.
    0	1	2

0	0	1	2

1	3	4	5

2	6	7	8
     */
    public void PlaceItemInCrafting(int x, int y, int itemId, Inventory Eq){
        if(Eq.removeOne(itemId)) {
            craft[w*y + x] = itemId;
        }
    }
    public void GetItemFromCrafting(int x, int y, int itemId, Inventory Eq){
        if(Eq.addItem(itemId)) {
            craft[w*y + x] = 0;
        }
    }

    public Size GetCurrDim(){
        int maxW=0, maxH=0, minW = w, minH;
        int craftH = craft.length/w;
        minH = craftH;
        for(int y=0; y< craftH;y++){
            for (int x=0; x<w; x++)
            {
                if(craft[w*y + x]!=0) {
                    if (x > maxW) {
                        maxW = x;
                    }
                    if (x < minW )
                        minW = x;
                    if (y > maxH) {
                        maxH = y;
                    }
                    if (y < minH)
                        minH = y;
                }
            }

        }
        return new Size(maxW-minW+1, maxH-minH+1);
    }


    public  int[] getSeperated() {
        int craftH = craft.length / w;
        int iter =0;
        boolean found = false;
        Size n = GetCurrDim();
        int [] curr = new int[n.getW() * n.getH()];
        int CornerX=0, CornerY = 0;
        for (int y = 0; y < craftH; y++) {
            for (int x = 0; x < w; x++) {
                if(craft[w*y + x]!=0){
                    CornerY = y;
                    CornerX = x;
                    found = true;
                    break;

                }
            }
            if(found){break;}
        }
        for (int y = CornerY; y < craftH; y++) {
            for (int x = CornerX; x <w; x++) {
                if(iter <= curr.length)
                {
                    curr[iter] = craft[w * y + x];
                    System.out.print(curr[iter] + " ");
                    iter++;

                }
            }
            System.out.println();
        }
        return  curr;
    }





}
