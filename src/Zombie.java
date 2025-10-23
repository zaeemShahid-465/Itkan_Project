import java.awt.*;

public class Zombie extends Entity {
    private int speed;
    Player p;
    public Zombie(Image img, Rectangle rec, HealthBar h, Player p) {
        super(img, rec, h);
        speed = 200;
        dx = 0;
        dy = 0;
    }

    @Override
    public void update(double dt) {
        if (Math.abs(rec.x - p.rec.x) <= 300) { //left or right
            if (rec.x - p.rec.x < 0)
                rec.x += (int)(speed * dt);
            if (rec.x - p.rec.x > 0)
                rec.x -= (int)(speed * dt);
        }

        if(Math.abs(rec.y - p.rec.y) <= 300) { //up or down
            if(rec.y - p.rec.y < 0)
                rec.y += (int)(speed * dt);
            if(rec.y - p.rec.y > 0)
                rec.y -= (int)(speed * dt);
        }
        health.redRec.x = rec.x;
        health.redRec.y = rec.y;
        health.greenRec.x = rec.x;
        health.greenRec.y = rec.y;
    }

    public void draw(Graphics g) {
        g.drawImage(img, rec.x, rec.y, rec.width, rec.height, null);
        health.draw(g);
    }
}
