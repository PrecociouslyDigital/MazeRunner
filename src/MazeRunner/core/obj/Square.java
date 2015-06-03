package MazeRunner.core.obj;

import MazeRunner.DrawingPanel;
import MazeRunner.core.generators.MazeGenerator;

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
    public Square(int x, int y){
        _walls = new boolean[]{true,true,true,true};
        setLoc(x,y);
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
                _walls[val] = _neighbors[val]._walls[3-val] = false;
                /*int res =*/ _neighbors[val].generate(step + 1);
            }
        }
        return 0;
    }
    public void setPath(int step, String pathString){
        gScore = step;
        _path = _neighbors["trlb".indexOf(pathString)];
    }
    public void setPath(int step, int index){
        gScore = step;
        _path = _neighbors[index];
    }
    public String toString(){
        return (_walls[0] ? "t" :".") + (_walls[1] ? "r" : ".") + (_walls[2] ? "l" : ".") + (_walls[3] ? "b" : "/");
    }
    public String pathToString(){
        for(int i = 0; i < 4; i++)
            if(_neighbors[i]==_path)
                return new String[]{"t","r","l","b"}[i];
        return ".";
    }
    public void draw(DrawingPanel panel, int originX, int originY, int width, int height){
        
    }
}
