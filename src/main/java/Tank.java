import javax.swing.*;
import java.awt.*;

public class Tank extends GameObject{
    protected int speed;
    protected boolean enemy;

    //上下左右四個方向
    private boolean[] dirs = new boolean[4];

    public boolean[] getDirs() {
        return dirs;
    }

    protected Direction direction;

    public Tank(int x, int y, Direction direction,Image[] image) {
        this(x,y,direction,false,image);
    }

    public Tank(int x, int y, Direction direction,boolean enemy,Image[] image) {
        super(x,y,image);
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
        speed = 5;
    }

    private void determineDirection(){
        //0:上 1:下 2:左 3:右
        if (dirs[0] && !dirs[1] && !dirs[2] && !dirs[3])direction = Direction.UP;
        else if (dirs[0] && !dirs[1] && dirs[2] && !dirs[3])direction = Direction.UP_LEFT;
        else if (dirs[0] && !dirs[1] && !dirs[2] && dirs[3])direction = Direction.UP_RIGHT;
        else if (!dirs[0] && dirs[1] && !dirs[2] && !dirs[3])direction = Direction.DOWN;
        else if (!dirs[0] && dirs[1] && dirs[2] && !dirs[3])direction = Direction.DOWN_LEFT;
        else if (!dirs[0] && dirs[1] && !dirs[2] && dirs[3])direction = Direction.DOWN_RIGHT;
        else if (!dirs[0] && !dirs[1] && dirs[2] && !dirs[3])direction = Direction.LEFT;
        else if (!dirs[0] && !dirs[1] && !dirs[2] && dirs[3])direction = Direction.RIGHT;
    }

    public void move(){
        oldX=x;
        oldY=y;
        switch (direction){
            case UP:
                y-=speed;
                break;
            case DOWN:
                y+=speed;
                break;
            case LEFT:
                x-=speed;
                break;
            case RIGHT:
                x+=speed;
                break;
            case UP_LEFT:
                y-=speed;
                x-=speed;
                break;
            case UP_RIGHT:
                y-=speed;
                x+=speed;
                break;
            case DOWN_LEFT:
                y+=speed;
                x-=speed;
                break;
            case DOWN_RIGHT:
                y+=speed;
                x+=speed;
                break;
        }
    }

    public void collision(){
        //邊界偵測
        if(x<0){
            x=0;
        }else if(x>TankWar.gameClient.getWidth()-width){
            x=TankWar.gameClient.getWidth()-width;
        }

        if(y<0){
            y=0;
        }else if(y>TankWar.gameClient.getHeight()-height){
            y=TankWar.gameClient.getHeight()-height;
        }

        //牆面碰撞
        for (GameObject object:TankWar.gameClient.getGameObjects()){
            if (object!=this){
                if (object.getRectangle().intersects(this.getRectangle())){
                    x=oldX;
                    y=oldY;
                    return;
                }
            }
        }
    }

    public void fire(){
        Bullet bullet = new Bullet(x,y,direction,false,TankWar.gameClient.bulletImg);
        TankWar.gameClient.addGameObject(bullet);
    }

    public boolean collisionBound(){
        boolean collision = false;
        //邊界偵測
        if(x<0){
            x=0;
            collision = true;
        }else if(x>TankWar.gameClient.getWidth()-width){
            x=TankWar.gameClient.getWidth()-width;
            collision = true;
        }

        if(y<0){
            y=0;
            collision = true;
        }else if(y>TankWar.gameClient.getHeight()-height){
            y=TankWar.gameClient.getHeight()-height;
            collision = true;
        }
        return collision;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void draw(Graphics g) {
        if (!isStop()) {
            determineDirection();
            move();
            collision();
        }
        g.drawImage(image[direction.ordinal()], x, y, null);
    }

    private boolean isStop(){
        for (boolean dir:dirs){
            if (dir){
                return false;
            }
        }
        return true;
    }
}
