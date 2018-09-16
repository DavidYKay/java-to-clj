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
      to-clj))

(defmethod to-clj Expression [e] :Expression)

(defmethod to-clj AnnotationExpr [e] :AnnotationExpr)

(defmethod to-clj ArrayAccessExpr [e] :ArrayAccessExpr)

(defmethod to-clj ArrayCreationExpr [e] :ArrayCreationExpr)

(defmethod to-clj ArrayInitializerExpr [e] :ArrayInitializerExpr)

(defmethod to-clj AssignExpr [e] :AssignExpr)

(defmethod to-clj BinaryExpr [e] :BinaryExpr)

(defmethod to-clj CastExpr [e] :CastExpr)

(defmethod to-clj ClassExpr [e] :ClassExpr)

(defmethod to-clj ConditionalExpr [e] :ConditionalExpr)

(defmethod to-clj EnclosedExpr [e] :EnclosedExpr)

(defmethod to-clj InstanceOfExpr [e] :InstanceOfExpr)

(defmethod to-clj LambdaExpr [e] :LambdaExpr)

(defmethod to-clj LiteralExpr [e] :LiteralExpr)

(defmethod to-clj MethodCallExpr [e]
  (let [s (-> e .getScope)
        n (.getName e)
        args (->> (.getArguments e)
                  (map to-clj))
        args (if (.isPresent s)
               (conj args (to-clj (.get s)))
               args)]
    (format "(.%s %s)" n
            (str/join " " args))))

(defmethod to-clj MethodReferenceExpr [e] :MethodReferenceExpr)

(defmethod to-clj ObjectCreationExpr [e]
  (let [t (.getType e)
        args (->> (.getArguments e)
                  (map to-clj))]
    (format "(%s. %s)" t
            (str/join " " args))))

(defmethod to-clj SuperExpr [e] :SuperExpr)

(defmethod to-clj ThisExpr [e] :ThisExpr)

(defmethod to-clj TypeExpr [e] :TypeExpr)

(defmethod to-clj UnaryExpr [e] :UnaryExpr)

(defmethod to-clj VariableDeclarationExpr [e]
  (str/join " "
            (map to-clj (.getVariables e))))

