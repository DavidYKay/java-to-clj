(ns java-to-clj.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [java-to-clj.convert]
            [java-to-clj.protocols :refer [to-clj]]
            [java-to-clj.parse :refer [parse-block parse-statement]]
            )
  (:import
   [com.github.javaparser JavaParser]
   [java.util Optional]
   [com.github.javaparser.ast CompilationUnit]
   ))

(defn load-file [p]
  (-> p
      io/resource
      slurp
      str/trim))

(defonce block-str     (load-file "code/Block.java"))
(defonce raw-block-str (load-file "code/BlockWithDots.java"))
(defonce for-each-str (load-file "code/ForEach.java"))
(defonce string-concat-str (load-file "code/StringConcat.java"))

(def hello-expression "(x == 1)")
(def hello-statement "Geometry coloredMesh = new Geometry (\"ColoredMesh\", cMesh);")

(def block (parse-block block-str))


(def lines ["float[] normals = new float[12];"
            "int [] indexes = { 2,0,1, 1,3,2 };"
            "normals = new float[]{0,0,1, 0,0,1, 0,0,1, 0,0,1};"
            "mesh.setBuffer(Type.Normal, 3, BufferUtils.createFloatBuffer(normals));"])

(def for-each (parse-statement for-each-str))

(-> for-each

    (.getVariable)
    )


;;(def s (parse-statement string-concat-statement))

#_(def a (->
        (nth lines 0)
        parse-statement
        .getExpression
        .getVariables
        first
        .getInitializer
        .get
        ))

