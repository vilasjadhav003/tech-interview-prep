final class StorageNode {
    final String name;
    final String host; // unique id for hashing
    StorageNode(String name, String host) { this.name = name; this.host = host; }
    @Override public String toString() { return name + "(" + host + ")"; }
}
