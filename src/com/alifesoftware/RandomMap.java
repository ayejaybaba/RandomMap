package com.alifesoftware;

import java.util.*;

/**
 * Created by anuj on 11/17/16.
 */
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
public class RandomMap {
    // Implementation Idea:
    //
    // Insert, Delete, Get are already O(1) operation for a
    // normal HashMap.
    //
    // The complication comes in for getRandomKey(). To make
    // getRandomKey O(1), we need a data structure that supports
    // O(1) time retrieval of elements, so in this case an ArrayList will
    // make sense. Basically when we have an ArrayList of n elements,
    // we can find a random number between 0 and n-1, and return the
    // value at that random index. In this case, the idea is to store
    // Keys in in the ArrayList, and then maintain a Map of Keys and the
    // index at which the Key is stored in the ArrayList.
    //
    //
    // If we didn't have O(1) limitation, then getRandomKey would be easier.
    // In that case, we can get Map's keys as Set<String> and then convert it
    // to an Array (using toArray method of Set), and then do the random int
    // index trick as mentioned above.
    //



    // Map to keep contain all the data that
    // user adds or removes
    private Map<String, String> dataMap = new HashMap<>();

    // List of Keys to be used for getRandomKey()
    private List<String> keyList = new ArrayList<>();

    // Map to keep track of Key (that was also the key for original
    // map) and the index in the array where that key was added
    //
    // To be used for getRandomKey()
    private Map<String, Integer> indexMap = new HashMap<>();

    // To generate Random number
    private Random random = new Random();

    public void insert(final String key, final String value) {
        // Add the Key-Value pair in the Map
        dataMap.put(key, value);

        // Add the Key to the ArrayList
        keyList.add(key);

        // Add the Key-Index pair to the Map
        //
        // Basically this map tells us the ArrayList index
        // of key, given the key, because if we don't know the
        // index, then we'll have to do a search in ArrayList
        // which is O(n)
        //
        indexMap.put(key, keyList.size() - 1);
    }

    public void delete(final String key) {
        // We can only delete something if it exists in the Map
        if(dataMap.containsKey(key)) {
            // Remove the Key-Value pair from Map using the key
            dataMap.remove(key);

            // Now, we need to remove the same key from the ArrayList
            //
            // If we didn't use index Map, then we'll have to find this
            // key in the ArrayList which would be O(n) operation, hence
            // we justify the use ot indexMap
            //

            // Step 1: Remove the key-index pair from indexMap
            int indexToRemove = indexMap.get(key);

            // Here comes part of the trick. Basically, when we remove
            // the item at indexToRemove from ArrayList, all other items
            // in the ArrayList will move up by one position. For example,
            // the second items in the ArrayList will become the first item,
            // third item will become the second item...and so on.
            //
            // This also means that now the indexMap and keyList data structures
            // are not in sync because keyList is having dynamic indexes whereas
            // indexMap has old values of these index items.
            //
            // It won't be O(1) to update the entire indexMap with new values
            // of index as that would be to O(n). So how do we do it? And this it
            // the main challenge of this problem. Here's how:
            //
            //
            // Move last item in the ArrayList of keys to the index
            // from where the key will be removed (i.e. indexToRemove),
            // and update index Map. This way, we will only be updating one
            // item in the Map which is again a O(1) operation


            // Remove the key item from keyList
            keyList.remove(indexToRemove);
            // Remove the key-index pair from the Map using the key
            indexMap.remove(key);
            // Move the last item in the ArrayList to the
            // position from where we removed the key
            //

            if(keyList.size() > 0) {
                String keyToMove = keyList.get(keyList.size() - 1);

                // Note: Only do this if we have more than one
                // item in the List
                if (keyList.size() > 1) {
                    keyList.add(indexToRemove, keyToMove);

                    // Update the Key-Index Map
                    indexMap.put(keyToMove, indexToRemove);

                    // Delete the last item in the ArrayList
                    keyList.remove(keyList.size() - 1);
                } else {
                    // Only one item - update is easy
                    indexMap.put(keyToMove, 0);
                }
            }
        }
    }

    // Note: Can return null - if the key
    // doesn't exist in the map
    public String get(final String key) {
        return dataMap.get(key);
    }

    public String getRandomKey() {
        if(keyList.size() > 0) {
            // Get a random index from 0 to n-1
            int randomInt = getRandomIndex();

            // Return the key at randomly generated index
            return keyList.get(randomInt);
        }

        return null;
    }

    private int getRandomIndex() {
        int max = keyList.size() - 1;
        int min = 0;

        int randomNum = random.nextInt((max - min) + 1) + min;
        return randomNum;
    }
}
