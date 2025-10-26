import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCacche extends LinkedHashMap<Integer,Integer> {

    private final int maxCapacity;

    public LRUCacche(int initialCapacity) {
        super(initialCapacity,1.0f,true);
        maxCapacity = initialCapacity;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > maxCapacity;
    }

    @Override
    public Integer get(Object key) {
        return super.getOrDefault(key,-1);
    }
}
