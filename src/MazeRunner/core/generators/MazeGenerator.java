package MazeRunner.core.generators;

import MazeRunner.core.obj.Maze;
import MazeRunner.core.obj.Square;
import java.util.HashSet;
import java.util.Random;

/**
 *
 * @author Benjamin
 */
public class MazeGenerator {
    private static HashSet<Square> connected = new HashSet<>();
    private static HashSet<Square> searched = new HashSet<>();
    private static Random rand = new Random();
    private static Maze GenMaze;
    public static void generate(Maze maze){
        GenMaze = maze;
        connected.add(maze.getSquare(0,0));
        while(!(connected.size() + searched.size()) < ){
            pickRandom(connected, rand.nextInt()).generate(rand.nextInt(100));
        }
    }
    public static void add(Square s){
        connected.add(s);
    }
    public static Square pickRandom(HashSet<Square> set, int rand){
        rand %= set.size();
        int i = 0;
        for(Square obj : set){
            if (i == rand)
                return obj;
            i = i + 1;
        }
        return null;
    }
    public static void reset(){
        HashSet<Square> connected = new HashSet<>();
        HashSet<Square> searched = new HashSet<>();
        Random rand = new Random();
        GenMaze = null;
    }
}
