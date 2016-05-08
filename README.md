## A 2D Delaunay Triangulation Library for Java
A simple and lean Java implementation of an incremental 2D Delaunay triangulation algorithm.
### How to get
Type the following command into your shell:
```bash
git clone https://github.com/jdiemke/delaunay-triangulator.git
```
This will create a copy of the repository in your current working directory.
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
The Delaunay triangulator library uses Gradle as a build tool and makes use of its multi project build capabilities. Each subproject contains its own build file and can be build separately. Hence, you can build only the part you want. For example, if you just want to build the library, then it is sufficient to locate into the project's root directory and type the following command into your shell:
```bash
gradle library:build
```
This will cause Gradle to build the `DelaunayTriangulator-1.0.3.jar` library artifact in `library/build/libs/`. If you just want to build the example, then type the following into your shell:
```bash
gradle example:build
```
This causes Gradle to build the `example.zip` and `example.tar` distribution artifacts in `example/build/distributions/`. In case you want to build the whole multi project, then type:
```bash
gradle build
```
### API Documentation
The Delaunay triangulator API documentation can be found [here](http://jdiemke.github.io/delaunay-triangulator/javadoc). You can also build it yourself using the javadoc Gradle task by typing the following into your shell:
```bash
gradle library:javadoc
```
This causes Gradle to build the javadoc API documentation artifacts in `library/build/docs/javadoc`.
### Dependencies
The Delaunay triangulation library itself does not have any dependencies; however, the example subproject uses JOGL 2.3.2 for rendering a triangulated point set using OpenGL. See [http://jogamp.org/](http://jogamp.org/) for further details on JOGL.
### Demo Application
The screenshot below shows the demo application from the example project. In order to create a Delaunay triangulation you have to add points to the canvas by pressing the left mouse button. You need at least 3 points for the triangulation to be created.

![demo screenshot](https://raw.githubusercontent.com/jdiemke/delaunay-triangulator/master/images/example_application.png "Demo Application")
### License
The Delaunay triangulation library is protected by the very permissive MIT license. This means you can do anything you want with the code with some minor restrictions related to attribution and liability (see the license below for more details). Nevertheless, it is prefered, but not necessary, that you share your enhancements concerning the project's source code.
```
The MIT License (MIT)

Copyright (c) 2015 Johannes Diemke

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```
### History
##### 2016-05-05 / Release 1.0.3
-   Adjusted dependencies from JOGL 2.3.1 to JOGL 2.3.2
-   Adjusted the example application to incorporate user interaction (adding points by clicking in the window)
-   Changed coding style guides (all indentation should be done with 4 space characters from now on)
-   The Delaunay triangulation library now uses the List interfaces in order to decouple client code from using a specific implementation for providing a list of 2d points 

##### 2015-11-22 / Release 1.0.2
-   Added [project site](http://jdiemke.github.io/delaunay-triangulator) and [Javadoc](http://jdiemke.github.io/delaunay-triangulator/javadoc)

##### 2015-09-19 / Release 1.0.1
-   Added the MIT License to the project

##### 2015-09-18 / Release 1.0.0
-   Minor refactoring
-   Created Gradle build files
-   Initial commit to GitHub

##### 2010-08-01 / Release 0.0.0
-   Initial implementation of the Delaunay triangulation algorithm
