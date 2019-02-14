import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

public class Tank extends GameObject {
    public static final int WIDTH = 50, HEIGHT = 60;

    private boolean isAlive;

    protected Blood blood;
    protected int speedX;
    protected int speedY;
    protected Direction direction;

    public Tank(Location location){
        super(location);
        isAlive = true;
    }

    public boolean isAlive(){
        return isAlive;
    }

    public Blood getBlood(){
        return this.blood;
    }

    public void setBlood(Blood blood) {
        this.blood = blood;
        if(this.blood.getHP() <= 0){
            this.isAlive = false;
            this.blood.setHP(0);
        }
    }


    public boolean outOfBounds(){
        int x = this.location.getX() + this.speedX * direction.xDir;
        int y = this.location.getY() + this.speedY * direction.yDir;
        if(x <= 0 || x > TankWar.WIDTH  - Tank.WIDTH|| y <= 0 || y > TankWar.HEIGHT - Tank.HEIGHT)
            return true;
        return false;
    }

    public void move(){
        this.location.setX(this.location.getX() + this.speedX * direction.xDir);
        this.location.setY((this.location.getY() + this.speedY * direction.yDir));
    }

    public void fire(){
        Tools.playAudio("shoot.wav");
        TankWar.getInstance().addMissile(new Missile(this, this.direction));
    }

    @Override
    public Rectangle getRectangle(){
        return new Rectangle(this.location.getX(), this.location.getY(), Tank.WIDTH, Tank.HEIGHT);
    }


    @Override
    public void draw(Graphics g) {
        g.drawImage(this.direction.getImage("Tank"), this.location.getX(), this.location.getY(), null);
        //draw HP
        if(this.getClass().getName() == "PlayerTank"){
            g.setColor(Color.RED);
            g.fillRect(this.location.getX(), this.location.getY() - 10, 35 * this.blood.getHP() / PlayerTank.FULL_HP, 10);
        }

    }
}