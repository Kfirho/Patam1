package test;

import java.util.HashMap;
import java.util.LinkedList;

public class LFU implements CacheReplacementPolicy {

    private LinkedList<String> words;
    private HashMap<String, Integer> frequencies;

    public LFU() {
        words = new LinkedList<>();
        frequencies = new HashMap<>();
    }

    @Override
    public void add(String word) {
        int freq = frequencies.getOrDefault(word, 0) + 1;
        frequencies.put(word, freq);
        words.remove(word);
        int index = 0;
        while (index < words.size() && frequencies.get(words.get(index)) > freq) {
            index++;
        }
        words.add(index, word);
    }

    @Override
    public String remove() {
        if (words.isEmpty()) {
            return null;
        }
        String removedWord = words.removeLast();
        frequencies.remove(removedWord);
        return removedWord;
    }
}