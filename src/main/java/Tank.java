import javax.swing.*;
import java.awt.*;

public class Tank extends MoveObject{
    //上下左右四個方向
    private boolean[] dirs = new boolean[4];

    public boolean[] getDirs() {
        return dirs;
    }

    public Tank(int x, int y, Direction direction,Image[] image) {
        this(x,y,direction,false,image);
    }

    public Tank(int x, int y, Direction direction,boolean enemy,Image[] image) {
        super(x,y,direction,enemy,image);
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
        speed = 15;
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

    public void collision(){
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
