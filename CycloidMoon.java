import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.Random;
public class CycloidMoon extends JFrame implements ActionListener{
    public static Random rnd = new Random();
    public static int daysIn1Year=365;
    public static int[][] points = new int[2][daysIn1Year];
    public static int day = daysIn1Year;
    public static double fatnes = 1; // ????
    public static double cycloidRad = 1;
    public static double numCyloid = 12; // ????
    public static JSlider moonV = new JSlider(JSlider.HORIZONTAL, -40, 40,(int) fatnes*10);
    public static JSlider dateSlider = new JSlider(JSlider.HORIZONTAL,0, daysIn1Year, daysIn1Year);
    public static JSlider monRad = new JSlider(JSlider.HORIZONTAL, 0, 400,(int) cycloidRad*100);
    public static JSlider AmountSlider = new JSlider(JSlider.HORIZONTAL,0, 360,(int)numCyloid*10);
    //public static JSlider earthAroundSun = new JSlider(JSlider.HORIZONTAL,daysIn1Year, 1000,daysIn1Year);
    public static planet[]  planets = {new planet(daysIn1Year/2,1)};
    public static JSlider planetLine = new JSlider(JSlider.HORIZONTAL, 0, planets.length-1,0);
    public static CycloidMoon myframe = new CycloidMoon();
    public static int starAmount = 3000;
    public static int[][] stars = new int[2][starAmount];
    public static double nextDay = daysIn1Year+90;
    public static boolean moonOn = false;
    public static boolean line = true;
    public static int timesIntP = 0;
    public static boolean nextP = true;
    //public static JSlider[] speed = JSlider(JSlider.VERTICAL,0,30,10)[10];//[planets.length];
    public static int[][] drawStars(int amount,int heigth,int width)
    {
        int[][] stars = new int[2][amount];
        for (int i = 0; i < amount; i++) {
            stars[0][i] = rnd.nextInt(width);
            stars[1][i] = rnd.nextInt(heigth);
        }
        return stars;
    }
    public static int[] windowInSize = {600,700};

    public static void main(String args[])
    {
//		earthAroundSun.addChangeListener(new ChangeListener() {
//	         public void stateChanged(ChangeEvent ce) {
//	        	 dateSlider.setMaximum(earthAroundSun.getValue());
//	        	 dateSlider.setValue(dateSlider.getMaximum());
//	        	 myframe.repaint();
//	         }
//	      });
        stars = drawStars(starAmount,windowInSize[1]*4,windowInSize[0]*4);
        while(true)
        {
            int cntrX = Graphicss.cntrX;
            int cntrY = Graphicss.cntrY;
            //System.out.println(cntrX+"+"+cntrY);
            fatnes = moonV.getValue()/10.0;
            day = dateSlider.getValue();
            cycloidRad = monRad.getValue()/100.0;
            numCyloid = AmountSlider.getValue()/10.0;
            //daysIn1Year = earthAroundSun.getValue();
            points = getTrajectory(daysIn1Year, fatnes, cycloidRad, numCyloid, daysIn1Year/2 ,cntrX,cntrY);
            //System.out.println(moonV.getValue());
            nextDay =((daysIn1Year/360.0)*(planets[0].nextDay+180))%daysIn1Year;//(nextDay >= 0)? nextDay-(1.0/350)*myframe.moonstop.getValue()/10.0:daysIn1Year-1;
            myframe.repaint();
            if(nextP)
                for (planet p : planets) {
                    p.update(myframe.stop.getValue()/10.0);
                }
        }

    }
    Graphicss graphics = new Graphicss();
    public JButton moon = new JButton("moon");
    public JButton lbutton = new JButton("clossest planet");
    public JSlider stop = new JSlider(JSlider.HORIZONTAL, 0,60,10);
    public JSlider moonstop = new JSlider(JSlider.HORIZONTAL, 0,30,5);
    JTextField sizeOfTr;
    JTextField speed;
    JButton newPlan;
    public CycloidMoon()
    {
        sizeOfTr = new JTextField(10);
        sizeOfTr.setColumns(10);
        sizeOfTr.setText("enter rad");
        speed =  new JTextField(10);
        speed.setText("enter speed");
        newPlan = new JButton("create new planet");
        JPanel panel = new JPanel(new GridLayout(3, 0));
        JPanel panel1 = new JPanel(new GridLayout(0, 4));
        JPanel newPlanet = new JPanel(new GridLayout(3, 0));
        JPanel newPlanet1 = new JPanel(new GridLayout(7, 0));
        newPlanet1.setBackground(new Color(0, 0, 0, 255));
        panel.setBackground(new Color(0, 0, 0, 255));
        this.setTitle("Moon Simulation");
        this.setSize(900,1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        moonV.setPaintTicks(true);
        moonV.setMajorTickSpacing(10);
        moonV.setMinorTickSpacing(2);
        moon.addActionListener(this);
        lbutton.addActionListener(this);
        newPlan.addActionListener(this);
        newPlanet.add(sizeOfTr);
        newPlanet.add(speed);
        newPlanet.add(newPlan);
        //moon.setBounds(10,50,100,60);
        panel1.add(moon);
        panel1.add(stop);
        panel1.add(planetLine);
        panel1.add(lbutton);
        panel.add(panel1);
        planetLine.setMinorTickSpacing(1);
        if(moonOn)
        {
            panel.add(moonV);
            panel.add(monRad);
            panel.add(AmountSlider);
            panel.add(moonstop);
            //panel.add(dateSlider);
        }
        newPlanet1.add(newPlanet);
        //this.setLayout(null);
        this.add(panel,BorderLayout.SOUTH);
        this.add(newPlanet1,BorderLayout.EAST);
        this.add(graphics);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource() == moon)
        {
            moonOn = !moonOn;
            myframe.dispose();
            myframe = new CycloidMoon();
        }
        if(e.getSource() == lbutton)
        {
            line = !line;
        }
        if(e.getSource() == newPlan)
        {
            try {
                nextP = false;
                planet[] p = planets.clone();
                planets = new planet[planets.length+1];
                for (int i = 0; i < p.length; i++) {
                    planets[i] = p[i];
                }
                planets[planets.length-1] = new planet(Double.parseDouble(sizeOfTr.getText()),Double.parseDouble(speed.getText()) );
                planetLine.setMaximum(planets.length-1);
                nextP = true;
            } catch (Exception e1) {
                System.err.println("ups, this was not castable to double");
            }
        }
    }
    public static double[] returnPoint(double fatnes, double cycloidRad, double pointX, double numCyloid,double earthAroundRad)
    {
        double x1 = fatnes/numCyloid*Math.sin(numCyloid*pointX + Math.PI)+pointX; // x cycloid
        double y1 = cycloidRad/numCyloid*Math.cos(numCyloid*pointX + Math.PI) + 1; // Y Cycloid
        // moon circular
        double x2 = (earthAroundRad*Math.cos(x1-Math.PI))*y1; // circle pos X with cycloid
        double y2 = (earthAroundRad*Math.sin(x1-Math.PI))*y1; // circle pos Y with cycloid
        //x of first dot (Moon)
        double[] vector = {x2,y2};
        return vector;
    }
    public static int[][] getTrajectory(int daysAroundSun,double fatnes, double cycloidRad, double numCyloid,double earthAroundRad,int width,int height)
    {
        double difPoint =  Math.PI/(daysAroundSun/2);
        int[][] trajPoints = new int[2][daysAroundSun];  // 0 - all x points, 1 - all y points
        for(int i = 0; i < daysAroundSun; i ++) // calculating full trajectory
        {
            double[] curPoint = returnPoint(fatnes,cycloidRad,difPoint*i,numCyloid,earthAroundRad);
            trajPoints[0][i] = (int)curPoint[0]+width;
            trajPoints[1][i] = (int)curPoint[1]+height;
        }
        return trajPoints;
    }
}
class Graphicss extends JPanel{
    public static int cntrX = 500/2;
    public static int cntrY = 600/2;
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        this.setBackground(Color.BLACK);
        cntrX = getWidth()/2;//panel Width/2
        cntrY = getHeight()/2;	//panel Height/2
        int date = CycloidMoon.day;
        Graphics2D g2d = (Graphics2D) g;
        int[][] stars = CycloidMoon.stars;
        int moonS = 20;
        // drawing stars
        g2d.setColor( Color.darkGray );
        for (int i = 0; i < stars[0].length; i++) {
            g2d.fillOval(stars[0][i],stars[1][i], 5, 5);
        }
        //drawing sun
        int radius = 40;	//sun's radius
        g2d.setColor( Color.YELLOW ); //sun's first color
        g2d.fillOval( cntrX-radius, cntrY-radius, radius*2, radius*2 );
        g2d.setStroke(new BasicStroke(3));
        g2d.setColor( Color.orange ); //sun's second color
        g2d.drawOval( cntrX-radius, cntrY-radius, radius*2, radius*2 );
        g2d.setStroke(new BasicStroke(2));
        int firstP = CycloidMoon.planetLine.getValue();
        planet closestP = CycloidMoon.planets[firstP];
        double minD = Integer.MAX_VALUE;
        int planetsInL = 0;
        for (planet p : CycloidMoon.planets) {
            Color randomColor = new Color(p.r,p.g,p.b).brighter();
            //drawing earth orbit
            Stroke dashed = new BasicStroke(3, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
            g2d.setStroke(dashed);
            g2d.setColor(randomColor);
            g2d.drawArc((int)( cntrX-p.sizeTraj),(int) (cntrY-p.sizeTraj),(int) p.sizeTraj*2 ,(int) p.sizeTraj*2,0,(int)-p.nextDay%360);
            //g2d.drawOval((int)( cntrX-p.sizeTraj),(int) (cntrY-p.sizeTraj),(int) p.sizeTraj*2 ,(int) p.sizeTraj*2);
            //drawing planets
            g2d.setStroke(new BasicStroke(3));
            g2d.fillOval((int) (p.planetPos[0] + cntrX),(int) (p.planetPos[1] + cntrY),(int)  p.size*2,(int)  p.size*2);
            if(p != CycloidMoon.planets[firstP])
            {
                double dist = calculateDistanceBetweenPoints(CycloidMoon.planets[firstP].planetPos[0],CycloidMoon.planets[firstP].planetPos[1], p.planetPos[0], p.planetPos[1]);
                if(minD> dist)
                {
                    minD = dist;
                    closestP = p;
                }
            }
            if(p.nextDay%360.0 < 1)
            {
                planetsInL ++;
            }
        }
        if(planetsInL == CycloidMoon.planets.length)
        {
            CycloidMoon.timesIntP ++;
        }
        if(CycloidMoon.line)
        {
            g2d.setColor( Color.lightGray );
            g2d.drawLine((int) (CycloidMoon.planets[firstP].planetPos[0]+ cntrX + CycloidMoon.planets[firstP].size), (int) (CycloidMoon.planets[firstP].planetPos[1]+ cntrY+ CycloidMoon.planets[firstP].size),(int) (closestP.planetPos[0]+ cntrX +closestP.size),(int) (closestP.planetPos[1]+ cntrY+closestP.size));
            g2d.setColor( Color.darkGray );
            g2d.fillOval((int) (CycloidMoon.planets[firstP].planetPos[0]+ cntrX + CycloidMoon.planets[firstP].size-9), (int) (CycloidMoon.planets[firstP].planetPos[1]+ cntrY+ CycloidMoon.planets[firstP].size-9),18,18);
        }
        if(CycloidMoon.moonOn)
        {
            //drawing moon trajectory;
            g2d.setColor( Color.lightGray );
            g2d.setStroke(new BasicStroke(3, BasicStroke.CAP_SQUARE, BasicStroke.JOIN_ROUND));
            int[][] points = CycloidMoon.points;// getTrajectory(daysIn1Year, fatnes, cycloidRad, numCyloid, 180, cntrX, cntrY);
            g2d.drawPolyline(points[0], points[1], (int)CycloidMoon.nextDay);
            g2d.setColor( Color.DARK_GRAY );
            g2d.fillOval(points[0][(int)CycloidMoon.nextDay]-moonS/2,points[1][(int)CycloidMoon.nextDay]-moonS/2,moonS,moonS);
        }
        g2d.setColor( Color.DARK_GRAY );
        g2d.setFont(new Font("Comic Sans",Font.BOLD, 25));
        drawCenteredString(String.valueOf(CycloidMoon.timesIntP),cntrX*2, cntrY*2,g2d);
    }
    public void drawCenteredString(String s, int w, int h, Graphics g) {
        FontMetrics fm = g.getFontMetrics();
        int x = (w - fm.stringWidth(s)) / 2;
        int y = (fm.getAscent() + (h - (fm.getAscent() + fm.getDescent())) / 2);
        g.drawString(s, x, y);
    }
    public double calculateDistanceBetweenPoints(
            double x1,
            double y1,
            double x2,
            double y2) {
        return Math.sqrt((y2 - y1) * (y2 - y1) + (x2 - x1) * (x2 - x1));
    }
}
class planet{
    public double[] planetPos = new double[2];
    public static Random rnd = new Random();
    double nextDay = 0;
    public double size = rnd.nextInt(20)+10;
    public double sizeTraj;
    public double speed;
    float r = rnd.nextInt(255)/255f;
    float g = rnd.nextInt(255)/255f;
    float b = rnd.nextInt(255)/255f;
    public planet(double sizeTraj, double speed)
    {
        this.size = size;
        this.sizeTraj = sizeTraj;
        this.speed = speed;

    }
    public void update(double stop)
    {
        nextDay += speed * 1.0/CycloidMoon.daysIn1Year*stop;
        planetPos[0] = Math.cos(Math.toRadians(nextDay)) * sizeTraj - size;
        planetPos[1] = Math.sin(Math.toRadians(nextDay))* sizeTraj - size;
    }
}