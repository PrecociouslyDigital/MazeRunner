package MazeRunner.core;
import MazeRunner.core.obj.Maze;
import java.io.PrintWriter;
import java.util.*;
public class Program{
    public static Maze maze;
    public static void main(String[] args){
        //System.exit(0);
        maze = new Maze();
        Scanner scn = new Scanner(System.in);
        String input = scn.nextLine();
        while(!input.equalsIgnoreCase("quit")){
            switch(input){
                case "generate":
                    maze.generate();
                    break;
                case "solve":
                    maze.solve();
                    break;
                case "load":
                    maze.loadFromFile("");
                    break;
                case "save":
                    maze.saveToFile("");
                    break;
                case "draw":
                    maze.draw();
                    break;
                case "test":
                    maze.generate();
                    maze.saveToFile("\\\\interlake-hs\\student\\s-yinb\\test.txt");
                    maze.loadFromFile("\\\\interlake-hs\\student\\s-yinb\\test.txt");
                    maze.draw();
                    break;
                case "print":
                    System.out.println(maze.toString());
                    break;
            }
            input = scn.nextLine();
        }
    }
}