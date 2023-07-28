import java.awt.*;

public class TextBox {

    private int x, y;
    private Color fontColor;

    public TextBox(int x, int y, Color fontColor) {
        this.x = x;
        this.y = y;
        this.fontColor = fontColor;
    }

    public void draw(Graphics2D g2) {
        g2.setColor(fontColor);
        g2.setFont(new Font("Serif", Font.ITALIC, 20));
        String textBox = """
                Dremovs Game of Life Rules and Controls:
                 - Press delete or backspace to make this text go away.
                
                 - Each cell with one or no neighbors dies, as if by loneliness.
                 - Each cell with four or more neighbors dies, as if by overpopulation.
                 - Each cell with two or three neighbors survives.
                 - Each cell with three neighbors becomes populated.
                 - Use your cursor to draw cells.
                 - The space bar plays and pauses the game when needed.
                 - Pressing delete or backspace will clear your board.
                 - The slider on the top dictates the speed at which your cells will multiply.""";
        int lineHeight = g2.getFontMetrics().getHeight();
        for (String line : textBox.split("\n"))
            g2.drawString(line, x, y += lineHeight);
    }
}
