package MazeRunner.test;
//Do not modify this file

import MazeRunner.DrawingPanel;
import static org.junit.Assert.*;
import java.io.FileNotFoundException;
import org.junit.Test;
import MazeRunner.core.obj.*;

public class Tests {
    
    public static class Checkpoint1
    {
        @Test
        public void testSetGetRowsCols() throws Exception {
            Maze maze = new Maze();
            for (int i = 10; i <= 100; i++) {
                maze.setRows(i);
                assertEquals(String.format("maze.setRows(%d); maze.getRows()", i), i, maze.getRows());
                maze.setCols(i);
                assertEquals(String.format("maze.setCols(%d); maze.getCols()", i), i, maze.getCols());
            }
        }
        
        @Test
        public void testIsGlade() throws Exception {
            Maze maze = new Maze();
            int [][] nGladeTest = {{10,2,7},{51,22,28},{100, 47, 52}};
            for (int i = 0; i < nGladeTest.length; i++) {
                int nR = nGladeTest[i][0];
                int grStart = nGladeTest[i][1];
                int grEnd = nGladeTest[i][2];
                for (int j = i; j < nGladeTest.length; j++) {
                    int nC = nGladeTest[j][0];
                    int gcStart = nGladeTest[j][1];
                    int gcEnd = nGladeTest[j][2];
                    maze.setRows(nR);
                    maze.setCols(nC);
                    for (int r = 0; r < nR; r++) {
                        for (int c = 0; c < nC; c++) {
                            assertEquals(String.format("maze[%d][%d]; maze.isGlade(%d,%d)",nR,nC,r,c),
                                    r>=grStart && r<=grEnd && c>=gcStart && c<=gcEnd, maze.isGlade(r,c));
                        }
                    }
                }
            }
        }
        
        @Test
        public void testIsEdge() throws Exception {
            Maze maze = new Maze();
            int [] nEdgeTest = {10,13,42,63,84,100};
            for (int i = 0; i < nEdgeTest.length; i++) {
                int nR = nEdgeTest[i];
                for (int j = i; j < nEdgeTest.length; j++) {
                    int nC = nEdgeTest[j];
                    maze.setRows(nR);
                    maze.setCols(nC);
                    for (int r = 0; r < nR; r++) {
                        for (int c = 0; c < nC; c++) {
                            assertEquals(String.format("maze[%d][%d]; maze.isEdge(%d,%d)",nR,nC,r,c),
                                    r==0 || r==nR-1 || c==0 || c==nC-1, maze.isEdge(r,c));
                        }
                    }
                }
            }
        }

        @Test
        public void testToString() throws Exception {
            Maze maze = new Maze();
            maze.toString();
        }
    
        @Test
        public void testGenerate() throws Exception {
            Maze maze = new Maze();
            try {
                maze.setCols(10);
                maze.setRows(10);
                maze.generate();
            } catch (Exception e) {
                assertEquals("maze.toString() 'Not yet implemented' ?-> " + e.getMessage(), true, 
                        e.getMessage().toLowerCase().matches(".*not.*implemented.*"));
            }
        }
        
        @Test
        public void testSaveToFile() throws Exception {
            Maze maze = new Maze();
            try {
                maze.setCols(10);
                maze.setRows(10);
                maze.generate();
                maze.saveToFile("DummyFile.mzr");
            } catch (Exception e) {
                assertEquals("maze.toString() 'Not yet implemented' ?-> " + e.getMessage(), true, 
                        e.getMessage().toLowerCase().matches(".*not.*implemented.*"));
            }
        }

        @Test
        public void testLoadFromFile() throws Exception {
            Maze maze = new Maze();
            try {
                maze.setCols(10);
                maze.setRows(10);
                maze.generate();
                maze.loadFromFile("DummyFile.mzr");
            } catch (Exception e) {
                assertEquals("maze.toString() 'Not yet implemented' ?-> " + e.getMessage(), true, 
                        e.getMessage().toLowerCase().matches(".*not.*implemented.*") || e instanceof FileNotFoundException);
            }
        }
        
        @Test
        public void testSolve() throws Exception {
            Maze maze = new Maze();
            try {
                maze.setCols(10);
                maze.setRows(10);
                maze.generate();
                maze.solve();
            } catch (Exception e) {
                assertEquals("maze.toString() 'Not yet implemented' ?-> " + e.getMessage(), true, 
                        e.getMessage().toLowerCase().matches(".*not.*implemented.*"));
            }            
        }
        
        @Test
        public void testDraw() throws Exception {
            Maze maze = new Maze();
            try {
                maze.setCols(10);
                maze.setRows(10);
                maze.generate();
                maze.draw();
            } catch (Exception e) {
                assertEquals("maze.toString() 'Not yet implemented' ?-> " + e.getMessage(), true, 
                        e.getMessage().toLowerCase().matches(".*not.*implemented.*"));
            }             
        }
    }

    public static class Checkpoint2 {
    	
        class SquareTest extends Square {
        	public boolean checkWalls(boolean... walls) {
        		boolean result = true;
        		for (int iWall = 0; result && iWall < 4; iWall++) {
        			result = result && (_walls[iWall] == walls[iWall]);
        		}
        		return result;
        	}
        	public boolean checkNeighbors(Square... neighbors) {
        		boolean result = true;
        		for (int iDirection = 0; result && iDirection < 4; iDirection++) {
        			result = result && (_neighbors[iDirection] == neighbors[iDirection]);
        		}
        		return result;
        	}
        	public Square getPath() {
        		return _path;
        	}
        }
        
    	@Test
        public void testSquareWalls() {
    		SquareTest square = new SquareTest();
    		square.setWalls("trlb");
    		assertEquals("square.setWalls(\"trlb\") -> {true,true,true,true}", true, square.checkWalls(true, true, true, true));
    		assertEquals("square.setWalls(\"trlb\"); square.toString() -> \"trlb\"", true,
    				square.toString().equals("trlb"));
    		square.setWalls("....");
    		assertEquals("square.setWalls(\"....\") -> {false,false,false,false}", true, square.checkWalls(false, false, false, false));
    		assertEquals("square.setWalls(\"....\"); square.toString() -> \"....\"", true,
    				square.toString().equals("...."));
    		square.setWalls("t...");
    		assertEquals("square.setWalls(\"t...\") -> {true,false,false,false}", true, square.checkWalls(true, false, false, false));
    		assertEquals("square.setWalls(\"t...\"); square.toString() -> \"t...\"", true,
    				square.toString().equals("t..."));
    		square.setWalls("...b");
    		assertEquals("square.setWalls(\"...b\") -> {false,false,false,true}", true, square.checkWalls(false, false, false, true));
    		assertEquals("square.setWalls(\"...b\"); square.toString() -> \"...b\"", true,
    				square.toString().equals("...b"));
    		square.setWalls(".rl.");
    		assertEquals("square.setWalls(\".rl.\") -> {false,true,true,false}", true, square.checkWalls(false, true, true, false));
    		assertEquals("square.setWalls(\".rl.\"); square.toString() -> \".rl.\"", true,
    				square.toString().equals(".rl."));
    	}
    	
    	@Test
    	public void testSquareNeighbors() {
    		Square nTop = new Square();
    		Square nRight = new Square();
    		Square nLeft = new Square();
    		Square nBottom = new Square();
    		SquareTest square = new SquareTest();
    		square.setNeighbors(nTop, nRight, nLeft, nBottom);
    		assertEquals("square.setNeighbors(nTop, nRight, nLeft, nBottom)", true, 
    				square.checkNeighbors(nTop, nRight, nLeft, nBottom));
    		square.setNeighbors(null, nRight, null, nBottom);
    		assertEquals("square.setNeighbors(null, nRight, null, nBottom)", true, 
    				square.checkNeighbors(null, nRight, null, nBottom));
    		square.setNeighbors(nTop, null, nLeft, null);
    		assertEquals("square.setNeighbors(nTop, null, nLeft, null)", true, 
    				square.checkNeighbors(nTop, null, nLeft, null));
    	}
    	
    	@Test
    	public void testSquarePath() {
    		Square nTop = new Square();
    		Square nRight = new Square();
    		Square nLeft = new Square();
    		Square nBottom = new Square();
    		SquareTest square = new SquareTest();
    		square.setNeighbors(nTop, nRight, nLeft, nBottom);
    		square.setPath(0, "t");
    		assertEquals("square.setPath(\"t\") ?-> nTop", nTop, square.getPath());
    		assertEquals("square.setPath(\"t\");square.pathToString() ?-> \"t\"", true,
    				square.pathToString().equals("t"));
    		square.setPath(0, "r");
    		assertEquals("square.setPath(\"r\") ?-> nRight", nRight, square.getPath());
    		assertEquals("square.setPath(\"r\");square.pathToString() ?-> \"r\"", true,
    				square.pathToString().equals("r"));
    		square.setPath(0, "l");
    		assertEquals("square.setPath(\"l\") ?-> nLeft", nLeft, square.getPath());
    		assertEquals("square.setPath(\"l\");square.pathToString() ?-> \"l\"", true,
    				square.pathToString().equals("l"));
    		square.setPath(0, "b");
    		assertEquals("square.setPath(\"b\") ?-> nBottom", nBottom, square.getPath());
    		assertEquals("square.setPath(\"b\");square.pathToString() ?-> \"b\"", true,
    				square.pathToString().equals("b"));
    	}
    	
    	@Test
    	public void testSquareGenerate() {
            SquareTest square = new SquareTest();
            try {
                int steps = square.generate(0);
                assertEquals("square.generate(0)", 0, steps);
            } catch (Exception e) {
                assertEquals("square.generate() 'Not yet implemented' ?-> " + e.getMessage(), true, 
                        e.getMessage().toLowerCase().matches(".*not.*implemented.*"));
            }
    	}
    	
    	@Test
    	public void testSquareSolve() {
            SquareTest square = new SquareTest();
            try {
                int steps = square.solve(0);
                assertEquals("square.solve(0)", -1, steps);
            } catch (Exception e) {
                assertEquals("square.solve() 'Not yet implemented' ?-> " + e.getMessage(), true, 
                        e.getMessage().toLowerCase().matches(".*not.*implemented.*"));
            }
    	}
    	
    	@Test
    	public void testSquareDraw() {
            SquareTest square = new SquareTest();
            try {
            	DrawingPanel drawingPanel = new DrawingPanel(792,660); 
                square.draw(drawingPanel, 0, 0, 44, 44);
            } catch (Exception e) {
                assertEquals("square.draw() 'Not yet implemented' ?-> " + e.getMessage(), true, 
                        e.getMessage().toLowerCase().matches(".*not.*implemented.*"));
            }
    	}
    	
    	@Test
    	public void testGladeSquareGenerate() {
            GladeSquare gladeSquare = new GladeSquare();
            try {
                int steps = gladeSquare.generate(0);
                assertEquals("gladeSquare.generate(0)", 0, steps);
            } catch (Exception e) {
                assertEquals("gladeSquare.generate() 'Not yet implemented' ?-> " + e.getMessage(), true, 
                        e.getMessage().toLowerCase().matches(".*not.*implemented.*"));
            }
    	}
    	
    	@Test
    	public void testGladeSquareSolve() {
            GladeSquare gladeSquare = new GladeSquare();
            try {
                int steps = gladeSquare.solve(0);
                assertEquals("gladeSquare.solve(0)", -1, steps);
            } catch (Exception e) {
                assertEquals("gladeSquare.solve() 'Not yet implemented' ?-> " + e.getMessage(), true, 
                        e.getMessage().toLowerCase().matches(".*not.*implemented.*"));
            }
    	}
    	
    	@Test
    	public void testGladeSquareDraw() {
            GladeSquare gladeSquare = new GladeSquare();
            try {
            	DrawingPanel drawingPanel = new DrawingPanel(792,660); 
                gladeSquare.draw(drawingPanel, 0, 0, 44, 44);
            } catch (Exception e) {
                assertEquals("gladeSquare.draw() 'Not yet implemented' ?-> " + e.getMessage(), true, 
                        e.getMessage().toLowerCase().matches(".*not.*implemented.*"));
            }
    	}
    	
    	@Test
    	public void testEdgeSquareSolve() {
            EdgeSquare edgeSquare = new EdgeSquare();
            try {
                int steps = edgeSquare.solve(0);
                assertTrue("edgeSquare.solve(0)", 0 == steps || -1 == steps);
            } catch (Exception e) {
                assertEquals("edgeSquare.solve() 'Not yet implemented' ?-> " + e.getMessage(), true, 
                        e.getMessage().toLowerCase().matches(".*not.*implemented.*"));
            }
    	}
    	
    	@Test
    	public void testEdgeSquareDraw() {
            EdgeSquare edgeSquare = new EdgeSquare();
            try {
            	DrawingPanel drawingPanel = new DrawingPanel(792,660); 
                edgeSquare.draw(drawingPanel, 0, 0, 44, 44);
            } catch (Exception e) {
                assertEquals("edgeSquare.draw() 'Not yet implemented' ?-> " + e.getMessage(), true, 
                        e.getMessage().toLowerCase().matches(".*not.*implemented.*"));
            }
    	}      	
	}
}

