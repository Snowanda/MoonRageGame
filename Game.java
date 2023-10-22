import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Iterator;
import java.lang.Math;

public class Game extends JFrame{
    private Container cp;
    private DrawPan pan = new DrawPan(); // customed for rows and column lines
    private JPanel right = new JPanel();
    private JPanel topR = new JPanel();
    private JPanel midR = new JPanel();
    private JPanel botR = new JPanel();
    private JLabel earth = new JLabel(new ImageIcon("Earth2D.png"));
    private JLabel moon = new JLabel(new ImageIcon("Moon2D.png"));
    private JLabel earthDed = new JLabel(new ImageIcon(new ImageIcon("earthPoof.png").getImage().getScaledInstance(600, 600, Image.SCALE_DEFAULT)));
    private JLabel earthRemain = new JLabel("Earth remains: 100%", SwingConstants.CENTER);
    private JLabel kills = new JLabel("Asteroids killed: 0", SwingConstants.CENTER);
    private JLabel score = new JLabel("Score: 0", SwingConstants.CENTER);
    private JLabel highest = new JLabel("Highest score: 0", SwingConstants.CENTER);
    private JLabel[] blank = new JLabel[7];
    private JButton mu = new JButton("▲");
    private JButton ml = new JButton("◀");
    private JButton mr = new JButton("▶");
    private JButton md = new JButton("▼");
    private JButton restart = new JButton("Restart");
    private JButton exit = new JButton("Exit");
    private Timer t1, t2, t3, t4;
    private int incre = 4, tmp;
    private int astKills = 0, earthHP = 100;
    private int earthMoveX = 8, earthMoveY = 8;
    private int typeRate = 4;
    private int curScore = 0, hiScore = 0;
    private boolean direc; // true: horizontal
    private ArrayList<Asteroid> asteroids = new ArrayList<>();
    private Random seed = new Random();

    public Game(){
        init();
    }

    private void init(){
        for(int i = 0; i < 7; i++){
            blank[i] = new JLabel("");
        }
        for(int i = 0; i < 16; i++){
            pan.addLine(i * 40, 0, i * 40, 600);
        }
        for(int i = 0; i < 16; i++){
            pan.addLine(0, i * 40, 600, i * 40);
        }
        cp = this.getContentPane();
        cp.setLayout(new BorderLayout());
        this.setBounds(100, 100, 800, 628);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setTitle("Moon Rage");
        pan.setBackground(new Color(0, 0, 0));
        pan.setLayout(null);

        earth.setOpaque(false);
        earth.setBounds(264, 280, 72, 40);
        pan.add(earth);
        moon.setOpaque(false);
        moon.setBounds(319, 280, 42, 40);
        pan.add(moon);
        earthDed.setBounds(0, 0, 600, 600);
        cp.add(pan, BorderLayout.WEST);

        right.setLayout(new GridLayout(3, 1));
        topR.setLayout(new GridLayout(3, 1));
        midR.setLayout(new GridLayout(3, 3));
        botR.setLayout(new GridLayout(3, 1));
        
        topR.add(earthRemain);
        topR.add(kills);
        topR.add(score);
        right.add(topR);

        midR.add(blank[0]);
        mu.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                mu.setEnabled(false);
                ml.setEnabled(false);
                mr.setEnabled(false);
                md.setEnabled(false);
                restart.setEnabled(false);
                incre = 4;
                tmp = incre;
                direc = false;
                t1.start();
                t2.start();
                int spawnRate = 4;
                if(astKills < 13){
                    spawnRate = 8 - astKills / 3;
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(seed.nextInt(14) + 1, 0, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(16, seed.nextInt(14) + 1, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(seed.nextInt(14) + 1, 16, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(0, seed.nextInt(14) + 1, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                for(Asteroid rock: asteroids){
                    rock.findDest(8, 7, 1);
                }
                earthMoveX = 8;
                earthMoveY = 7;
            }
        });
        mu.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("UP"), "upping");
        mu.getActionMap().put("upping", new Clicked());
        midR.add(mu);
        midR.add(blank[1]);
        ml.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                mu.setEnabled(false);
                ml.setEnabled(false);
                mr.setEnabled(false);
                md.setEnabled(false);
                restart.setEnabled(false);
                incre = 4;
                tmp = incre;
                direc = true;
                t1.start();
                t2.start();
                int spawnRate = 4;
                if(astKills < 13){
                    spawnRate = 8 - astKills / 3;
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(seed.nextInt(14) + 1, 0, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(16, seed.nextInt(14) + 1, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(seed.nextInt(14) + 1, 16, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(0, seed.nextInt(14) + 1, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                for(Asteroid rock: asteroids){
                    rock.findDest(7, 8, 1);
                }
                earthMoveX = 7;
                earthMoveY = 8;
            }
        });
        ml.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("LEFT"), "lefting");
        ml.getActionMap().put("lefting", new Clicked());
        midR.add(ml);
        midR.add(blank[2]);
        mr.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                mu.setEnabled(false);
                ml.setEnabled(false);
                mr.setEnabled(false);
                md.setEnabled(false);
                restart.setEnabled(false);
                incre = -4;
                tmp = incre;
                direc = true;
                t1.start();
                t2.start();
                int spawnRate = 4;
                if(astKills < 13){
                    spawnRate = 8 - astKills / 3;
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(seed.nextInt(14) + 1, 0, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(16, seed.nextInt(14) + 1, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(seed.nextInt(14) + 1, 16, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(0, seed.nextInt(14) + 1, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                for(Asteroid rock: asteroids){
                    rock.findDest(9, 8, 1);
                }
                earthMoveX = 9;
                earthMoveY = 8;
            }
        });
        mr.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("RIGHT"), "righting");
        mr.getActionMap().put("righting", new Clicked());
        midR.add(mr);
        midR.add(blank[3]);
        md.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                mu.setEnabled(false);
                ml.setEnabled(false);
                mr.setEnabled(false);
                md.setEnabled(false);
                restart.setEnabled(false);
                incre = -4;
                tmp = incre;
                direc = false;
                t1.start();
                t2.start();
                int spawnRate = 4;
                if(astKills < 13){
                    spawnRate = 8 - astKills / 3;
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(seed.nextInt(14) + 1, 0, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(16, seed.nextInt(14) + 1, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(seed.nextInt(14) + 1, 16, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                if(seed.nextInt(spawnRate) == 0){
                    Asteroid newRock = new Asteroid(0, seed.nextInt(14) + 1, seed.nextInt(typeRate) / 3);
                    asteroids.add(newRock);
                    pan.add(newRock);
                }
                for(Asteroid rock: asteroids){
                    rock.findDest(8, 9, 1);
                }
                earthMoveX = 8;
                earthMoveY = 9;
            }
        });
        md.getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("DOWN"), "downing");
        md.getActionMap().put("downing", new Clicked());
        midR.add(md);
        midR.add(blank[4]);
        right.add(midR);

        botR.add(highest);
        restart.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                Iterator<Asteroid> i = asteroids.iterator();
                while(i.hasNext()){
                    Asteroid rock = i.next();
                    pan.remove(rock);
                    i.remove();
                }
                astKills = 0;
                kills.setText("Asteroids killed: 0");
                incre = 4;
                earthHP = 100;
                earthRemain.setText("Earth remains: 100%");
                earthMoveX = 8;
                earthMoveY = 8;
                if(curScore > hiScore){
                    hiScore = curScore;
                    highest.setText("Highest score: " + Integer.toString(hiScore));
                }
                curScore = 0;
                score.setText("Score: 0");
                mu.setEnabled(true);
                ml.setEnabled(true);
                mr.setEnabled(true);
                md.setEnabled(true);
                // pan.clearLines();
                for(int j = 0; j < 16; j++){
                    pan.addLine(j * 40, 0, j * 40, 600);
                }
                for(int j = 0; j < 16; j++){
                    pan.addLine(0, j * 40, 600, j * 40);
                }
                moon.setLocation(319, 280);
            }
        });
        botR.add(restart);
        exit.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                System.exit(0);
            }
        });
        botR.add(exit);
        right.add(botR);

        cp.add(right, BorderLayout.CENTER);

        t1 = new Timer(40, new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                pan.clearLines();
                if(direc){
                    for(int i = 0; i < 16; i++){
                        pan.addLine(i * 40 + incre, 0, i * 40 + incre, 600);
                    }
                    for(int i = 0; i < 16; i++){
                        pan.addLine(0, i * 40, 600, i * 40);
                    }
                    incre += tmp;
                }else{
                    for(int i = 0; i < 16; i++){
                        pan.addLine(0, i * 40 + incre, 600, i * 40 + incre);
                    }
                    for(int i = 0; i < 16; i++){
                        pan.addLine(i * 40, 0, i * 40, 600);
                    }
                    incre += tmp;
                }
                Iterator<Asteroid> i = asteroids.iterator();
                while(i.hasNext()){
                    Asteroid rock = i.next();
                    rock.moving();
                    if(((moon.getX() + 1) - (rock.getPrevX() * 40 - 40 + rock.getMoveX())) * 
                       ((moon.getX() + 1) - (rock.getPrevX() * 40 - 40 + rock.getMoveX())) + 
                       ((moon.getY()) - (rock.getPrevY() * 40 - 40 + rock.getMoveY())) * 
                       ((moon.getY()) - (rock.getPrevY() * 40 - 40 + rock.getMoveY())) < 400){
                        pan.remove(rock);
                        i.remove();
                        astKills++;
                        kills.setText("Asteroids killed: " + Integer.toString(astKills));
                        curScore += 10 * rock.type + 10;
                        score.setText("Score: " + Integer.toString(curScore));
                    }
                }
                // moon.setLocation(moon.getX() + mxInc, moon.getY() + myInc);
                moon.setLocation((int)((moon.getX() - 279) * Math.cos(Math.toRadians(-10)) - (moon.getY() - 280) * Math.sin(Math.toRadians(-10)) + 279.5), 
                                 (int)((moon.getX() - 279) * Math.sin(Math.toRadians(-10)) + (moon.getY() - 280) * Math.cos(Math.toRadians(-10)) + 280.5));
            }
        });

        t2 = new Timer(440, new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                t1.stop();
                t2.stop();
                if(moon.getX() > 294){
                    moon.setLocation(319, 280);
                }
                if(moon.getX() < 264){
                    moon.setLocation(239, 280);
                }
                if(moon.getY() > 295){
                    moon.setLocation(279, 320);
                }
                if(moon.getY() < 265){
                    moon.setLocation(279, 240);
                }
                pan.clearLines();
                for(int i = 0; i < 16; i++){
                    pan.addLine(i * 40, 0, i * 40, 600);
                }
                for(int i = 0; i < 16; i++){
                    pan.addLine(0, i * 40, 600, i * 40);
                }
                Iterator<Asteroid> i = asteroids.iterator();
                while(i.hasNext()){
                    Asteroid rock = i.next();
                    rock.setMoveXY(0, 0);
                    rock.setPrevXY(rock.getToX(), rock.getToY());
                    rock.setLocation((int)(rock.getToX() * 40 - 40), (int)(rock.getToY() * 40 - 40));
                    if(rock.getToX() == 8 && rock.getToY() == 8){
                        // theRock.setOpaque(false);
                        pan.remove(rock);
                        i.remove();
                        earthHP -= 25;
                        if(earthHP < 0){
                            earthHP = 0;
                        }
                        earthRemain.setText("Earth remains: " + Integer.toString(earthHP) + "%");
                    }else if(rock.getType() == 1){
                        rock.findDest(earthMoveX, earthMoveY, 0);
                    }
                }
                t3.start();
                t4.start();
            }
        });

        t3 = new Timer(40, new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                Iterator<Asteroid> i = asteroids.iterator();
                while(i.hasNext()){
                    Asteroid rock = i.next();
                    if(rock.getType() == 1){
                        rock.moving();
                        if(((moon.getX() + 1) - (rock.getPrevX() * 40 - 40 + rock.getMoveX())) * 
                           ((moon.getX() + 1) - (rock.getPrevX() * 40 - 40 + rock.getMoveX())) + 
                           ((moon.getY()) - (rock.getPrevY() * 40 - 40 + rock.getMoveY())) * 
                           ((moon.getY()) - (rock.getPrevY() * 40 - 40 + rock.getMoveY())) < 400){
                            pan.remove(rock);
                            i.remove();
                            astKills++;
                            kills.setText("Asteroids killed: " + Integer.toString(astKills));
                        }
                    }
                }
            }
        });

        t4 = new Timer(440, new ActionListener(){
            public void actionPerformed(ActionEvent ae){
                t3.stop();
                t4.stop();
                Iterator<Asteroid> i = asteroids.iterator();
                while(i.hasNext()){
                    Asteroid rock = i.next();
                    if(rock.getType() == 1){
                        rock.setMoveXY(0, 0);
                        rock.setPrevXY(rock.getToX(), rock.getToY());
                        if(rock.getToX() == 8 && rock.getToY() == 8){
                            // theRock.setOpaque(false);
                            pan.remove(rock);
                            i.remove();
                            earthHP -= 25;
                            if(earthHP < 0){
                                earthHP = 0;
                            }
                            earthRemain.setText("Earth remains: " + Integer.toString(earthHP) + "%");
                        }
                        rock.setLocation((int)(rock.getToX() * 40 - 40), (int)(rock.getToY() * 40 - 40));
                    }
                }
                if(earthHP > 0){
                    mu.setEnabled(true);
                    ml.setEnabled(true);
                    mr.setEnabled(true);
                    md.setEnabled(true); 
                }else{
                    JOptionPane.showMessageDialog(pan, "", "", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(new ImageIcon("earthPoof.png").getImage().getScaledInstance(600, 600, Image.SCALE_DEFAULT)));
                }
                restart.setEnabled(true);
            }
        });
    }

    class Asteroid extends JLabel{
        private int type, toX, toY, prevX, prevY;
        private int moveX = 0, moveY = 0;
        private boolean willMove = true;

        public Asteroid(int xpos, int ypos, int rockType){
            this.prevX = xpos;
            this.prevY = ypos;
            this.type = rockType;
            setBounds(xpos * 40 - 40, ypos * 40 - 40, 40, 40);
            setOpaque(false);
            if(rockType == 0){
                setIcon(new ImageIcon("slowAsteroid.png"));
            }else if(rockType == 1){
                setIcon(new ImageIcon("fastAsteroid.png"));
            }
        }

        public void findDest(int earthX, int earthY, int drift){
            if((earthX - prevX) * (earthX - prevX) > (earthY - prevY) * (earthY - prevY)){
                if(earthX == 8){
                    toX = prevX + 
                            (int)Math.sqrt((8 - prevX) * (8 - prevX)) / (8 - prevX);
                    toY = prevY + (8 - earthY) * drift;
                }else{
                    toX = prevX + (8 - earthX) * drift + 
                            (int)Math.sqrt((8 - prevX) * (8 - prevX)) / (8 - prevX);
                    toY = prevY;
                }
            }else if((earthX - prevX) * (earthX - prevX) < (earthY - prevY) * (earthY - prevY)){
                if(earthX == 8){
                    toX = prevX; 
                    toY = prevY + (8 - earthY) * drift + 
                            (int)Math.sqrt((8 - prevY) * (8 - prevY)) / (8 - prevY);
                }else{
                    toX = prevX + (8 - earthX) * drift;
                    toY = prevY + 
                            (int)Math.sqrt((8 - prevY) * (8 - prevY)) / (8 - prevY);
                }
            }else if(earthX - prevX != 0 || earthY - prevY != 0){
                if((8 - prevX) * (8 - prevX) > (8 - prevY) * (8 - prevY)){
                    if(earthX == 8){
                        toX = prevX + 
                                (int)Math.sqrt((8 - prevX) * (8 - prevX)) / (8 - prevX);
                        toY = prevY + (8 - earthY) * drift;
                    }else{
                        toX = prevX + (8 - earthX) * drift + 
                                (int)Math.sqrt((8 - prevX) * (8 - prevX)) / (8 - prevX);
                        toY = prevY;
                    }
                }else if((8 - prevX) * (8 - prevX) < (8 - prevY) * (8 - prevY)){
                    if(earthX == 8){
                        toX = prevX; 
                        toY = prevY + (8 - earthY) * drift + 
                                (int)Math.sqrt((8 - prevY) * (8 - prevY)) / (8 - prevY);
                    }else{
                        toX = prevX + (8 - earthX) * drift;
                        toY = prevY + 
                                (int)Math.sqrt((8 - prevY) * (8 - prevY)) / (8 - prevY);
                    }
                }
            }else{
                if(earthX == 8){
                    toY = prevY + (8 - earthY) * drift;
                }else{
                    toX = prevX + (8 - earthX) * drift;
                }
            }
        }
/*
                willMove = !willMove;
            }else if(type == 0){
                if(earthX == 8){
                    toX = prevX; 
                    toY = prevY + 8 - earthY;
                }else{
                    toX = prevX + 8 - earthX;
                    toY = prevY;
                }
                willMove = !willMove;
            }
*/ 

        public void moving(){
            int xV = toX - prevX;
            int yV = toY - prevY;
            if(xV != 0 || yV != 0){
                moveX += (int)(xV * 4);
                moveY += (int)(yV * 4);
                setLocation((int)(prevX * 40 - 40 + moveX), (int)(prevY * 40 - 40 + moveY));
            }
        }
        
        public void setPrevXY(int a, int b){
            prevX = a;
            prevY = b;
        }
        public void setMoveXY(int a, int b){
            moveX = a;
            moveY = b;
        }
        public int getType(){
            return type;
        }
        public int getPrevX(){
            return prevX;
        }
        public int getPrevY(){
            return prevY;
        }
        public int getToX(){
            return toX;
        }
        public int getToY(){
            return toY;
        }
        public int getMoveX(){
            return moveX;
        }
        public int getMoveY(){
            return moveY;
        }
    }

    class DrawPan extends JPanel{
        public DrawPan(){
            setPreferredSize(new Dimension(600, 600));
        }

        public ArrayList<Line> lines = new ArrayList<>();
        
        public void addLine(int x1, int y1, int x2, int y2) {
            lines.add(new Line(x1, y1, x2, y2));
            repaint();
        }
        
        public void clearLines() {
            lines.clear();
            repaint();
        }

        @Override
        public void paintComponent(Graphics g){
            super.paintComponent(g);
            g.setColor(new Color(180, 180, 180));
            for (Line line : this.lines) {
                g.setColor(new Color(180, 180, 180));
                g.drawLine(line.x1, line.y1, line.x2, line.y2);
            }
        }        
    }

    public static class Line{
        int x1, y1, x2, y2;
    
        public Line(int x1, int y1, int x2, int y2) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
        }               
    }

    public class Clicked extends AbstractAction {

        public Clicked() {
            // putValue(NAME, "Click me");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            JButton btn = (JButton) e.getSource();
            btn.doClick();
        }

    }

    public static void main(String[] args){
        Game bml = new Game();
        bml.setVisible(true);
    }
}