package MazeRunner.core;
import MazeRunner.core.obj.Maze;
import java.io.PrintWriter;
import java.util.*;
public class Program{
    private static Maze maze;
    public static void main(String[] args){
        System.exit(0);
        maze = new Maze();
        Scanner scn = new Scanner(System.in);
        String input = scn.nextLine();
        while(!input.equalsIgnoreCase("quit")){
            switch(input){
                case "generate":
                    maze.generate();
                case "solve":
                    maze.solve();
                case "load":
                    maze.loadFromFile("");
                case "save":
                    maze.saveToFile("");
                case "draw":
                    maze.draw();
            }
        }
    }
}