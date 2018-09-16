(ns java-to-clj.misc
  (:require
  [java-to-clj.protocols :refer [to-clj]])
  (:import
   [com.github.javaparser.ast
    body.VariableDeclarator
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