package MazeRunner.core.obj;

import MazeRunner.DrawingPanel;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author s-yinb
 */
public class Maze implements Serializable {

    int rows, cols;
    Square[][] grid;

    public Maze() {
        this(20, 20);
    }

    public Maze(int x, int y) {
        setRows(x);
        setCols(y);
        resetSquares();
    }

    public void resetSquares() {
        grid = new Square[rows][cols];
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                grid[i][j] = new Square();
            }
        }
        for (int i = rows / 2 - 3; i < rows / 2 + (rows % 2 == 0 ? 3 : 4); i++) {
            for (int j = cols / 2 - 3; j < cols / 2 + (cols % 2 == 0 ? 3 : 4); j++) {
                grid[i][j] = new GladeSquare();
            }
        }
        for (int i = 0; i < rows; i++) {
            grid[i][0] = new EdgeSquare(i, 0);
            grid[i][cols - 1] = new EdgeSquare(i, cols - 1);
        }
        for (int i = 1; i < cols - 1; i++) {
            grid[0][i] = new EdgeSquare(0, i);
            grid[rows - 1][i] = new EdgeSquare(rows - 1, i);
        }
        grid[0][0].setNeighbors(null, grid[1][0], null, grid[0][1]);
        grid[0][cols - 1].setNeighbors(grid[0][cols - 2], grid[1][cols - 1], null, null);
        grid[rows - 1][cols - 1].setNeighbors(grid[rows - 1][cols - 2], null, grid[rows - 2][cols - 1], null);
        grid[rows - 1][0].setNeighbors(null, null, grid[rows - 2][0], grid[rows - 1][1]);
        for (int i = 1; i < rows - 1; i++) {
            grid[i][0].setNeighbors(grid[i - 1][0], grid[i][1], null, grid[i + 1][0]);
            grid[i][cols - 1].setNeighbors(grid[i - 1][cols - 1], null, grid[i][cols - 2], grid[i + 1][cols - 1]);
        }
        for (int i = 1; i < cols - 1; i++) {
            grid[0][i].setNeighbors(null, grid[0][i + 1], grid[0][i - 1], grid[1][i]);
            grid[rows - 1][i].setNeighbors(grid[rows - 2][i], grid[rows - 1][i + 1], grid[rows - 1][i - 1], null);
        }
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                grid[i][j].setNeighbors(grid[i][j - 1], grid[i + 1][j], grid[i - 1][j], grid[i][j + 1]);
            }
        }
    }

    public void setRows(int nRows) {
        rows = nRows;
    }

    public int getRows() {
        return rows;
    }

    public Square getSquare(int x, int y) {
        return grid[x][y];
    }

    public void setCols(int nCols) {
        cols = nCols;
    }

    public int getSquareSize() {
        return Math.max(800 / (rows + 2), 800 / (cols + 2));
    }

    public int getCols() {
        return cols;
    }

    public int getArea() {
        return rows * cols;
    }

    public boolean isGlade(int row, int col) {
        return checkGladeIndex(row, rows) && checkGladeIndex(col, cols);
    }

    public boolean checkGladeIndex(int num, int total) {
        if (total % 2 == 1) {
            return Math.abs(total / 2 + 1 - num) <= 3;
        } else {
            return num > total / 2 ? num - total / 2 < 4 : total / 2 - num < 3;
        }
    }

    public boolean isEdge(int row, int col) {
        return row == 0 || row == rows - 1 || col == 0 || col == cols - 1;
    }

    @Override
    public String toString() {
        String total = "";
        for (int t = 0; t < grid.length; t++) {
            for (int s = 0; s < grid[t].length; s++) {
                total += grid[t][s].toString() + " ";
            }
            total += "\n";
        }
        return total;
    }

    public void generate() {
        resetSquares();
        grid[0][0].generate(0);
    }

    public void test() {
        for (Square[] s : grid) {
            for (Square t : s) {
                t.test();
            }
        }
    }

    public void saveToFile(String fileName) {
        FileWriter fw = null;
        try {
            fw = new FileWriter(fileName);
            fw.write(rows + " , " + cols + "\n" + this.toString());
        } catch (IOException ex) {
            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
        }finally{
            try {
                fw.flush();
            } catch (IOException ex) {
                Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public String fileOutput() {
        return rows + " , " + cols + "\n" + this.toString() + "\n" + grid[rows/2 + 3][cols/2 + 3].pathToString();
    }

    public void loadFromFile(String fileName) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String[] parts = in.readLine().split(",");
            setRows(Integer.parseInt(parts[0].trim()));
            setCols(Integer.parseInt(parts[1].trim()));
            resetSquares();
            for (int t = 0; t < grid.length; t++) {
                parts = in.readLine().split(" ");
                for (int s = 0; s < grid[t].length; s++) {
                    grid[t][s].setWalls(parts[s]);
                }
            }
            grid[rows/2 + 3][cols/2 + 3].setPath(0, in.readLine().trim());
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Maze.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public int solve() {
        return 0;
    }

    public void draw() {
        DrawingPanel pane = new DrawingPanel((getRows() + 2) * getSquareSize(), (getCols() + 2) * getSquareSize());
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j].draw(pane, (i + 1) * getSquareSize(), (j + 1) * getSquareSize(), getSquareSize(), getSquareSize());
            }
        }
        grid[rows/2 + 3][cols/2 + 3].drawPath(pane, rows/2*getSquareSize(), cols/2*getSquareSize(), getSquareSize(), getSquareSize());
    }
}
