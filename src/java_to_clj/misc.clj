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
    ]
   [com.github.javaparser.ast.expr
    ArrayCreationExpr
    ArrayInitializerExpr]
   [com.github.javaparser.ast.type
    ArrayType
    ClassOrInterfaceType
    PrimitiveType
    PrimitiveType$Primitive
    WildcardType]
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
  (let [d (.getDimension a)]
    (if (.isPresent d)
      (.get d)
      nil)))

(defmethod to-clj PrimitiveType [t]
  (condp = (.getType t)
    PrimitiveType$Primitive/BOOLEAN "boolean"
    PrimitiveType$Primitive/BYTE  "byte"
    PrimitiveType$Primitive/CHAR  "char"
    PrimitiveType$Primitive/DOUBLE "double"
    PrimitiveType$Primitive/FLOAT "float"
    PrimitiveType$Primitive/INT   "int"
    PrimitiveType$Primitive/LONG  "long"
    PrimitiveType$Primitive/SHORT "short" ))

(defmethod to-clj VariableDeclarator [vd]
  (let [n (.getName vd)
        t (.getType vd)
        i (.getInitializer vd)]
    (if (.isPresent i)
      (let [i (.get i)
            conventional-init (format "(def ^%s %s %s)"
                                      (to-clj t)
                                      (to-clj n)
                                      (to-clj i))]
        ;; TODO: this code is currently redundant with the implementation in expressions.clj
        (cond
          ;; new float[12]
          (instance? ArrayCreationExpr i) conventional-init
          ;; { 1,2,3,4 }
          (and (instance? ArrayInitializerExpr i)
               (instance? PrimitiveType (.getComponentType t)))
          (format "(def ^%s %s (%s [%s]))"
                  (to-clj t)
                  (to-clj n)
                  (condp = (.getType (.getComponentType t))
                    PrimitiveType$Primitive/BOOLEAN 'boolean-array
                    PrimitiveType$Primitive/BYTE    'byte-array
                    PrimitiveType$Primitive/CHAR    'char-array
                    PrimitiveType$Primitive/DOUBLE  'double-array
                    PrimitiveType$Primitive/FLOAT   'float-array
                    PrimitiveType$Primitive/INT     'int-array
                    PrimitiveType$Primitive/LONG    'long-array
                    PrimitiveType$Primitive/SHORT   'short-array
                    (do
                      (println "type was: " t)
                      (println "component type: " (.getComponentType t))
                      :unknown-primitive))
                  (to-clj i))

          ;; Conventional init
          :default conventional-init
          ))

      ;;:valueless-init
      ;; Valueless init
      (format "(def ^%s %s)"
              (to-clj t)
              (to-clj n)))))

