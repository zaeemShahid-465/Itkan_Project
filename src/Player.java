import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Player extends Entity implements KeyListener {
    private int speed;

    public Player(Image img, Rectangle rec, HealthBar healthBar) {
        super(img, rec, healthBar);
        dx = 0;
        dy = 0;
        speed = 200;
    }
    
    @Override
    public void update(double dt) {
        rec.x += (int)(speed * dx * dt);
        rec.y += (int)(speed * dy * dt);

        health.redRec.x = rec.x;
        health.redRec.y = rec.y;
        health.greenRec.x = rec.x;
        health.greenRec.y = rec.y;

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
}