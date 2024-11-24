package game;

public class Pair<K, V> {
    public K first;
    public V second;

    public Pair(K key, V value) {
        this.first = key;
        this.second = value;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}