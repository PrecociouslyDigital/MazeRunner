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
    public int fScore(){
        
    }
    public int generate(int step){
        if(_walls[step%4]){
                _walls[step%4] = false;
                MazeGenerator.add(_neighbors[step%4]);
                return 0;
        }
        if(_walls[(step + 1)%4]){
                _walls[(step + 1)%4] = false; 
                MazeGenerator.add(_neighbors[(step + 1)%4]);
                return 1;
        }
        return -1;
    }
    public void secondGenerate(int step){
        if(_walls[step%4]){
                _walls[step%4] = false; 
                MazeGenerator.add(_neighbors[step%4]);
                return;
        }
        if(_walls[(step + 1)%4]){
                _walls[(step + 1)%4] = false; 
                MazeGenerator.add(_neighbors[(step + 1)%4]);
                return;
        }
        if(_walls[(step + 2)%4]){
                _walls[(step + 2)%4] = false; 
                MazeGenerator.add(_neighbors[(step + 1)%4]);
                return;
        }
    }
    public void setPath(int step, String pathString){
        gScore = step;
        _path = _neighbors["trlb".indexOf(pathString)];
    }
    public void setPath(int step, int index){
        gScore = step;
        _path = _neighbors[index];
    }
    public int solve(int step){
        for(Square s: _neighbors){
            s.checkBetter(this);
        }
        return 0;
    }
    public boolean checkBetter(Square s){
        if(_path == null){
            _path = s;
            return true;
        }else if(s.fScore() < _path.fScore()){
            _path = s;
            return true;
        }
        return false;
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
