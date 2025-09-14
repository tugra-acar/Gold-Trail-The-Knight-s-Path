//Name: Abdullah Tugra Acar
//Student Number: 2023400219
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
public class Map {
    private int row;
    private int column;
    private Tile[] tiles;


    Map(File mapData) throws Exception {
        //takes every tile data in mapData File and it adds it to tiles list
        Scanner input = new Scanner(mapData);
        column = input.nextInt();
        row = input.nextInt();
        tiles = new Tile[row*column];
        for (int i = 0; i < row*column; i++) {
            tiles[i] = new Tile(input.nextInt(), input.nextInt(), input.nextInt());
        }
        //adds adjacent passable tiles to each tile
        this.initializeAdjacents();

    }
    //returns the tile in given position if it exists otherwise it returns null
    public Tile getTileAt(int x, int y) {
        if (x < 0 || x >= column || y < 0 || y>= row) {
            return null;
        }
        for (Tile tile : tiles) {
            if (tile.getColumn() == x && tile.getRow() == y) {
                return tile;
            }
        }
        return null;
    }

    private void initializeAdjacents() {
        for (Tile tile : tiles) {
            int x = tile.getColumn();
            int y = tile.getRow();

            // up
            Tile up = getTileAt(x, y + 1);
            if (up != null && up.getType() != 2) {
                tile.addAdjacentTile(up);
            }
            // down
            Tile down = getTileAt(x, y - 1);
            if (down != null && down.getType() != 2) {
                tile.addAdjacentTile(down);
            }
            // left
            Tile left = getTileAt(x - 1, y);
            if (left != null && left.getType() != 2) {
                tile.addAdjacentTile(left);
            }
            // right
            Tile right = getTileAt(x + 1, y);
            if (right != null && right.getType() != 2) {
                tile.addAdjacentTile(right);
            }
        }
    }


    public void draw( List<int[]> coins ) {
        for ( Tile tile : tiles ) {
            tile.draw();
        }
        for (int i= 0; i<coins.size(); i++) {
            int[] obj = coins.get(i);
            int column = obj[0];
            int row = obj[1];
            StdDraw.picture(15 + 30 * column, 15 + 30 * row,"misc/coin.png",30,30);
        }

    }
     public Tile[] getTiles() {
        return tiles;
     }
     public int getColumn() {
        return column;
     }
     public int getRow() {
        return row;
     }
}
