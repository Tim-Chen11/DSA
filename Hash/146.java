class LRUCache {

    private static class Node{
        int key, value;
        Node prev, next;
        Node(int k, int v) {key = k; value = v;}
    }

    private final int capacity;
    private final Map<Integer, Node> map =  new HashMap<>();
    private final Node head = new Node(-1, -1);
    private final Node tail = new Node(-1, -1);

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }
    
    public int get(int key) {
        Node node = map.get(key);
        if (node == null) return -1;
        moveToHead(node);
        return node.value;
    }
    
    public void put(int key, int value) {
        Node node = map.get(key);
        if (node != null) {
            node.value = value;
            moveToHead(node);
            return;
        }
        Node n = new Node(key, value);
        map.put(key, n);
        addAfterHead(n);
        if (map.size() > capacity) {
            Node lru = removeLRU();
            map.remove(lru.key);
        }
    }

    private void addAfterHead(Node n){
        n.prev = head;
        n.next = head.next;
        head.next.prev = n;
        head.next = n;
    }

    private void remove(Node n){
        n.prev.next = n.next;
        n.next.prev = n.prev;
    }

    private void moveToHead(Node n){
        remove(n);
        addAfterHead(n);
    }

    private Node removeLRU() {
        Node lru = tail.prev;
        remove(lru);
        return lru;
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */