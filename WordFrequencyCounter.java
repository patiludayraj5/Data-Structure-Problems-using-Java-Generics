import java.util.LinkedList;

class MyMapNode<K, V> {
    K key;
    V value;

    // Constructor
    public MyMapNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}

class MyHashTable<K, V> {
    private LinkedList<MyMapNode<K, V>>[] buckets;
    private int size;

    // Constructor
    public MyHashTable(int size) {
        this.size = size;
        this.buckets = new LinkedList[size];
        for (int i = 0; i < size; i++) {
            buckets[i] = new LinkedList<>();
        }
    }

    // Method to get index based on key
    private int getBucketIndex(K key) {
        int hashCode = key.hashCode();
        int index = Math.abs(hashCode) % size;
        return index;
    }

    // Method to get value based on key
    public V get(K key) {
        int index = getBucketIndex(key);
        LinkedList<MyMapNode<K, V>> bucket = buckets[index];
        for (MyMapNode<K, V> node : bucket) {
            if (node.key.equals(key)) {
                return node.value;
            }
        }
        return null;
    }

    // Method to put key-value pair into the hash table
    public void put(K key, V value) {
        int index = getBucketIndex(key);
        LinkedList<MyMapNode<K, V>> bucket = buckets[index];

        // Check if key already exists, if yes, update the value
        for (MyMapNode<K, V> node : bucket) {
            if (node.key.equals(key)) {
                node.value = value;
                return;
            }
        }

        // If key doesn't exist, add a new node to the bucket
        MyMapNode<K, V> newNode = new MyMapNode<>(key, value);
        bucket.add(newNode);
    }

    // Method to remove key and its associated value from the hash table
    public void remove(K key) {
        int index = getBucketIndex(key);
        LinkedList<MyMapNode<K, V>> bucket = buckets[index];

        // Find and remove the node with the specified key
        for (MyMapNode<K, V> node : bucket) {
            if (node.key.equals(key)) {
                bucket.remove(node);
                return;
            }
        }
    }

    // Method to get all keys in the hash table
    public LinkedList<K> getKeys() {
        LinkedList<K> keys = new LinkedList<>();
        for (LinkedList<MyMapNode<K, V>> bucket : buckets) {
            for (MyMapNode<K, V> node : bucket) {
                keys.add(node.key);
            }
        }
        return keys;
    }
}

public class WordFrequencyCounter {
    public static void main(String[] args) {
        String phrase = "Paranoids are not paranoid because they are paranoid but "
                + "because they keep putting themselves deliberately into paranoid avoidable situations";

        MyHashTable<String, Integer> wordFrequencyTable = new MyHashTable<>(10);

        // Count the frequency of each word in the phrase
        String[] words = phrase.split(" ");
        for (String word : words) {
            String cleanedWord = word.toLowerCase().replaceAll("[^a-zA-Z]", "");
            Integer frequency = wordFrequencyTable.get(cleanedWord);
            if (frequency == null) {
                frequency = 1;
            } else {
                frequency++;
            }
            wordFrequencyTable.put(cleanedWord, frequency);
        }

        // Display the original word frequencies
        System.out.println("Original Word Frequencies:");
        for (String word : wordFrequencyTable.getKeys()) {
            System.out.println(word + ": " + wordFrequencyTable.get(word));
        }

        // Remove the word "avoidable" from the hash table
        wordFrequencyTable.remove("avoidable");

        // Display the word frequencies after removal
        System.out.println("\nWord Frequencies after Removal:");
        for (String word : wordFrequencyTable.getKeys()) {
            System.out.println(word + ": " + wordFrequencyTable.get(word));
        }
    }
}
