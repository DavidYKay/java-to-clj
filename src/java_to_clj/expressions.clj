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
    BinaryExpr$Operator
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
    UnaryExpr$Operator
    VariableDeclarationExpr
    BooleanLiteralExpr
    LiteralStringValueExpr
    CharLiteralExpr
    DoubleLiteralExpr
    IntegerLiteralExpr
    LongLiteralExpr
    ]))

(defn- escape-str [s]
  (str \" s \"))

(defmethod to-clj BooleanLiteralExpr [e] (.getValue e))
(defmethod to-clj LiteralStringValueExpr [e] (escape-str (.getValue e)))
(defmethod to-clj CharLiteralExpr [e] (.asChar e))
(defmethod to-clj DoubleLiteralExpr [e] (.asDouble e))
(defmethod to-clj IntegerLiteralExpr [e] (.asInt e))
(defmethod to-clj LongLiteralExpr [e] (.asLong e))
(defmethod to-clj StringLiteralExpr [e] (escape-str (.asString e)))

(defmethod to-clj NameExpr [e] (-> e .getName to-clj))

(defmethod to-clj Expression [e] :Expression)

(defmethod to-clj AnnotationExpr [e] :AnnotationExpr)

(defmethod to-clj ArrayAccessExpr [e]
  (if (.isArrayAccessExpr e)
    (format "(aget %s %s)"
            (.getName e)
            (.getIndex e))
    :non-array-access-expression))

(defmethod to-clj ArrayCreationExpr [e]
  ;;(.getInitializer e)
  (format "(make-array %s %s)"
          (to-clj (.getElementType e))
          (->> (.getLevels e)
               (map to-clj)
               (str/join " "))))

(defmethod to-clj ArrayInitializerExpr [e]
  (format "(into-array [%s])"
          (str/join " " (->> (.getValues e)
                             (map to-clj)))))

(defmethod to-clj AssignExpr [e]
  (let [t (.getTarget e)
        v (.getValue e)]
    (if (isa? (class t) ArrayAccessExpr)
      (format "(aset %s %s %s)"
              (.getName t)
              (.getIndex t)
              (to-clj v))
      (format "(def %s %s)"
              (to-clj t)
              (to-clj v)))))

(defmethod to-clj BinaryExpr [e]
  (format "(%s %s %s)"
          (to-clj (.getOperator e))
          (to-clj (.getLeft e))
          (to-clj (.getRight e))))

(defmethod to-clj BinaryExpr$Operator [e]
  (.asString e))

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

(defmethod to-clj ThisExpr [e]
  "this")

(defmethod to-clj TypeExpr [e] :TypeExpr)

(defmethod to-clj UnaryExpr [e]
  (format "(%s %s)"
          (to-clj (.getOperator e))
          (to-clj (.getExpression e))))

(defmethod to-clj UnaryExpr$Operator [e]
  (condp = e
    UnaryExpr$Operator/BITWISE_COMPLEMENT "bit-not"
    UnaryExpr$Operator/LOGICAL_COMPLEMENT "not"
    UnaryExpr$Operator/MINUS "-"
    UnaryExpr$Operator/PLUS "int"
    UnaryExpr$Operator/POSTFIX_DECREMENT "dec"
    UnaryExpr$Operator/POSTFIX_INCREMENT "inc"
    UnaryExpr$Operator/PREFIX_DECREMENT "dec"
    UnaryExpr$Operator/PREFIX_INCREMENT "inc"))

(defmethod to-clj VariableDeclarationExpr [e]
  (str/join " "
            (map to-clj (.getVariables e))))

;; TODO: move these utility fns
(defn name-is-class? [s]
  (Character/isUpperCase (first s)))

(defn innermost-is-class? [e]
  (loop [cur e]
    (if (= FieldAccessExpr (class cur))
      (recur (.getScope cur))
      (name-is-class? (-> cur
                          .getName
                          .asString)))))

(defn- dollar-sign [e]
  (loop [cur e
         accum []]
    (let [name (.asString (.getName cur))]
      (if (= FieldAccessExpr (class cur))
        (recur (.getScope cur)
               (conj accum name
                     ;; (if (name-is-class? name) (vector name ) (vector name ))
                     ))
        (let [elems (reverse (conj accum name))]
          (str (str/join "$" (butlast elems)) "/" (last elems)))))))

(defmethod to-clj FieldAccessExpr [e]
  ;;(if (innermost-is-class? e)
  (dollar-sign e)
  ;; TODO: support regular field accesses
  )
