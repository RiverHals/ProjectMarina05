package problem;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import java.util.Random;

/**
 * Класс точки
 */
public class Point {
    /**
     * константа множества 1
     */
    public static final int SET_1 = 0;
    /**
     * константа множества 2
     */
    public static final int SET_2 = 1;
    /**
     * номер множества
     */
    int setNumber;
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
     * @param setNumber номер множества, к которому принадлежит точка
     */
    Point(double x, double y, int setNumber) {
        this.x = x;
        this.y = y;
        this.setNumber = setNumber;
    }

    /**
     * Получить случайную точку
     *
     * @return случайная точка
     */
    static Point getRandomPoint() {
        Random r = new Random();
        double nx = (double) r.nextInt(50) / 25 - 1;
        double ny = (double) r.nextInt(50) / 25 - 1;
        int nSetVal = r.nextInt(2);
        return new Point(nx, ny, nSetVal);
    }

    /**
     * Рисование точки
     *
     * @param gl переменная OpenGl для рисования
     */
    void render(GL2 gl) {
        if (isSolution)
            gl.glColor3d(1.0, 0.0, 0.0);
        else
            switch (setNumber) {
                case Point.SET_1:
                    gl.glColor3d(0.0, 1.0, 0.0);
                    break;
                case Point.SET_2:
                    gl.glColor3d(0.0, 0.0, 1.0);
                    break;
            }
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
        return "Точка с координатами: {" + x + "," + y + "} из множества: " + setNumber;
    }
}
