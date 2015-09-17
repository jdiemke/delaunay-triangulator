## Delaunay Triangulator Library
A simple and lean Java implementation of an incremental 2D Delaunay triangulation algorithm.
### How to use
The code below shows how to use the `DelaunayTriangulator` class in order to triangulate a given set of points:
```java
try {
    Vector<Vector2D> pointSet = loadPointSet("data/normal-formation.conf");
    
    delaunayTriangulator = new DelaunayTriangulator(pointSet);
    delaunayTriangulator.triangulate();
    
    Vector<Triangle2D> triangleSoup = delaunayTriangulator.getTriangles();
    
} catch (NotEnoughPointsException e) {
}
```
The constructor throws a `NotEnoughPointsException` if it is invoked with less than three points.
### How to build
The Delaunay TriangulatorL ibrary uses Gradle as build tool and makes use of it's multi project build capabilities. Each subproject contains it's own build file and can be build separately. Hence you can build only the part you want. For example if you just want to build the library, then it is sufficient to type:
```bash
gradle library:build
```
This will cause Gradle to build the `DelaunayTriangulator-1.0.0.jar` library artefact in `library/build/libs/`. If your just want to build the example, then type:
```bash
gradle example:build
```
This causes Gradle to build the `example.zip` and `example.tar` distribution artefacts in `example/build/distributions/`.
In case you want to build the whole multi project then type:
```bash
gradle build
```
