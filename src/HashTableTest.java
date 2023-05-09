class Node {
    String key;
    String value;
    Node next;

    public Node(String key, String value) {
        this.key = key;
        this.value = value;
    }
}

class MyHashTable {
    private static final int TABLE_SIZE = 10; // Size of the hash table array
    private Node[] table;

    public MyHashTable() {
        table = new Node[TABLE_SIZE];
    }

    private int hash(String key) {
        return Math.abs(key.hashCode()) % TABLE_SIZE; // Computing the hash code
    }

    public String get(String key) {
        int index = hash(key);
        Node currentNode = table[index];

        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                return currentNode.value;
            }
            currentNode = currentNode.next;
        }

        return null; // Key not found
    }

    public void put(String key, String value) {
        int index = hash(key);
        Node newNode = new Node(key, value);

        if (table[index] == null) {
            table[index] = newNode;
        } else {
            Node currentNode = table[index];
            while (currentNode.next != null) {
                if (currentNode.key.equals(key)) {
                    currentNode.value = value; // Update the existing value
                    return;
                }
                currentNode = currentNode.next;
            }
            currentNode.next = newNode; // Append the new node to the end of the list
        }
    }

    public void remove(String key) {
        int index = hash(key);
        Node currentNode = table[index];
        Node prevNode = null;

        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                if (prevNode == null) {
                    table[index] = currentNode.next; // Removing the first node
                } else {
                    prevNode.next = currentNode.next; // Removing a node in the middle or at the end
                }
                return;
            }
            prevNode = currentNode;
            currentNode = currentNode.next;
        }
    }

    public boolean containsKey(String key) {
        int index = hash(key);
        Node currentNode = table[index];

        while (currentNode != null) {
            if (currentNode.key.equals(key)) {
                return true;
            }
            currentNode = currentNode.next;
        }

        return false;
    }

    public int size() {
        int count = 0;
        for (Node node : table) {
            Node currentNode = node;
            while (currentNode != null) {
                count++;
                currentNode = currentNode.next;
            }
        }
        return count;
    }
}

public class HashTableTest {
    public static void main(String[] args) {
        MyHashTable hashTable = new MyHashTable();

        hashTable.put("key1", "value1");
        hashTable.put("key2", "value2");
        hashTable.put("key3", "value3");

        System.out.println("Size: " + hashTable.size());
        System.out.println("Contains key 'key2': " + hashTable.containsKey("key2"));
        System.out.println("Value for key 'key1': " + hashTable.get("key1"));

        hashTable.remove("key2");

        System.out.println("Size after removing 'key2': " + hashTable.size());
        System.out.println("Contains key 'key2' after removal: " + hashTable.containsKey("key2"));
    }
}
