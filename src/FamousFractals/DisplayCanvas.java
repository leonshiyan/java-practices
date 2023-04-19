package RecursiveWalks;

import java.awt.*;
import javax.swing.*;

public class DisplayCanvas extends JPanel {

    private Point[] points; // array to hold the points
    private int recursionLevel; // current recursion level
    public DisplayCanvas() {
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(500, 500));
        recursionLevel = 1;
    }

    public void setPoints(Point[] points) {
        this.points = points;
        repaint();
    }

    public void setRecursionLevel(int level) {
        recursionLevel = level;
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
            Point[] path = generatePath(points, recursionLevel);
            for (int i = 0; i < path.length - 1; i++) {
                g.drawLine(path[i].x, path[i].y, path[i + 1].x, path[i + 1].y);
            }
        }
    }

    private Point[] generatePath(Point[] points, int level) {
        if (level == 1) {
            return points;
        }
        else {
            Point[] newPath = new Point[(points.length - 1) * (int)Math.pow(points.length - 1, level - 2) + 1];
            newPath[0] = points[0];
            newPath[newPath.length - 1] = points[points.length - 1];
            int index = 1;
            for (int i = 0; i < points.length - 1; i++) {
                Point[] subPath = generatePath(getSubArray(points, i, i + 1), level - 1);
                for (int j = 1; j < subPath.length; j++) {
                    newPath[index++] = subPath[j];
                }
            }
            return newPath;
        }
    }

    private Point[] getSubArray(Point[] array, int start, int end) {
        Point[] subArray = new Point[end - start + 1];
        for (int i = start; i <= end; i++) {
            subArray[i - start] = array[i];
        }
        return subArray;
    }
}
