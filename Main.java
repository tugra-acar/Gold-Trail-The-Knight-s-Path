//Name: Abdullah Tugra Acar
//Student Number: 2023400219
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

public class Main {
    /**
     * removes coin at reached goal from coins list
     * @param goal reached coin
     * @param coins coins list
     */
    public static void removeCoinAt(Tile goal, List<int[]> coins) {
        for (int i = 0; i < coins.size(); i++) {
            int[] coin = coins.get(i);
            if (coin[0] == goal.getColumn() && coin[1] == goal.getRow()) {
                coins.remove(i);
                break;
            }
        }
    }

    /**
     * draws the map, coins, knight, and walking dots with using StdDraw library
     * @param path path coordinates, Tile list to draw dots and knight
     * @param map maps block coordinates info
     * @param coins list of coin coordinates
     */
    public static void animatePath(List<Tile> path, Map map, List<int[]> coins) {
        //in every frame draw map knight and dots increase dot number in each frame
        for (int i = 0; i < path.size(); i++) {
            StdDraw.clear();
            //draw map and coins left
            map.draw(coins);
            //draw the dots
            StdDraw.setPenColor(StdDraw.RED);
            for (int j = i-1; j >= 0; j--) {
                Tile tile = path.get(j);
                double cx = 15 + 30 * tile.getColumn();
                double cy = 15 + 30 * tile.getRow();
                StdDraw.filledCircle(cx, cy, 4);
            }
            //draw the knight
            double knightX = 15 + 30*path.get(i).getColumn();
            double knightY = 15 + 30*path.get(i).getRow();
            StdDraw.picture(knightX,knightY,"misc/knight.png",30, 30);
            StdDraw.show();
            StdDraw.pause(200);
        }


    }

    public static boolean DRAW = false;

    public static void main(String[] args) throws Exception {

        int entry = 0;
        if (args.length == 4 && args[0].equals("-draw")) {
            DRAW = true;
            entry = 1;
            StdDraw.enableDoubleBuffering();
        }

        File mapFile = new File(args[entry]);
        File costFile = new File(args[entry + 1]);
        File objectivesFile = new File(args[entry + 2]);
        File outputFile = new File("out/output.txt");

        PrintWriter writer = new PrintWriter(new FileWriter(outputFile));

        //initialize the files
        Map map = new Map(mapFile);
        Cost cost = new Cost(costFile);
        PathFinder pathFinder = new PathFinder(map, cost);
        //set the canvas if draw is mentioned
        if (DRAW) {
            StdDraw.setCanvasSize(map.getColumn()*30, map.getRow()*30);
            StdDraw.setXscale(0, map.getColumn()*30);
            StdDraw.setYscale(map.getRow()*30, 0);
        }
        //making objectives a 2D list
        List<int[]> objectives = new ArrayList<>();
        Scanner scanner = new Scanner(objectivesFile);
        while (scanner.hasNextInt()) {
            int x = scanner.nextInt();
            int y = scanner.nextInt();
            objectives.add(new int[]{x, y});
        }
        //taking coins to another list for drawing
        List<int[]> coins = new ArrayList<>();
        for (int i = 1; i < objectives.size(); i++) {
            int[] coin = objectives.get(i);
            coins.add(coin);
        }
        scanner.close();

        //taking where to start from objectives
        int startX = objectives.getFirst()[0];
        int startY = objectives.getFirst()[1];
        Tile current = map.getTileAt(startX, startY);

        //keep the total cost, step, and objective count
        int totalStep = 0;
        double totalCost = 0.0;
        int objectiveCount = 1;


        for (int i = 1; i < objectives.size(); i++) {
            int[] goalCoords = objectives.get(i);
            Tile goal = map.getTileAt(goalCoords[0], goalCoords[1]);

            List<Tile> path = pathFinder.findPath(current, goal);


            if (path.isEmpty()) {
                writer.println("Objective " + objectiveCount + " cannot be reached!");
                objectiveCount++;
                continue;
            }

            writer.println("Starting position: (" + current.getColumn() + ", " + current.getRow() + ")");


            Tile prev = null;
            int stepCount = 0;
            double localCost = 0.0;

            for (Tile tile : path) {
                if (prev != null) {
                    stepCount++;
                    double stepCost = cost.getCost(prev, tile);
                    localCost += stepCost;
                    totalCost += stepCost;
                    totalStep++;
                    String stepText = "Step Count: " + stepCount + ", move to (" + tile.getColumn() + ", " + tile.getRow() + "). Total Cost: " + String.format("%.2f", localCost) + ".";
                    writer.println(stepText);
                }
                prev = tile;
            }

            writer.println("Objective " + objectiveCount + " reached!");

            if (DRAW) {
                animatePath(path, map, coins);
            }
            removeCoinAt(goal, coins);
            if (DRAW) {
                //after animation frame(to delete the path at last coin)
                Tile finalTile = path.getLast();
                StdDraw.clear();
                map.draw(coins);
                double fx = 15 + 30 * finalTile.getColumn();
                double fy = 15 + 30 * finalTile.getRow();
                StdDraw.picture(fx, fy, "misc/knight.png", 30, 30);

                StdDraw.show();
            }

            current = goal;
            objectiveCount++;


        }

        writer.println("Total Step: " + totalStep + ", Total Cost: " + String.format("%.2f", totalCost));

        writer.close();
    }
}
