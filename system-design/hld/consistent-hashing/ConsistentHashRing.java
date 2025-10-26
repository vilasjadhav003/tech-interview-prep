import java.util.ArrayList;
import java.util.List;

final class ConsistentHashRing {
    private final long totalSlots;                 // e.g., a large number
    private final List<Long> keys = new ArrayList<>();     // ring positions
    private final List<StorageNode> nodes = new ArrayList<>(); // aligned with keys

    ConsistentHashRing(long totalSlots) {
        this.totalSlots = totalSlots;
    }

    // --- assign(key) => node to the immediate right (O(log n)) ---
    public StorageNode assign(String item) {
        if (nodes.isEmpty()) throw new IllegalStateException("ring is empty");
        long pos = HashUtil.hashToSlot(item, totalSlots);
        int idx = upperBound(keys, pos);          // first key > pos
        idx = idx % keys.size();                  // wrap to 0 if past end
        return nodes.get(idx);
    }

    // --- add node: find its position, insert at sorted index (O(log n)) ---
    public long addNode(StorageNode node) {
        if (keys.size() >= totalSlots) throw new IllegalStateException("hash space is full");
        long key = HashUtil.hashToSlot(node.host, totalSlots);
        int idx = upperBound(keys, key);          // insertion point to keep sorted

        // collision check (optional; rarely happens with large space)
        if (idx > 0 && keys.get(idx - 1) == key) {
            throw new IllegalStateException("collision occurred");
        }

        // (Data migration happens in practice here: move keys in (prevKey, key] to this node)
        keys.add(idx, key);
        nodes.add(idx, node);
        return key;
    }

    // --- remove node: locate its exact key, then remove (O(log n)) ---
    public long removeNode(StorageNode node) {
        if (keys.isEmpty()) throw new IllegalStateException("hash space is empty");
        long key = HashUtil.hashToSlot(node.host, totalSlots);
        int idx = lowerBound(keys, key);          // first index >= key
        if (idx >= keys.size() || !keys.get(idx).equals(key)) {
            throw new IllegalStateException("node does not exist");
        }
        // (Data migration happens in practice here: keys from this node go to next clockwise node)
        keys.remove(idx);
        nodes.remove(idx);
        return key;
    }

    // Helpers: lowerBound / upperBound on sorted List<Long>
    private static int lowerBound(List<Long> a, long x) {
        int lo = 0, hi = a.size();
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a.get(mid) < x) lo = mid + 1; else hi = mid;
        }
        return lo;
    }
    private static int upperBound(List<Long> a, long x) {
        int lo = 0, hi = a.size();
        while (lo < hi) {
            int mid = (lo + hi) >>> 1;
            if (a.get(mid) <= x) lo = mid + 1; else hi = mid;
        }
        return lo;
    }
}