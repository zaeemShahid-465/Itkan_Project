import java.awt.*;

public class Zombie extends Entity {
    private int speed;
    Player p;
    public Zombie(Image left, Image right, Image idle, Rectangle rec, HealthBar h, Player p) {
        super(idle, right, left, rec, h);
        speed = 100;
        dx = 0;
        dy = 0;
        this.p = p;
    }

    @Override
    public void update(double dt) {
        if (Math.abs(rec.x - p.rec.x) <= 300 && Math.abs(rec.y - p.rec.y) <= 300) { //left or right
            if (rec.x - p.rec.x < 0)
                rec.x += (int)(speed * dt);
            if (rec.x - p.rec.x > 0)
                rec.x -= (int)(speed * dt);
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
        g.drawImage(main, rec.x, rec.y, rec.width, rec.height, null);
        health.draw(g);
    }
}
