import com.sun.javafx.application.PlatformImpl;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

class TankWar extends JComponent {
    public static final int WIDTH = 800, HEIGHT = 600;

    private static final int REPAINT_INTERVAL = 50;

    private boolean gameStart;
    private int x = WIDTH / 2, y = HEIGHT / 2; //initialized location of playerTank

    private PlayerTank playerTank;
    private List<EnemyTank> enemyTanks;
    private Blood blood;
    private List<Missile> missiles;
    private List<Explode> explodes;
    private List<Wall> walls;

    private TankWar() {
        gameStart = false;
    }

    private void initWorld(){
        playerTank = new PlayerTank(new Location(x,y));
        enemyTanks = new ArrayList<>();
        missiles = new ArrayList<>();
        explodes = new ArrayList<>();
        walls = new ArrayList<>();
        blood = new Blood(new Location(360, 270));
        this.addKeyListener(this.playerTank);
        //initialize enemy tanks
        int dist = (WIDTH - 120) / 12;
        for(int i = 0;i < 12; ++i){
            enemyTanks.add(new EnemyTank(new Location(50 + dist * i,HEIGHT / 2 + 100)));
        }
        //initialize walls
        walls.add(new Wall(new Location(250, 100), 6, 1, 6));
        walls.add(new Wall(new Location(100, 200), 6, 6, 1));
        walls.add(new Wall(new Location(680, 200), 6, 6, 1));
    }

    private static TankWar INSTANCE;

    public static TankWar getInstance(){
        if(INSTANCE == null){
            INSTANCE = new TankWar();
        }
        return INSTANCE;
    }

    public void restart(){
        this.initWorld();
        //this.gameStart = false;
    }

    public boolean isGameStart() {
        return gameStart;
    }

    public void setGameStart(boolean gameStart) {
        this.gameStart = gameStart;
    }


    public void addMissile(Missile missile){
        missiles.add(missile);
    }

    private void triggerEvent() throws CloneNotSupportedException {
        if(playerTank.isAlive() && this.gameStart){
            playerTankEatBlood();
            playerTankIsDying();
            enemyTankRandomMoveAndFire();
            missileOutOfBounds();
            missileHitWalls();
            missileHitTank();
        }
    }

    private void playerTankEatBlood(){
        if(playerTank.getRectangle().intersects(blood.getRectangle())){
            playerTank.setHp(PlayerTank.FULL_HP);
            blood.setAppear(false);
        }
    }



    private void playerTankIsDying(){
        if(playerTank.getHp() < PlayerTank.FULL_HP / 2){
            int rand = Tools.nextInt(3);
            if(rand == 0 || rand == 1)
                blood.setAppear(true);
        }
    }


    private void enemyTankRandomMoveAndFire() throws CloneNotSupportedException {
        for(EnemyTank enemyTank : enemyTanks){
            enemyTank.randomMove();
            enemyTank.randomFire();
        }
    }

    private void missileHitWalls(){
        for(Wall wall : walls){
            missiles.removeIf(missile -> missile.getRectangle().intersects(wall.getRectangle()));
        }
    }

    private void missileOutOfBounds(){
        missiles.removeIf(missile -> missile.outOfBounds());
        for (int i = 0; i < missiles.size(); i++) {
            missiles.get(i).move();
        }
    }

    private void missileHitTank(){
        ListIterator<Missile> iterMissile = missiles.listIterator();
        while(iterMissile.hasNext()){
            Missile missile = iterMissile.next();
            //shoot enemy tank
            if(missile.getTankType() == "PlayerTank"){
                ListIterator<EnemyTank> iterEnemyTank = enemyTanks.listIterator();
                while(iterEnemyTank.hasNext()){
                    EnemyTank enemyTank = iterEnemyTank.next();
                    if(enemyTank.getRectangle().intersects(missile.getRectangle())){
                        explodes.add(new Explode(enemyTank.getLocation()));
                        iterEnemyTank.remove();
                        iterMissile.remove();
                        break;
                    }
                }
                //enemyTanks.removeIf(enemyTank -> enemyTank.getRectangle().intersects(missile.getRectangle()));
            }else{
                if(playerTank.getRectangle().intersects(missile.getRectangle())){
                    playerTank.setHp(this.playerTank.getHp() - 20);
                }
            }
        }
    }

    public boolean tankHitEachOther(Tank tank){
        for(int i = 0;i < enemyTanks.size(); ++i) {
            EnemyTank enemy = enemyTanks.get(i);
            if (enemy.getId() != tank.getId()) {
                if (tank.getRectangle().intersects(enemyTanks.get(i).getRectangle())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean tankHitBounds(Tank tank){
        int x = tank.getLocation().getX();
        int y = tank.getLocation().getY();
        if(x <= 0 || x >= TankWar.WIDTH  - tank.getWidth() * 1.5|| y <= 0 || y >= TankWar.HEIGHT - tank.getHeight() * 1.8)
            return true;
        return false;
    }

    public boolean tankHitWalls(Tank tank){
        for(Wall wall : walls){
            if(wall.getRectangle().intersects(tank.getRectangle())){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void paintComponent(Graphics g) {

        //draw background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        //start UI
        if(!this.gameStart){
            g.setColor(Color.RED);
            g.setFont(new Font("Default", Font.BOLD, 30));
            g.drawString("PRESS SPACE TO START GAME", x - 250, y - 100);
        }
        //draw game data
        g.setColor(Color.WHITE);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString("Missiles: " + missiles.size(), 10, 50);
        g.drawString("Explodes: " + explodes.size(), 10, 70);
        g.drawString("Our Tank HP: " + playerTank.getHp(), 10, 90);
        g.drawString("Enemies Left: " + enemyTanks.size(), 10, 110);
        g.drawString("Enemies Killed: " + (12 - enemyTanks.size()), 10, 130);
        //draw game objects
        for(Wall wall : walls)
            wall.draw(g);
        blood.draw(g);

        //if player tank is alive, then draw the player tank and enemy tanks
        if(playerTank.isAlive()){
            playerTank.draw(g);
            for(int i = 0; i < missiles.size(); ++i)
                missiles.get(i).draw(g);
            for(int i = 0;i < enemyTanks.size(); ++i)
                enemyTanks.get(i).draw(g);
            for(int i = 0;i < explodes.size(); ++i)
                explodes.get(i).draw(g);

        }else{
            //draw the UI of game over
            Tools.playAudio("death.mp3");
            g.setColor(Color.RED);
            g.setFont(new Font("Default", Font.BOLD, 30));
            g.drawString("GAME OVER ", x - 100, y - 100);
            g.drawString("PRESS F2 TO RESTART", x - 160, y - 60);
        }


    }

    public void start() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                while (true) {
                    try {
                        repaint();
                        triggerEvent();
                        Tools.sleepSilently(REPAINT_INTERVAL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }

    public static void main(String[] args) {

        TankWar tankWar = TankWar.getInstance();
        tankWar.initWorld();
        PlatformImpl.startup(() -> {});
        Tools.setTheme();
        JFrame frame = new JFrame("Tank War");
        frame.setIconImage(new ImageIcon(TankWar.class.getResource("/icon.png")).getImage());
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(400, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        frame.add(tankWar);
        // KeyListeners need to be on the focused component to work
        tankWar.setFocusable(true);
        frame.setVisible(true);
    }
}
