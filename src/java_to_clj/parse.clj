(ns java-to-clj.parse
  (:import
   [com.github.javaparser JavaParser]
   [com.github.javaparser.ast
    CompilationUnit
    stmt.BlockStmt]
   [java.util Optional]
   ))

(defn hello-parse []
  (let [hello-class "class A { }"
        ^CompilationUnit compilationUnit (JavaParser/parse hello-class)
        ^Optional a (.getClassByName compilationUnit "A")]
    (when (.isPresent a)
      (.get a))))

(defn parse-statement [s]
  (JavaParser/parseStatement s))

(defn parse-expression [s]
  (JavaParser/parseExpression s))

(defn parse-block ^BlockStmt [s]
  (JavaParser/parseBlock s))
