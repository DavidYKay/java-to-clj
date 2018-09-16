(ns java-to-clj.expressions
  (:import
   [com.github.javaparser JavaParser]
   [com.github.javaparser.ast.expr
    NameExpr
    StringLiteralExpr]
   ))


(defmulti to-str class)

(defmethod to-str String [s] s)

(defmethod to-str StringLiteralExpr [e]
  (str \" (.asString e) \"))

(defmethod to-str NameExpr [e]
  (-> e
      .getName
      .asString))
