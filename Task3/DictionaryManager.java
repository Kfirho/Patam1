package test;

import java.util.HashMap;
import java.util.Map;

public class DictionaryManager{

    private Map<String, Dictionary> map;
    private static DictionaryManager instance;

    public DictionaryManager()
    {
        map = new HashMap<String, Dictionary>();
    }


    public boolean query(String...args)
    {
        if (args.length == 0)
        {
            return false;
        }
        String searchWord = args[args.length-1];
        boolean wordExsits = false;

        for (int i = 0; i < args.length-1 ; i++)
        { 
            String book = args[i];
            Dictionary dictionary = map.computeIfAbsent(book,k -> new Dictionary(book));
            wordExsits = dictionary.query(searchWord)||wordExsits;
        }

        return wordExsits;

    }

    public boolean challenge(String...args)
    { 
        if (args.length == 0)
        {
            return false;
        }
        String searchWord = args[args.length-1]; 
        Boolean wordExists = false;   
        for (int i = 0; i < args.length-1 ; i++)
        {
            String book = args[i];
            Dictionary dictionary = map.computeIfAbsent(book,k -> new Dictionary(book));
            wordExists = dictionary.challenge(searchWord)||wordExists;
            
        }
        return wordExists;
    }

    public int getSize()
    {
        return map.size();
    }

    public static DictionaryManager get()
    {

        if (instance == null)
        {
            instance = new DictionaryManager();
        }

        return instance;
    }
}

