package problem;
import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;
import java.util.Random;

public class Circle {
    Vector center;
    double rad;

    public Circle(Vector center, double rad) {
        this.center = center;
        this.rad = rad;
    }

    public void render(GL2 gl){
        Figures.renderCircle(gl,center,rad,false);
    }

    public static Circle getRandomCircle(){
        Random random = new Random();
        return new Circle(
                new Vector(random.nextDouble()*2-1,random.nextDouble()*2-1),
                random.nextDouble()*0.3
        );
    }
}
