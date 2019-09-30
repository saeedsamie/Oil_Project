import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

class PointGrapher extends JPanel {
    ArrayList<Dot> points;
    ArrayList<String> xLabels, yLabels;
    final int PAD;
    double xScale, yScale;
    NumberFormat nf;

    public PointGrapher(List input, String name, String Description) {
        points = new ArrayList<Dot>(input);

        JFrame f = new JFrame(name);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(this);
        if (name.equals("Pattern"))
            f.setSize(300, 300);
        else
            f.setSize(1500, 300);
        f.setVisible(true);
        JLabel label1 = new JLabel("Test");
        label1.setVisible(true);
        label1.setText(Description);
        add(label1);


//        populatePoints();
        xLabels = new ArrayList<>();
        yLabels = new ArrayList<>();
        PAD = 20;
        nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(1);
        setBackground(Color.white);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        double w = getWidth();
        double h = getHeight();
        double xInc = (w - 2 * PAD) / points.size();
        double yInc = (h - 2 * PAD) / points.size();
        double x1 = PAD, y1 = h - PAD;
        double[] ranges = computeScales(w, h);
        createLabels(ranges);
        // abcissa
        g2.draw(new Line2D.Double(PAD, h - PAD, w - PAD, h - PAD));

        for (int i = 0; i < (points.size()); i++) {
            if (i % 5 == 0) {
                g2.draw(new Line2D.Double(x1, y1 - 2, x1, y1 + 2));
                g2.drawString(xLabels.get(i), (float) (x1), (float) (y1 + 16));
            }
// else
//                g2.draw(new Line2D.Double(x1, y1, x1, y1 + 2));
            x1 += xInc;
        }
        // ordinate
        g2.draw(new Line2D.Double(PAD, PAD, PAD, h - PAD));
        x1 = PAD;
        y1 = PAD;
        for (int i = 0; i < maximumPointsArray(points); i++) {
            if (i % 5 == 0) {
                g2.draw(new Line2D.Double(x1 - 2, y1, x1 + 2, y1));
                g2.drawString(yLabels.get(i), (float) (x1 - 16), (float) (y1));
            }
// else
//                g2.draw(new Line2D.Double(x1, y1, x1 + 2, y1));
            y1 += yInc;
        }
        Random rand = new Random();
        float red = (float) (rand.nextFloat() / 2f + 0.25);
        float green = (float) (rand.nextFloat() / 2f + 0.25);
        float blue = (float) (rand.nextFloat() / 2f + 0.25);
        Color randomColor = new Color(red, green, blue);
        g2.setPaint(randomColor);

        // plot points
        Dot p1, p2;
        for (int i = 0; i < points.size(); i++) {
            p1 = (Dot) points.get(i);
            x1 = PAD + xScale * p1.x;
            y1 = h - PAD - yScale * p1.y;
            g2.fill(new Ellipse2D.Double(x1 - 1, y1 - 1, 5, 5));

            try {
                p2 = (Dot) points.get(i + 1);
                g2.draw(
                        new Line2D.Double
                                (PAD + xScale * p1.x, h - PAD - yScale * p1.y,
                                        PAD + xScale * p2.x, h - PAD - yScale * p2.y));
            } catch (Exception ignored) {
            }

        }
    }

    /**
     * Make up something plausable...
     */
//    private void populatePoints()
//    {
//        Random seed = new Random();
//        double x, y;
//        Dot p;
//        for(int i = -1000; i < 1000; i++)
//        {
////            x = seed.nextInt(200) - 100;
////            y = seed.nextInt(400) - 200;
//            x = i;
//            y =  Math.pow(i,2) ;
//            p = new Dot(x, y);
//            points.add(p);
//        }
//    }
    Double maximumPointsArray(ArrayList<Dot> points) {
        ArrayList<Double> tmp = new ArrayList<>();
        for (int i = 0; i < points.size(); i++) {
            tmp.add(points.get(i).y);
        }
        Collections.sort(tmp);
        return tmp.get(tmp.size() - 1);
    }

    private void createLabels(double[] xy) {
        for (int i = 0; i < points.size(); i++)
            xLabels.add(String.valueOf(i));

        for (int i = 0; i < maximumPointsArray(points) + 1; i++)
            yLabels.add(String.valueOf(i + 1));


    }

    private double[] computeScales(double w, double h) {
        double xMin, xMax, yMin, yMax;
        xMin = yMin = Double.MAX_VALUE;
        xMax = yMax = Double.MIN_VALUE;
        Dot p;
        for (int i = 0; i < points.size(); i++) {
            p = points.get(i);
            if (p.x < xMin) xMin = p.x;
            if (p.x > xMax) xMax = p.x;
            if (p.y < yMin) yMin = p.y;
            if (p.y > yMax) yMax = p.y;
        }
        // abcissa scale
        double xRange = Math.max(Math.abs(xMin), Math.abs(xMax));
        xScale = (w - PAD) / xRange;
        // ordinate scale
        double yRange = Math.max(Math.abs(yMin), Math.abs(yMax));
        yScale = (h * 3 / 4 - PAD) / yRange;
        return new double[]{xRange, yRange};
    }

}
