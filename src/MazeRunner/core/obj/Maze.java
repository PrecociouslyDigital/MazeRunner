/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MazeRunner.core.obj;

import java.io.Serializable;

/**
 *
 * @author s-yinb
 */
public class Maze implements Serializable{

    int rows, cols;

    public void setRows(int nRows) {
        rows = nRows;
    }

    public int getRows() {
        return rows;
    }

    public void setCols(int nCols) {
        cols = nCols;
    }

    public int getCols() {
        return  cols;
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
        return "";
    }

    public void generate() {
    }

    public void saveToFile(String fileName) {
    }

    public void loadFromFile(String fileName) {
    }

    public int solve() {
        return 0;
    }

    public void draw(){
    }
}
