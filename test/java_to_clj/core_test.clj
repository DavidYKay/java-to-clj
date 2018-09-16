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

(defn name-is-class? [s]
  (Character/isUpperCase (first s)))

(defn innermost-is-class? [e]
  (loop [cur e]
    (if (= FieldAccessExpr (class cur))
      (recur (.getScope cur))
      (name-is-class? (-> cur
                          .getName
                          .asString)))))

(defn join-components [cs]
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

(defn dollar-sign [e]
  (loop [cur e
         accum []]
    (let [name (.asString (.getName cur))]
      (if (= FieldAccessExpr (class cur))
        (recur (.getScope cur)
               (into accum
                     (if (name-is-class? name)
                       (vector name \$)
                       (vector name \/))))
        (-> (into accum [name])
             reverse
             str/join)))))

(defmethod to-clj FieldAccessExpr [e]
  ;;(if (innermost-is-class? e)
  ;;  (format "ClassScope")
    (str
     (.getScope e)
     "."
     (.getName e)
     ))


(def block-str (slurp (io/resource "code/Block.java")))
(def block (parse-block block-str))

(def hello-statement "Geometry coloredMesh = new Geometry (\"ColoredMesh\", cMesh);")
(def statement (parse-statement "mesh.setMode(Mesh.Mode.Points);"))
(def expression (.getExpression statement))
(def arg (-> expression
             .getArguments
             first))

;;expression

arg
(dollar-sign arg)

;;(def field-access (.getScope arg))
;;
;;field-access

;;(def inner-scope (.getScope field-access))
;;
;;inner-scope
;;(-> (.getName inner-scope)
;;    .asString)

;;(.getName arg)

;;(to-clj expression)

