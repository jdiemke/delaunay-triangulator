
import java.awt.Frame;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.Vector;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

import io.github.jdiemke.triangulation.DelaunayTriangulator;
import io.github.jdiemke.triangulation.NotEnoughPointsException;
import io.github.jdiemke.triangulation.Triangle2D;
import io.github.jdiemke.triangulation.Vector2D;

/**
 * Simple implementation of an incremental 2D Delaunay triangulation algorithm
 * written in Java.
 * 
 * @author Johannes Diemke
 */
public class ExampleDelaunayTriangulation implements GLEventListener {

    DelaunayTriangulator delaunayTriangulator;

    public static void main(String[] args) {
        Frame frame = new Frame("Delaunay Triangulation");

        GLCanvas canvas = new GLCanvas();
        canvas.addGLEventListener(new ExampleDelaunayTriangulation());

        frame.add(canvas);
        frame.setSize(700, 480);

        // final Animator animator = new Animator(canvas);

        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                // Run this on another thread than the AWT event queue to
                // make sure the call to Animator.stop() completes before
                // exiting
                new Thread(new Runnable() {
                    public void run() {
                        // animator.stop();
                        System.exit(0);
                    }
                }).start();
            }
        });
        frame.setVisible(true);
        // animator.start();
    }

    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glDisable(GL.GL_CULL_FACE);
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glDepthFunc(GL.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL.GL_NICEST);

        gl.setSwapInterval(1);

        Vector<Vector2D> pointSet = loadPointSet("data/normal-formation.conf");
        // intSet.add(new Vector2D(5, 3));

        try {
            delaunayTriangulator = new DelaunayTriangulator(pointSet);
        } catch (NotEnoughPointsException e) {
            // handle exception here
        }

        // delaunay is in general faster if the points of the point set are
        // added in a random order. this is a custom permutation for
        // tracking down bugs.
        int[] permutation = new int[] { 56, 6, 57, 4, 85, 93, 55, 67, 84, 104, 107, 83, 30, 38, 8, 108, 29, 36, 106, 95,
                86, 100, 111, 73, 25, 74, 79, 31, 16, 40, 32, 14, 80, 81, 103, 68, 51, 101, 42, 3, 0, 49, 89, 75, 21,
                54, 24, 47, 82, 90, 44, 17, 19, 105, 92, 2, 99, 12, 13, 65, 94, 87, 33, 72, 97, 96, 102, 78, 48, 91, 50,
                110, 26, 88, 112, 41, 18, 43, 39, 113, 20, 60, 23, 58, 62, 64, 77, 10, 63, 114, 66, 9, 69, 22, 98, 15,
                59, 34, 28, 11, 109, 1, 46, 37, 61, 70, 45, 5, 35, 71, 7, 52, 53, 27, 76 };

        // shuffle with a permutation that leads to an erroneous delaunay
        // triangulation to track down bugs
        // delaunayTriangulator.shuffle(permutation);

        // measure execution time
        long start = System.currentTimeMillis();

        delaunayTriangulator.triangulate();

        long elapsed = System.currentTimeMillis() - start;
        System.out.println("Delaunay Triangulation generated in " + elapsed + " ms");
    }

    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        float aspect;

        if (height == 0) {
            height = 1;
        }

        aspect = (float) width / (float) height;

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, aspect, 0.1f, 100.0f);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        gl.glTranslatef(0.0f, 0.0f, -98.0f);

        // draw all triangles
        gl.glColor3f(1.65f, 0.25f, 0.25f);
        gl.glBegin(GL.GL_LINES);

        for (int i = 0; i < delaunayTriangulator.getTriangles().size(); i++) {
            Triangle2D triangle = delaunayTriangulator.getTriangles().get(i);
            Vector2D a = triangle.a;
            Vector2D b = triangle.b;
            Vector2D c = triangle.c;

            gl.glVertex2d(a.x, a.y);
            gl.glVertex2d(b.x, b.y);
            gl.glVertex2d(b.x, b.y);
            gl.glVertex2d(c.x, c.y);
            gl.glVertex2d(c.x, c.y);
            gl.glVertex2d(a.x, a.y);
        }

        gl.glEnd();

        // draw all points
        gl.glPointSize(5.0f);
        gl.glColor3f(0.2f, 1.2f, 0.25f);

        gl.glBegin(GL.GL_POINTS);

        for (Vector2D vector : delaunayTriangulator.getPointSet()) {
            gl.glVertex2d(vector.x, vector.y);
        }

        gl.glEnd();
    }

    public void displayChanged(GLAutoDrawable drawable, boolean modeChanged, boolean deviceChanged) {
    }

    private Vector<Vector2D> loadPointSet(String fileName) {

        Vector<Vector2D> pointSet = new Vector<Vector2D>();
        BufferedReader input = null;
        try {
            input = new BufferedReader(new FileReader(
                    ExampleDelaunayTriangulation.class.getClassLoader().getResource(fileName).getFile()));

            String line;
            while ((line = input.readLine()) != null) {
                Scanner scanner = new Scanner(line);
                scanner.useDelimiter(" ");

                if (scanner.hasNext()) {
                    String identifier = scanner.next();

                    if (identifier.equals("Ball")) {
                        String xpos = scanner.next();
                        String ypos = scanner.next();
                        pointSet.add(new Vector2D(Double.parseDouble(xpos), Double.parseDouble(ypos)));
                    }
                }
                scanner.close();
            }

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        } finally {
            if (input != null)
                try {
                    input.close();
                } catch (IOException e) {
                }
        }

        return pointSet;
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {
        // TODO Auto-generated method stub

    }
}