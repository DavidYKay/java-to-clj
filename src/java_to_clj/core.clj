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
  (let [
        ;s "int x = 5;"
        s "Geometry coloredMesh = new Geometry (\"ColoredMesh\", cMesh);"
        ]
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


(defn convert-block [block]
  )

(def block (parse-block))

;; VariableDeclarationStatement
(def statement (parse-statement))

(def variable-declarator
  (->> statement
       .getChildNodes
       first
       .getVariables
       first
       ))

(def initializer (-> (.getInitializer variable-declarator)
                     .get))

initializer

(defn initializer-to-clj [i]
  (let [t (.getType i)
        args (->> (.getArguments i)
                  (map to-str))]
    (format "(%s. %s)" t
            (str/join " " args))))

(defn variable-declarator-to-clj [vd]
  (let [n (.getName vd)
        ;; t (.getType vd)
        i (.getInitializer vd)
        i (if (.isPresent i)
            (initializer-to-clj (.get i))
            nil)]
    (format "^%s (%s. %s)" n n i)
    ))

;;(variable-declarator-to-clj variable-declarator)

;;.getChildNodes

;;(convert-block block)
;;(->> (.getStatements block) first)


(initializer-to-clj initializer)
