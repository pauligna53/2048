package jpp.numbergame;

import javax.naming.ldap.UnsolicitedNotification;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class NumberGame {
    int width;
    int height;
    int initialTiles;
    int points;
    Tile[][] tiles;

    public NumberGame(int width, int height){
        this.points = 0;
        tiles = new Tile[width][height];
        if(width < 1 || height < 1) {
            throw new IllegalArgumentException();
        }
        this.width = width;
        this.height = height;

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                tiles[i][j] = null;
            }
        }
    }

    public NumberGame(int width, int height, int initialTiles){
        if(initialTiles < 0 || initialTiles > width*height || width < 1 || height < 1) {
            throw new IllegalArgumentException();
        }
        this.points = 0;
        this.height = height;
        this.width = width;
        tiles = new Tile[width][height];
        this.initialTiles = initialTiles;

        for (int i = 0; i < initialTiles; i++) {
             addRandomTile();
        }
    }

    public int get(Coordinate2D coord){
        if(tiles[coord.getX()][coord.getY()] == null)
            return 0;
        if(coord.y >= height || coord.x >= width || coord.y < 0 || coord.x < 0)
            throw new IndexOutOfBoundsException();
        return tiles[coord.x][coord.y].value;
    }

    public int get(int x, int y){
        if(tiles[x][y] == null)
            return 0;
        if(y >= height || x >= width || y < 0 || x < 0)
            throw new IndexOutOfBoundsException();
        return tiles[x][y].value;
    }

    public int getPoints(){
        return points;
    }

    public boolean isFull(){
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if(tiles[i][j] == null)
                    return false;
            }
        }
        return true;
    }

    public Tile addRandomTile(){
        Random random = new Random();
        if (isFull())
            throw new TileExistsException();

        int x = random.nextInt(height);
        int y = random.nextInt(width);

        while (tiles[x][y] != null) {
            x = random.nextInt(height);
            y = random.nextInt(width);
        }

        int percent = random.nextInt(100);
        if (percent < 10) {
            return addTile(x,y,4);

        }
       return addTile(x,y,2);
    }

    public Tile addTile(int x, int y, int value){
        if(tiles[x][y] != null)
                throw new TileExistsException();
        Coordinate2D coordinate2D = new Coordinate2D(x,y);
        tiles[x][y] = new Tile(coordinate2D,value);
        return new Tile(coordinate2D,value);
    }

    public List<Move> move(Direction dir){
        List<Move> out = new ArrayList<>();
        int startHere;
        boolean vibeCheck = false;
        //initialize move with some values
        int a = 1; int b = 1;
        Coordinate2D init = new Coordinate2D(a,b);
        Move move = new Move(init,init,1,1);
        if(dir.equals(Direction.UP)) {
            for (int i = 0; i < width; i++) {
                startHere = 0;
                for (int j = 1; j < width; j++) {
                    if(tiles[i][j] != null){
                        for (int k = startHere; k < j; k++) {
                            for (int l = k+1; l < j; l++) {
                                if(tiles[i][l] != null){
                                    vibeCheck = true;
                                    break;
                                }
                            }
                            if(tiles[i][k] == null){
                                out.add(new Move(new Coordinate2D(i,j), new Coordinate2D(i,k),tiles[i][j].getValue()));
                                this.addTile(i,k,tiles[i][j].value);
                                tiles[i][j] = null;
                                startHere = k+1;
                                break;
                            }
                            else if(tiles[i][j].getValue() == tiles[i][k].getValue() && !vibeCheck){
                                out.add(new Move(new Coordinate2D(i,j),new Coordinate2D(i,k),tiles[i][j].getValue(),tiles[i][j].getValue()*2));
                                this.points += 2*tiles[i][j].getValue();
                                tiles[i][k] = new Tile(new Coordinate2D(i,k),2*tiles[i][j].getValue());
                                tiles[i][j] = null;
                                startHere = k+1;
                                break;
                            }
                            if(k-1 == j)
                                startHere = j;
                        }
                        vibeCheck = false;
                    }
                }
            }
        }
        if(dir.equals(Direction.DOWN)) {
            for (int i = 0; i < width; i++) {
                startHere = width-1;
                for (int j = width-1; j >= 0; j--) {
                    if(tiles[i][j] != null){
                        for (int k = startHere; k > j; k--) {
                            for (int l = k-1; l > j; l--) {
                                if(tiles[i][l] != null){
                                    vibeCheck = true;
                                    break;
                                }
                            }
                            if(tiles[i][k] == null){
                                out.add(new Move(new Coordinate2D(i,j), new Coordinate2D(i,k),tiles[i][j].getValue()));
                                this.addTile(i,k,tiles[i][j].value);
                                tiles[i][j] = null;
                                startHere = k-1;
                                break;
                            }
                            else if(tiles[i][j].getValue() == tiles[i][k].getValue() && !vibeCheck){
                                out.add(new Move(new Coordinate2D(i,j),new Coordinate2D(i,k),tiles[i][j].getValue(),tiles[i][j].getValue()*2));
                                this.points += 2*tiles[i][j].getValue();
                                tiles[i][k] = new Tile(new Coordinate2D(i,k),2*tiles[i][j].getValue());
                                tiles[i][j] = null;
                                startHere = k-1;
                                break;
                            }
                            if(k-1 == i)
                                startHere = i;
                        }
                        vibeCheck = false;
                    }
                }
            }
        }
        if(dir.equals(Direction.LEFT)) {
            for (int j = 0; j < width; j++) {
                startHere = 0;
                for (int i = 1; i < width; i++) {
                    if(tiles[i][j] != null){
                        for (int k = startHere; k < i; k++) {
                            for (int l = k+1; l < i; l++) {
                                if(tiles[l][j] != null){
                                    vibeCheck = true;
                                    break;
                                }
                            }
                            if(tiles[k][j] == null){
                                out.add(new Move(new Coordinate2D(i,j), new Coordinate2D(k,j),tiles[i][j].getValue()));
                                this.addTile(k,j,tiles[i][j].value);
                                tiles[i][j] = null;
                                startHere = k+1;
                                break;
                            }
                            else if(tiles[i][j].getValue() == tiles[k][j].getValue() && !vibeCheck){
                                out.add(new Move(new Coordinate2D(i,j),new Coordinate2D(k,j),tiles[i][j].getValue(),tiles[i][j].getValue()*2));
                                this.points += 2*tiles[i][j].getValue();
                                tiles[k][j] = new Tile(new Coordinate2D(i,k),2*tiles[i][j].getValue());
                                tiles[i][j] = null;
                                startHere = k+1;
                                break;
                            }
                            if(k-1 == j)
                                startHere = j;
                        }
                        vibeCheck = false;
                    }
                }
            }
        }
        if(dir.equals(Direction.RIGHT)) {
            for (int j = 0; j< width; j++) {
                startHere = width-1;
                for (int i = width-2; i >= 0; i--) {
                    if(tiles[i][j] != null){
                        for (int k = startHere; k > j; k--) {
                            for (int l = i+1; l > k; l--) {
                                if (tiles[l][j] != null) {
                                    vibeCheck = true;
                                    break;
                                }
                            }
                            if(tiles[k][j] == null){
                                out.add(new Move(new Coordinate2D(i,j),new Coordinate2D(k,j),tiles[i][j].getValue()));
                                this.addTile(k,j,tiles[i][j].value);
                                tiles[i][j] = null;
                                startHere = k;
                                break;
                            }
                            else if(tiles[i][j].getValue() == tiles[k][j].getValue() && !vibeCheck){
                                out.add(new Move(new Coordinate2D(i,j),new Coordinate2D(k,j),tiles[i][j].getValue(),tiles[i][j].getValue()*2));
                                this.points += 2*tiles[i][j].getValue();
                                tiles[k][j] = new Tile(new Coordinate2D(k,j),2*tiles[i][j].getValue());
                                tiles[i][j] = null;
                                startHere = k;
                                break;
                            }
                            if(k-1 == i)
                                startHere = k-1;
                            vibeCheck = false;
                        }
                    }
                }
            }
        }
        return out;
    }

    public boolean canMove(Direction dir){
        if(dir.equals(Direction.UP)){
            for (int j = 1; j < height; j++) {
                for (int i = 0; i < width; i++){
                     if (tiles[i][j] != null)
                        if(tiles[i][j-1] == null || tiles[i][j-1].getValue() == tiles[i][j].getValue())
                            return true;
                }
            }
        }
        if(dir.equals(Direction.DOWN)){
            for (int j = height-2; j >= 0; j--) {
                for (int i = 0; i < width; i++){
                     if (tiles[i][j] != null)
                        if(tiles[i][j+1] == null || tiles[i][j+1].getValue() == tiles[i][j].getValue())
                            return true;
                }
            }
        }
        if(dir.equals(Direction.RIGHT)){
            for (int i = height-2; i >= 0; i--) {
                for (int j = 0; j < width; j++){
                    if(tiles[i][j] != null)
                        if(tiles[i+1][j] == null || tiles[i+1][j].getValue() == tiles[i][j].getValue())
                            return true;
                }
            }
        }
        if(dir.equals(Direction.LEFT)){
            for (int i = 1; i < height; i++) {
                for (int j = 0; j < width; j++){
                    if (tiles[i][j] != null)
                        if(tiles[i-1][j] == null || tiles[i-1][j].getValue() == tiles[i][j].getValue())
                            return true;
                }
            }
        }
        return false;
    }

    public boolean canMove(){
        return canMove(Direction.UP) || canMove(Direction.DOWN) || canMove(Direction.LEFT) || canMove(Direction.RIGHT);
    }
}
