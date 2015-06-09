package MazeRunner.core.obj;

import MazeRunner.DrawingPanel;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 * @author Benjamin
 */
public class Square {
    protected boolean [] _walls;
    protected Square [] _neighbors;
    protected Square _path;
    protected Square _parent;
    protected boolean generated = false;
    protected int gScore;
    protected int[] loc;
    public Square(){
        _walls = new boolean[]{true,true,true,true};
    }
    public Square(Square... squares){
        _walls = new boolean[]{true,true,true,true};
        setNeighbors(squares);
    }
    public void setWalls(String walls){
        for(int i = 0; i < 4; i++){
            _walls[i] = walls.charAt(i) != '.';
        }
    }
    public void setNeighbors(Square... squares){
        if(squares.length == 4){
            _neighbors = squares;
        }
    }
    public void setLoc(int x, int y){
        loc = new int[]{x,y};
    }
    public int[] getLoc(){
        return loc;
    }
    public int generate(int step){
        int base = (int) (Math.random() * 4);
        /*if(Math.random() > 0.01){
            return (int)Math.random() * 3 + 1;
        }*/
        generated = true;
        for(int i = 0; i < 4; i++){
            int val = (base + i) % 4;
            if(_neighbors[val] == null)
                continue;
            if(_walls[val] && !_neighbors[val].generated){
                _walls[val] = false;
                _neighbors[val]._walls[3-val] = false;
                _neighbors[val]._path = this;
                /*int res =*/ _neighbors[val].generate(step + 1);
            }
        }
        return 0;
    }
    public void setPath(int step, String pathString){
        _path = _neighbors["tlrb".indexOf(pathString.charAt(step))];
        _path.setPath(step+1, pathString);
    }
    public String toString(){
        return (_walls[0] ? "t" :".") + (_walls[1] ? "r" : ".") + (_walls[2] ? "l" : ".") + (_walls[3] ? "b" : ".");
    }
    public String pathToString(){
        return "tlrb".charAt(indexOfInArray(_neighbors, _path)) + _path.pathToString();
    }
    public static int indexOfInArray(Square[] array, Square comparator){
        for(int i = 0; i < array.length; i++)
            if(comparator.equals(array[i])) return i;
        return -1;
    }
    public void draw(DrawingPanel panel, int originX, int originY, int width, int height){
        Graphics2D g = panel.getGraphics();
        g.setColor(_walls[0] ? Color.gray :Color.white);
        g.drawLine(originX, originY, originX+width, originY);
        g.setColor(_walls[1] ? Color.gray :Color.white);
        g.drawLine(originX, originY+height, originX, originY);
        g.setColor(_walls[2] ? Color.gray :Color.white);
        g.drawLine(originX + width, originY+height, originX+width, originY + height);
        g.setColor(_walls[3] ? Color.gray :Color.white);
        g.drawLine(originX + width, originY+height, originX + width, originY);
    }
}