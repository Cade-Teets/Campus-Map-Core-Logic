package graphs.shortestpaths;

import graphs.Edge;
import graphs.Graph;


import java.util.*;

/**
 * Topological sorting implementation of the {@link ShortestPathSolver} interface for <b>directed acyclic graphs</b>.
 *
 * @param <V> the type of vertices.
 * @see ShortestPathSolver
 */
public class ToposortDAGSolver<V> implements ShortestPathSolver<V> {
    private final Map<V, Edge<V>> edgeTo;
    private final Map<V, Double> distTo;

    /**
     * Constructs a new instance by executing the toposort-DAG-shortest-paths algorithm on the graph from the start.
     *
     * @param graph the input graph.
     * @param start the start vertex.
     */
    public ToposortDAGSolver(Graph<V> graph, V start) {
        edgeTo = new HashMap<>();
        distTo = new HashMap<>();
        Set<V> visited = new HashSet<V>();
        List<V> result = new ArrayList<>();

        // Add the start distance
        distTo.put(start, 0.0);

        // Store post-order traversal in result and reverse it
        dfsPostOrder(graph, start, visited, result);
        Collections.reverse(result);

        // Relax the edges of reversed post-order nodes
        for (V vertex : result) {
            if (distTo.containsKey(vertex)) {
                for (Edge<V> edge : graph.neighbors(vertex)) {
                    // make helper function to compare edge weights and swap
                    relax(edge);
                }
            }
        }
    }
    /**
     * Recursively adds nodes from the graph to the result in DFS postorder from the start vertex.
     *
     * @param graph   the input graph.
     * @param start   the start vertex.
     * @param visited the set of visited vertices.
     * @param result  the destination for adding nodes.
     */
    private void dfsPostOrder(Graph<V> graph, V start, Set<V> visited, List<V> result) {
        // Add starting vertex to visited set to avoid endless loop
        visited.add(start);

        // For each edge that leaves the start node
        for (Edge<V> e : graph.neighbors(start)) {
            // Get the vertex connected to e and recurse if it has not been visited
            V to = e.to;
            if (!visited.contains(to)) {
                dfsPostOrder(graph, to, visited, result);
            }
            result.add(to);
        }
    }

    @Override
    public List<V> solution(V goal) {
        List<V> path = new ArrayList<>();
        V curr = goal;
        path.add(curr);
        while (edgeTo.get(curr) != null) {
            curr = edgeTo.get(curr).from;
            path.add(curr);
        }
        Collections.reverse(path);
        return path;
    }
    private void relax(Edge<V> edge) {
        V from = edge.from;;
        V to = edge.to;

        double oldDist = distTo.getOrDefault(to, Double.POSITIVE_INFINITY);
        double newDist = distTo.get(from) + edge.weight;
        if (newDist < oldDist) {
            edgeTo.put(to, edge);
            distTo.put(to, newDist);
        }
    }
}
