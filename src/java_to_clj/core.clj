(ns java-to-clj.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [java-to-clj.convert :refer [to-clj]]
            [java-to-clj.parse :refer [parse-block parse-statement]]
            )
  (:import
   [com.github.javaparser JavaParser]
   [java.util Optional]
   [com.github.javaparser.ast CompilationUnit]
   ))

(def block-str (slurp (io/resource "code/Block.java")))
(def raw-block-str (slurp (io/resource "code/BlockWithDots.java")))


(def block (parse-block))

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
