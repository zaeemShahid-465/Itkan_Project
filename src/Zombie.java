import java.awt.*;

public class Zombie extends Entity {
    private int speed;
    Player p;
    public boolean active;

    public double damageCoolDown;

    public Zombie(Image left, Image right, Image idle, Rectangle rec, HealthBar h, Player p) {
        super(idle, right, left, rec, h);
        speed = 100;
        dx = 0;
        dy = 0;
        this.p = p;
        active = true;

        damageCoolDown = 0;
    }

    public void setActive(boolean value) {
        this.active = value;
    }

    @Override
    public void update(double dt) {
        super.update(dt);

        if (damageCoolDown > 0)
            damageCoolDown -= dt;

//        if (Math.abs(rec.x - p.rec.x) <= 300 && Math.abs(rec.y - p.rec.y) <= 300) { //left or right
        if (rec.x - p.rec.x < 0) {
            rec.x += (int) (speed * dt);
            dx = -1;
        }
        if (rec.x - p.rec.x > 0) {
            rec.x -= (int) (speed * dt);
            dx = 1;
        }
        if(rec.y - p.rec.y < 0) {
            rec.y += (int) (speed * dt);
            dy = 1;
        }
        if(rec.y - p.rec.y > 0) {
            rec.y -= (int) (speed * dt);
            dy = -1;
        }
//        }
        // Commented the "if test" because zombie should ALWAYS follow player

        health.redRec.x = rec.x;
        health.redRec.y = rec.y;
        health.greenRec.x = rec.x;
        health.greenRec.y = rec.y;

        if (health.getHealth() <= 0)
            setActive(false);
    }

    public void draw(Graphics g) {

        g.drawImage(main, rec.x, rec.y, rec.width, rec.height, null);
        health.draw(g);
    }
}
