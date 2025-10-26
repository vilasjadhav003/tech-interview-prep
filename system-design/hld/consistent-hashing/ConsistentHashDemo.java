
public class ConsistentHashDemo {
    public static void main(String[] args) {
        // Use a big ring. For demo’s sake, keep it smaller, e.g. 1e9.
        ConsistentHashRing ring = new ConsistentHashRing(1_000_000_000L);

        StorageNode A = new StorageNode("A", "10.0.0.1");
        StorageNode B = new StorageNode("B", "10.0.0.2");
        StorageNode C = new StorageNode("C", "10.0.0.3");

        ring.addNode(A);
        ring.addNode(B);
        ring.addNode(C);

        // Assign some keys
        System.out.println("K1 -> " + ring.assign("K1"));
        System.out.println("K2 -> " + ring.assign("K2"));

        // Add a node: only keys in the slice before its position move
        StorageNode D = new StorageNode("D", "10.0.0.4");
        ring.addNode(D);

        // Remove a node: only keys belonging to that node move to the next clockwise node
        ring.removeNode(B);
    }
}
