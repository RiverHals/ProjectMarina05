package problem;

import javax.media.opengl.GL;
import javax.media.opengl.GL2;
import javax.media.opengl.GL2GL3;

public class Figures {
    public static void renderPoint(GL2 gl, Vector pos, float size) {
        gl.glPointSize(size);
        gl.glBegin(GL.GL_POINTS);
        gl.glVertex2d(pos.x, pos.y);
        gl.glEnd();
    }

    public static void renderLine(GL2 gl, Vector A, Vector B, float width) {
        gl.glLineWidth(width);
        gl.glBegin(GL.GL_LINES);
        gl.glVertex2d(A.x, A.y);
        gl.glVertex2d(B.x, B.y);
        gl.glEnd();
    }

    public static void renderTriangle(GL2 gl, Vector A, Vector B, Vector C, boolean filled) {
        if (filled) {
            gl.glBegin(GL.GL_TRIANGLES);
            gl.glVertex2d(A.x, A.y);
            gl.glVertex2d(B.x, B.y);
            gl.glVertex2d(C.x, C.y);
            gl.glEnd();
        } else {
            gl.glBegin(GL.GL_LINE_STRIP);
            gl.glVertex2d(A.x, A.y);
            gl.glVertex2d(B.x, B.y);
            gl.glVertex2d(C.x, C.y);
            gl.glVertex2d(C.x, C.y);
            gl.glEnd();
        }
    }

    public static void renderQuad(GL2 gl, Vector A, Vector B, Vector C, Vector D, boolean filled) {

        gl.glBegin(GL2GL3.GL_QUADS);
        gl.glVertex2d(A.x, A.y);
        gl.glVertex2d(B.x, B.y);
        gl.glVertex2d(C.x, C.y);
        gl.glVertex2d(D.x, D.y);
        gl.glEnd();
    }

    public static void renderCircle(GL2 gl, Vector A, double rad, boolean filled) {
        int n = 200;
        if (filled) {
            gl.glBegin(GL.GL_TRIANGLE_FAN);
            gl.glVertex2d(A.x, A.y);
            for (int i = 0; i <= n; i++) {
                double angle = (2 * Math.PI) / n * i;
                double x = rad * Math.cos(angle) + A.x;
                double y = rad * Math.sin(angle) + A.y;
                gl.glVertex2d(x, y);
            }
            gl.glEnd();
        } else {
            gl.glBegin(GL.GL_LINE_STRIP);
            for (int i = 0; i <= n; i++) {
                double angle = (2 * Math.PI) / n * i;

                double x = rad * Math.cos(angle) + A.x;
                double y = rad * Math.sin(angle) + A.y;
               // System.out.printf("%.3f %.3f\n", x,y);
                gl.glVertex2d(x, y);
            }
            gl.glEnd();
        }
    }

}