(ns java-to-clj.util
  (:require
   [java-to-clj.protocols :refer [to-clj]])
  (:import
   [com.github.javaparser.ast
    body.VariableDeclarator]))

(defn non-def-variable [^VariableDeclarator vd]
  (let [n (.getName vd)
        t (.getType vd)
        ;;i (.getInitializer vd)
        ]
    (format "^%s %s" t n)))
