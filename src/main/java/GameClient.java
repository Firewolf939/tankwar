import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class GameClient extends JComponent {

    private int screenWidth;
    private int screenHeight;
    //玩家坦克
    private Tank playerTank;
    //敵方坦克
    private List<Tank>enemyTanks = new ArrayList<>(12);
    //牆壁
    private List<Wall> walls = new ArrayList<>();

    private boolean stop;

    GameClient(){
        this(1024,768);
    }

    public GameClient(int screenWidth,int screenHeight){
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
        this.setPreferredSize(new Dimension(screenWidth,screenHeight));

        init();

        new Thread(()->{
            while (!stop){
                repaint();
                try {
                    Thread.sleep(50);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void init(){

        playerTank = new Tank(getCenterPosX(47),getCenterPosY(100),Direction.DOWN);
        for (int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                enemyTanks.add(new Tank(350+j*80,500+i*80,Direction.UP,true));
            }
        }

        walls.add(new Wall(250,150,true,15));
        walls.add(new Wall(150,200,false,15));
        walls.add(new Wall(800,200,false,15));
    }



    @Override
    protected void paintComponent(Graphics g) {
        //super.paintComponent(g);
        playerTank.draw(g);
        for (Tank tank:enemyTanks){
            tank.draw(g);
        }

        for (Wall wall : walls){
            wall.draw(g);
        }
        g.drawImage(playerTank.getImage(),
                playerTank.getX(),playerTank.getY(),null);
    }

    private int getCenterPosX(int width){
        return (screenWidth-width)/2;
    }

    private int getCenterPosY(int Height){
        return (screenHeight-Height)/2;
    }


    //按鍵按壓偵測
    public void KeyPressed(KeyEvent e){
        boolean[] dirs = playerTank.getDirs();
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                dirs[0]=true;
                //playerTank.setDirection(Direction.UP);
                //playerTank.setY(playerTank.getY()-playerTank.getSpeed());
                break;
            case KeyEvent.VK_DOWN:
                dirs[1]=true;
                //playerTank.setDirection(Direction.DOWN);
                //playerTank.setY(playerTank.getY()+playerTank.getSpeed());
                break;
            case KeyEvent.VK_LEFT:
                dirs[2]=true;
                //playerTank.setDirection(Direction.LEFT);
                //playerTank.setX(playerTank.getX()-playerTank.getSpeed());
                break;
            case KeyEvent.VK_RIGHT:
                dirs[3]=true;
                //playerTank.setDirection(Direction.RIGHT);
                //playerTank.setX(playerTank.getX()+playerTank.getSpeed());
                break;
            default:
        }
        //repaint();
        //playerTank.move();
    }

    //按鍵釋放偵測 和"KeyPressed"對稱
    public void keyReleased(KeyEvent e) {
        boolean[] dirs = playerTank.getDirs();
        switch (e.getKeyCode()){
            case KeyEvent.VK_UP:
                dirs[0]=false;
                break;
            case KeyEvent.VK_DOWN:
                dirs[1]=false;
                break;
            case KeyEvent.VK_LEFT:
                dirs[2]=false;
                break;
            case KeyEvent.VK_RIGHT:
                dirs[3]=false;
                break;
            default:
        }
    }
}
