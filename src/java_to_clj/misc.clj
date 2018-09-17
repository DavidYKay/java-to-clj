(ns java-to-clj.misc
  (:require
  [java-to-clj.protocols :refer [to-clj]])
  (:import
   [com.github.javaparser.ast
    body.VariableDeclarator
    expr.SimpleName
    comments.Comment
    type.ClassOrInterfaceType
    ]
   ))
(defmethod to-clj String [s] s)

(defmethod to-clj ClassOrInterfaceType [x] :ClassOrInterfaceType)

(defmethod to-clj SimpleName [x]
  (.asString x))

(defmethod to-clj Comment [c]
  (format ";; %s" (.getContent c)))

(defmethod to-clj VariableDeclarator [vd]
  (let [n (.getName vd)
        t (.getType vd)
        i (.getInitializer vd)
        i (if (.isPresent i)
            (to-clj (.get i))
            nil)]
    (format "(def ^%s %s %s)" t n i)))
