import java.awt.*;

public class HealthBar {
    private int health;
    private Image redImg;
    private Rectangle redRec;
    private Rectangle greenRec;
    private Image greenImg;

    public HealthBar(int h, Image greenImg, Rectangle redRec, Rectangle greenRec, Image redImg) {
        health = h;
        this.greenImg = greenImg;
        this.redRec = redRec;
        this.greenRec = greenRec;
        this.redImg = redImg;
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
        g.drawImage(greenImg, greenRec.x, greenRec.y, greenRec.width, greenRec.height, null);
        g.drawImage(redImg, redRec.x, redRec.y, redRec.width, redRec.height, null);
    }
}
