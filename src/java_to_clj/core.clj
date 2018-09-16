(ns java-to-clj.core
  (:require [clojure.java.io :as io])
  (:import
   [com.github.javaparser JavaParser]
   [java.util Optional]
   [com.github.javaparser.ast CompilationUnit]
   ))

(def block-str (slurp (io/resource "code/Block.java")))
(def raw-block-str (slurp (io/resource "code/BlockWithDots.java")))
(def hello-class "class A { }")
(def hello-expression "class A { }")

(defn parse-string-as-statements []

  )

(defn parse-statement []
  (let [
        ;;s "Geometry coloredMesh = new Geometry ("ColoredMesh", cMesh);"
        s "int x = 5;"
        expression (JavaParser/parseStatement s)]
    expression
    ))

(defn parse-expression []
  (let [
        ;;s "Geometry coloredMesh = new Geometry ("ColoredMesh", cMesh);"
        s "int x = 5;"
        expression (JavaParser/parseExpression s)]
    expression
    ))

(defn parse-block []
  (let [^BlockStmt block (JavaParser/parseBlock raw-block-str)]
    block
    ))


(defn hello-parse []
  (let [^CompilationUnit compilationUnit (JavaParser/parse hello-class)
        ^Optional a (.getClassByName compilationUnit "A")]
    (when (.isPresent a)
      (.get a))))

;;(defn parse-snippet []
;;  (parse
;;  (format "\n%s\n" snippet)
;;
;;  )

;; (defn find-statements [compilationUnit]
;;   (let [a (.findAll compilationUnit (FieldDeclaration.class).stream())
;;         b (.filter a (fn [f]
;;                        (and (.isPublic f)
;;                             (not (!isStatic f)))))
;;         c (.forEach b (fn [f]
;;                         (println "Check field at line "
;;                                    f.getRange().map(r -> r.begin.line).orElse(-1)))

