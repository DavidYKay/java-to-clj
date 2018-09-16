(ns java-to-clj.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [java-to-clj.expressions :refer [to-str]]
            )
  (:import
   [com.github.javaparser JavaParser]
   [java.util Optional]
   [com.github.javaparser.ast CompilationUnit]
   ))

(def block-str (slurp (io/resource "code/Block.java")))
(def raw-block-str (slurp (io/resource "code/BlockWithDots.java")))
(def hello-class "class A { }")
(def hello-expression "class A { }")

(defn parse-statement []
  (let [s "Geometry coloredMesh = new Geometry (\"ColoredMesh\", cMesh);"]
    (JavaParser/parseStatement s)))

(defn parse-expression []
  (let [s "(x == 1)"]
    (JavaParser/parseExpression s)))

(defn parse-block []
  (let [^BlockStmt block (JavaParser/parseBlock block-str)]
    block))

(defn hello-parse []
  (let [^CompilationUnit compilationUnit (JavaParser/parse hello-class)
        ^Optional a (.getClassByName compilationUnit "A")]
    (when (.isPresent a)
      (.get a))))

(def block (parse-block))

;; VariableDeclarationStatement
(def statement (parse-statement))

(def statements (->> block
                     .getChildNodes))

statements

(def variable-declarator
  (->> statement
       .getChildNodes
       first
       .getVariables
       first))

variable-declarator

(def initializer (-> (.getInitializer variable-declarator)
                     .get))

initializer

;;(variable-declarator-to-clj variable-declarator)

;;.getChildNodes

;;(convert-block block)
;;(->> (.getStatements block) first)

;;(initializer-to-clj initializer)
