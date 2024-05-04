package test;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.BitSet;

public class BloomFilter {
    private BitSet bitSet;
    private MessageDigest[] hashFunc;

    public BloomFilter(int size, String... algs) {
        this.bitSet = new BitSet(size);
        this.hashFunc = new MessageDigest[algs.length];
        try {
            for (int i = 0; i < algs.length; i++)
            {
                hashFunc[i] = MessageDigest.getInstance(algs[i]);
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
           
        
    }

    public void add(String word)
    {
        for (MessageDigest h : hashFunc)
        {
            byte[] bts = h.digest(word.getBytes());
            int bitIndex = Math.abs(new BigInteger(bts).intValue()) % bitSet.size();
            bitSet.set(bitIndex);
        }
    }

    public boolean contains(String word)
    {
        for (MessageDigest h : hashFunc)
        {
            byte[] bts = h.digest(word.getBytes());
            int bitIndex = Math.abs(new BigInteger(bts).intValue()) % bitSet.size();
            if (!bitSet.get(bitIndex))
            {
                return false;
            }
        } 
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        boolean leadingZeros = true;
        for (int i = bitSet.length() - 1; i >= 0; i--) {
            boolean bit = bitSet.get(i);
            if (bit) {
                leadingZeros = false;
                sb.append("1");
            } else if (!leadingZeros) {
                sb.append("0");
            }
        }
        if (sb.length() == 0) {
            return "0";
        }
        return sb.reverse().toString();
    }

    
}
