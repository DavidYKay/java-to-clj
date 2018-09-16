(ns java-to-clj.convert-test
  (:require [java-to-clj.convert :as sut :refer [convert-block convert-statement]]
            [java-to-clj.parse :refer [parse-block]]
            [clojure.java.io :as io]
            [clojure.test :as t :refer :all]
            [clojure.string :as str]))

(def block-str (slurp (io/resource "code/Block.java")))
(def block-output (str/trim (slurp (io/resource "code/block.clj"))))

(deftest convert

  (testing "Can convert the first line of the block"
    (is (= "(def ^Geometry coloredMesh (Geometry. \"ColoredMesh\" cMesh))"
           (convert-statement "Geometry coloredMesh = new Geometry (\"ColoredMesh\", cMesh);"))))

  (testing "Can convert second statement"
    (is (= "(.setMode mesh Mesh$Mode/Points)"
           (convert-statement "mesh.setMode(Mesh.Mode.Points);"))))

  (testing "Can convert a block"
    (is (= block-output
           (convert-block block-str))))


  )

