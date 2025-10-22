import java.awt.*;

public class HealthBar {
    private int health;
    public Rectangle redRec;
    public Rectangle greenRec;

    public HealthBar(int h, Rectangle redRec, Rectangle greenRec) {
        health = h;
        this.redRec = redRec;
        this.greenRec = greenRec;
    }

    public int getHealth() {
        return health;
    }

    public void heal() {
        health++;
    }

    public void damage() {
        health--;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillRect(redRec.x, redRec.y - config.healthBarHeight - 5 /* <- offset */, config.maxHealth / 2, config.healthBarHeight);
        g.setColor(Color.GREEN);
        g.fillRect(greenRec.x, greenRec.y - config.healthBarHeight - 5, health / 2, config.healthBarHeight);
    }
}
