import java.awt.*;

public class Bullet extends MoveObject{

    public Bullet(int x, int y, Direction direction, boolean enemy, Image[] image) {
        super(x, y, direction, enemy, image);
        speed=10;
    }

    @Override
    public boolean collision(){
        if (collisionBound()){
            alive = false;
            return false;
        }

        //子彈不互相抵銷(火花也不偵測)
        for (GameObject object:TankWar.gameClient.getGameObjects()){
            if (object == this||object instanceof Bullet||object instanceof Explosion){
                continue;
            }
            //偵測坦克碰撞
            if (object instanceof Tank){
                if (((Tank) object).enemy == enemy){
                    continue;
                }
            }
            if (object.getRectangle().intersects(this.getRectangle())){
                    alive = false;
                    //敵方坦克消失
                    if (object instanceof Tank){
                        object.alive = false;
                    }

                    TankWar.gameClient.addGameObject(new Explosion(x,y,GameClient.explosionImg));

                return true;
            }
        }
        return false;
    }
}
