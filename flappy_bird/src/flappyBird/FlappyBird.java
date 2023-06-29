package flappyBird;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.Timer;

public class FlappyBird implements ActionListener, MouseListener {
    public final int width =1000, height= 800;
    public static FlappyBird flappybird;
    public Render render;
    public Rectangle bird;
    public ArrayList<Rectangle> colums;
    public Random rand ;
    public int ticks, ymotion,score, finalscore,speed;

    public boolean gameOver, started;
    public JButton set;
    public FlappyBird(int speed2){
        this.speed = speed2;
        rand = new Random();
        colums = new ArrayList<Rectangle>();
        Timer timer = new Timer(speed,this);
        System.out.println(speed);
        render = new Render();
        JFrame jframe = new JFrame();
        jframe.add(render);
        jframe.setSize(width,height);
        jframe.addMouseListener(this);
        jframe.setVisible(true);
        jframe.setResizable(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        set = new JButton();
        set.setSize(200,30);
        set.setLocation(0,0);
        set.setFont(new Font("Arial", 1, 20));
        set.setBackground(Color.WHITE); // Set background color
        set.setForeground(Color.BLACK); // Set foreground color


        set.setText("Settings");
        set.setFocusable(false);
        set.setVisible(true);
        set.addActionListener(this);
        jframe.add(set);
        timer.start();
        bird = new Rectangle(width/2 -200, height/2 -100, 20, 20);
        addcolum(true);
        addcolum(true);
        addcolum(true);
        addcolum(true);
    }

    public void jump(){
        if(gameOver){
            bird = new Rectangle(width/2 -200, height/2 -100, 20, 20);
            colums.clear();
            ymotion = 0;
            score = 0;
            addcolum(true);
            addcolum(true);
            addcolum(true);
            addcolum(true);
            gameOver = false;
        }
        if(!started){
            started = true;

        } else{
            if(ymotion > 0){
                ymotion = 0;
            }
            ymotion -= 10;

        }
    }
    public void repaint(Graphics g) {
        g.setColor(Color.cyan);
        g.fillRect(0,0,width,height);

        g.setColor(Color.orange);
        g.fillRect(0,height-150,width,150);
        g.setColor(Color.green);
        g.fillRect(0,height-170 ,width,20);
        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);


        for(Rectangle colum : colums){
            print(g, colum);
        }


        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 100));


        if (!started)
        {
            g.drawString("Click To Start", 200, height / 2 - 50);
        }
        if (gameOver)
        {
            g.drawString("Game Over!", 200, height / 2 - 50);
            g.drawString(String.valueOf(finalscore), width/2 - 20, height / 2 + 50);
        }
        if (!gameOver && started)
        {
            g.drawString(String.valueOf(score), width / 2 - 25, 100);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == set){
            settings();
        }

        ticks++;
        for(int  i = 0; i < colums.size();i++){
            Rectangle colum = colums.get(i);
            colum.x -= 10;
            if(colum.x + colum.width < 0){
                colums.remove(colum);
                if(colum.y == 0){
                    addcolum(false);
                }
            }

        }
        if(ticks%2 == 0 && ymotion <15){
            ymotion += 2;

        }
        bird.y += ymotion;
        for(Rectangle colum: colums){
            if(colum.y == 0 && bird.x + bird.width / 2 > colum.x + colum.width / 2 - 10 && bird.x + bird.width / 2 < colum.x + colum.width / 2 + 10) {
                score++;

            }
            if(colum.intersects(bird)){
                gameOver = true;
                bird.x = colum.x - bird.width;



            }
        }
        if (bird.y > height - 170 || bird.y < 0)
        {
            gameOver = true;

        }

        if (bird.y + ymotion >= height - 170)
        {
            bird.y = height - 170 - bird.height;
            gameOver = true;
        }
        if(gameOver) finalscore = score;
        render.repaint();

    }
    public void addcolum(boolean start){
        int space = 300;
        int width1 = 100;
        int height1 = 50 + rand.nextInt(300);
        if(start){
            colums.add(new Rectangle(width + width1 + colums.size()*space,height-height1-170,width1,height1));
            colums.add(new Rectangle(width + width1 + (colums.size()-1)*space,0,width1,height-height1-space));
        }else{
            colums.add(new Rectangle(colums.get(colums.size()-1).x + 600,height-height1-170,width1,height1));
            colums.add(new Rectangle(colums.get(colums.size()-1).x ,0,width1,height-height1-space));

        }


    }
    public void settings(){
        JFrame setting = new JFrame();
        setting.setSize(width,height);

        setting.setResizable(false);
        setting.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JButton set1 = new JButton();
        set1.setSize(100,30);
        set1.setLocation(120,10);
        set1.setFont(new Font("Arial", 1, 20));
        set1.setBackground(Color.WHITE); // Set background color
        set1.setForeground(Color.BLACK); // Set foreground color


        set1.setText("Okay");
        set1.setFocusable(false);
        set1.setVisible(true);
        set1.addActionListener(this);


        Container c = setting.getContentPane();
        c.setLayout(null);

        String[] items = {"Level 1", "Level 2", "Level 3"};
        // Create a JComboBox with the items array
        JComboBox<String> dropdown = new JComboBox<>(items);
        // Set the default selected item
        dropdown.setSelectedIndex(0);
        dropdown.setBounds(10,10,100,30);
        // Add the dropdown to the frame
        c.add(dropdown);
        c.add(set1);

        setting.setVisible(true);
        if(dropdown.getSelectedItem().equals("Level 2"))speed = 5;
        if(dropdown.getSelectedItem().equals("Level 1"))speed = 15;
        setting.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        System.out.println(speed);
        FlappyBird flappyBird = new FlappyBird(speed);
    }
    public void print(Graphics g, Rectangle colum){
        g.setColor(Color.green.darker());
        g.fillRect(colum.x,colum.y,colum.width,colum.height);
    }
    public static void main(String[] args) {
        flappybird = new FlappyBird(20);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
    }

    @Override
    public void mousePressed(MouseEvent e) {

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
}



