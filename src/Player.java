import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.awt.Image;

public class Player extends Entity implements KeyListener, MouseListener {
    private int speed;
    private ArrayList<Bullet> bullets;
    Image bulletImg;

    public Player(Image idle, Image left, Image right, Rectangle rec, HealthBar healthBar) {
        super(idle, left, right, rec, healthBar);
        dx = 0;
        dy = 0;
        speed = 200;
        bullets = new ArrayList<>();
        loadBullet();
    }

    public void loadBullet() {
        bulletImg = null;
        try {
            File f = new File("UpdatedBulletTexture.png");
            bulletImg = ImageIO.read(f);
        } catch (Exception x) {
            x.printStackTrace();
        }
    }
    
    @Override
    public void update(double dt) {
        super.update(dt);

        rec.x += (int)(speed * dx * dt);
        rec.y += (int)(speed * dy * dt);

        health.redRec.x = rec.x;
        health.redRec.y = rec.y;
        health.greenRec.x = rec.x;
        health.greenRec.y = rec.y;

        for(int i = 0; i < bullets.size(); i++) { //for every element/bullet in bullets
            bullets.get(i).update(dt);
        }

        for(int i = bullets.size() - 1; i >= 0; i--) { //checks if each bullet is active
            if(!bullets.get(i).isActive()) {
                bullets.remove(i);
            }
        }

    }

    public void draw(Graphics g) {
        super.draw(g);

        for (Bullet b : bullets) {
            b.draw(g);
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {


    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W)
            dy = -1;
        if (e.getKeyCode() == KeyEvent.VK_A)
            dx = -1;
        if (e.getKeyCode() == KeyEvent.VK_S)
            dy = 1;
        if (e.getKeyCode() == KeyEvent.VK_D)
            dx = 1;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W)
            dy = 0;
        if (e.getKeyCode() == KeyEvent.VK_A)
            dx = 0;
        if (e.getKeyCode() == KeyEvent.VK_S)
            dy = 0;
        if (e.getKeyCode() == KeyEvent.VK_D)
            dx = 0;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON1) {
            int mouseX = e.getX();
            int mouseY = e.getY();

            double angle = Math.atan2(mouseY - rec.y, mouseX - rec.x);

            Rectangle rec = new Rectangle(this.rec.x, this.rec.y, 10, 10);
            bullets.add(new Bullet(rec, bulletImg, angle));
        }
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