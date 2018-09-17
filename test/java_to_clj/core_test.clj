(ns java-to-clj.core-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [java-to-clj.parse :refer [parse-block parse-statement]]
            [java-to-clj.protocols :refer [to-clj]]

            [clojure.string :as str])
  (:import
   [com.github.javaparser.ast.expr
    Expression
    AnnotationExpr
    ArrayAccessExpr
    ArrayCreationExpr
    ArrayInitializerExpr
    AssignExpr
    BinaryExpr
    CastExpr
    ClassExpr
    ConditionalExpr
    EnclosedExpr
    FieldAccessExpr
    InstanceOfExpr
    LambdaExpr
    LiteralExpr
    MethodCallExpr
    MethodReferenceExpr
    NameExpr
    ObjectCreationExpr
    SuperExpr
    StringLiteralExpr
    ThisExpr
    TypeExpr
    UnaryExpr
    VariableDeclarationExpr
    ])
  )

;;(def expr (-> statement .getExpression))

;;(def variables (-> expr .getVariables))
;;(def variable (first variables))
;;variables
;;(first variables)

;;(to-clj expr)

;;(def initializer (-> variable .getInitializer .get))

;;(to-clj initializer)
;;(to-clj variable)
;;(to-clj statement)



#_(defn join-components [cs]
  (loop [cs cs
         accum ""]
    (if (empty? cs)
      accum
      (let [{:keys [name kind]} (first cs)]
        (recur (rest cs)
               (str accum
                    (condp = kind
                      :class "$"
                      :field "/")
                    name))))))


(def hello-statement "Geometry coloredMesh = new Geometry (\"ColoredMesh\", cMesh);")

(def statement (parse-statement "int [] indexes = { 2,0,1, 1,3,2 };"))

statement

(def expression (.getExpression statement))


expression

(def v (-> expression .getVariables first))

(def initializer (.get (.getInitializer v)))

(def literal
(-> initializer
    .getValues
    first))

literal

;;(def arg (-> expression .getArguments ))



;;(def field-access (.getScope arg))
;;
;;field-access

;;(def inner-scope (.getScope field-access))
;;
;;inner-scope
;;(-> (.getName inner-scope)
;;    .asString)

;;(.getName arg)

;(to-clj expression)
