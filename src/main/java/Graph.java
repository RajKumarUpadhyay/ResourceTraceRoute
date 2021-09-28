import java.util.*;

public class Graph {

    private static final String ERROR_MSG = "NO SUCH TRACE";
    private Map<String, Node> vertexes = new HashMap<>();
    private Map<String, Edge> edges = new HashMap<>();

    public void addEdge(String from, String to, Integer latency) {
        Node vertexFrom = vertexes.getOrDefault(from, new Node(from));
        Node vertexTo = vertexes.getOrDefault(to, new Node(to));
        vertexFrom.getNeighbors().put(to, vertexTo);
        vertexes.putIfAbsent(from, vertexFrom);
        vertexes.putIfAbsent(to, vertexTo);

        edges.putIfAbsent(from + to, new Edge(from + to, latency));
    }

    public String getAverageLatency(String vertexes) {
        int idx = 0;
        int latency = 0;

        while (idx + 2 < vertexes.length()) {
            String from = vertexes.substring(idx, idx + 1);
            String to = vertexes.substring(idx + 2, idx + 3);

            Edge edge = edges.get(from + to);
            if (edge != null) {
                latency = latency + edge.getLatency();
            } else {
                return ERROR_MSG;
            }

            idx = idx + 2;
        }

        return String.valueOf(latency);
    }

    public Integer getTracesByStops(String from, String to, Integer stops, Response condition) {
        int traceNumber = 0;
        int depth = 0;

        Stack<String> visited = new Stack<>();
        Stack<String> stack = new Stack<>();
        Stack<Integer> depthStack = new Stack<>();
        stack.push(from);
        depthStack.push(depth);
        while (!stack.isEmpty()) {
            String node = stack.pop();
            depth = depthStack.pop() + 1;
            visited.add(node);
            Node vertex = vertexes.get(node);
            Map<String, Node> neighbors = vertex.getNeighbors();

            if (depth != stops + 1) {
                pushNeighborsToStack(depth, stack, depthStack, neighbors);
            } else {
                goBack(visited, depthStack);
            }

            if (isReachedDestination(node, to, depth, stops, condition))
                traceNumber++;
        }

        return traceNumber;
    }

    public Integer getShortestTrace(String from, String to) {
        int minLatency = 0;
        int depth = 0;

        Stack<String> visited = new Stack<>();
        Stack<String> stack = new Stack<>();
        Stack<Integer> depthStack = new Stack<>();
        stack.push(from);
        depthStack.push(depth);
        while (!stack.isEmpty()) {
            String node = stack.pop();
            depth = depthStack.pop() + 1;
            visited.add(node);
            Node vertex = vertexes.get(node);
            Map<String, Node> neighbors = vertex.getNeighbors();

            if (!node.equals(to) || visited.size() == 1) {
                for (String neighbor : neighbors.keySet()) {
                    if (!visited.contains(neighbor) || (visited.contains(neighbor) && neighbor.equals(to))) {
                        stack.push(neighbor);
                        depthStack.push(depth);
                    }
                }
            }

            if (neighbors.isEmpty() && !node.equals(to)) {
                goBack(visited, depthStack);
            }

            else if (node.equals(to) && visited.size() > 1) {
                int averageLatency = getAverage(visited);
                if (minLatency == 0 || minLatency > averageLatency)
                    minLatency = averageLatency;

                goBack(visited, depthStack);
            }
        }

        return minLatency;
    }

    public Integer getShortestTraces(String from, String to, Integer maxLatency) {
        int shortestTraces = 0;
        int depth = 0;

        Stack<String> visited = new Stack<>();
        Stack<String> stack = new Stack<>();
        Stack<Integer> depthStack = new Stack<>();
        stack.push(from);
        depthStack.push(depth);
        while (!stack.isEmpty()) {
            String node = stack.pop();
            depth = depthStack.pop() + 1;
            visited.add(node);
            Node vertex = vertexes.get(node);
            Map<String, Node> neighbors = vertex.getNeighbors();

            int averageLatency = getAverage(visited);
            if (averageLatency > maxLatency) {
                goBack(visited, depthStack);
            } else {
                pushNeighborsToStack(depth, stack, depthStack, neighbors);

                if (neighbors.isEmpty() && !node.equals(to)) {
                    goBack(visited, depthStack);
                }
                else if (node.equals(to) && visited.size() > 1) {
                    if (averageLatency < maxLatency)
                        shortestTraces++;

                    goBack(visited, depthStack);
                }
            }
        }

        return shortestTraces;
    }

    private void goBack(Stack<String> visited, Stack<Integer> depthStack) {
        if (!depthStack.isEmpty()) {
            int deltaDepth = depthStack.peek();
            int backDepth = visited.size() - deltaDepth;
            for (int i = 0; i < backDepth; i++)
                visited.pop();
        }
    }

    private int getAverage(Stack<String> visited) {
        int average = 0;
        Queue<String> queue = new ArrayDeque<>(visited);
        String from = queue.poll();
        while (!queue.isEmpty()) {
            String to = queue.poll();
            Edge edge = edges.get(from + to);

            average = average + edge.getLatency();

            from = to;
        }

        return average;
    }

    private void pushNeighborsToStack(int depth, Stack<String> stack, Stack<Integer> depthStack, Map<String, Node> neighbors) {
        for (String neighbor : neighbors.keySet()) {
            stack.push(neighbor);
            depthStack.push(depth);
        }
    }

    private boolean isReachedDestination(String node, String to, int depth, Integer stops, Response condition) {
        if (condition.equals(Response.MAX_STOPS) &&
                node.equals(to) && 1 < depth && depth <= stops + 1)
            return true;
        else return condition.equals(Response.EXACT_STOPS) &&
                node.equals(to) && depth == stops + 1;
    }
}
