package io.github.jdiemke.triangulation;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class TriangleSoupTest {

    @Test
    public void testThatTriangleSoupIsInitiallyEmpty() {
        TriangleSoup soup = new TriangleSoup();
        List<Triangle2D> triangles = soup.getTriangles();

        Assert.assertNotNull("The returned list should not be null.", triangles);
        Assert.assertEquals("The triangle soup should not contain any triangles if no triangles were added.", 0,
                triangles.size());
    }

}
