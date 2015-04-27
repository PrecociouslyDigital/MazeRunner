package MazeRunner.core.obj;

import MazeRunner.DrawingPanel;

/**
 *
 * @author Benjamin
 */
public class Square {
    protected boolean [] _walls;
    protected Square [] _neighbors;
    protected Square _path;
    protected boolean generated = false;
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
    public int generate(int step){
        if(generated){
            if(step > 95){
                if(_walls[])
            }
        }
    }
    public void setPath(int step, String pathString){
        
    }
    public String toString(){
        
    }
    public String pathToString(){
        
    }
    public void draw(DrawingPanel panel, int originX, int originY, int width, int height){
        
    }
}
