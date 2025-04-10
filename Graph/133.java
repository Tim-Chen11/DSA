import java.util.*;

// class Node {
//     public int val;
//     public List<Node> neighbors;
//     public Node() {
//         val = 0;
//         neighbors = new ArrayList<>();
//     }
//     public Node(int _val) {
//         val = _val;
//         neighbors = new ArrayList<>();
//     }
//     public Node(int _val, ArrayList<Node> _neighbors) {
//         val = _val;
//         neighbors = _neighbors;
//     }
// }

class Solution {
    public Node cloneGraph(Node node) {
        if (node == null) return null;

        Map<Node, Node> map = new HashMap<>(); // original -> clone
        Queue<Node> queue = new LinkedList<>();
        queue.offer(node);
        map.put(node, new Node(node.val)); // create clone of start node

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            for (Node neighbor : current.neighbors) {
                if (!map.containsKey(neighbor)) {
                    // Clone neighbor and add it to the map
                    map.put(neighbor, new Node(neighbor.val));
                    queue.offer(neighbor);
                }
                // Link cloned neighbors
                map.get(current).neighbors.add(map.get(neighbor));
            }
        }

        return map.get(node); // return the clone of the start node
    }
}
