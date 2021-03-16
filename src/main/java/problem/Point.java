package problem;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.Random;

/**
 * Класс точки
 */
public class Point {
    /**
     * пересекается ли точка с точкой из другого множества
     * (является ли она решением)
     */
    boolean isSolution = false;
    /**
     * x - координата точки
     */
    double x;
    /**
     * y - координата точки
     */
    double y;

    /**
     * Конструктор точки
     *
     * @param x         координата
     * @param y         координата y
     */
    Point(double x, double y) {
        this.x = x;
        this.y = y;
    }


    /**
     * Получить случайную точку
     *
     * @return случайная точка
     */
    static Point getRandomPoint() {
        Random random = new Random();
        double nx = random.nextDouble()*2-1;
        double ny = random.nextDouble()*2-1;
        return new Point(nx, ny);
    }
    
    /**
     * Рисование точки
     *
     * @param gl переменная OpenGl для рисования
     */
    void render(GL2 gl) {
//        if (isSolution)
//            gl.glColor3d(1.0, 0.0, 0.0);
//        else
//            switch (setNumber) {
//                case Point.SET_1:
//                    gl.glColor3d(0.0, 1.0, 0.0);
//                    break;
//                case Point.SET_2:
//                    gl.glColor3d(0.0, 0.0, 1.0);
//                    break;
//            }
        gl.glPointSize(3);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(x, y);
        gl.glEnd();
        gl.glPointSize(1);
    }

    /**
     * Получить строковое представление точки
     *
     * @return строковое представление точки
     */
    @Override
    public String toString() {
        return "Точка с координатами: {" + x + "," + y + "}";
    }
}
