(ns java-to-clj.convert-test
  (:require [java-to-clj.convert :as sut :refer [convert-statement]]
            [clojure.test :as t :refer :all]))

"Mesh.Mode.Points"
Mesh$Mode/Points

(deftest convert
  (testing "Can convert dots to $ /"
    (is (= "(.setMode mesh Mesh$Mode/Points)"

  (testing "Can convert the first line of the block"
    (is (= "(def ^Geometry coloredMesh (Geometry. \"ColoredMesh\" cMesh))"
           (convert-statement "Geometry coloredMesh = new Geometry (\"ColoredMesh\", cMesh);")))))
