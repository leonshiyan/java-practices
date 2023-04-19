package FamousFractals;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class InputCanvas extends JPanel implements MouseListener, MouseMotionListener {

    private Point[] points; // array to hold the points
    private int currentPoint; // index of the current point being dragged
    private DisplayCanvas display; // reference to the display canvas

    public InputCanvas(DisplayCanvas display) {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(500, 500));
        addMouseListener(this);
        addMouseMotionListener(this);
        this.display = display;
    }

    public void setPointCount(int n) {
        points = new Point[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point(50 + i * 100, 50 + (int)(Math.random() * 400));
        }
        repaint();
    }

    public void install(int[] coords) {
        points = new Point[coords.length / 2];
        for (int i = 0; i < points.length; i++) {
            points[i] = new Point(coords[2 * i], coords[2 * i + 1]);
        }
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (points != null) {
            g.setColor(Color.BLACK);
            for (int i = 0; i < points.length; i++) {
                g.fillOval(points[i].x - 5, points[i].y - 5, 10, 10);
            }
            g.setColor(Color.BLUE);
            for (int i = 0; i < points.length - 1; i++) {
                g.drawLine(points[i].x, points[i].y, points[i + 1].x, points[i + 1].y);
            }
        }
    }

    public void mousePressed(MouseEvent e) {
        currentPoint = -1;
        for (int i = 0; i < points.length; i++) {
            if (Math.abs(e.getX() - points[i].x) <= 5 && Math.abs(e.getY() - points[i].y) <= 5) {
                currentPoint = i;
                break;
            }
        }
    }

    public void mouseDragged(MouseEvent e) {
        if (currentPoint >= 0) {
            points[currentPoint].x = e.getX();
            points[currentPoint].y = e.getY();
            repaint();
            display.setPoints(points);
        }
    }

    public void mouseReleased(MouseEvent e) {
        currentPoint = -1;
    }

    public void mouseClicked(MouseEvent e) { }
    public void mouseEntered(MouseEvent e) { }
    public void mouseExited(MouseEvent e) { }
    public void mouseMoved(MouseEvent e) { }
}
