import java.awt.*;

public abstract class Entity {
    HealthBar health;
    Rectangle rec;
    Image idle, left, right, main;
    int dx, dy;

    public Entity(Image idle, Image left, Image right, Rectangle rec, HealthBar h) {
        health = h;
        this.rec = rec;
        this.idle = idle;
        this.left = left;
        this.right = right;
        main = idle;
    }


    public void update(double dt) {
        if (dx == 1)
            main = right;
        else if (dx == -1)
            main = left;
        else
            main = idle;
    }

    public void draw(Graphics g) {
        g.drawImage(main, rec.x, rec.y, rec.width, rec.height, null);
        health.draw(g);
    }


}
