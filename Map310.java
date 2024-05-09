//TODO:
// -Implement method keySet() and values(). Make sure to add JavaDoc for them.

import java.util.Map;
import java.util.Set;
import java.util.Iterator;

import java.util.Collection; //for returning in the values() function only
import java.util.LinkedList;//for returning in the values() function only

/**
 * Implements a map that maps key to value.
 *
 * @param <K> the type of keys (must be comparable)
 * @param <V> the type of values
 */
class Map310<K extends Comparable<? super K>, V> implements Map<K, V> {

    /**
     * Internal storage of the map: BST of pairs.
     */
    private WeissBST<Pair> storage;

    /**
     * Constructor of Map using BST as internal storage.
     */
    public Map310() {
        //use BST as internal storage
        storage = new WeissBST<>();
    }

    /**
     * {@inheritDoc}
     */
    public void clear() {
        storage.makeEmpty();

    }

    /**
     * {@inheritDoc}
     */
    public boolean isEmpty() {
        return size() == 0;
    }


    /**
     * {@inheritDoc}
     */
    public int size() {
        //return the number of elements in map
        return storage.size();
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public V get(Object key) {
        //find what value the given key is mapped to

        V val;
        Pair pair = storage.find(new Pair((K) key, null));

        if (pair != null)
            val = pair.getValue();
        else
            val = null;

        return val;
    }


    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    public V remove(Object key) {
        //remove key from map; return the value it mapped to before removal
        V currVal = get(key);
        if (currVal != null)
            storage.remove(new Pair((K) key, null));
        return currVal;
    }

    /**
     * {@inheritDoc}
     */
    public V put(K key, V value) {
        //associate key with value in map
        //if not a new key, return its previous associated value

        V oldVal;
        Pair pair = storage.find(new Pair(key, null));

        if (pair != null)
            oldVal = pair.getValue();
        else
            oldVal = null;

        if (oldVal != null)
            storage.remove(new Pair(key, null));

        storage.insert(new Pair(key, value));
        return oldVal;
    }

    /**
     * {@inheritDoc}
     */
    public boolean containsKey(Object key) {
        return this.get(key) != null;

    }

    /**
     * {@inheritDoc}
     */
    public String toString() {
        return storage.toString();
    }

    /**
     * Operation not supported: guaranteed to throw an exception.
     * {@inheritDoc}
     */
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }

    /**
     * Operation not supported: guaranteed to throw an exception.
     * {@inheritDoc}
     */
    public boolean containsValue(Object value) {
        return values().contains(value);
    }

    /**
     * Operation not supported: guaranteed to throw an exception.
     * {@inheritDoc}
     */
    public Set<Map.Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }

    /**
     * Returns a set of keys contained in this map.
     *
     * @return a set of keys contained in this map.
     */
    public Set<K> keySet() {
        //returns a Set of keys contained in this map.
        //a Set310 is a Set, so construct and return one of those.
        //
        //return an empty set for empty map.
        //O(N) where N is the number of <key, value> pairs in map if set operations are O(1)
        Set310<K> set = new Set310<>();
        if (storage.isEmpty()) {
            return set;
        } else {
            for (Pair pair : storage.values()) {
                set.add(pair.getKey());
            }
        }
        return set;
    }


    /**
     * Returns a collection of values contained in this map.
     *
     * @return a collection of values contained in this map.
     */
    public Collection<V> values() {
        //returns a Collection of values contained in this map.
        //duplicates are possible so use a LinkedList as your return.
        //
        //return an empty linked list for empty map.
        //O(N) where N is the number of <key, value> pairs in map.
        LinkedList<V> list = new LinkedList<>();
        if (storage.isEmpty()) {
            return list;
        } else {
            for (Pair pair : storage.values()) {
                list.add(pair.getValue());
            }
        }
        return list;
    }

    //********************************************************************************
    // TESTING CODE
    //********************************************************************************
    // Edit as much as you want ...
    //********************************************************************************

    /**
     * Implements a pair class with a key and a value.
     * We need it to be comparable to use with BST class.
     */
    private class Pair implements Comparable<Pair> {

        /**
         * Key of the pair.
         */
        private K key;

        /**
         * Value associated with key in the pair.
         */
        private V value;

        /**
         * Constructor.
         *
         * @param key   Key of the pair.
         * @param value Value of the pair.
         */
        Pair(K key, V value) {
            this.key = key;
            this.value = value;
        }

        /**
         * {@inheritDoc}
         */
        public String toString() {
            return "<" + key + "," + value + ">";
        }

        /**
         * Getter of key.
         *
         * @return Key of the pair.
         */
        public K getKey() {
            return key;
        }

        /**
         * Setter of key.
         *
         * @param key Key to set in the pair.
         */
        public void setKey(K key) {
            this.key = key;
        }

        /**
         * Getter of value.
         *
         * @return Value of the pair.
         */
        public V getValue() {
            return value;
        }

        /**
         * Setter of value.
         *
         * @param value Value to set in the pair.
         */
        public void setValue(V value) {
            this.value = value;
        }

        /*
         * {@inheritDoc}
         */
        @Override
        public int compareTo(Pair p) {
            return key.compareTo(p.getKey()); //compare based on key
        }

    }


}