import javax.imageio.ImageIO;
import javax.print.attribute.standard.RequestingUserName;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

public class Game extends Canvas implements Runnable {
    Random rand;

    private JFrame frame;
    private boolean running = false;
    public final int WIDTH = 800;
    public final int HEIGHT = 600;
    private Thread gameThread;

    Player p;
    ArrayList<Zombie> enemies;

    // Zombie data
    File zI = new File("ZombieIdle.png");
    Image zombieIdle;
    File zR = new File("ZombieR.png");
    Image zombieRight;
    File zL = new File("ZombieL.png");
    Image zombieLeft;

    int timer;

    int score;

    double damageCoolDown;

    boolean gameOver;

    public Game() {
        rand = new Random();

        timer = 0;

        score = 0;

        gameOver = true;

        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);

        frame = new JFrame("Rapid Reload");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        loadContent();
    }

    public void loadContent() {
        Image playerIdle, playerL, playerR;
        enemies = new ArrayList<>();
        try {
            File f = new File("playerIdle.png");
            playerIdle = ImageIO.read(f);
            File g = new File("playerL.png");
            playerL = ImageIO.read(g);
            File h = new File("playerR.png");
            playerR = ImageIO.read(h);
            p = new Player(
                    playerIdle, playerL, playerR,
                    new Rectangle(100, 100, config.playerSize, config.playerSize),
                    new HealthBar(
                            config.maxHealth, new Rectangle(100, 100, config.healthBarLength, config.healthBarHeight),
                            new Rectangle(100, 100, config.healthBarLength, config.healthBarHeight)));

            zombieIdle = ImageIO.read(zI);
            zombieRight = ImageIO.read(zR);
            zombieLeft = ImageIO.read(zL);
//            z = new Zombie(
//                    zombieIdle, zombieLeft, zombieRight,
//                    new Rectangle(500, 500, config.playerSize, config.playerSize),
//                    new HealthBar(config.maxHealth, new Rectangle(500, 500, config.healthBarLength, config.healthBarHeight),
//                            new Rectangle(500, 500, config.healthBarLength, config.healthBarHeight)), p);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        addKeyListener(p);
        addMouseListener(p);

        setFocusable(true);
        requestFocus();
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        gameThread = new Thread(this, "Game Thread");
        gameThread.start();
    }

    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Game Loop method
    public void run() {
        final int TARGET_FPS = 60;
        final double TIME_PER_TICK = 1000000000.0 / TARGET_FPS;

        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        double delta = 0;

        int frames = 0;
        int ticks = 0;


        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / TIME_PER_TICK;
            lastTime = now;

            if (delta >= 1) {
                double dt = 1.0 / TARGET_FPS;
                tick(dt);    // update logic
                render();  // render frame
                ticks++;
                frames++;
                delta--;
            }

            // Limit FPS (sleep a bit)
            try {
                Thread.sleep(1); // minimal sleep to reduce CPU usage
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (System.currentTimeMillis() - timer >= 1000) {
                timer += 1000;
                frames = 0;
                ticks = 0;
            }
        }

        stop();
    }



    // Game logic goes here
    public void tick(double dt) {
        timer++;

        // Adding new zombies
        if (timer % (60 * 3) == 0) {
            int num = rand.nextInt(4) +1;
            int xPos = 0, yPos = 0;
            switch (num) {
                case 1:
                    xPos = rand.nextInt(WIDTH);
                    yPos = -50;
                    break;
                case 2:
                    xPos = WIDTH + 50;
                    yPos = rand.nextInt(HEIGHT);
                    break;
                case 3:
                    xPos = rand.nextInt(WIDTH);
                    yPos = HEIGHT + 50;
                    break;
                case 4:
                    xPos = -50;
                    yPos = rand.nextInt(HEIGHT);
                    break;
            }
            enemies.add(new Zombie(
                    zombieLeft, zombieRight, zombieIdle,
                    new Rectangle(xPos, yPos, config.playerSize, config.playerSize),
                    new HealthBar(config.maxHealth, new Rectangle(500, 500, config.healthBarLength, config.healthBarHeight),
                            new Rectangle(500, 500, config.healthBarLength, config.healthBarHeight)), p));
        }

        // Updating all zombies
        for (Zombie z : enemies) {
            z.update(dt);
        }

        p.update(dt);
//        if (z != null)
//            z.update(dt);

        // Checking for enemy/bullet collisions
        for (int i = 0; i < p.bullets.size(); i++) {
            for (int j = 0; j < enemies.size(); j++) {
                if (p.bullets.get(i).getBounds().intersects(enemies.get(j).rec)) {
                    Zombie z = enemies.get(j);
                    z.health.damage(20);
                    p.bullets.get(i).setActive(false);
                }
            }
        }

        // Removing bullets and enemies if inactive
        for (int i = enemies.size() - 1; i >= 0; i--) {
            Zombie e = enemies.get(i);
            if (!e.active) {
                enemies.remove(i);
                score += 50;
            }
        }

        // Zombie player collision logic
        for (Zombie z : enemies) {
            if (z.rec.intersects(p.rec) && z.damageCoolDown <= 0) {
                p.health.damage(20);
                z.damageCoolDown = 1.0;
            }
        }

        if (p.health.getHealth() <= 0)
            gameOver = true;

    }

    // Drawing
    public void render() {
        BufferStrategy bs = getBufferStrategy();
        if (bs == null) {
            createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();

        // Clearing screen
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        // Put drawing logic here
        // Game Over Screen
        if (gameOver) {
            Font f = new Font("", Font.PLAIN, 40);
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, WIDTH, HEIGHT);
            g.setFont(f); g.setColor(Color.RED);
            g.drawString("GAME OVER", WIDTH / 2 - 130, HEIGHT / 2);
            running = false;
        }
        else {


            // score
            g.setColor(Color.RED);
            g.drawString("SCORE -> " + score, 10, 20);
            g.setColor(Color.WHITE);


            p.draw(g);

//        if (z != null)
//            z.draw(g);

            for (Zombie z : enemies) {
                z.draw(g);
            }
        }


        g.dispose();
        bs.show();
    }

    public static void main(String args[]) {
        Game game = new Game();
        game.start();
    }

}
