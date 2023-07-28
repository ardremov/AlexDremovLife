import java.awt.*;

public class Board {

    private int[][] grid;
    private int squareSize;
    private boolean text;
    private TextBox textBox;

    public Board(int rows, int cols, int squareSize, boolean text){
        // save squareSize to the instance field
        this.squareSize = squareSize;
        this.text = text;

        grid = new int[rows][cols];
    }

    public void draw(Graphics2D g2){
        // go to each spot in board
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (grid[r][c] == 1) {
                    Color squareColor = new Color(73, 0, (int)(Math.random() * 100 + 125)); // 73, 0, 150
                    g2.setColor(squareColor);
                    g2.fillRect(c * squareSize, r * squareSize, squareSize, squareSize);
                }
            }
        }
        // fill a rect for each 1
        if (text) {
            textBox = new TextBox(50, 100, Color.WHITE);
            textBox.draw(g2);
        }
    }

    public int getNeighborCount(int r, int c) {
        int count = 0;
        for (int i = r - 1; i <= r + 1; i++) {
            for (int j = c - 1; j <= c + 1; j++) {
                if (i == r && j == c)
                    continue;
                if (isLegalIndex(i, j) && grid[i][j] == 1)
                    count++;
            }
        }
        return count;
    }


    // returns true if [r][c] is in the bounds of grid.
    public boolean isLegalIndex(int r, int c){
        return (r > -1 && c > -1 && r < grid.length && c < grid[0].length);
    }

    public int getSquareSize(){
        return squareSize;
    }

    public void setColor(int r, int c, int toColor) {
        grid[r][c] = toColor;
    }

    public void toggle(int r, int c){
        grid[r][c] = 1;
    }

    public void nextGen(){
        int[][] next = new int[grid.length][grid[0].length];
        for (int r = 0; r < grid.length; r++) {
            for (int c = 0; c < grid[r].length; c++) {
                if (getNeighborCount(r, c) == 3 && grid[r][c] == 0) {
                    next[r][c] = 1;
                } else if (getNeighborCount(r, c) > 3 && grid[r][c] == 1 || getNeighborCount(r, c) < 2 && grid[r][c] == 1) {
                    next[r][c] = 0;
                } else if (grid[r][c] == 1 && 1 < getNeighborCount(r,c) || grid[r][c] == 1 && getNeighborCount(r,c) < 4) {
                    next[r][c] = 1;
                }
            }
        }
       grid = next;
    }
}