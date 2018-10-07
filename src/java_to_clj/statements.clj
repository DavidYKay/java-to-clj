(ns java-to-clj.statements
  (:require
   [java-to-clj.protocols :refer [to-clj]]
   [java-to-clj.util :refer [non-def-variable]]
   [clojure.string :as str])
  (:import
   [com.github.javaparser JavaParser]
   [java.util Optional]
   [com.github.javaparser.ast
    expr.Expression
    stmt.BlockStmt]

   [com.github.javaparser.ast.stmt
    AssertStmt
    BlockStmt
    BreakStmt
    ContinueStmt
    DoStmt
    EmptyStmt
    ExplicitConstructorInvocationStmt
    ExpressionStmt
    ForeachStmt
    ForStmt
    IfStmt
    LabeledStmt
    LocalClassDeclarationStmt
    ReturnStmt
    SwitchEntryStmt
    SwitchStmt
    SynchronizedStmt
    ThrowStmt
    TryStmt
    UnparsableStmt
    WhileStmt]))

(defmethod to-clj AssertStmt [s]
  (format "(assert %s)"
          (to-clj (.getCheck s))))

(defmethod to-clj BlockStmt [s]
  (str/join "\n" (->> s
                     .getChildNodes
                     (map to-clj))))

(defmethod to-clj BreakStmt [s]
  "")

(defmethod to-clj ContinueStmt [s] :ContinueStmt)

(defmethod to-clj DoStmt [s] :DoStmt)

(defmethod to-clj EmptyStmt [s] :EmptyStmt)

(defmethod to-clj ExplicitConstructorInvocationStmt [s] :ExplicitConstructorInvocationStmt)

(defmethod to-clj ExpressionStmt [s]
  (to-clj (.getExpression s)))

(defmethod to-clj ForeachStmt [s]
  (format "(for [%s %s]\n  %s)"
          (non-def-variable
           (-> s
               .getVariable
               .getVariables
               first))
          (to-clj (.getIterable s))
          (to-clj (.getBody s))))

(defmethod to-clj ForStmt [s] :ForStmt)

(defmethod to-clj IfStmt [s]
  (let [c (.getCondition s)
        t (.getThenStmt s)
        e (.getElseStmt s)]
    (if (.isPresent e)
      (format "(if %s %s %s)"
              (to-clj c)
              (to-clj t)
              (to-clj (.get e)))
      (format "(when %s %s)"
              (to-clj c)
              (to-clj t)))))

(defmethod to-clj LabeledStmt [s] :LabeledStmt)

(defmethod to-clj LocalClassDeclarationStmt [s] :LocalClassDeclaration)

(defmethod to-clj ReturnStmt [s]
  (let [e (.getExpression s)]
    (if (.isPresent e)
      (to-clj (.get e))
      :EmptyReturn)))

(defmethod to-clj SwitchEntryStmt [x]
  (let [l (.getLabel x)
        ss (->> (.getStatements x)
                (remove #(instance? BreakStmt %)))

        components [(cond (.isPresent l) (.get l)
                          (empty? ss) nil
                          :default ":default")

                    (if (empty? ss)
                      nil
                      (->> ss
                           (map to-clj)
                           (str/join " ")))]]
        (str/join " " components)))


(defmethod to-clj SwitchStmt [s]
  (format "(condp = %s\n  %s)"
          (to-clj (.getSelector s))
          (->> (.getEntries s)
               (map to-clj)
               (remove str/blank?)
               (str/join "\n  "))))

(defmethod to-clj SynchronizedStmt [s] :SynchronizedStmt)

(defmethod to-clj ThrowStmt [s]
  (let [e (.getExpression s)]
    (println "Throwing E: " e)
    (format "(throw %s)"
            (to-clj e))))

(defmethod to-clj TryStmt [s]
  (let [f (.getFinallyBlock s)
        c (.getCatchClauses s)
        t (.getTryBlock s)
        r (.getResources s)
        has-resources? (not (empty? r))
        cs  (->> c
                 (map to-clj)
                 (str/join "\n"))
        has-catch? (not (empty? c))
        let-resources (if has-resources?
                        "(let [^MemoryStack stack (.stackPush)]"
                        nil)
        close-resources (if has-resources?
                          "(.close stack)"
                          nil)
        components (remove nil?
                           [(if has-catch? "(try" nil)
                            let-resources
                            (to-clj t)
                            cs
                            (cond
                              (and (.isPresent f) has-resources?)       (format "(finally %s %s)" (to-clj (.get f)) close-resources)
                              (and (.isPresent f) (not has-resources?)) (format "(finally %s)" (to-clj (.get f)))
                              (and (not (.isPresent f))
                                   has-catch?
                                   has-resources?) (format "(finally %s)" close-resources)
                              (and (not (.isPresent f)) has-resources?) (format "%s" close-resources)

                              (and (not (.isPresent f)) (not has-resources?)) nil)
                            (if has-catch?
                            "))"
                            ")")])]
    (str/join " " components)))

(defmethod to-clj UnparsableStmt [s] :UnparsableStmt)

(defmethod to-clj WhileStmt [s] :WhileStmt)

