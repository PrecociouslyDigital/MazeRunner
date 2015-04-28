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
    protected int gScore;
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
        if(_walls[step%4]){
                _walls[step%4] = false; 
                return 0;
        }
        if(_walls[(step + 1)%4]){
                _walls[(step + 1)%4] = false; 
                return 1;
        }
        return -1;
    }
    public void secondGenerate(int step){
        if(_walls[step%4]){
                _walls[step%4] = false; 
                return;
        }
        if(_walls[(step + 1)%4]){
                _walls[(step + 1)%4] = false; 
                return;
        }
        if(_walls[(step + 2)%4]){
                _walls[(step + 2)%4] = false; 
                return;
        }
    }
    public void setPath(int step, String pathString){
        
    }
    public void solve(int gScore){
        
    }
    public String toString(){
        return (_walls[0] ? "t" :".") + (_walls[1] ? "r" : ".") + (_walls[2] ? "l" : ".") + (_walls[3] ? "b" : "/");
    }
    public String pathToString(){
        
    }
    public void draw(DrawingPanel panel, int originX, int originY, int width, int height){
        
    }
}
