import javax.swing.*;
import java.awt.*;

public abstract class GameObject {
    protected int oldX;
    protected int oldY;

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    protected boolean alive;

    protected Image[] image;

    public GameObject(int x, int y, Image[] image) {
        this.x = x;
        this.y = y;
        this.image=image;
        this.alive=true;
        width =image[0].getWidth(null);
        height = image[0].getHeight(null);
    }

    //取得物件寬度
    public Rectangle getRectangle(){
        return new Rectangle(x,y,width,height);
    }

    abstract void draw(Graphics g);
}
