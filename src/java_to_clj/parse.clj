(ns java-to-clj.parse

  (:import
   [com.github.javaparser JavaParser]
   [com.github.javaparser.ast
    CompilationUnit
    stmt.BlockStmt]
   [java.util Optional]
   ))

(def hello-class "class A { }")

(defn hello-parse []
  (let [^CompilationUnit compilationUnit (JavaParser/parse hello-class)
        ^Optional a (.getClassByName compilationUnit "A")]
    (when (.isPresent a)
      (.get a))))

(defn parse-statement []
  (let [s "Geometry coloredMesh = new Geometry (\"ColoredMesh\", cMesh);"]
    (JavaParser/parseStatement s)))

(defn parse-expression []
  (let [s "(x == 1)"]
    (JavaParser/parseExpression s)))

(defn parse-block ^BlockStmt [s]
  (JavaParser/parseBlock s))
