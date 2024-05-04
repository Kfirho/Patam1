package test;

import java.util.LinkedList;

public class LRU implements CacheReplacementPolicy{

    private LinkedList<String> list;

    public LRU()
    {
        list = new LinkedList();
    }
    
    public void add(String word)
    {
        if (list.contains(word))
        {
            list.remove(word);
            list.addFirst(word);
        }
        else
        {
            list.addFirst(word);
        }
    }

    public String remove(){
        String lru = list.removeLast();

        return lru;
    }
    
    
}