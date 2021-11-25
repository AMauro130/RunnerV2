import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

public class Foe {
    private double x, y;
    private boolean hit = false, touched = false; //hit pour héro touché, touched pour ennemy touché par une fireball
    private ImageView img;

    //GETTER & SETTER
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }
    public ImageView getImage() {
        return img;
    }
    public boolean getHit(){return hit;}
    public void setHit(boolean hit) {this.hit = hit;}
    public boolean getTouched(){return touched;}

    //CONSTRUCTOR
    public Foe(double xpos, double ypos, String fileName){
        this.x = xpos;
        this.y = ypos;
        Image spriteSheet = new Image(fileName);
        img = new ImageView(spriteSheet);
        img.setX(xpos);
        img.setY(ypos);
    }

    //UPDATE
    public void update(Rectangle2D heroR, Rectangle2D foeR, Hero hero, ArrayList<Double> foesX){
        //Rectangle2D du foe
        Rectangle2D hitHeroFoe = new Rectangle2D(x,y,60,80); //collision hero/foe
        Rectangle2D hitFoeFireball = new Rectangle2D(x,y,60,80); //collision foe/fireball

        x = x - (hero.getDx()/10 + 3); //évolution du foe
        img.setX(x);

        if (hitHeroFoe.intersects(heroR)){
            hit = true;
        }
        if (hitFoeFireball.intersects(foeR)){
            touched = true;
        }

        if (x < -50 || hit || touched){
            if(x > 800){
                hit = false;
                touched = false;
            } //pour pouvoir rester le hit au tour d'après, juste 1 temps de hit. Pareil pour touched

            double a2 = Math.random() * 10;
            int b2;
            b2 = (int) a2;
            x = 810 + (81 * b2);

            for (int i=0;i<foesX.size();i++) {
                while (Math.abs(x - foesX.get(i% foesX.size())) <= 81 && Math.abs(x - foesX.get(i)) != 0){
                    a2 = Math.random()*10;
                    b2 = (int) a2;
                    x = 810 + (81 * b2);
                }
                img.setX(x);
            }
        }
    }
}
