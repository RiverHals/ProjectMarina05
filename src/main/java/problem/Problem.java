package problem;

import javax.media.opengl.GL2;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * Класс задачи
 */
public class Problem {
    /**
     * текст задачи
     */
    public static final String PROBLEM_TEXT = "Постановка задачи" +
            "На плоскости задано множество точек.\n " +
            "Найти окружность, содержащую внутри себя хотя бы две точки множества,\n " +
            "имеющую наибольшую плотность точек внутри себя (количество точек на \n" +
            "единицу площади). В качестве ответа нарисовать найденную окружность и\n" +
            " выделить все точки, находящиеся внутри нее. ";

    /**
     * заголовок окна
     */
    public static final String PROBLEM_CAPTION = "Итоговый проект ученицы 10-7 Малышкиной Марины";

    /**
     * путь к файлу
     */
    private static final String FILE_NAME = "points.txt";

    /**
     * список точек
     */
    private ArrayList<Point> points;
    Circle circle;

    /**
     * Конструктор класса задачи
     */
    public Problem() {
        points = new ArrayList<>();
    }

    /**
     * Добавить точку
     *
     * @param x координата X точки
     * @param y координата Y точки
     */
    public void addPoint(double x, double y) {
        Point point = new Point(x, y);
        points.add(point);
    }

    static double dist(Vector a,Vector b){
        Vector d=vdiff(a,b);
        return Math.sqrt(Math.pow(d.x,2)+Math.pow(d.y,2));
    }
    static Vector vdiff(Vector a,Vector b){
        return new Vector(a.x-b.x,a.y-b.y);
    }

    static Vector midpoint(Vector a,Vector b){
        return new Vector(0.5 * (a.x + b.x), 0.5 * (a.y + b.y));
    }
    static Circle getCircleOnRadius(Vector a,Vector b) {
        return new Circle(midpoint(a,b), 0.5 * dist(a, b));
    }
    static Circle getCircleThreePoints(Vector a,Vector b,Vector c) {
        // перпендикуляр в центре ab
        Vector mab=midpoint(a,b);
        Vector dab=vdiff(a,b);
        // перпендикуляр в центре bc
        Vector mbc=midpoint(b,c);
        Vector dbc=vdiff(b,c);
        // линейная система для нахождения пересечения перпендикуляров
        double c11=dab.x,c12=dab.y,
                c21=dbc.x,c22=dbc.y,
                f1=mab.x*c11+mab.y*c12,
                f2=mbc.x*c21+mbc.y*c22;
        double det=c11*c22-c12*c21,
                xc=(f1*c22-c12*f2)/det,
                yc=-(c11*f2-f1*c12)/det;
        Vector center=new Vector(xc,yc);
        return new Circle(center,dist(center,a));
    }

    int cointPointsInside(Circle c,int s1,int s2,int s3){
        int num=0;
        for(int i=0,n=points.size();i<n;i++){
            if(i==s1||i==s2||i==s3){
                num++; //уже выбраны
            }
            else if(dist(points.get(i),c.center)<=c.rad){
                num++;
            }
        }
        return num;
    }
    double calculateDensity(Circle c,int s1,int s2,int s3){
        int n=cointPointsInside(c,s1,s2,s3);
        return n/(Math.PI*c.rad*c.rad);
    }

    /**
     * Решить задачу
     */
    public void solve() {
        // перебираем пары и тройки точек
        double maxdensity = 0;
        Circle maxdensitycircle = null;

        for (int i = 0, n = points.size(); i < n; i++) {
            Point pi = points.get(i);
            for (int j = i + 1; j < n; j++) {
                Point pj = points.get(j);

                // 1. две точки на границе окружности (на краях диаметра)
                // 2. некая третья точка есть на границе окружности

                // любая окружность характеризуется центром и радиусом
                // цикл по всем вариантам
                // 1. тройки точек
                // 2. "специальный" случай на диаметре
                for (int k = j + 1; k <= n; k++) { // перебор: все тройки + диаметр
                    Circle c;
                    if (k >= n) {
                        // на диаметре, специальный случай k=n (k выходящее за границы индекса 0..n-1 мы используем как флаг использовать пару точек на диаметре, а не тройку точек
                        c = getCircleOnRadius(pi, pj);
                    } else {
                        Point pk = points.get(k);
                        c = getCircleThreePoints(pi, pj, pk);
                    }

                    double density = calculateDensity(c, i, j, k);
                    if (density >= maxdensity) {
                        // больше записанной
                        maxdensity = density;
                        maxdensitycircle = c;
                    }
                } // end for k
            } // end for j
        } // end for i

        // имеем результат:
        //    maxdensitycircle - окружность;
        //    maxdensity - плотность;

        circle = maxdensitycircle;
    }

    /**
     * Загрузить задачу из файла
     */
    public void loadFromFile() {
        points.clear();
        try {
            File file = new File(FILE_NAME);
            Scanner sc = new Scanner(file);
            // пока в файле есть непрочитанные строки
            while (sc.hasNextLine()) {
                double x = sc.nextDouble();
                double y = sc.nextDouble();
                sc.nextLine();
                Point point = new Point(x, y);
                points.add(point);
            }
        } catch (Exception ex) {
            System.out.println("Ошибка чтения из файла: " + ex);
        }
    }

    /**
     * Сохранить задачу в файл
     */
    public void saveToFile() {
        try {
            PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME));
            for (Point point : points) {
                out.printf("%.2f %.2f %d\n", point.x, point.y);
            }
            out.close();
        } catch (IOException ex) {
            System.out.println("Ошибка записи в файл: " + ex);
        }
    }

    /**
     * Добавить заданное число случайных точек
     *
     * @param n кол-во точек
     */
    public void addRandomPoints(int n) {
        for (int i = 0; i < n; i++) {
            points.add(Point.getRandomPoint());
        }
    }

    /**
     * Очистить задачу
     */
    public void clear() {
        points.clear();
        circle = null;
    }

    /**
     * Нарисовать задачу
     *
     * @param gl переменная OpenGL для рисования
     */
    public void render(GL2 gl) {
        for (Point point : points)
            point.render(gl);
        if (circle != null)
            circle.render(gl);
    }
}
