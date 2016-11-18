package com.alifesoftware;

/*
Implement data structure "RandomMap" storing pairs of string (key, value)
Define following member functions in O(1) runtime:
   void insert(String key, String value),
   void delete(String key),
   String get(String key),
   String getRandomKey().

RandomMap mRandom = new RandomMap()
mRandom.insert("1","abc");
mRandom.insert("2","def");
mRandom.insert("3","def");
mRandom.insert("4","efg");
mRandom.insert("john","smith");
mRandom.insert("john","doe");
mRandom.delete("3");
mRandom.get("4"); //.. efg
mRandom.getRandomkey(); // .. random key from [1, 2, 4, john]
*/

public class Main {

    public static void main(String[] args) {
        RandomMap mRandom = new RandomMap();
        mRandom.insert("1", "A");
        mRandom.insert("2", "B");
        mRandom.insert("3", "C");

        mRandom.delete("3");
        mRandom.delete("1");

        String randomKey = mRandom.getRandomKey();
        System.out.println("Random Key: " + randomKey + " Value at Random Key: " + mRandom.get(randomKey));
    }
}
