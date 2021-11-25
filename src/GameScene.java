import javafx.animation.AnimationTimer;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import java.util.ArrayList;

public class GameScene extends Scene {

    private int numberOfLives = 3;
    private double posXinit =270;
    private Camera camera;
    private StaticThing backgL, backgR;
    private Hero hero;
    private Fireball fireball;
    private Boxes gameover,restart,pause,shootOk;
    private ArrayList<Foe> foes = new ArrayList<>(); // liste des foes
    private ArrayList<Double> foesX = new ArrayList<>(); //liste avec les positions x des foes
    private ArrayList<Boxes> heart = new ArrayList<>(); //liste des coeurs pour le nombre de vies

    AnimationTimer timer;

    //CONSTRUCTOR
    public GameScene(Group root, double length, double width){
        super(root, length, width);

        double a = Math.random()*10%3 + 1;
        int b;
        b = (int) a; //Nombre d'ennemis aléatoire, réduit entre 1 et 3 pour pas que le jeu soit trop dur à jouer
        System.out.println("number of foes for this round = "+b);

        backgL = new StaticThing(0,0,"desert.png",0,0,800,400);
        root.getChildren().add(backgL.getImg());
        backgR = new StaticThing(800,0,"desert.png",0,0,800,400);
        root.getChildren().add(backgR.getImg());
        hero = new Hero(150,250,"heros.png",numberOfLives);
        root.getChildren().add(hero.getImg());
        camera = new Camera(150,250, hero);
        //FOES
        for (int i=0;i<b;i++){ //mettre b à la place de 3
            double a1 = Math.random()*10;
            int b1;
            b1 = (int) a1;
            foes.add(new Foe(810+81*b1,250,"ennemy.png"));
            root.getChildren().add(foes.get(i).getImage());
            foesX.add(foes.get(i).getX());
        }
        fireball = new Fireball(posXinit,-800,"heros.png");
        root.getChildren().add(fireball.getImg());
        gameover = new Boxes(210,-800,380,380,"gameover.png"); //210 pour être au milieu de l'écran
        root.getChildren().add(gameover.getBox());
        restart = new Boxes(260,-800,380,100,"restart.png");
        root.getChildren().add(restart.getBox());
        pause = new Boxes(140,-800,800,200,"pause.png");
        root.getChildren().add(pause.getBox());
        shootOk = new Boxes(310,50,86,86,"target.png"); // x = -310
        root.getChildren().add(shootOk.getBox());
        //HEART
        for (int i=0;i<numberOfLives;i++) {
            heart.add(new Boxes(64*i, 0, 64, 64, "heart.png"));
            root.getChildren().add(heart.get(i).getBox());
        }

        //ANIMATION
        timer = new AnimationTimer() {
            @Override
            public void handle(long time) {
                hero.update(time,foes,fireball,shootOk,heart);
                camera.update(time);
                for (int i=0;i<foes.size();i++) {
                    foes.get(i).update(new Rectangle2D(200, hero.getY(), 60, 80), new Rectangle2D(fireball.getX(), fireball.getY(), 50, 60), hero,foesX);
                    foesX.add(foes.get(i).getX());
                }
                fireball.update(hero,foes,posXinit);
                shootOk.blinking(root);
                updateGS();
            }
        };
        timer.start();

        //KEY PRESSED
        this.setOnKeyPressed(
                t -> {
                    if (t.getCode() == KeyCode.SPACE){ //to jump
                        hero.jump();
                    }
                    if (t.getCode() == KeyCode.UP) { //to speed up
                        hero.dxPlus();
                    }
                    if (t.getCode() == KeyCode.DOWN) { //to speed down
                        hero.dxMinus();
                    }
                    if (t.getCode() == KeyCode.F){ //to shoot
                        hero.fire();
                    }
                    if (t.getCode() == KeyCode.R && hero.getLives() == 0){ //to restart the game when it's over
                        hero.setLives(numberOfLives);
                        hero.setReload(0);
                        timer.start();
                        gameover.restartBox();
                        restart.restartBox();
                        for (int i=0;i< heart.size();i++){heart.get(i).restartBox();}
                    }
                    if (t.getCode() == KeyCode.P){ //mettre en pause
                        pause.setY(40);
                        pause.displayBox();
                        timer.stop();
                    }
                    if (t.getCode() == KeyCode.M){ // redemarrer le jeu
                        pause.setY(-800);
                        pause.displayBox();
                        timer.start();
                    }
                });
    }

    //UPDATE GAMESCENE
    public void updateGS(){
        if (hero.getLives() == 0){
            System.out.println("GAME OVER");
            gameover.setY(-50);
            gameover.displayBox();
            restart.setY(280);
            restart.displayBox();
            shootOk.setX(-300);
            shootOk.displayBox();
            timer.stop();
        }
        double position = camera.getX()%800; //position de la camera %800 taille img
        backgL.getImg().setX(0-position);
        backgR.getImg().setX(800-position); //faire passer l'img de gauche à droite, etc...
    }
}
