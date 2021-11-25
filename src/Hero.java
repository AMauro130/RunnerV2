import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;

//HERO ET FOE ETAIT TROP DIFFERENT, PLUS SIMPLE 2 CLASSES DIFFERENTES

public class Hero {
    private double x, vx, ax, y, vy, ay; //position, vitesse, acceleration
    private long dx = 70;
    private ImageView img;
    private boolean jmp, shoot;
    private int lives;
    private double invincibility = 0, reload = 0;

    //CONSTRUCTEUR
    public Hero(double xpos, double ypos, String fileName, int lives){
        // index : ligne ; frame : colonne
        this.x = xpos;
        this.vx = 400;
        this.ax = 0;
        this.y = ypos;
        this.vy = 0;
        this.ay = 0;
        this.jmp = false;
        this.lives = lives;
        Image spriteSheet = new Image(fileName);
        img = new ImageView(spriteSheet);
        img.setX(xpos);
        img.setY(ypos);
    }

    //GETTER & SETTER
    public double getX(){return x;}
    public double getY(){return y;}
    public long getDx() {return dx;}
    public double getVx(){return vx;}
    public double getAx(){return ax;}
    public int getLives(){return lives;}
    public void setLives(int lives) {this.lives = lives;}
    public boolean getShoot(){return shoot;}
    public void setShoot(boolean shoot){this.shoot = shoot;}
    public double getReload() {return reload;}
    public void setReload(double reload) {this.reload = reload;}
    public ImageView getImg(){return img;}

    //JUMP
    public void jump(){
        double ayJ = ay;
        ay = -35000;
        jmp = true;
        if (ayJ != 0){ ay = ayJ;}
    }

    //FIRE
    public void fire(){
        shoot = true;
    }

    //Modification de la vitesse
    public void dxPlus() {dx+=10;}
    public void dxMinus() {dx-=10;}

    //UPDATE
    public void update(long time, ArrayList<Foe> foes, Fireball fireball, Boxes shootOk, ArrayList<Boxes> heart){

        if (reload > 0){ //reload du fire
            shoot = false;
            shootOk.setX(-310);
            shootOk.displayBox();
        }
        if (reload == 0){
            shootOk.setX(310);
            shootOk.displayBox();
        }

        double elapsedTime = 0.0166; // =1/60Hz ordi pour calcul de la vitesse en x et y
        x += dx;
        vy = vy + elapsedTime * ay;
        y = y + elapsedTime * vy;

        if (jmp){ //si on saute
            if (vy < 0){ //montée
                img.setViewport(new Rectangle2D(83.5*0, 164*1,85, 100));
                img.setY(y);
                ay = 1300;
                if (shoot){
                    img.setViewport(new Rectangle2D(83.5*0, 164*3,85, 100));
                    img.setY(y);
                }
            }
            if (vy > 0){ //descente
                img.setViewport(new Rectangle2D(83.5*1, 164*1,85, 100));
                img.setY(y);
                if (shoot){
                    img.setViewport(new Rectangle2D(83.5*1, 164*3,85, 100));
                    img.setY(y);
                }
            }
        }

        if (ay == 0 && !shoot){ //si on est au sol tranquille sans tirer
            double frame = (int) ((time / 100000000)) % 6; //chaque 0,1s car parameter du timer est en nanosecondes donc 10^8
            img.setViewport(new Rectangle2D(83.5*frame,164*0,85,100)); //1ère ligne pour la course
        }

        if (ay == 0 && shoot ){ //si on est au sol et on tire
            double frame = (int) ((time / 100000000)) % 6;
            img.setViewport(new Rectangle2D(2+83.5*frame,164*2,77,100)); //2ème ligne pour la course avec shoot
        }

        if (fireball.getX() > 600){ //revenir à courir sans tirer
            double frame = (int) ((time / 100000000)) % 6;
            img.setViewport(new Rectangle2D(83.5*frame,164*0,85,100));
        }

        for (int i=0;i<foes.size();i++) { //invincibilité du héros s'il est touché et on enlève un coeur
            if (foes.get(i).getHit() && invincibility <= 0) {
                invincibility = 25000000000L;
                lives--;
                heart.get(lives).setX(-800);
                heart.get(lives).displayBox();
                System.out.println("lives : " + lives);
            }
        }

        if (invincibility <= 25000000000L) {invincibility -= 200000000;} //temps d'invincibilité assez élévé pour récupérer
        if (invincibility <= 0){invincibility = 0;}

        if (reload <= 25000000000L) {reload -= 200000000;} //temps de reload du fire
        if (reload <= 0){reload = 0;}

        if (y > 250){ //gravité, fin du jump et éviter que le héros tombe du décor....
            ay = 0;
            vy = 0;
            y = 250;
            img.setY(y);
            jmp = false;
        }
    }
}
