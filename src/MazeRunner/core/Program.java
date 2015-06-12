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
            if(input.equalsIgnoreCase("draw"))
                maze.draw();
            else if(input.toLowerCase().startsWith("load"))
                maze.loadFromFile(input.substring(4).trim());
            else if(input.toLowerCase().startsWith("save"))
                maze.saveToFile(input.substring(4).trim());
            else if(input.toLowerCase().startsWith("generate")){
                maze = new Maze(Integer.parseInt(input.split(",")[0].trim()),Integer.parseInt(input.split(",")[1].trim()));
                maze.generate();
            }
            input = scn.nextLine();
        }
    }
}