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
     NullLiteralExpr
     ]
    [com.github.javaparser.ast.type
     PrimitiveType
    PrimitiveType$Primitive]
    (com.github.javaparser.ast.visitor VoidVisitorAdapter)))

(defn- escape-str [s]
  (str \" s \"))

(defmethod to-clj BooleanLiteralExpr [e] (.getValue e))
(defmethod to-clj LiteralStringValueExpr [e] (escape-str (.getValue e)))
(defmethod to-clj CharLiteralExpr [e] (.asChar e))
(defmethod to-clj DoubleLiteralExpr [e] (.asDouble e))
(defmethod to-clj IntegerLiteralExpr [e] (.asInt e))
(defmethod to-clj LongLiteralExpr [e] (.asLong e))
(defmethod to-clj StringLiteralExpr [e] (escape-str (.asString e)))
(defmethod to-clj NullLiteralExpr [e] "nil")

(defmethod to-clj NameExpr [e] (-> e .getName to-clj))

(defmethod to-clj Expression [e] :Expression)

(defmethod to-clj AnnotationExpr [e] :AnnotationExpr)

(defmethod to-clj ArrayCreationExpr [e]
  (let [i (.getInitializer e)
        t (.getElementType e)]
    (if (and (.isPresent i)
             (instance? PrimitiveType t))
      (let [t (.getElementType e)
            array-fn (condp = (.getType t)
                       PrimitiveType$Primitive/BOOLEAN 'boolean-array
                       PrimitiveType$Primitive/BYTE    'byte-array
                       PrimitiveType$Primitive/CHAR    'char-array
                       PrimitiveType$Primitive/DOUBLE  'double-array
                       PrimitiveType$Primitive/FLOAT   'float-array
                       PrimitiveType$Primitive/INT     'int-array
                       PrimitiveType$Primitive/LONG    'long-array
                       PrimitiveType$Primitive/SHORT   'short-array
                       :unknown-primitive)]
        (format "(%s [%s])" array-fn
                (to-clj (.get i))))
      (format "(make-array %s %s)"
              (to-clj t)
              (->> (.getLevels e)
                   (map to-clj)
                   (str/join " "))))))

(defmethod to-clj ArrayInitializerExpr [e]
  (format "%s"
          (str/join " " (->> (.getValues e)
                             (map to-clj)))))

(defmethod to-clj ArrayAccessExpr [e]
  (if (.isArrayAccessExpr e)
    (format "(aget %s %s)"
            (.getName e)
            (.getIndex e))
    :non-array-access-expression))

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


;;(defn is-str-join? [e]
;;  (let [v (proxy VoidVisitorAdapter []
;;                 (visit [^MethodCallExpr n ^Object arg]
;;                        (proxy-super visit n arg)
;;                        (println " [L " (.getBeginLine n) + "] " + n)))]
;;    (.visit v e)))

(defmethod to-clj BinaryExpr [e]
  (let [
        l (.getLeft e)
        r (.getRight e)
        o (.getOperator e)
        o (if (and (= BinaryExpr$Operator/PLUS o)
                   (or (instance? StringLiteralExpr l)
                       (instance? StringLiteralExpr r)))
            "str"
            (to-clj o))]
        (format "(%s %s %s)"
                o
                (to-clj l)
                (to-clj r))
    ))

(defmethod to-clj BinaryExpr$Operator [e]
  (condp = e
    BinaryExpr$Operator/UNSIGNED_RIGHT_SHIFT "bit-shift-right"
    BinaryExpr$Operator/AND                  "and"
    BinaryExpr$Operator/BINARY_AND           "bit-and"
    BinaryExpr$Operator/BINARY_OR            "bit-or"
    BinaryExpr$Operator/DIVIDE               "/"
    BinaryExpr$Operator/EQUALS               "="
    BinaryExpr$Operator/GREATER              ">"
    BinaryExpr$Operator/GREATER_EQUALS       ">="
    BinaryExpr$Operator/LEFT_SHIFT           "bit-shift-left"
    BinaryExpr$Operator/LESS                 "<"
    BinaryExpr$Operator/LESS_EQUALS          "<="
    BinaryExpr$Operator/MINUS                "-"
    BinaryExpr$Operator/MULTIPLY             "*"
    BinaryExpr$Operator/NOT_EQUALS           "not="
    BinaryExpr$Operator/OR                   "or"
    BinaryExpr$Operator/PLUS                 "+"
    BinaryExpr$Operator/REMAINDER            "mod"
    BinaryExpr$Operator/SIGNED_RIGHT_SHIFT   "unsigned-bit-shift-right"
    BinaryExpr$Operator/XOR                  "bit-xor"))

(defmethod to-clj CastExpr [e]
  (format "(cast %s %s)"
           (to-clj (.getType e))
           (to-clj (.getExpression e))))

(defmethod to-clj ClassExpr [e] :ClassExpr)

(defmethod to-clj ConditionalExpr [e]
  (format "(if %s %s %s)"
          (to-clj (.getCondition e))
          (to-clj (.getThenExpr e))
          (to-clj (.getElseExpr e))))

(defmethod to-clj EnclosedExpr [e]
  (to-clj (.getInner e)))

(defmethod to-clj InstanceOfExpr [e]
  (format "(instance? %s %s)"
          (to-clj (.getType e))
          (to-clj (.getExpression e))))

(defmethod to-clj LambdaExpr [e]
  (let [b (.getExpressionBody e)]
    (format "(fn [%s] %s)"
            (->> (.getParameters e)
                 (map to-clj)
                 (str/join " "))
            (to-clj (.getBody e)))))

(defmethod to-clj LiteralExpr [e]
  :LiteralExpr)

(defmethod to-clj MethodCallExpr [e]
  (let [s (-> e .getScope)
        n (.getName e)
        args (->> (.getArguments e)
                  (map to-clj))]
    (cond
      (= (to-clj n) "println")                                (format "(%s %s)" n (str/join " " args))
      (and (.isPresent s)
           (Character/isUpperCase (first (to-clj (.get s))))) (format "(%s/%s %s)"
                                                                      (to-clj (.get s))
                                                                      (to-clj n)
                                                                      (str/join " " args))
      :else (format "(.%s %s)"
                    (to-clj n)
                    (str/join " " (if (.isPresent s)
                                    (conj args (to-clj (.get s)))
                                    (conj args "this")))))))

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
