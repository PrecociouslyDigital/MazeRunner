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
    public Square[][] _squares;

    public Maze() {
        this(20, 20);
    }

    public Maze(int x, int y) {
        setRows(x);
        setCols(y);
        resetSquares();
    }

    public void resetSquares() {
        _squares = new Square[rows][cols];
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                _squares[i][j] = new Square();
            }
        }
        for (int i = rows / 2 - 3; i < rows / 2 + (rows % 2 == 0 ? 3 : 4); i++) {
            for (int j = cols / 2 - 3; j < cols / 2 + (cols % 2 == 0 ? 3 : 4); j++) {
                _squares[i][j] = new GladeSquare();
            }
        }
        for (int i = 0; i < rows; i++) {
            _squares[i][0] = new EdgeSquare(i, 0);
            _squares[i][cols - 1] = new EdgeSquare(i, cols - 1);
        }
        for (int i = 1; i < cols - 1; i++) {
            _squares[0][i] = new EdgeSquare(0, i);
            _squares[rows - 1][i] = new EdgeSquare(rows - 1, i);
        }
        _squares[0][0].setNeighbors(null, _squares[1][0], null, _squares[0][1]);
        _squares[0][cols - 1].setNeighbors(_squares[0][cols - 2], _squares[1][cols - 1], null, null);
        _squares[rows - 1][cols - 1].setNeighbors(_squares[rows - 1][cols - 2], null, _squares[rows - 2][cols - 1], null);
        _squares[rows - 1][0].setNeighbors(null, null, _squares[rows - 2][0], _squares[rows - 1][1]);
        for (int i = 1; i < rows - 1; i++) {
            _squares[i][0].setNeighbors(_squares[i - 1][0], _squares[i][1], null, _squares[i + 1][0]);
            _squares[i][cols - 1].setNeighbors(_squares[i - 1][cols - 1], null, _squares[i][cols - 2], _squares[i + 1][cols - 1]);
        }
        for (int i = 1; i < cols - 1; i++) {
            _squares[0][i].setNeighbors(null, _squares[0][i + 1], _squares[0][i - 1], _squares[1][i]);
            _squares[rows - 1][i].setNeighbors(_squares[rows - 2][i], _squares[rows - 1][i + 1], _squares[rows - 1][i - 1], null);
        }
        for (int i = 1; i < rows - 1; i++) {
            for (int j = 1; j < cols - 1; j++) {
                _squares[i][j].setNeighbors(_squares[i][j - 1], _squares[i + 1][j], _squares[i - 1][j], _squares[i][j + 1]);
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
        return _squares[x][y];
    }
    public int getSquareX(int col){
        return col*getSquareSize();
    }
    public int getSquareY(int col){
        return col*getSquareSize();
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
        for (int t = 0; t < _squares.length; t++) {
            for (int s = 0; s < _squares[t].length; s++) {
                total += _squares[t][s].toString() + " ";
            }
            total += "\n";
        }
        return total;
    }

    public void generate() {
        resetSquares();
        _squares[0][0].generate(0);
    }

    public void test() {
        for (Square[] s : _squares) {
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
            System.out.println("Invalid File location!");
        }finally{
            try {
                fw.flush();
            } catch (IOException ex) {
                System.out.println("IO Error!");
            }
        }
    }

    public void loadFromFile(String fileName) {
        try {
            BufferedReader in = new BufferedReader(new FileReader(fileName));
            String[] parts = in.readLine().split(",");
            setRows(Integer.parseInt(parts[0].trim()));
            setCols(Integer.parseInt(parts[1].trim()));
            resetSquares();
            for (int t = 0; t < _squares.length; t++) {
                parts = in.readLine().split(" ");
                for (int s = 0; s < _squares[t].length; s++) {
                    _squares[t][s].setWalls(parts[s]);
                }
            }
            String s = in.readLine();
            if(s != null)
                _squares[rows/2 + 3][cols/2 + 3].setPath(0, s.trim());
        } catch (FileNotFoundException ex) {
            System.out.println("File does not exist!");
        } catch (IOException ex) {
            System.out.println("File does not exist!");
        }
    }

    public int solve() {
        return 0;
    }

    public void draw() {
        DrawingPanel pane = new DrawingPanel((getRows() + 2) * getSquareSize(), (getCols() + 2) * getSquareSize());
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                _squares[i][j].draw(pane, (i + 1) * getSquareSize(), (j + 1) * getSquareSize(), getSquareSize(), getSquareSize());
            }
        }
        System.nanoTime();
        _squares[rows/2 + 3][cols/2 + 3].drawPath(pane, (rows/2 + 3)*getSquareSize(), (cols/2+3)*getSquareSize(), getSquareSize(), getSquareSize());
    }
}
