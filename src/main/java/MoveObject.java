import java.awt.*;

public abstract class MoveObject extends GameObject{
    protected int speed;
    protected boolean enemy;
    protected Direction direction;

    public MoveObject(int x, int y, Direction direction, Image[] image) {
        this(x,y,direction,false,image);
    }

    public MoveObject(int x, int y, Direction direction, boolean enemy, Image[] image) {
        super(x,y,image);
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.enemy = enemy;
        speed = 5;
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

    public abstract void collision();

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
        if (!alive){
            return;
        }
        move();
        collision();
        g.drawImage(image[direction.ordinal()], x, y, null);
    }

}
