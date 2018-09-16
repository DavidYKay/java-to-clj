(ns java-to-clj.convert
  (:require
   [clojure.string :as str]
   [java-to-clj.expressions]
   [java-to-clj.protocols :refer [to-clj]]
   [java-to-clj.statements]
   )
  (:import
   [com.github.javaparser JavaParser]
   [java.util Optional]
   [com.github.javaparser.ast
    body.VariableDeclarator
    expr.Expression
    stmt.BlockStmt
    ]
   )
  )

(defmethod to-clj String [s] s)

(defmethod to-clj VariableDeclarator [vd]
  (let [n (.getName vd)
        ;; t (.getType vd)
        i (.getInitializer vd)
        i (if (.isPresent i)
            (to-clj (.get i))
            nil)]
    (format "^%s (%s. %s)" n n i)))
