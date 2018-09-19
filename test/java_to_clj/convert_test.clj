(ns java-to-clj.convert-test
  (:require [java-to-clj.convert :as sut :refer [convert-block convert-main convert-statement]]
            [java-to-clj.parse :refer [parse-block]]
            [clojure.java.io :as io]
            [clojure.test :as t :refer :all]
            [clojure.string :as str]))

(def block-str (slurp (io/resource "code/Block.java")))
(def android-prefs (slurp (io/resource "code/AndroidPrefs.java")))
(def block-no-braces (slurp (io/resource "code/BlockNoBraces.java")))
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

  (testing "Can convert a block without braces"
    (is (= block-output
           (convert-main block-no-braces))))


  (testing
      "Can initialize a Java array"
    (is (= "(def ^int[] indexes (into-array [2 0 1 1 3 2]))"
           (convert-statement "int [] indexes = { 2,0,1, 1,3,2 };"))))


  (testing "Can convert a setBuffer call"
    (is (= "(.setBuffer mesh Type/Position 3 (BufferUtils/createFloatBuffer vertices))"
           (convert-statement "mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));"))))

  (testing "Can convert a throw statement"
    (is (= "(throw (RuntimeException. \"Failed to create the GLFW window\"))"
           (convert-statement "throw new RuntimeException(\"Failed to create the GLFW window\");"))))

  (testing "Can convert a binary expression"
    (is (= "(println \"Hello LWJGL\" (Version/getVersion) \"!\")"
           (convert-statement "System.out.println(\"Hello LWJGL \" + Version.getVersion() + \"!\");"))))

  (testing "Can correctly set an int value"
    (is (= "(def x 5)"
           (convert-statement "x = 5;"))))

  (testing "Can convert an assignment expression"
    (is (= "(def ^int x 5)"
           (convert-statement "int x = 5;"))))


  ;;(testing "Can convert an if statement"
  ;;  (is (= ""
  ;;         (convert-statement
  ;;          if (x == 5) {

  ;;                    }
  ;;          ));"))))

  ;; (testing "Can convert an android example" (is (= "(.setBuffer mesh Type/Position 3 (BufferUtils/createFloatBuffer vertices))" (convert-main android-prefs))))
  )

#_(deftest ^:test-refresh/focus focused
  ;;(testing "Can correctly set an array value"
  ;;  (is (= "(aset vertices 0 (Vector3f. 0 0 0))"
  ;;         (convert-statement "vertices[0] = new Vector3f(0,0,0);"))))
  )

