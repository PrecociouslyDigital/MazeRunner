package MazeRunner.core.obj;

import MazeRunner.DrawingPanel;
import java.awt.Color;
import java.awt.Graphics2D;
import MazeRunner.core.Program;

/**
 *
 * @author Benjamin
 */
public class EdgeSquare extends Square{
    public EdgeSquare(int x, int y){
        super();
        setLoc(x,y);
    }
    public void draw(DrawingPanel panel, int originX, int originY, int width, int height){
        Graphics2D g = panel.getGraphics();
        g.setColor(Color.cyan);
        g.fillRect(originX, originY, width, height);
        //super.draw(panel,originX,originY,width,height);
        g.setColor(Color.black);
        if(this.loc[1] == 0)
            g.drawLine(originX, originY, originX + width, originY);
        else if(Program.maze.rows == this.loc[1]+1)
            g.drawLine(originX, originY+ height, originX + width, originY + height);
        if(this.loc[0] == 0)
            g.drawLine(originX, originY, originX, originY + height);
        else if(Program.maze.rows == this.loc[0]+1)
            g.drawLine(originX + width, originY , originX + width, originY + height);
    }
    @Override
    public String drawPath(DrawingPanel panel, int originX, int originY, int width, int height){
        return "";
    }
}