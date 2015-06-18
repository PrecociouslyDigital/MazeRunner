package MazeRunner.core.obj;

import MazeRunner.DrawingPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * @author Benjamin
 */
public class Square {

    protected boolean[] _walls;
    protected Square[] _neighbors;
    protected Square _path;
    protected boolean generated = false;
    protected int gScore;
    protected int[] loc;

    public Square() {
        _walls = new boolean[]{true, true, true, true};
    }

    public Square(Square... squares) {
        _walls = new boolean[]{true, true, true, true};
        setNeighbors(squares);
    }

    public void setWalls(String walls) {
        for (int i = 0; i < 4; i++) {
            _walls[i] = walls.charAt(i) != '.';
        }
    }

    public void setNeighbors(Square... squares) {
        if (squares.length == 4) {
            _neighbors = squares;
        }
    }

    public void setLoc(int x, int y) {
        loc = new int[]{x, y};
    }

    public int[] getLoc() {
        return loc;
    }

    public int generate(int step) {
        int base = (int) (Math.random() * 4);
        /*if(Math.random() > 0.01){
         return (int)Math.random() * 3 + 1;
         }*/
        generated = true;
        for (int i = 0; i < 4; i++) {
            int val = (base + i) % 4;
            if (_neighbors[val] == null) {
                continue;
            }
            if (_walls[val] && !_neighbors[val].generated) {
                _walls[val] = false;
                _neighbors[val]._walls[3 - val] = false;
                _neighbors[val]._path = this;
                /*int res =*/ _neighbors[val].generate(step + 1);
            }
        }
        return 0;
    }

    public void setPath(int step, String pathString) {
        _path = _neighbors["tlrb".indexOf(pathString.charAt(step))];
        _path.setPath(step + 1, pathString);
    }

    public String toString() {
        return (_walls[0] ? "t" : ".") + (_walls[1] ? "r" : ".") + (_walls[2] ? "l" : ".") + (_walls[3] ? "b" : ".");
    }

    public static int indexOfInArray(Square[] array, Square comparator) {
        for (int i = 0; i < array.length; i++) {
            if (comparator.equals(array[i])) {
                return i;
            }
        }
        return -1;
    }

    public void draw(DrawingPanel panel, int originX, int originY, int width, int height) {
        Graphics g = panel.getGraphics();
        //g.drawRect(originX, originY, width, height);
        g.setColor(Color.LIGHT_GRAY);
        if (_walls[1]) { //Right
            g.drawLine(originX + (width - 1), originY, originX + (width - 1), originY + (height - 1));
        }
        if (_walls[3]) { //Bottom
            g.drawLine(originX, originY + (height - 1), originX + (width - 1), originY + (height - 1));
        }
        g.setColor(Color.GRAY);
        if (_walls[0]) { //Top
            g.drawLine(originX, originY, originX + (width - 1), originY);
        }
        if (_walls[2]) { //Left
            g.drawLine(originX, originY, originX, originY + (height - 1));
        }
    }

    public void drawPath(DrawingPanel panel, int originX, int originY, int width, int height) {
        Graphics g = panel.getGraphics();
        //g.drawRect(originX, originY, width, height);
        g.setColor(Color.red);
        if (_walls[1]) { //Right
            g.drawLine(originX + (width/2), originY + height/2, originX + (3*width/2), originY + (height/2));
            _path.drawPath(panel, originX + width, originY, width, height);
        }
        if (_walls[3]) { //Bottom
            g.drawLine(originX + width/2, originY + (height/2), originX + (width/2), originY + (3*height/2));
            _path.drawPath(panel, originX, originY + height, width, height);
        }
        if (_walls[0]) { //Top
            g.drawLine(originX + width/2, originY + width/2 , originX + (width/2), originY - width/2);
            _path.drawPath(panel, originX, originY - height, width, height);
        }
        if (_walls[2]) { //Left
            g.drawLine(originX + width/2, originY + width/2, originX - width/2, originY + (height/2));
            _path.drawPath(panel, originX - width, originY, width, height);
        }
    }

    public String pathToString() {
        if (_path == _neighbors[0]) {
            return _path.pathToString() + "t";
        }
        if (_path == _neighbors[1]) {
            return _path.pathToString() + "l";
        }
        if (_path == _neighbors[2]) {
            return _path.pathToString() + "r";
        }
        if (_path == _neighbors[3]) {
            return _path.pathToString() + "b";
        }
        return "";
    }

    public void test() {
        generated = true;
        _walls = new boolean[]{false, false, false, false};
    }
}
