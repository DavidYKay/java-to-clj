(def ^Geometry coloredMesh (Geometry. "ColoredMesh" cMesh))
(.setMode mesh Mesh$Mode/Points)
(.updateBound mesh)
(.setStatic mesh)
(def ^Geometry points (Geometry. "Points" mesh))
(.setMaterial points mat)
(.attachChild rootNode points)
(.attachChild rootNode geo)
