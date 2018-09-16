(ns java-to-clj.core-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            [java-to-clj.parse :refer [parse-block parse-statement]]
            [java-to-clj.protocols :refer [to-clj]]
            )
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

(defn innermost-is-class? [e]
  (loop [cur e]
    (if (= FieldAccessExpr (class cur))
      (recur (.getScope cur))
      (Character/isUpperCase (first (-> cur
                                        .getName
                                        .asString))))))

(defmethod to-clj FieldAccessExpr [e]
  ;;(if (innermost-is-class? e)
  ;;(class (.getScope e)

  (str
   (.getScope e)
   "."
       (.getName e)
       )
  )

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

(def field-access (.getScope arg))

field-access

(def inner-scope (.getScope field-access))

inner-scope
(-> (.getName inner-scope)
    .asString)

;;(.getName arg)

(to-clj expression)

