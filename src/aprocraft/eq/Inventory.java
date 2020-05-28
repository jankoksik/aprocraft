package aprocraft.eq;

import aprocraft.APROCraft;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class Inventory {

    //stack - liczba bloków w stacku
    // space - ilosc kratek na przedmioty

    private  ArrayList<Item> eq = new ArrayList<>();
    private int stack;
    private int space;
    private int w;
    private ArrayList<Item> map= new ArrayList<>();


    public Inventory(int stack, int w, int h) {
        this.stack = stack;
        this.space = w*h;
        this.w = w;
    }

    //malo optymalne
    public List<Item> getEq(){
        ArrayList<Item> sum = new ArrayList<>();
        for(Item i : eq)
        {
            i.setSize(stack);
            sum.add(i);
            //getEq(i.getId()).setSize(stack);
        }
        for(Item i : map)
        {
           // aprocraft.eq.Item add = new aprocraft.eq.Item(i.getId());
           // add.setSize(i.getSize());
            sum.add(i);
        }
        return sum;
    }

    private boolean contain(Item n){
        for(Item k : map)
        {
            if(k.getId() == n.getId())
                return true;
        }
        return false;
    }
    private boolean contain(int id){
        for(Item k : map)
        {
            if(k.getId() == id)
                return true;
        }
        return false;
    }
    private Item get(Item n){
        for(Item k : map)
        {
            if(k.getId() == n.getId())
                return k;
        }
        return null;
    }

    public int GetSpace(){
        return space;
    }
    public int GetW(){
        return w;
    }
    private Item get(int id){
        for(Item k : map)
        {
            if(k.getId() == id)
                return k;
        }
        return null;
    }
    private boolean removeStack(Item i){
        for(Item k : map)
        {
            if(k.getId() == i.getId()) {
                map.remove(k);
                return true;
            }
        }
        return false;
    }
    private boolean removeStack(int id){
        for(Item k : map)
        {
            if(k.getId() == id) {
                map.remove(k);
                return true;
            }
        }
        return false;
    }
    private boolean removeStackEq(int id){
        for(Item k : eq)
        {
            if(k.getId() == id) {
                eq.remove(k);
                return true;
            }
        }
        return false;
    }
    public boolean removeOne(int id){
        for(Item k : map)
        {
            if(k.getId() == id) {
                int nmbr = k.getSize()-1;
                if(nmbr ==0)
                    map.remove(k);
                else
                    k.setSize(nmbr);
                return true;
            }
        }
        for(Item k : eq)
        {
            if(k.getId() == id) {
                eq.remove(k);
                Item n = new Item(id);
                n.setSize(stack-1);
                map.add(n);
                return true;
            }
        }

        return false;
    }
    public Item getEq(int id){
        for(Item k : eq)
        {
            if(k.getId() == id)
                return k;
        }
        return null;
    }

    public  int getNmbrOfItems(int id){
        int nmbr = 0;
        for(Item i : eq) {
            if(i.getId() == id){
                nmbr += stack;
            }
        }
        for(Item i : map) {
            if(i.getId() == id){
                nmbr += i.getSize();
            }
        }

        return  nmbr;
    }

    // force -> true - usun tyle ile sie da /  false - nie usunie itemow jesli niema wystarczajacej ilosci
    public boolean remove(int id, int nmbr, boolean force){
        if(nmbr>getNmbrOfItems(id) && !force)
            return false;
        int br = nmbr/stack;
        int del=0;
        for(;br>0; br--, del++)
        {
            if(!removeStackEq(id))
                break;
        }
        int left = nmbr - del*stack;
        if(contain(id) && left>0)
        {
            int v = get(id).getSize() - left;
            if(v<=0)
            {
                map.remove(get(id));
            }
            else{
                get(id).setSize(v);
            }
        }
        return  true;
    }


    public boolean addItem(int itemId){
        Item n = new Item(itemId);
        if(!map.isEmpty() && contain(n))
        {
            get(n.getId()).AddOne();
                if(get(n.getId()).getSize() == stack)
                {
                    eq.add(n);
                    eq.get(eq.indexOf(n)).setSize(stack);
                   removeStack(n.getId());
                }
        return true;
        }
        else
        {
            if(eq.size() + map.size() < space){
                n.AddOne();
                map.add(n);
                return true;
            }
            else{
                return false;
            }

        }


    }

    // force -> true - dodaj tyle ile sie da /  false - nie doda itemow jesli niema miejsca
    public boolean addItem(int itemId, int NmbrOfItems, boolean force) {
        Item n = new Item(itemId);
        int stacks = NmbrOfItems / stack;
        if ((stacks + eq.size() + map.size() > space) && !force)
            return false;

        int miejsce = space - eq.size() - map.size();
        if(miejsce < stacks+1 && !force)
            return false;
        n.setSize(stack);
        int added = 0;
        for (int i = stacks; i > 0 && miejsce > 0; i--, miejsce--, added++) {
            eq.add(n);
        }
        int left = (NmbrOfItems - (added * stack));
        if (left > 0 && get(n.getId()) != null) {
            while (left > 0 && get(n.getId()) != null) {
                get(n.getId()).AddOne();
                left--;
            }
            if (left > 0 && eq.size() + map.size() < space) {
                n.setSize(left);
                map.add(n);
                return true;
            }
        } else if (eq.size() + map.size() < space && left > 0) {
            Item k = new Item(itemId);
            k.setSize(left);
            map.add(k);
            return true;
        }
        return  true;
    }

    //przekaz itemy do innego eq
    public boolean passItem(Inventory other, int id, int nmbr)
    {
        if(remove(id,nmbr, false)){
        if (other.addItem(id, nmbr, false)) {
            return true;
        }
        else{
            addItem(id,nmbr, false);
            return false;
        }
        }
        return  false;
    }


    public boolean saveEq(String filename){
        try {
            Gson gson = new Gson();
        PrintWriter pw = null;

            pw = new PrintWriter("./data/"+filename+".json");

        pw.write(gson.toJson(getEq()));

        pw.flush();
        pw.close();
        return  true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return  false;
        }




    }

    public void readEq(String filename){
        try {
            Gson gson = new Gson();
            boolean success = (new File("./Saves/"+ APROCraft.GAME_NAME)).mkdirs();
            FileReader obj = new FileReader("./Saves/"+ APROCraft.GAME_NAME+ "/"+filename+".json");
            Type type = new TypeToken<ArrayList<Item>>(){}.getType();
            ArrayList<Item> lista = gson.fromJson(obj, type);

            for(Item i : lista){
                if(i.getSize() == stack)
                {
                    eq.add(i);
                }
                else{
                    map.add(i);
                }
            }



        } catch (IOException e) {
            e.printStackTrace();
        }




    }


}
