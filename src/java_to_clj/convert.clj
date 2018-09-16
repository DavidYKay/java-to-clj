(ns java-to-clj.convert
  (:require
   [clojure.string :as str]
   [java-to-clj.expressions :refer [to-str]])
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

(defmulti to-clj class)

(defmethod to-clj String [s] s)

(defmethod to-clj BlockStmt [e]

  )

(defmethod to-clj Expression [e] 
  (let [t (.getType e)
        args (->> (.getArguments e)
                  (map to-str))]
    (format "(%s. %s)" t
            (str/join " " args))))

(defmethod to-clj VariableDeclarator [vd]
  (let [n (.getName vd)
        ;; t (.getType vd)
        i (.getInitializer vd)
        i (if (.isPresent i)
            (to-clj (.get i))
            nil)]
    (format "^%s (%s. %s)" n n i)))
