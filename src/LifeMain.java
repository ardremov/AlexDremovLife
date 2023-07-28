import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.io.File;

public class LifeMain extends JPanel{

    private Timer timer;
    private Board board;
    private int rows, cols, squareSize;
    private JSlider slider;
    private final Color backgroundColor = Color.BLACK;


    public LifeMain(int w, int h){
        setSize(w, h);
        rows = 256;
        cols = 160;
        squareSize = 10;

        board = new Board(rows, cols, squareSize, true); 

        int delay = 100;
        timer = new Timer(delay, e -> update());

        setupListeners(); // setting up listeners multiple times is necessary?

        slider = new JSlider(JSlider.HORIZONTAL, 0, 200, 100);
        setLayout(new BorderLayout());
        slider.setBackground(backgroundColor);
        slider.setFocusable(false);
        slider.setPaintTicks(true);
        slider.setMajorTickSpacing(1);
        setupListeners();
        slider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent ce) {
                int delay = slider.getMaximum() - slider.getValue();
                timer.setDelay(delay);
                repaint();
            }
        });
        add(slider, BorderLayout.NORTH);
        setupListeners();
    }

    public void update(){
        board.nextGen();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        setBackground(backgroundColor);
        board.draw(g2);
        board.getNeighborCount(3, 5);
    }

    public static void main(String[] args) {
        JFrame window = new JFrame("Dremovs Game of Life");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        int width = 2560;
        int height = 1600;
        window.setBounds(0, 0, width, height + 28);

        LifeMain panel = new LifeMain(width, height);
        panel.setFocusable(true);
        panel.grabFocus();

        window.add(panel);
        window.setVisible(true);
        window.setResizable(true);
    }

    public void setupListeners() {
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                int row = y / board.getSquareSize();
                int col = x / board.getSquareSize();

                board.toggle(row, col);

                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();

                int row = y / board.getSquareSize();
                int col = x / board.getSquareSize();

                if (e.getButton() == MouseEvent.BUTTON1)
                    board.setColor(row, col, 1);
                else
                    board.setColor(row, col, 0);

                repaint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) { // pause
                    if (timer.isRunning())
                        timer.stop();
                    else {
                        timer.start();
                    }
                }
                if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE) { // clear board
                    board = new Board(rows, cols, squareSize, false);
                    repaint();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
    }
}