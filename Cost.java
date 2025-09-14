//Name: Abdullah Tugra Acar
//Student Number: 2023400219
import java.io.*;
import java.util.*;

public class Cost {
    private HashMap<String, Double> costMap;

    Cost(File travelCosts) throws Exception {
        costMap = new HashMap<>();
        Scanner input = new Scanner(travelCosts);
        while (input.hasNext()) {
            int x1 = input.nextInt();
            int y1 = input.nextInt();
            int x2 = input.nextInt();
            int y2 = input.nextInt();
            double cost = input.nextDouble();
            String key = createKey(x1, y1, x2, y2);
            costMap.put(key, cost);
            String key2 = createKey(x2, y2, x1, y1);
            costMap.put(key2, cost);
        }
        input.close();
    }
    private String createKey(int x1, int y1, int x2, int y2) {
        return x1 + "," + y1 + "-" + x2 + "," + y2;
    }

    public Double getCost(Tile from, Tile to) {
        String key = createKey(from.getColumn(), from.getRow(), to.getColumn(), to.getRow());
        return costMap.getOrDefault(key, Double.POSITIVE_INFINITY);
    }
}
