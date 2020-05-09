import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Queue;

public class Inventory {

    //stack - liczba bloków w stacku
    // space - ilosc kratek na przedmioty

    private  ArrayList<Item> eq = new ArrayList<>();
    private int stack;
    private int space;
    private ArrayList<Item> map= new ArrayList<>();


    public Inventory(int stack, int space) {
        this.stack = stack;
        this.space = space;
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
           // Item add = new Item(i.getId());
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
    private Item get(Item n){
        for(Item k : map)
        {
            if(k.getId() == n.getId())
                return k;
        }
        return null;
    }
    private Item get(int id){
        for(Item k : map)
        {
            if(k.getId() == id)
                return k;
        }
        return null;
    }
    private boolean remove(Item i){
        for(Item k : map)
        {
            if(k.getId() == i.getId()) {
                map.remove(k);
                return true;
            }
        }
        return false;
    }
    private boolean remove(int id){
        for(Item k : map)
        {
            if(k.getId() == id) {
                map.remove(k);
                return true;
            }
        }
        return false;
    }
    private Item getEq(int id){
        for(Item k : eq)
        {
            if(k.getId() == id)
                return k;
        }
        return null;
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
                   remove(n.getId());
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
        return  false;
    }





}
