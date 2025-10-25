import java.awt.*;

public class Bullet {
    private Rectangle rect;
    private Image img;
    private int speed;
    public int maxLifeTime;
    public int timeAlive;
    private double angle;
    private boolean active;

    public Bullet(Rectangle rect, Image img, double angle) {
        this.rect = rect;
        this.img = img;
        speed = 500;
        maxLifeTime = 10 * 60; //in milliseconds
        timeAlive = 0;
        this.angle = angle;
        active = true;
    }

    public boolean isActive() {
        return active;
    }

    public Rectangle getBounds() {
        return rect;
    }

    public void update(double dt) {
        if(active == false) {
            return;
        }
        timeAlive += dt;
        if(timeAlive > maxLifeTime) {
            active = false;
        }
        double dx = Math.cos(angle) * speed * dt;
        double dy = Math.sin(angle) * speed * dt;
        rect.x += dx;
        rect.y += dy;
    }

    public void draw(Graphics g) {
        if(active) {
            g.drawImage(img, rect.x, rect.y, rect.width, rect.height, null);
        }
    }
}
