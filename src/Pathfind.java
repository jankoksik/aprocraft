import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Pathfind  {

    World map;
    float sx,sy,sz;
    float dx, dy,dz;
    int cx, cy, cz;
    int h = 2;
    boolean check = false;

    public void setSx(float sx) {
        this.sx = sx;
    }
    public void setSy(float sy) {
        this.sy = sy;
    }
    public void setSz(float sz) {
        this.sz = sz;
    }
    public void setDx(float dx) {
        this.dx = dx;
    }
    public void setDy(float dy) {
        this.dy = dy;
    }
    public void setDz(float dz) {
        this.dz = dz;
    }
    public void setH(int h){this.h = h;}

    public Pathfind(World map, int h){
        this.map = map;
    }


    public ArrayList<Block> FindRoute2D(){
     ArrayList<Block> route = new ArrayList<>();

        cx = (int)sx;
        cz = (int) sz;
        while( cx!= (int)dx && cz != (int)dz)
        {
            check = false;
            if(cx!=dx) {
                int d = (int) dx - cx;
                if(d>0 && !Colision(cx+1, cy,cz))
                {
                    cx+=1;
                    route.add(map.getBlock(cx, cy,cz));
                    check = true;
                }
                else if(d<0 && !Colision(cx-1, cy,cz))
                {
                    cx-=1;
                    route.add(map.getBlock(cx, cy,cz));
                    check = true;
                }

            }
            if(cz!=dz) {
                int d = (int) dz - cz;
                if(d>0 && !Colision(cx, cy,cz+1))
                {
                    cz+=1;
                    route.add(map.getBlock(cx, cy,cz));
                    check = true;
                }
                else if(d<0 && !Colision(cx, cy,cz-1))
                {
                    cz-=1;
                    route.add(map.getBlock(cx, cy,cz));
                    check = true;
                }

            }
            if(!check && (cx!= (int)dx || cz != (int)dz))
            {
                return  null;
            }
        }
        return  route;
    }

    private float max(float a, float b)
    {
        if(a > b)
            return a;
        else return b;
    }
    private float min(float a, float b)
    {
        if(a < b)
            return a;
        else return b;
    }
    private boolean Colision(int x, int y, int z)
    {
        for(int i=0; i<h; i++){
            if(map.getBlock(x, y+i, z)!=null)return true;
        }
        return  false;
    }



}
