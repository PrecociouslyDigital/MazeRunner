package MazeRunner.core.obj;

import MazeRunner.DrawingPanel;
import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author Benjamin
 */
public class GladeSquare extends Square{
    public void draw(DrawingPanel panel, int originX, int originY, int width, int height){
        Graphics2D g = panel.getGraphics();
        g.setColor(Color.green);
        g.fillRect(originX, originY, width, height);
    }
}
