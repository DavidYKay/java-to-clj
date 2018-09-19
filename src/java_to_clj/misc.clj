(ns java-to-clj.misc
  (:require
  [java-to-clj.protocols :refer [to-clj]])
  (:import
   [com.github.javaparser.ast
    ArrayCreationLevel
    body.VariableDeclarator
    body.Parameter
    expr.SimpleName
    comments.Comment
    type.ClassOrInterfaceType
    type.WildcardType
    ]
   ))
(defmethod to-clj String [s] s)

(defmethod to-clj ClassOrInterfaceType [t]
  (to-clj (.getName t)))

(defmethod to-clj SimpleName [x]
  (.asString x))

(defmethod to-clj Parameter [x]
  (str (if (.isVarArgs x)
         "& "
         "")
       (let [n (.getName x)
             t (.getType x)]
         (if (= WildcardType t)
           (format "^%s %s"
                   (to-clj t)
                   (to-clj n))
           (to-clj n)))))

(defmethod to-clj Comment [c]
  (format ";; %s" (.getContent c)))

(defmethod to-clj ArrayCreationLevel [a]
  (.get (.getDimension a)))

(defmethod to-clj VariableDeclarator [vd]
  (let [n (.getName vd)
        t (.getType vd)
        i (.getInitializer vd)
        i (if (.isPresent i)
            (to-clj (.get i))
            nil)]
    (format "(def ^%s %s %s)" t n i)))
