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
    @Override
    public String toString(){
        return "....";
    }
    public GladeSquare(){
        _walls  = new boolean[]{false, false, false, false};
    }
    @Override
    public int generate(int step){
        super.generate(step);
        _walls  = new boolean[]{false, false, false, false};
        for(int i = 0; i  < 4; i++)
            _neighbors[i]._walls[3-i] = false;
        return 0;
    }
}
