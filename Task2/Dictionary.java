package test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;

public class Dictionary {
    CacheManager exsitingCache;
    CacheManager unexsitingCache;
    BloomFilter bloomFilter;
    String[] files;
    
    public Dictionary(String...fileNames) {
        exsitingCache = new CacheManager(400, new LRU());
        unexsitingCache = new CacheManager(100, new LFU());
        bloomFilter = new BloomFilter(256, "MD5","SHA1");
        getFileNames(fileNames);
        
        for (String fileName : fileNames) {
            try {
                BufferedReader reader = new BufferedReader(new FileReader(fileName));
                String line;
                if ((line = reader.readLine()) !=null) {
                    String[] words = line.split("\\s+");
                    for (String word : words) {
                        bloomFilter.add(word);
                    }
                }
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    public boolean query(String word)
    {
        if (exsitingCache.query(word))
        {
            return true;
        }
        else if (unexsitingCache.query(word))
        {
         return false;
        }
        else
        {
            boolean isContains =  bloomFilter.contains(word);
            if (isContains)
            {
                exsitingCache.add(word);
            }
            else{
                unexsitingCache.add(word);
            }
            return isContains;
        }

    }

    public boolean challenge(String word)
    { 
        try {
          Boolean isPresent = IOSearcher.search(word,files);
          if (isPresent)
          {
            exsitingCache.add(word);
          }
          else{
            unexsitingCache.add(word);
          }
          return isPresent;

        } catch (Exception e) {
            return false;
        }
    }

    public void getFileNames(String... fileNames)
    {
        this.files = new String[fileNames.length];
        for ( int i = 0; i < fileNames.length; i++)
        {
            files[i] = fileNames[i];
        }
    }

}
