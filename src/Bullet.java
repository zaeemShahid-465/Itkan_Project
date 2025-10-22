import java.awt.*;

public class Bullet {
    private Rectangle rect;
    private Image img;
    private int speed;
    public int maxLifeTime;
    public int timeAlive;
    private double angle;
    private boolean active;

    public Bullet(Rectangle rect, Image img) {
        this.rect = rect;
        this.img = img;
        speed = 200;
        maxLifeTime = 10 * 60; //in milliseconds
        timeAlive = 0;
        angle = 0;
        active = true;
    }

    public boolean isActive() {
        return active;
    }

    public Rectangle getBounds() {
        return rect;
    }

    public void update() {
        if(active == false) {
            return;
        }
        timeAlive++;
        if(timeAlive > maxLifeTime) {
            active = false;
        }
        double dx = Math.cos(angle) * speed;
        double dy = Math.sin(angle) * speed;
        rect.x += dx;
        rect.y += dy;
    }

    public void draw(Graphics g) {
        if(active) {
            g.drawImage(img, rect.x, rect.y, rect.width, rect.height, null);
        }
    }
}
