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

(def block-str (slurp (io/resource "code/Block.java")))
(def raw-block-str (slurp (io/resource "code/BlockWithDots.java")))

(def hello-expression "(x == 1)")
(def hello-statement "Geometry coloredMesh = new Geometry (\"ColoredMesh\", cMesh);")

(def block (parse-block block-str))
(defonce string-concat-statement (str/trim (slurp (io/resource "code/StringConcat.java"))))


(def lines ["float[] normals = new float[12];"
            "int [] indexes = { 2,0,1, 1,3,2 };"
            "normals = new float[]{0,0,1, 0,0,1, 0,0,1, 0,0,1};"
            "mesh.setBuffer(Type.Normal, 3, BufferUtils.createFloatBuffer(normals));"])

;;(def s (parse-statement string-concat-statement))

(def a (->
        (nth lines 0)
        parse-statement
        .getExpression
        .getVariables
        first
        .getInitializer
        .get
        ))

(def b (->
        (nth lines 1)
        parse-statement
        .getExpression
        .getVariables
        first
        .getInitializer
        .get
        ;;.getType
        ;;to-clj
        ;;.getValue
        ))

(-> b
    ;;.getInitializer
    ;;.get
    ;;to-clj
    )

;;(-> s .getExpression .getVariables first .getType)

;;(def b (->> s
;;            .getExpression
;;
;;            .getVariables
;;            first
;;            .getInitializer
;;            .get)
;;  )
;;
;;(-> b
;;    .getLeft
;;    .getLeft
;;    .getLeft
;;    .getLeft)
