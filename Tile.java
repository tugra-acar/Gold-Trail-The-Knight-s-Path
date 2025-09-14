//Name: Abdullah Tugra Acar
//Student Number: 2023400219
import java.util.ArrayList;

public class Tile {
    private int column;//column information of that specific tile
    private int row;//row information of that specific tile
    private int type;//type information of thet specific tile
    private ArrayList<Tile> adjacentTiles  = new ArrayList<>();//neighbouring tiles array list if they are not impassible
    //constructor
    Tile(int column, int row, int type) {
        this.column = column;
        this.row = row;
        this.type = type;
    }
    //tile drawing function according to its coordinates adn type
    public void draw() {
        String file = type == 0 ? "misc/grassTile.jpeg": (type == 2 ? "misc/impassableTile.jpeg" : "misc/sandTile.png");
        StdDraw.picture(15 + 30 * column, 15 + 30 * row,file,30,30);

    }

    //setter
    public void addAdjacentTile(Tile tile) {
        adjacentTiles.add(tile);
    }

    //getters
    public int getColumn() {
        return column;
    }

    public int getRow() {
        return row;
    }

    public int getType() {
        return type;
    }

    public ArrayList<Tile> getAdjacentTiles() {
        return adjacentTiles;
    }

}
