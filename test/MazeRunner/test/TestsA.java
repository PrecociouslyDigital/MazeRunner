package MazeRunner.test;
//Do not modify this file

import MazeRunner.DrawingPanel;
import MazeRunner.core.obj.EdgeSquare;
import MazeRunner.core.obj.GladeSquare;
import MazeRunner.core.obj.Maze;
import MazeRunner.core.obj.Square;
import static org.junit.Assert.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

import org.junit.Test;

public class TestsA {
    
    public static class Checkpoint1 {
        @Test
        public void testMaze_setget_RowsCols() throws Exception {
            Maze maze = new Maze();
            for (int i = 10; i <= 100; i++) {
                maze.setRows(i);
                assertEquals(String.format("maze.setRows(%d); maze.getRows()", i), i, maze.getRows());
                maze.setCols(i);
                assertEquals(String.format("maze.setCols(%d); maze.getCols()", i), i, maze.getCols());
            }
        }
        
        @Test
        public void testMaze_isGlade() throws Exception {
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
        public void testMaze_isEdge() throws Exception {
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
        public void testMaze_toString() throws Exception {
            Maze maze = new Maze();
            maze.toString();
        }
    
        @Test
        public void testMaze_generate() throws Exception {
            Maze maze = new Maze();
            try {
                Method method = maze.getClass().getMethod("generate");
                Type returnType = method.getGenericReturnType();
                assertEquals("class Maze: int generate() return type?", "int", returnType.toString());                
            }
            catch (NoSuchMethodException e) {
                assertTrue("class Maze: void generate() is not implemented!", false);
            } 
        }
        
        @Test
        public void testMaze_saveToFile() throws Exception {
            Maze maze = new Maze();
            try {
                Method method = maze.getClass().getMethod("saveToFile", String.class);
                Type returnType = method.getGenericReturnType();                
                assertEquals("class Maze: void saveToFile() return type?", "void", returnType.toString());
            }
            catch (NoSuchMethodException e) {
                    assertTrue("class Maze: void saveToFile(String) is not implemented!", false);
            }
        }
        
        @Test
        public void testMaze_draw() throws Exception {
            Maze maze = new Maze();
            try {
                Method method = maze.getClass().getMethod("draw");
                Type returnType = method.getGenericReturnType();
                assertEquals("class Maze: void draw() return type?", "void", returnType.toString());                
            }
            catch (NoSuchMethodException e) {
                assertTrue("class Maze: void draw() is not implemented!", false);
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
            
            public String wallsConfigString(int walls) {
                StringBuilder wallsString = new StringBuilder();
                for (int iWall = 0; iWall < 4; iWall++, walls <<= 1) {
                    if ((walls & 8) == 8) {
                        _walls[iWall] = true;
                        wallsString.append("t");
                    }
                    else {
                        _walls[iWall] = false;
                        wallsString.append("f");
                    }
                }
                return wallsString.toString();
            }
            
            public String pathConfigString(int pathIndex) {
                _path = _neighbors[pathIndex];
                return (pathIndex == 0) ? "t" 
                        : (pathIndex == 1) ? "r"
                        : (pathIndex == 2) ? "l"
                        : "b";
            }
        }
        
        @Test
        public void testSquare_ctor() throws Exception {
            SquareTest squareTest = new SquareTest();
            assertEquals("square._walls -> {true,true,true,true}", true, 
                    squareTest.checkWalls(true, true, true, true));
            assertEquals("square._neighbors -> {null, null, null, null}", true,
                    squareTest.checkNeighbors(null, null, null, null));
            assertEquals("square._path -> null", true,
                    squareTest.getPath() == null);
        }
        
        @Test
        public void testSquare_setNeighbors() throws Exception {
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
        public void testSquare_generate() {
            Square square = new Square();
            try {
                Method method = square.getClass().getMethod("generate", int.class);
                Type returnType = method.getGenericReturnType();
                assertEquals("class Square: int generate(int) return type?", "int", returnType.toString());  
            } catch (NoSuchMethodException e) {
                assertTrue("class Square: int generate(int) is not implemented!", false);
            }  
        }
        
        @Test
        public void testSquare_toString() {
            SquareTest squareTest = new SquareTest();
            assertEquals("new Square(); square.toString();",
                    "trlb", 
                    squareTest.toString());
            String [] toStringExpected = {
                    "....", "...b", "..l.", "..lb", 
                    ".r..", ".r.b", ".rl.", ".rlb",
                    "t...", "t..b", "t.l.", "t.lb",
                    "tr..", "tr.b", "trl.", "trlb"
            };
            for (int i = 0; i < 16; i++) {
                assertEquals(String.format("Square._walls[]:'%s';",squareTest.wallsConfigString(i)),
                        toStringExpected[i], squareTest.toString());
            }
        }

        @Test
        public void testSquare_pathToString() {
            Square nTop = new Square();
            Square nRight = new Square();
            Square nLeft = new Square();
            Square nBottom = new Square();
            SquareTest squareTest = new SquareTest();
            squareTest.setNeighbors(nTop, nRight, nLeft, nBottom);
            for (int i = 0; i < 4; i++) {
                String expectedPath = squareTest.pathConfigString(i);
                assertEquals(String.format("_path is \"%s\"; Square.pathToString() starts with \"%s\"?", 
                        expectedPath, expectedPath), true, squareTest.pathToString().startsWith(expectedPath));
            }
        }
        
        @Test
        public void testSquare_draw() {
            Square square = new Square();
            try {
                Method method = square.getClass().getMethod("draw", DrawingPanel.class, int.class, int.class, int.class, int.class);
                Type returnType = method.getGenericReturnType();
                assertEquals("class Square: void draw(DrawingPanel,int,int,int,int) return type?", "void", returnType.toString());  
            } catch (NoSuchMethodException e) {
                assertTrue("class Square: void draw(DrawingPanel,int,int,int,int) is not implemented!", false);
            }              
        }
        
        @Test
        public void testGladeSquare_generate() {
            GladeSquare gladeSquare = new GladeSquare();
            try {
                assertEquals("class GladeSquare extending Square?", "Square", gladeSquare.getClass().getSuperclass().getSimpleName());
                Method method = gladeSquare.getClass().getMethod("generate", int.class);
                Type returnType = method.getGenericReturnType();
                assertEquals("class GladeSquare: int generate(int) return type?", "int", returnType.toString());  
            } catch (NoSuchMethodException e) {
                assertTrue("class GladeSquare: int generate(int) is not implemented!", false);
            }             
        }
        
        @Test
        public void testGladeSquare_draw() {
            GladeSquare gladeSquare = new GladeSquare();
            try {
                assertEquals("class GladeSquare extending Square?", "Square", gladeSquare.getClass().getSuperclass().getSimpleName());
                Method method = gladeSquare.getClass().getMethod("draw", DrawingPanel.class, int.class, int.class, int.class, int.class);
                Type returnType = method.getGenericReturnType();
                assertEquals("class GladeSquare: void draw(DrawingPanel,int,int,int,int) return type?", "void", returnType.toString());  
            } catch (NoSuchMethodException e) {
                assertTrue("class GladeSquare: void draw(DrawingPanel,int,int,int,int) is not implemented!", false);
            }  
        }
        
        @Test
        public void testEdgeSquare_draw() {
            EdgeSquare edgeSquare = new EdgeSquare();
            try {
                assertEquals("class EdgeSquare extending Square?", "Square", edgeSquare.getClass().getSuperclass().getSimpleName());
                Method method = edgeSquare.getClass().getMethod("draw", DrawingPanel.class, int.class, int.class, int.class, int.class);
                Type returnType = method.getGenericReturnType();
                assertEquals("class EdgeSquare: void draw(DrawingPanel,int,int,int,int) return type?", "void", returnType.toString());  
            } catch (NoSuchMethodException e) {
                assertTrue("class EdgeSquare: void draw(DrawingPanel,int,int,int,int) is not implemented!", false);
            }  
        }          
    }

    public static class Checkpoint3 {
        
        class SquareTest extends Square {
            
            int _generateSteps;
            
            public SquareTest() {
                super();
                _generateSteps = -1;
            }
            public SquareTest(int generateSteps) {
                super();
                _generateSteps = generateSteps;
            }
            
            public String wallsConfigString(int walls) {
                StringBuilder wallsString = new StringBuilder();
                for (int iWall = 0; iWall < 4; iWall++, walls <<= 1) {
                    if ((walls & 8) == 8) {
                        _walls[iWall] = true;
                        wallsString.append("t");
                    }
                    else {
                        _walls[iWall] = false;
                        wallsString.append("f");
                    }
                }
                return wallsString.toString();
            }
            
            @Override
            public int generate(int step) {
                if (_generateSteps >= 0) {
                    return _generateSteps;
                }
                try {
                    return super.generate(step);
                }
                catch (Exception e) {
                    assertTrue(String.format("Square generate() threw exception '%s'", e.getMessage()), false);
                    return  -1;
                }
            }
        }        

        class MazeTest extends Maze {
            
            public void assertMazeResetOk() throws Exception {
                for (int r = 0; r < getRows(); r++) {
                    for (int c = 0; c < getCols(); c++) {
                        assertEquals(String.format("rows=%d, cols=%d; maze[%d][%d] is not null?", getRows(), getCols(), r,c), 
                                true, _squares[r][c] != null);
                        if (isGlade(r,c)) {
                            assertEquals(String.format("rows=%d, cols=%d; maze[%d][%d] is GladSquare?", getRows(), getCols(), r, c), 
                                    true, _squares[r][c] instanceof GladeSquare);
                        }
                        else if (isEdge(r,c)) {
                            assertEquals(String.format("rows=%d, cols=%d; maze[%d][%d] is EdgeSquare?", getRows(), getCols(), r, c), 
                                    true, _squares[r][c] instanceof EdgeSquare);
                        }
                        else {
                            assertEquals(String.format("rows=%d, cols=%d; maze[%d][%d] is pure Square?", getRows(), getCols(), r, c), 
                                    true, !(_squares[r][c] instanceof EdgeSquare) && !(_squares[r][c] instanceof GladeSquare));
                        }
                    }
                }
            }

            public void assertMazeGenerateOk() throws Exception {
                int steps = generate();
                assertTrue(String.format("rows=%d, cols=%d; longest path %d not in range [%d,%d]?", getRows(), getCols(), steps, Math.max(getRows(), getCols())/2, getRows()*getCols()),
                        steps >= Math.max(getRows(), getCols())/2 && steps <= getRows()*getCols());
                
                for (int r=0; r < getRows(); r++) {
                    for (int c=0; c < getCols(); c++) {
                        String squareString = _squares[r][c].toString();
                        assertEquals(String.format("rows=%d, cols=%d; maze[%d][%d].toString() \"%s\" has wrong length!", getRows(), getCols(), r, c, squareString),
                                4, squareString.length());
                        assertTrue(String.format("rows=%d, cols=%d; maze[%d][%d].toString() \"%s\" is walled in!", getRows(), getCols(), r, c, squareString),
                                squareString.contains("."));
                        if (isGlade(r,c)) {
                            if (isGlade(r-1,c)) {
                                assertTrue(String.format("rows=%d, cols=%d; maze[%d][%d].toString() \"%s\" is a wrongly walled GladeSquare!", getRows(), getCols(), r, c, squareString),
                                        squareString.charAt(0) == '.');
                            }
                            if (isGlade(r+1, c)) {
                                assertTrue(String.format("rows=%d, cols=%d; maze[%d][%d].toString() \"%s\" is a wrongly walled GladeSquare!", getRows(), getCols(), r, c, squareString),
                                        squareString.charAt(3) == '.');
                            }
                            if (isGlade(r, c+1)) {
                                assertTrue(String.format("rows=%d, cols=%d; maze[%d][%d].toString() \"%s\" is a wrongly walled GladeSquare!", getRows(), getCols(), r, c, squareString),
                                        squareString.charAt(1) == '.');
                            }
                            if (isGlade(r, c-1)) {
                                assertTrue(String.format("rows=%d, cols=%d; maze[%d][%d].toString() \"%s\" is a wrongly walled GladeSquare!", getRows(), getCols(), r, c, squareString),
                                        squareString.charAt(2) == '.');
                            }
                        }
                        if (isEdge(r,c)) {
                            if (r == 0) {
                                assertTrue(String.format("rows=%d, cols=%d; maze[%d][%d].toString() \"%s\" is a wrongly walled EdgeSquare!", getRows(), getCols(), r, c, squareString),
                                        squareString.charAt(0) == 't');
                            }
                            if (r == getRows()-1) {
                                assertTrue(String.format("rows=%d, cols=%d; maze[%d][%d].toString() \"%s\" is a wrongly walled EdgeSquare!", getRows(), getCols(), r, c, squareString),
                                        squareString.charAt(3) == 'b');
                            }
                            if (c == 0) {
                                assertTrue(String.format("rows=%d, cols=%d; maze[%d][%d].toString() \"%s\" is a wrongly walled EdgeSquare!", getRows(), getCols(), r, c, squareString),
                                        squareString.charAt(2) == 'l');
                            }
                            if (c == getCols()-1) {
                                assertTrue(String.format("rows=%d, cols=%d; maze[%d][%d].toString() \"%s\" is a wrongly walled EdgeSquare!", getRows(), getCols(), r, c, squareString),
                                        squareString.charAt(1) == 'r');
                            }
                        }
                    }
                }
            }

        }
        
        @Test
        public void testSquare_isGenerated() throws Exception {
            SquareTest squareTest = new SquareTest();
            for (int i = 0; i < 16; i++) {
                assertEquals(String.format("Square._walls[]:'%s'; Square.isGenerated()?",squareTest.wallsConfigString(i)),
                        i != 15, squareTest.isGenerated());
            }
        }
        
        @Test
        public void testSquare_generate() throws Exception {
            // Test is configuring a few square like below
            // then it's invoking generate() on the center square
            //    +--+          +--+
            //    |11|          |  |
            // +--+--+--+    +--+  +--+
            //  17|  |13| ->    |     |
            // +--+  +--+    +--+  +--+
            //    |19|          |  |
            //    +--+          +--+
            // top, right, left and bottom squares are configured to
            // return the values in the image when their generate()
            // method gets invoked.
            SquareTest nTop = new SquareTest(11);
            SquareTest nRight = new SquareTest(13);
            SquareTest nLeft = new SquareTest(17);
            nLeft.wallsConfigString(13);
            SquareTest nBottom = new SquareTest(19);
            nBottom.wallsConfigString(7);
            SquareTest square = new SquareTest();
            square.wallsConfigString(14);
            square.setNeighbors(nTop, nRight, nLeft, nBottom);
           
            assertEquals("Square.generate(): Maze is configured to return 13",
                    13, square.generate(0));
            assertEquals("Top neighbor Square.toString()?", "trl.", nTop.toString());
            assertEquals("Right neighbor Square.toString()?", "tr.b", nRight.toString());
            assertEquals("Left neighbor Square.toString()?", "tr.b", nLeft.toString());
            assertEquals("Bottom neighbor Square.toString()?", ".rlb", nBottom.toString());
            assertEquals("Square.toString()?","..l.", square.toString());
        }
        
        @Test
        public void testMaze_resetSquares() throws Exception {
            int [][] mazeSizes = {{55,10},{64,64},{85,87}};
            MazeTest mazeTest = new MazeTest();
            for (int i=0; i < mazeSizes.length; i++) {
                mazeTest.setRows(mazeSizes[i][0]);
                mazeTest.setCols(mazeSizes[i][1]);
                mazeTest.resetSquares();
                mazeTest.assertMazeResetOk();
            }
        }
        
        @Test
        public void testMaze_toString() throws Exception {
            int [][] mazeSizes = {{55,10},{64,64},{85,87}};
            Maze maze = new Maze();
            for (int i=0; i < mazeSizes.length; i++) {
                maze.setRows(mazeSizes[i][0]);
                maze.setCols(mazeSizes[i][1]);
                maze.resetSquares();
                String mazeString = maze.toString();
                Scanner mazeScanner = new Scanner(mazeString);
                for (int r=0; r < maze.getRows(); r++) {
                    assertTrue(String.format("Insufficient lines! Stopped at %d. Expected %d", r, maze.getRows()), mazeScanner.hasNextLine());
                    String [] mazeRowString = mazeScanner.nextLine().split(" ");
                    assertEquals("Incorrect words in line!", maze.getCols(), mazeRowString.length);
                    for(String mazeSquareString : mazeRowString) {
                        assertEquals("Incorrect square text!", "trlb", mazeSquareString);
                    }
                }
                assertTrue("Extra lines!", !mazeScanner.hasNextLine());
                mazeScanner.close();
            }
        }
            
        @Test
        public void testMaze_generate() throws Exception {
            int [][] mazeSizes = {{55,10},{64,64},{85,87}};
            MazeTest mazeTest = new MazeTest();
            for (int i=0; i < mazeSizes.length; i++) {
                mazeTest.setRows(mazeSizes[i][0]);
                mazeTest.setCols(mazeSizes[i][1]);
                mazeTest.assertMazeGenerateOk();
            }
        }
    }

    public static class Checkpoint4 {
        
        class EdgeSquareTest extends EdgeSquare {
            
            public DrawingPanel _drawingPanel = null;

            public EdgeSquareTest(EdgeSquare edgeSquare) {
                _walls = edgeSquare._walls;
                _neighbors = edgeSquare._neighbors;
                _path = edgeSquare._path;                
            }
            
            @Override
            public void draw(DrawingPanel panel, int originX, int originY, int width, int height) {
                _drawingPanel = panel;
                try {
                    super.draw(panel, originX, originY, width, height);
                }
                catch (Exception e) {
                    assertTrue(String.format("EdgeSquare draw() threw exception '%s'", e.getMessage()), false);
                }
            }        
        }
        
        class MazeTest extends Maze {
            
            EdgeSquareTest _edgeSquareTest = null;

            public void initialize(String mazeDataFile) throws Exception {
                DataInputStream mazeIn = new DataInputStream(new FileInputStream(new File(mazeDataFile)));
                setRows(mazeIn.readByte());
                setCols(mazeIn.readByte());
                resetSquares();
                for(int r=0; r < getRows(); r++) {
                    for (int c=0; c < getCols(); c++) {
                        int wallsByte = mazeIn.readByte();
                        for (int w=0; w < 4; w++, wallsByte>>=1) {
                            _squares[r][c]._walls[3-w] = ((wallsByte & 1) != 0);
                        }
                    }
                }
                try {
                    Square path = _squares[getRows()/2][getCols()/2];
                    while(path != null) {
                        int nextStep = mazeIn.readByte();
                        path._path = path._neighbors[nextStep];
                        path = path._path;
                    }
                }
                catch (Exception e) {
                }
                mazeIn.close();
                assertEquals(String.format("maze[%d][%d]; first square should be EdgeSquare:", getRows(), getCols()),
                        true, _squares[0][0] instanceof EdgeSquare);
                _edgeSquareTest = new EdgeSquareTest((EdgeSquare)_squares[0][0]);
                _squares[0][0] = _edgeSquareTest;
            }
            
            public DrawingPanel getDrawingPanel() {
                return _edgeSquareTest._drawingPanel;
            }

            public boolean isSolved(int row, int col) {
                boolean isSolved = (_squares[row][col]._path != null);
                for (int n = 0; n < 4 && !isSolved; n++) {
                    isSolved = (_squares[row][col]._neighbors[n] != null) && (_squares[row][col]._neighbors[n]._path == _squares[row][col]);
                }
                return isSolved;
            }
        
            public void assertGuiEquals(int row, int col, String message, Object expected, Object actual) {
                if (!expected.equals(actual)) {
                    System.out.printf("%s; Expected: <%s> Actual: <%s>\n", message, expected, actual);
                    Graphics graphics = getDrawingPanel().getGraphics();
                    graphics.setColor(Color.ORANGE);                    
                    graphics.drawOval(getSquareX(col) - getSquareSize()/2, getSquareY(row) - getSquareSize()/2, 2 * getSquareSize(), 2 * getSquareSize());
                    System.out.printf("Press <Enter> to continue.");
                    Scanner scIn = new Scanner(System.in);
                    scIn.nextLine();
                    scIn.close();
                    assertEquals(message, expected, actual);
                }
            }
            
            public void assertGuiTrue(int row, int col, String message, boolean condition) {
                if (!condition) {
                    System.out.printf("%s\n", message, condition);
                    Graphics graphics = getDrawingPanel().getGraphics();
                    graphics.setColor(Color.ORANGE);                    
                    graphics.drawOval(getSquareX(col) - getSquareSize()/2, getSquareY(row) - getSquareSize()/2, 2 * getSquareSize(), 2 * getSquareSize());
                    System.out.printf("Press <Enter> to continue.");
                    Scanner scIn = new Scanner(System.in);
                    scIn.nextLine();
                    scIn.close();
                    assertTrue(message, condition);
                }
            }
        }        
        
        @Test
        public void testMaze_getSquareSize() throws Exception {
            Maze maze = new Maze();
            int [] nSquares  = {10,11,14,21,34,35,36,37,59,60,64,65,86,87,91,98,99,100};
            int [] pxSize =    {66,61,50,34,22,21,21,20,13,12,12,11, 9, 8, 8, 8, 7,  7};
            for (int i = 0; i < nSquares.length; i++) {
                int nR = nSquares[i];
                for (int j = 0; j <= i; j++) {
                    int nC = nSquares[j];
                    maze.setRows(nR);
                    maze.setCols(nC);
                    maze.resetSquares();
                    assertEquals(String.format("maze[%d][%d]; getSquareSize()?",nR,nC), 
                            pxSize[i],
                            maze.getSquareSize());
                    maze.setRows(nC);
                    maze.setCols(nR);
                    maze.resetSquares();
                    assertEquals(String.format("maze[%d][%d]; getSquareSize()?",nC,nR), 
                            pxSize[i],
                            maze.getSquareSize());
                }
            }
        }
        
        @Test
        public void testMaze_getWidthHeight() throws Exception {
            Maze maze = new Maze();
            int [] nSquares  = { 10, 11, 14, 21, 34, 35, 36, 37, 59, 60, 64, 65, 86, 87, 91, 98, 99,100};
            int [] pxPanel =   {792,793,800,782,792,777,798,780,793,744,792,737,792,712,744,800,707,714};
            for (int i = 0; i < nSquares.length; i++) {
                int nR = nSquares[i];
                for (int j = 0; j <= i; j++) {
                    int nC = nSquares[j];
                    maze.setRows(nR);
                    maze.setCols(nC);
                    maze.resetSquares();
                    assertEquals(String.format("maze[%d][%d]; getHeight()?",nR,nC), 
                            pxPanel[i],
                            maze.getHeight());
                    assertEquals(String.format("maze[%d][%d]; getWidth()?",nR,nC), 
                            pxPanel[i]/(nR+2)*(nC+2),
                            maze.getWidth());
                    maze.setRows(nC);
                    maze.setCols(nR);
                    maze.resetSquares();
                    assertEquals(String.format("maze[%d][%d]; getHeight()?",nR,nC), 
                            pxPanel[i]/(nR+2)*(nC+2),
                            maze.getHeight());
                    assertEquals(String.format("maze[%d][%d]; getWidth()?",nR,nC), 
                            pxPanel[i],
                            maze.getWidth());                    
                }
            }
        }
        
        @Test
        public void testMaze_getSquareX() throws Exception {
            Maze maze = new Maze();
            int [] nSquares = {10,11,14,21,34,35,36,37,59,60,64,65,86,87,91,98,99,100};
            int [] pxSize =   {66,61,50,34,22,21,21,20,13,12,12,11, 9, 8, 8, 8, 7,  7};
            for (int r = 0; r < nSquares.length; r++) {
                for (int c = 0; c < nSquares.length; c++) {
                    int squareSize = Math.min(pxSize[r],pxSize[c]);
                    maze.setRows(nSquares[r]);
                    maze.setCols(nSquares[c]);
                    maze.resetSquares();
                    for (int i = 0; i < c; i++) {
                        assertEquals(String.format("maze[%d][%d]; getSquareX() for column %d?", r, c, i),
                            squareSize * (i+1),
                            maze.getSquareX(i));
                    }
                }
            }
        }
        
        @Test
        public void testMaze_getSquareY() throws Exception {
            Maze maze = new Maze();
            int [] nSquares = {10,11,14,21,34,35,36,37,59,60,64,65,86,87,91,98,99,100};
            int [] pxSize =   {66,61,50,34,22,21,21,20,13,12,12,11, 9, 8, 8, 8, 7,  7};
            for (int r = 0; r < nSquares.length; r++) {
                for (int c = 0; c < nSquares.length; c++) {
                    int squareSize = Math.min(pxSize[r],pxSize[c]);
                    maze.setRows(nSquares[r]);
                    maze.setCols(nSquares[c]);
                    maze.resetSquares();
                    for (int i = 0; i < r; i++) {
                        assertEquals(String.format("maze[%d][%d]; getSquareX() for column %d?", r, c, i),
                            squareSize * (i+1),
                            maze.getSquareY(i));
                    }
                }
            }
        }

        @Test
        public void testMaze_draw_WidthHeight() throws Exception {
            String [] mazeFileNames = {"Maze55x10.mzd","Maze64x64.mzd","Maze85x87.mzd"};
            int [][] mazeWidthHeight = {{168,798},{792,792},{712,696}};
            MazeTest mazeTest = new MazeTest();                
            for (int i=0; i < mazeFileNames.length; i++) {
                mazeTest.initialize(mazeFileNames[i]);
                mazeTest.draw();
                assertTrue(String.format("maze[%d][%d]; _squares[0][0].draw() not called or called with null panel", mazeTest.getRows(), mazeTest.getCols()),
                        mazeTest.getDrawingPanel() != null);
                BufferedImage image = mazeTest.getDrawingPanel().getImage();
                
                int width = image.getWidth();
                int height = image.getHeight();
                assertEquals(String.format("maze[%d][%d]; Panel width?", mazeTest.getRows(), mazeTest.getCols()),
                        mazeWidthHeight[i][0], width);
                assertEquals(String.format("maze[%d][%d]; Panel height?", mazeTest.getRows(), mazeTest.getCols()),
                        mazeWidthHeight[i][1], height);
            }            
        }

        @Test
        public void testMaze_draw_GladeColor() throws Exception {
            String [] mazeFileNames = {"Maze55x10.mzd","Maze64x64.mzd","Maze85x87.mzd"};
            int [][] mazeCenterXY = {{84,399},{396,396},{356,348}};
            MazeTest mazeTest = new MazeTest();                
            for (int i=0; i < mazeFileNames.length; i++) {
                mazeTest.initialize(mazeFileNames[i]);
                mazeTest.draw();
                BufferedImage image = mazeTest.getDrawingPanel().getImage();
                
                mazeTest.assertGuiEquals(
                        mazeTest.getRows()/2, mazeTest.getCols()/2,
                        String.format("maze[%d][%d]; Glade color?", mazeTest.getRows(), mazeTest.getCols()),
                        Color.GREEN, new Color(image.getRGB(mazeCenterXY[i][0], mazeCenterXY[i][1])));
            } 
        }

        @Test
        public void testMaze_draw_EdgeColor() throws Exception {
            String [] mazeFileNames = {"Maze55x10.mzd","Maze64x64.mzd","Maze85x87.mzd"};
            MazeTest mazeTest = new MazeTest();                
            for (int i=0; i < mazeFileNames.length; i++) {
                mazeTest.initialize(mazeFileNames[i]);
                mazeTest.draw();
                BufferedImage image = mazeTest.getDrawingPanel().getImage();
                int squareSize = mazeTest.getSquareSize();
                for (int c=0; c < mazeTest.getCols(); c++) {
                    int edgeCenterX = mazeTest.getSquareX(c) + squareSize / 2 + 1;
                    int edgeCenterY = mazeTest.getSquareY(0) + squareSize / 2 + 1;
                    mazeTest.assertGuiEquals(
                            0, c,
                            String.format("maze[%d][%d]; Edge[0][%d] color?", mazeTest.getRows(), mazeTest.getCols(), c),
                            Color.CYAN, new Color(image.getRGB(edgeCenterX, edgeCenterY)));
                    edgeCenterY = mazeTest.getSquareY(mazeTest.getRows()-1) + squareSize / 2;
                    mazeTest.assertGuiEquals(
                            mazeTest.getRows()-1, c,
                            String.format("maze[%d][%d]; Edge[%d][%d] color?", mazeTest.getRows(), mazeTest.getCols(), mazeTest.getRows()-1, c),
                            Color.CYAN, new Color(image.getRGB(edgeCenterX, edgeCenterY)));
                }
                for (int r=0; r < mazeTest.getRows(); r++) {
                    int edgeCenterX = mazeTest.getSquareX(0) + squareSize / 2 + 1;
                    int edgeCenterY = mazeTest.getSquareY(r) + squareSize / 2 + 1;
                    mazeTest.assertGuiEquals(
                            r, 0,
                            String.format("maze[%d][%d]; Edge[%d][0] color?", mazeTest.getRows(), mazeTest.getCols(), r),
                            Color.CYAN, new Color(image.getRGB(edgeCenterX, edgeCenterY)));
                    edgeCenterX = mazeTest.getSquareX(mazeTest.getCols()-1) + squareSize / 2;
                    mazeTest.assertGuiEquals(
                            r, mazeTest.getCols()-1,
                            String.format("maze[%d][%d]; Edge[%d][%d] color?", mazeTest.getRows(), mazeTest.getCols(), r, mazeTest.getCols()-1),
                            Color.CYAN, new Color(image.getRGB(edgeCenterX, edgeCenterY)));
                }                
            }            
        }
        
        @Test
        public void testMaze_draw_Walls() throws Exception {
            MazeTest mazeTest = new MazeTest();
            mazeTest.initialize("Maze85x87.mzd");
            mazeTest.draw();
            BufferedImage image = mazeTest.getDrawingPanel().getImage();
            int squareSize = mazeTest.getSquareSize();
            String mazeString = mazeTest.toString();
            Scanner mazeScanner = new Scanner(mazeString);
            for (int r=0; r < mazeTest.getRows(); r++) {
                String [] mazeRowString = mazeScanner.nextLine().split(" ");
                for(int c=0; c < mazeTest.getCols(); c++) {
                    Color background = mazeTest.isEdge(r, c) ? Color.CYAN : 
                                        mazeTest.isGlade(r, c) ? Color.GREEN : Color.WHITE;
                    
                    Color colorTop = (mazeRowString[c].charAt(0) == '.') ? background : 
                                        r == 0 ? Color.BLACK : Color.GRAY;
                    mazeTest.assertGuiEquals(
                            r, c,
                            String.format("maze[85][87]; square[%d][%d]=\"%s\"; Top wall?", r, c, mazeRowString[c]),
                            colorTop, new Color(image.getRGB(mazeTest.getSquareX(c)+squareSize/3, mazeTest.getSquareY(r))));
                    
                    Color colorRight = (mazeRowString[c].charAt(1) == '.') ? background : 
                                        c == mazeTest.getCols()-1 ? Color.BLACK : Color.LIGHT_GRAY;
                    mazeTest.assertGuiEquals(
                            r, c,
                            String.format("maze[85][87]; square[%d][%d]=\"%s\"; Right wall?", r, c, mazeRowString[c]),
                            colorRight, new Color(image.getRGB(mazeTest.getSquareX(c)+squareSize-1, mazeTest.getSquareY(r) + squareSize/3)));

                    Color colorLeft = (mazeRowString[c].charAt(2) == '.') ? background : 
                                        c == 0 ? Color.BLACK : Color.GRAY;
                    mazeTest.assertGuiEquals(
                            r, c,
                            String.format("maze[85][87]; square[%d][%d]=\"%s\"; Left wall?", r, c, mazeRowString[c]),
                            colorLeft, new Color(image.getRGB(mazeTest.getSquareX(c), mazeTest.getSquareY(r) + squareSize/3)));

                    Color colorBottom = (mazeRowString[c].charAt(3) == '.') ? background : 
                                        r == mazeTest.getRows()-1 ? Color.BLACK : Color.LIGHT_GRAY;
                    mazeTest.assertGuiEquals(
                            r, c,
                            String.format("maze[85][87]; square[%d][%d]=\"%s\"; Bottom wall?", r, c, mazeRowString[c]),
                            colorBottom, new Color(image.getRGB(mazeTest.getSquareX(c)+squareSize/3, mazeTest.getSquareY(r) + squareSize-1)));
                }
            }
            mazeScanner.close();
        }
        
        @Test
        public void testMaze_draw_path() throws Exception {
            MazeTest mazeTest = new MazeTest();
            mazeTest.initialize("Maze32x32.mzd");
            mazeTest.draw();
            BufferedImage image = mazeTest.getDrawingPanel().getImage();
            int squareSize = mazeTest.getSquareSize();
            for (int r = 0; r < mazeTest.getRows(); r++) {
                for (int c = 0; c < mazeTest.getCols(); c++) {
                    int centerX = mazeTest.getSquareX(c) + squareSize / 2;
                    int centerY = mazeTest.getSquareY(r) + squareSize / 2;
                    Color centerColor = new Color(image.getRGB(centerX, centerY));
                    if (mazeTest.isEdge(r, c)) {
                        if (mazeTest.isSolved(r,c)) {
                            mazeTest.assertGuiTrue(
                                    r, c,
                                    String.format("maze[32][32]; solved EdgeSquare[%d][%d] center pixel %s is not CYAN or RED!", r, c, centerColor),
                                    centerColor.getRGB() == Color.RED.getRGB() || centerColor.getRGB() == Color.CYAN.getRGB());
                        }
                        else {
                            mazeTest.assertGuiTrue(
                                    r, c,
                                    String.format("maze[32][32]; unsolved EdgeSquare[%d][%d] center pixel %s is not CYAN!", r, c, centerColor),
                                    centerColor.getRGB() == Color.CYAN.getRGB());
                        }
                    }
                    else if (mazeTest.isGlade(r, c)) {
                        if (mazeTest.isSolved(r,c)) {
                            mazeTest.assertGuiTrue(
                                    r, c,
                                    String.format("maze[32][32]; solved GladeSquare[%d][%d] center pixel %s is not GREEN or RED!", r, c, centerColor),
                                    centerColor.getRGB() == Color.RED.getRGB() || centerColor.getRGB() == Color.GREEN.getRGB());
                        }
                        else {
                            mazeTest.assertGuiTrue(
                                    r, c,
                                    String.format("maze[32][32]; unsolved GladeSquare[%d][%d] center pixel %s is not GREEN!", r, c, centerColor),
                                    centerColor.getRGB() == Color.GREEN.getRGB());
                        }
                    }
                    else {
                        if (mazeTest.isSolved(r,c)) {
                            mazeTest.assertGuiTrue(
                                    r, c,
                                    String.format("maze[32][32]; solved Square[%d][%d] center pixel %s is not RED!", r, c, centerColor),
                                    centerColor.getRGB() == Color.RED.getRGB());
                        }
                        else {
                            mazeTest.assertGuiTrue(
                                    r, c,
                                    String.format("maze[32][32]; unsolved Square[%d][%d] center pixel %s is not WHITE!", r, c, centerColor),
                                    centerColor.getRGB() == Color.WHITE.getRGB());
                        }
                    }
                }
            }
        }
    }

    public static class Checkpoint5 {
        
        class EdgeSquareTest extends EdgeSquare {
            
            public DrawingPanel _drawingPanel = null;

            public EdgeSquareTest(EdgeSquare edgeSquare) {
                _walls = edgeSquare._walls;
                _neighbors = edgeSquare._neighbors;
                _path = edgeSquare._path;                
            }
            
            @Override
            public void draw(DrawingPanel panel, int originX, int originY, int width, int height) {
                _drawingPanel = panel;
                try {
                    super.draw(panel, originX, originY, width, height);
                }
                catch (Exception e) {
                    assertFalse(String.format("EdgeSquare draw() threw exception '%s'", e.getMessage()), false);
                }
            }        
        }
        
        class MazeTest extends Maze {
            
            EdgeSquareTest _edgeSquareTest = null;

            public void initialize(String mazeDataFile) throws Exception {
                DataInputStream mazeIn = new DataInputStream(new FileInputStream(new File(mazeDataFile)));
                setRows(mazeIn.readByte());
                setCols(mazeIn.readByte());
                resetSquares();
                for(int r=0; r < getRows(); r++) {
                    for (int c=0; c < getCols(); c++) {
                        char[] wallsChars={'t','r', 'l', 'b'};
                        int wallsByte = mazeIn.readByte();
                        for (int w=0; w < 4; w++, wallsByte>>=1) {
                            if ((wallsByte & 1)==0) {
                                wallsChars[3-w] = '.';
                            }
                        }
                        _squares[r][c].setWalls(new String(wallsChars));
                    }
                }
                try {
                    Square path = _squares[getRows()/2][getCols()/2];
                    while(path != null) {
                        int nextStep = mazeIn.readByte();
                        path._path = path._neighbors[nextStep];
                        path = path._path;
                    }
                }
                catch (Exception e) {
                }                
                mazeIn.close();
                _edgeSquareTest = new EdgeSquareTest((EdgeSquare)_squares[0][0]);
                _squares[0][0] = _edgeSquareTest;                
            }
            
            public DrawingPanel getDrawingPanel() {
                return _edgeSquareTest._drawingPanel;
            }
            
            public void assertGuiTrue(int row, int col, String message, boolean condition) {
                if (!condition) {
                    System.out.printf("%s\n", message, condition);
                    Graphics graphics = getDrawingPanel().getGraphics();
                    graphics.setColor(Color.ORANGE);                    
                    graphics.drawRoundRect(getSquareX(col)-2, getSquareY(row)-2, getSquareSize() + 4, getSquareSize() + 4, 4, 4);
                    System.out.printf("Press <Enter> to continue.");
                    Scanner scIn = new Scanner(System.in);
                    scIn.nextLine();
                    scIn.close();
                    assertTrue(message, condition);
                }
            }

            public void checkPath(String pathExpectedString) throws Exception {
                int r = getRows()/2;
                int c = getCols()/2;
                Square pathSquare = _squares[r][c];
                String pathActualString = pathSquare.pathToString();
                assertEquals(String.format("maze[%d][%d]; _squares[%d][%d].pathToString() length?", getRows(), getCols(), r, c),
                        pathExpectedString.length(), pathActualString.length());
                for (int i = 0; i < pathActualString.length(); i++) {
                    char actualChar = pathActualString.charAt(i);
                    char expectedChar = pathExpectedString.charAt(i);
                    
                    int n = "trlb".indexOf(actualChar);
                    if (n == -1) {
                        assertTrue(String.format("maze[%d][%d]; path diff @%d: Invalid character '%c'", 
                                getRows(), getCols(), i, actualChar), false);
                    }
                    if (expectedChar != actualChar) {
                        super.draw();
                        assertGuiTrue(r, c, String.format("maze[%d][%d]; path diff @%d: expected '%c', actual '%c'!",
                                getRows(), getCols(), i, expectedChar, actualChar), false);
                    }
                    r = (n == 3) ? r + 1 : (n == 0) ? r - 1 : r;
                    c = (n == 1) ? c + 1 : (n == 2) ? c - 1 : c;
                }
            }

            public void checkFile(String mazeExpectedFileName) throws Exception {
                Scanner scExpected = new Scanner(new File(mazeExpectedFileName));
                Scanner scActual = new Scanner(new File("_" + mazeExpectedFileName));
                String expectedLine = scExpected.nextLine();
                String actualLine = scActual.nextLine();
                assertEquals(String.format("maze[%d][%d]; first line: 'row#,col#'?", getRows(), getCols()), expectedLine, actualLine);
                for (int r=0; r < getRows(); r++) {
                    String [] expectedSquares = scExpected.nextLine().split(" ");
                    String [] actualSquares = scActual.nextLine().split(" ");
                    assertEquals(String.format("maze[%d][%d]; number of squares on row %d?", getRows(), getCols(), r), 
                            expectedSquares.length, actualSquares.length);
                    for (int c=0; c < getCols(); c++) {
                        assertEquals(String.format("maze[%d][%d]; _squares[%d][%d]?", getRows(), getCols(), r, c), 
                                expectedSquares[c], actualSquares[c]);
                    }
                }
                if (scExpected.hasNextLine()) {
                    assertEquals(String.format("maze[%d][%d]; has path line?", getRows(), getCols()), true, scActual.hasNextLine());
                    expectedLine = scExpected.nextLine();
                    actualLine = scActual.nextLine();
                    assertEquals(String.format("maze[%d][%d]; length of path line?", getRows(), getCols()), expectedLine.length(), actualLine.length());
                    for (int p=0; p < expectedLine.length(); p++) {
                        assertEquals(String.format("maze[%d][%d]; _path[%d]?", getRows(), getCols(), p), 
                                expectedLine.charAt(p), actualLine.charAt(p));
                    }
                }
                assertEquals(String.format("maze[%d][%d]; extra lines in the maze file?", getRows(), getCols()), false, scExpected.hasNextLine());
                scActual.close();
                scExpected.close();
            }            
        }           
        
        @Test
        public void testMaze_pathToString() throws Exception {
            String [] mazeFileNames = {"Maze55x10.mzd","Maze64x64.mzd","Maze85x87.mzd"};
            String [] mazePaths = {
                    "rrtltrtlttlttrrtlllbltlttrbrtrtlltllbbbbl",
                    "bltttlltrrrtrtrttlbltttrtrrbbrbrbrrrtrbrtrbrttrrtrrbrtrbbbbbbblttttltlbbrbbblbrblltllttlbblbrblbltlblbbrbrttrrtrrtrrbbblbrrbrttltrrbbrrrtrrtlttrtrbbbbbrtrrttrbbrrtlttttrr",
                    "brrtrbbbllllltlblllblbbbrbllttttllllltrttrbbrtttrbrbrtrrtltlttlttrtrrrrrtrbrrtrtrrttttrtlltlbbrblltllblblblbbltttlltllbrbrblltlbllbrrbbblltllltrtrtttttttrbbbrbrttrrrtltrrtltrttrrrrtlltttrrttrtltrrtttllblbbbbblltrttttllltltrtrbbrtrbrtrrrrbbbbbrrblbrbbbbbbbbrbrtrbrbrrbllbllbbbrbrtrrbrtrrtlttrbrtttrrrrtltllttrbrtrtllltttrttlblttrtrrtrttrrbbrrbrtrrrblbbrbblbrrbblbltllbllttltrrttrtrtlllltllbbrrbbltllbbrrbbbrbrrrrbbbbbblllbrrblbbrblbrrrttrrbblbbrblbltlblllllbbrbrrrbrbbrrrbrbrrtrrttrbbrrbllbbrrrbrrrrrtrrbbbr"                    
            };
            
            MazeTest mazeTest = new MazeTest();                
            for (int i=0; i < mazeFileNames.length; i++) {
                mazeTest.initialize(mazeFileNames[i]);
                mazeTest.checkPath(mazePaths[i]);
            }
        }
        
        @Test
        public void testMaze_saveToFile() throws Exception {
            String [] mazeFileNames = {"Maze55x10.mzd","Maze64x64.mzd","Maze85x87.mzd"};
            MazeTest mazeTest = new MazeTest();                
            for (int i=0; i < mazeFileNames.length; i++) {
                mazeTest.initialize(mazeFileNames[i]);
                mazeTest.saveToFile("_" + mazeFileNames[i].replace("mzd", "mzr"));
                mazeTest.checkFile(mazeFileNames[i].replace("mzd", "mzr"));
            }
        }
    }
}
