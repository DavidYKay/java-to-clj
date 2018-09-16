(ns java-to-clj.expressions
  (:require [clojure.string :as str]
            [java-to-clj.protocols :refer [to-clj]])
  (:import
   [com.github.javaparser JavaParser]
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
    ]))

(defmethod to-clj StringLiteralExpr [e]
  (str \" (.asString e) \"))

(defmethod to-clj NameExpr [e]
  (-> e
      .getName
      .asString))

(defmethod to-clj Expression [e]
  (let [t (.getType e)
        args (->> (.getArguments e)
                  (map to-clj))]
    (format "(%s. %s)" t
            (str/join " " args))))

(defmethod to-clj AnnotationExpr [e] nil)

(defmethod to-clj ArrayAccessExpr [e] nil)

(defmethod to-clj ArrayCreationExpr [e] nil)

(defmethod to-clj ArrayInitializerExpr [e] nil)

(defmethod to-clj AssignExpr [e] nil)

(defmethod to-clj BinaryExpr [e] nil)

(defmethod to-clj CastExpr [e] nil)

(defmethod to-clj ClassExpr [e] nil)

(defmethod to-clj ConditionalExpr [e] nil)

(defmethod to-clj EnclosedExpr [e] nil)

(defmethod to-clj FieldAccessExpr [e] nil)

(defmethod to-clj InstanceOfExpr [e] nil)

(defmethod to-clj LambdaExpr [e] nil)

(defmethod to-clj LiteralExpr [e] nil)

(defmethod to-clj MethodCallExpr [e] nil)

(defmethod to-clj MethodReferenceExpr [e] nil)

(defmethod to-clj NameExpr [e] nil)

(defmethod to-clj ObjectCreationExpr [e] nil)

(defmethod to-clj SuperExpr [e] nil)

(defmethod to-clj ThisExpr [e] nil)

(defmethod to-clj TypeExpr [e] nil)

(defmethod to-clj UnaryExpr [e] nil)

(defmethod to-clj VariableDeclarationExpr [e] nil)

