import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import java.util.ListIterator;

public class PlayerTank extends Tank implements KeyListener {
    public final static int FULL_HP = 100;
    private int totalMissiles;

    public PlayerTank(Location location){
        super(location);
        this.blood = new Blood(FULL_HP);
        this.speedX = 5;
        this.speedY = 5;
        this.direction = Direction.Down;
        this.totalMissiles = 50;
    }

    public int getTotalMissiles() {
        return totalMissiles;
    }

    public void setTotalMissiles(int totalMissiles) {
        this.totalMissiles = totalMissiles;
    }

    public void updateLocation(){
        //update the location of tank
        Direction newDir = updateDirection();
        if(this.direction == newDir) {
            if(!this.outOfBounds())
                this.move();
        }else{
            this.direction = newDir;
        }
    }

    private boolean bu, bd, bl, br;

    public Direction updateDirection(){
        Direction newDir = this.direction;
        if(bu && !bd && !bl && !br)
            newDir = Direction.Up;
        else if(!bu && bd && !bl && !br)
            newDir = Direction.Down;
        else if(!bu && !bd && bl && !br)
            newDir = Direction.Left;
        else if(!bu && !bd && !bl && br)
            newDir = Direction.Right;
        else if(bu && !bd && bl && !br)
            newDir = Direction.LeftUp;
        else if(bu && !bd && !bl && br)
            newDir = Direction.RightUp;
        else if(!bu && bd && bl && !br)
            newDir = Direction.LeftDown;
        else if(!bu && bd && !bl && br)
            newDir = Direction.RightDown;
         return newDir;
    }


    public void superFire(){
        Tools.playAudio(Tools.nextBoolean() ? "supershoot.wav" : "supershoot.aiff");
        for(Direction direction : Direction.values())
            TankWar.getInstance().addMissile(new Missile(this, direction));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_CONTROL :
                this.totalMissiles--;
                this.fire();
                return;
            case KeyEvent.VK_A :
                this.totalMissiles -= 8;
                this.superFire();
                return;
            case KeyEvent.VK_LEFT :
                bl = true;
                break;
            case KeyEvent.VK_UP :
                bu = true;
                break;
            case KeyEvent.VK_RIGHT :
                br = true;
                break;
            case KeyEvent.VK_DOWN :
                bd = true;
                break;
            default : break;
        }
        updateLocation();

//        if (my >= HEIGHT) {
//            my = Tools.nextInt(HEIGHT);
//        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key){
            case KeyEvent.VK_CONTROL :
                break;
            case KeyEvent.VK_A :
                break;
            case KeyEvent.VK_LEFT :
                bl = false;
                break;
            case KeyEvent.VK_UP :
                bu = false;
                break;
            case KeyEvent.VK_RIGHT :
                br = false;
                break;
            case KeyEvent.VK_DOWN :
                bd = false;
                break;
            default : break;
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

}