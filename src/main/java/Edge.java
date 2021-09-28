public class Edge {
    private String key;
    private int latency;

    public Edge(String key, int latency) {
        this.key = key;
        this.latency = latency;
    }

    public String getKey() {
        return key;
    }

    public int getLatency() {
        return latency;
    }
}
