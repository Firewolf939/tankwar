import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameClient extends JComponent {

    private int screenWidth;
    private int screenHeight;

    private PlayerTank playerTank;
    private boolean gameOver;

    private CopyOnWriteArrayList<GameObject> gameObjects = new CopyOnWriteArrayList<>();

    public List<GameObject> getGameObjects(){
        return gameObjects;
    }

    //子彈圖片
    public static Image[] bulletImg = new Image[8];
    public static Image[] explosionImg = new Image[11];

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

    public void addGameObject(GameObject object){
        gameObjects.add(object);
    }

    public void init(){

//        gameOver=false;
//        gameObjects.clear();
        Image[] brickImage = {Tools.getImage("brick.png")};
        Image[] iTankImage = new Image[8];
        Image[] eTankImage = new Image[8];

        String[] sub = {"U.png","D.png","L.png","R.png","LU.png","RU.png","LD.png","RD.png",};

        for (int i=0; i<iTankImage.length; i++){
            iTankImage[i] = Tools.getImage("itank"+sub[i]);
            eTankImage[i] = Tools.getImage("etank"+sub[i]);
            bulletImg[i] = Tools.getImage("missile"+sub[i]);
        }

        for (int i=0;i<explosionImg.length;i++){
            explosionImg[i]= Tools.getImage(i+".png");
        }

        playerTank = new PlayerTank(getCenterPosX(47),100,Direction.DOWN,iTankImage);
        gameObjects.add(playerTank);

        for (int i=0; i<3; i++){
            for (int j=0; j<4; j++){
                gameObjects.add(new EnemyTank(350+j*80,500+i*80,Direction.UP,true,eTankImage));
            }
        }

        gameObjects.add(new Wall(250,150,true,15,brickImage));
        gameObjects.add(new Wall(150,200,false,15,brickImage));
        gameObjects.add(new Wall(800,200,false,15,brickImage));
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth(),getHeight());

        for (GameObject object:gameObjects){
            object.draw(g);
        }

        for (GameObject object:gameObjects){
            if (!object.alive)
            gameObjects.remove(object);
        }

//        Iterator<GameObject> iterator = gameObjects.iterator();
//
//        while (iterator.hasNext()){
//            if (!iterator.next().alive){
//                iterator.remove();
//            }
//        }
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
            case KeyEvent.VK_CONTROL:
                playerTank.fire();
                break;
            case KeyEvent.VK_A:
                playerTank.superFire();
                break;
//            case KeyEvent.VK_F2:
//                for (GameObject object:objects){
//                    if (object instanceof EnemyTank){
//                        objects.remove(object);
//                    }
//                }
//                break;
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

//    public void checkGameStatus(){
//        boolean gameWin=true;
//
//        for (GameObject object:objects){
//            if (object instanceof EnemyTank){
//                gameWin=false;
//            }
//        }
//        if (gameWin){
//            for (int i=0;i<3;i++){
//                for (int j=0;j<4;j++){
//                    addGameObject(new EnemyTank(320+j*100,450+100*i,Direction.UP,eTankImage));
//                }
//            }
//        }
//    }
}
