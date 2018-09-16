(ns java-to-clj.core-test
  (:require [clojure.test :refer :all]
            [clojure.java.io :as io]
            ;;[java-to-clj.core :refer []]
            [java-to-clj.convert :refer [convert-statement]]
            [java-to-clj.parse :refer [parse-block parse-statement]]
            [java-to-clj.protocols :refer [to-clj]]
            ))

(def block-str (slurp (io/resource "code/Block.java")))
(def block (parse-block block-str))

(def hello-statement "Geometry coloredMesh = new Geometry (\"ColoredMesh\", cMesh);")
(def statement (parse-statement hello-statement))

statement

(def expr (-> statement
              .getExpression))

(def variables
  (->
   expr
   .getVariables))

(def variable (first variables))

variables
(first variables)

(to-clj expr)

(def initializer
  (->
   variable
   .getInitializer
   .get))

initializer

(to-clj initializer)

(to-clj variable)
(to-clj statement)

(deftest convert
  (testing "Can convert variable"
    )

  (testing "Can convert the first line of the block"
    (is (= "(def ^Geometry coloredMesh (Geometry. \"ColoredMesh\" cMesh))"
           (convert-statement hello-statement))))
  )
