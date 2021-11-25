public class Camera {
    private double x, xinit = 150, vx = 0, vxinit = 0, ax;
    private double y, yinit = 200, vy = 0, vyinit = 0, ay;
    private Hero hero;
    private double time_sample = 0.54;

    //CONSTRUCOR
    public Camera(double x, double y, Hero hero){
        this.x = x;
        this.y = y;
        this.hero = hero;
    }

    //GETTER
    public double getX() {
        return x;
    }
    public double getY() {
        return y;
    }

    //UPDATE
    public void update(long time){
        ax = 1*(hero.getX()-x) - 1.2*vx;
        vx = ax * time_sample + vxinit;
        x = vx * time_sample + xinit;
        ay = 1*(hero.getY()-y) + 1.2*vy;
        vy = ay * time_sample + vyinit;
        y = vy * time_sample + yinit;
    }

    @Override
    public String toString(){
        return "Coordonnées : "+x+";"+y;
    }
}
