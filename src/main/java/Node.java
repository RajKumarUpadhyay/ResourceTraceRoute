import java.util.HashMap;
import java.util.Map;

public class Node<K, E> {
    private String name;
    private Map<String, Node> neighbors = new HashMap<>();

    Node(String name) {
        this.name = name;
    }

    Map<String, Node> getNeighbors() {
        return neighbors;
    }
}
