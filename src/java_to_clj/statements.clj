(ns java-to-clj.statements
  (:require
   [java-to-clj.protocols :refer [to-clj]]
   )
  (:import
   [com.github.javaparser JavaParser]
   [java.util Optional]
   [com.github.javaparser.ast
    body.VariableDeclarator
    expr.Expression
    stmt.BlockStmt
    ]

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
    WhileStmt
    ]
   )
  )

(defmethod to-clj AssertStmt [s] :AssertStmt)

(defmethod to-clj BlockStmt [s] :BlockStmt)

(defmethod to-clj BreakStmt [s] :BreakStmt)

(defmethod to-clj ContinueStmt [s] :CoStmtnil)

(defmethod to-clj DoStmt [s] :DoStmt)

(defmethod to-clj EmptyStmt [s] :EmptyStmt)

(defmethod to-clj ExplicitConstructorInvocationStmt [s] :ExplicitCoInvocationStmtnil)

(defmethod to-clj ExpressionStmt [s]
  (to-clj (.getExpression s)))

(defmethod to-clj ForeachStmt [s] :ForeachStmt)

(defmethod to-clj ForStmt [s] :ForStmt)

(defmethod to-clj IfStmt [s] :IfStmt)

(defmethod to-clj LabeledStmt [s] :LabeledStmt)

(defmethod to-clj LocalClassDeclarationStmt [s] :LocalClassDeclaration)

(defmethod to-clj ReturnStmt [s] :ReturnStmt)

(defmethod to-clj SwitchEntryStmt [s] :SwitchEntryStmt)

(defmethod to-clj SwitchStmt [s] :SwitchStmt)

(defmethod to-clj SynchronizedStmt [s] :SynhcronizedStmt)

(defmethod to-clj ThrowStmt [s] :ThrowStmt)

(defmethod to-clj TryStmt [s] :TryStmt)

(defmethod to-clj UnparsableStmt [s] :UnparsableStmt)

(defmethod to-clj WhileStmt [s] :WhileStmt)

