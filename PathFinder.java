//Name: Abdullah Tugra Acar
//Student Number: 2023400219
import java.util.*;
import java.io.*;

public class PathFinder {
    private Map map;
    private Cost costGraph;

    PathFinder(Map map, Cost costGraph) {
        this.map = map;
        this.costGraph = costGraph;
    }

    public List<Tile> findPath(Tile start, Tile goal) {
        PriorityQueue<Node> pq = new PriorityQueue<>();
        HashMap<Tile, Double> distance = new HashMap<>();
        HashMap<Tile, Tile> previous = new HashMap<>();

        for (Tile tile : map.getTiles()) {
            distance.put(tile, Double.POSITIVE_INFINITY);
        }

        distance.put(start, 0.0);
        pq.add(new Node(start, 0.0));

        while (!pq.isEmpty()) {
            Node currentNode = pq.poll();
            Tile currentTile = currentNode.tile;

            if (currentTile.equals(goal)) {
                return reconstructPath(previous, goal);
            }

            for (Tile neighbor : currentTile.getAdjacentTiles()) {
                double cost = costGraph.getCost(currentTile, neighbor);

                if (cost == Double.POSITIVE_INFINITY) continue;

                double newDistance = distance.get(currentTile) + cost;
                if (newDistance < distance.get(neighbor)) {
                    distance.put(neighbor, newDistance);
                    previous.put(neighbor, currentTile);
                    pq.add(new Node(neighbor, newDistance));
                }
            }
        }

        return new ArrayList<>();
    }

    private List<Tile> reconstructPath(HashMap<Tile, Tile> previous, Tile goal) {
        List<Tile> path = new ArrayList<>();
        Tile current = goal;
        while (current != null) {
            path.add(current);
            current = previous.get(current);
        }
        Collections.reverse(path);
        return path;
    }

    private class Node implements Comparable<Node> {
        Tile tile;
        double cost;

        Node(Tile tile, double cost) {
            this.tile = tile;
            this.cost = cost;
        }

        @Override
        public int compareTo(Node other) {
            return Double.compare(this.cost, other.cost);
        }
    }
}
