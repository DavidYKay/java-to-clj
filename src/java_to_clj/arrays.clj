(ns java-to-clj.arrays
  (:require [java-to-clj.protocols :refer [to-clj]])
  (:import [com.github.javaparser.ast.type ArrayType]))

(defn array-initialize []

  )

(defmethod to-clj ArrayType [e]
  (str e))
