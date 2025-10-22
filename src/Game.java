import javax.imageio.ImageIO;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;

public class Game extends Canvas implements Runnable {
    private JFrame frame;
    private boolean running = false;
    public final int WIDTH = 800;
    public final int HEIGHT = 600;
    private Thread gameThread;

    Player p;

    public Game() {
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
        Image playerImg = null;
        try {
            File f = new File("Thanos.jpg");
            playerImg = ImageIO.read(f);
            p = new Player(
                    playerImg,
                    new Rectangle(100, 100, config.playerSize, config.playerSize),
                    new HealthBar(
                            config.maxHealth, new Rectangle(100, 100, config.healthBarLength, config.healthBarHeight),
                            new Rectangle(100, 100, config.healthBarLength, config.healthBarHeight)));
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        addKeyListener(p);
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
                System.out.println("FPS: " + frames + " | TICKS: " + ticks);
                frames = 0;
                ticks = 0;
            }
        }

        stop();
    }



    // Game logic goes here
    public void tick(double dt) {
        p.update(dt);
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
        g.clearRect(0, 0, WIDTH, HEIGHT);

        // Put drawing logic here
        p.draw(g);


        g.dispose();
        bs.show();
    }

    public static void main(String args[]) {
        Game game = new Game();
        game.start();
    }

}
