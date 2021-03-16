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
            "На плоскости задано множество точек. " +
            "Найти окружность, содержащую внутри себя хотя бы две точки множества, " +
            "имеющую наибольшую плотность точек внутри себя (количество точек на " +
            "единицу площади). В качестве ответа нарисовать найденную окружность и" +
            " выделить все точки, находящиеся внутри нее. ?? Превратить к кружочки??";

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
    private ArrayList<Circle> circles;

    /**
     * Конструктор класса задачи
     */
    public Problem() {
        points = new ArrayList<>();
        circles = new ArrayList<>();
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

    /**
     * Решить задачу
     */
    public void solve() {
        // перебираем пары точек
        for (Point p : points) {
            for (Point p2 : points) {
                // если точки являются разными
                if (p != p2) {
                    // если координаты у них совпадают
                    if (Math.abs(p.x - p2.x) < 0.0001 && Math.abs(p.y - p2.y) < 0.0001) {
                        p.isSolution = true;
                        p2.isSolution = true;
                    }
                }
            }
        }
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
            Point point = Point.getRandomPoint();
            points.add(Point.getRandomPoint());
        }
    }

    /**
     * Очистить задачу
     */
    public void clear() {
        points.clear();
    }

    /**
     * Нарисовать задачу
     *
     * @param gl переменная OpenGL для рисования
     */
    public void render(GL2 gl) {
        for (Point point : points)
            point.render(gl);
    }
}
