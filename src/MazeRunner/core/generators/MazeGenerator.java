package MazeRunner.core.generators;

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
    public static void generate(){
        while(!connected.isEmpty()){
            
        }
    }
    public static void add(Square s){
        connected.add(s);
    }
}
