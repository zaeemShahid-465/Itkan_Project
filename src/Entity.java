import java.awt.*;

public abstract class Entity {
    HealthBar health;
    Rectangle rec;
    Image img;
    int dx, dy;

    public Entity(Image img, Rectangle rec, HealthBar h) {
        health = h;
        this.rec = rec;
        this.img = img;
    }


    public abstract void update(double dt);

    public void draw(Graphics g) {
        g.drawImage(img, rec.x, rec.y, rec.width, rec.height, null);
        health.draw(g);
    }


}
