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

(defmethod to-clj AssertStmt [s]
  nil)

(defmethod to-clj BlockStmt [s]
  nil)

(defmethod to-clj BreakStmt [s]
  nil)

(defmethod to-clj ContinueStmt [s]
  nil)

(defmethod to-clj DoStmt [s]
  nil)

(defmethod to-clj EmptyStmt [s]
  nil)

(defmethod to-clj ExplicitConstructorInvocationStmt [s]
  nil)

(defmethod to-clj ExpressionStmt [s]
  nil)

(defmethod to-clj ForeachStmt [s]
  nil)

(defmethod to-clj ForStmt [s]
  nil)

(defmethod to-clj IfStmt [s]
  nil)

(defmethod to-clj LabeledStmt [s]
  nil)

(defmethod to-clj LocalClassDeclarationStmt [s]
  nil)

(defmethod to-clj ReturnStmt [s]
  nil)

(defmethod to-clj SwitchEntryStmt [s]
  nil)

(defmethod to-clj SwitchStmt [s]
  nil)

(defmethod to-clj SynchronizedStmt [s]
  nil)

(defmethod to-clj ThrowStmt [s]
  nil)

(defmethod to-clj TryStmt [s]
  nil)

(defmethod to-clj UnparsableStmt [s]
  nil)

(defmethod to-clj WhileStmt [s]
  nil)

