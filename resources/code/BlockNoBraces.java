Geometry coloredMesh = new Geometry ("ColoredMesh", cMesh);
mesh.setMode(Mesh.Mode.Points);
mesh.updateBound();
mesh.setStatic();
Geometry points = new Geometry("Points", mesh);
points.setMaterial(mat);
rootNode.attachChild(points);
rootNode.attachChild(geo);
