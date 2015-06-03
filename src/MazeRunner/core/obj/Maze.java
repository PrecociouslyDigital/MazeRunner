package MazeRunner.core.obj;

import MazeRunner.DrawingPanel;
import java.io.Serializable;

/**
 *
 * @author s-yinb
 */
public class Maze implements Serializable{
    private static final int squarePixel = 20;
    int rows, cols;
    Square[][] grid;
    DrawingPanel pane;
    
    public Maze(){
        this(10,10);
    }
    public Maze(int x, int y){
        pane = new DrawingPanel(x*squarePixel,y*squarePixel);
        setRows(x);
        setCols(y);
        resetSquares();
    }
    public void resetSquares(){
        grid = new Square[rows][cols];
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++){
                grid[i][j] = new Square();
                grid[i][j].setLoc(i,j);
            }
        grid[0][0] = new GladeSquare();
        grid[0][0].setNeighbors(null, grid[1][0], null, grid[0][1]);
        grid[0][cols-1].setNeighbors(grid[0][cols-2], grid[1][cols-1], null, null);
        grid[rows-1][cols-1].setNeighbors(grid[rows-1][cols-2], null, grid[rows-2][cols-1], null);
        grid[rows-1][0].setNeighbors(null, null, grid[rows-2][0], grid[rows-1][1]);
        for(int i = 1; i < rows - 1; i++){
            grid[i][0].setNeighbors(grid[i-1][0],grid[i][1],null,grid[i+1][0]);
            grid[i][cols-1].setNeighbors(grid[i-1][cols-1],null, grid[i][cols-2],grid[i+1][cols-1]);
        }
        for(int i = 1; i < cols - 1; i++){
            grid[0][i].setNeighbors(null,grid[0][i+1],grid[0][i-1], grid[1][i]);
            grid[rows-1][i].setNeighbors(grid[rows-2][i],grid[rows-1][i+1],grid[rows-1][i-1], null);
        }
        for(int i = 1; i < rows-1; i++)
            for(int j = 1; j < cols-1; j++)
                grid[i][j].setNeighbors(grid[i][j-1], grid[i+1][j], grid[i-1][j], grid[i][j+1]);
    }
    public void setRows(int nRows) { 
        rows = nRows;
    }

    public int getRows() {
        return rows;
    }
    public Square getSquare(int x, int y){
        return grid[x][y];
    }
    public void setCols(int nCols){
        cols = nCols;
    }

    public int getCols() {
        return  cols;
    }
    public int getArea(){
        return rows * cols;
    }

    public boolean isGlade(int row, int col) {
        return checkGladeIndex(row, rows) && checkGladeIndex(col,cols);
    }

    public boolean checkGladeIndex(int num, int total){
        if(total%2 == 1){
            return Math.abs(total/2 + 1 - num) <= 3;
        }else{
            return num > total/2 ? num - total/2  < 4 : total/2 - num < 3;
        }
    }

    public boolean isEdge(int row, int col) {
        return row == 0 || row == rows-1 || col == 0 || col == cols-1;
    }

    @Override
    public String toString() {
        String total = "";
        for(Square[] t : grid){
            for(Square s : t)
                total += s.toString();
            total += "\n";
        }
        return total;
    }

    public void generate() {
        grid[0][0].generate(0);
    }

    public void saveToFile(String fileName) {
    }

    public void loadFromFile(String fileName) {
        
    }

    public int solve() {
        return 0;
    }

    public void draw(){
        for(int i = 0; i < rows; i++)
            for(int j = 0; j < cols; j++){
                grid[i][j].draw(pane, i*squarePixel, j * squarePixel, squarePixel, squarePixel);
            }
    }
}