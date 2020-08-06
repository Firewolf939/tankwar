import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GameClient extends JComponent {

    private int screenWidth;
    private int screenHeight;

    private Tank playerTank;

    private List<GameObject> objects = new ArrayList<>();

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

        Image[] brickImage = {Tools.getImage("brick.png")};
        Image[] iTankImage = new Image[8];
        Image[] eTankImage = new Image[8];

        String[] sub = {"U.png","D.png","L.png","R.png","LU.png","RU.png","LD.png","RD.png",};

        for (int i=0; i<iTankImage.length; i++){
            iTankImage[i] = Tools.getImage("itank"+sub[i]);
            eTankImage[i] = Tools.getImage("etank"+sub[i]);
        }

        playerTank = new Tank(getCenterPosX(47),100,Direction.DOWN,iTankImage);
        objects.add(playerTank);

        for (int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                objects.add(new Tank(350+j*80,500+i*80,Direction.UP,true,eTankImage));
            }
        }

        //Image image = Tools.getImage("brick.png");
        objects.add(new Wall(250,150,true,15,brickImage));
        objects.add(new Wall(150,200,false,15,brickImage));
        objects.add(new Wall(800,200,false,15,brickImage));
    }



    @Override
    protected void paintComponent(Graphics g) {
        for (GameObject object:objects){
            object.draw(g);
        }
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
