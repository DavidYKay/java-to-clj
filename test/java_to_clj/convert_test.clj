(ns java-to-clj.convert-test
  (:require [java-to-clj.convert :as sut :refer [convert-block convert-expression convert-main convert-statement]]
            [java-to-clj.parse :refer [parse-block]]
            [clojure.java.io :as io]
            [clojure.test :as t :refer :all]
            [clojure.string :as str]))

(defonce block-str (slurp (io/resource "code/Block.java")))
(defonce android-prefs (slurp (io/resource "code/AndroidPrefs.java")))
(defonce block-no-braces (slurp (io/resource "code/BlockNoBraces.java")))
(defonce block-output (str/trim (slurp (io/resource "code/block.clj"))))
(defonce if-statement (str/trim (slurp (io/resource "code/If.java"))))
(defonce if-else-statement (str/trim (slurp (io/resource "code/IfElse.java"))))
(defonce switch-statement (str/trim (slurp (io/resource "code/Switch.java"))))
(defonce switch-output (str/trim (slurp (io/resource "code/switch.clj"))))
(defonce assert-statement (str/trim (slurp (io/resource "code/Assert.java"))))
(defonce string-concat-statement (str/trim (slurp (io/resource "code/StringConcat.java"))))
(defonce settings-block (str/trim (slurp (io/resource "code/Settings.java"))))
(defonce for-statement (str/trim (slurp (io/resource "code/For.java"))))
(defonce for-each-statement (str/trim (slurp (io/resource "code/ForEach.java"))))
(defonce for-each-statement-clj (str/trim (slurp (io/resource "code/ForEach.clj"))))

(deftest ^:test-refresh/focus convert

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
    (is (= "(def ^int[] indexes (int-array [2 0 1 1 3 2]))"
           (convert-statement "int [] indexes = { 2,0,1, 1,3,2 };"))))

  (testing "Can convert a throw statement"
    (is (= "(throw (RuntimeException. \"Failed to create the GLFW window\"))"
           (convert-statement "throw new RuntimeException(\"Failed to create the GLFW window\");"))))
  (testing "Can correctly add two numbers"
    ;; Should this be alter-var-root?
    (is (= "(def ^int x (+ 1 1))"
           (convert-statement "int x = 1 + 1;"))))

  (testing "Can correctly set an int value"
    ;; Should this be alter-var-root?
    (is (= "(def x 5)"
           (convert-statement "x = 5;"))))

  (testing "Can convert an assignment expression"
    (is (= "(def ^int x 5)"
           (convert-statement "int x = 5;"))))

  (testing "Can correctly get an array value"
    (is (= "(aget vertices 0)"
           (convert-statement "vertices[0];"))))

  (testing "Can correctly set an array value"
    (is (= "(aset vertices 0 (Vector3f. 0 0 0))"
           (convert-statement "vertices[0] = new Vector3f(0,0,0);"))))

  (testing "Can correctly create an array"
    (is (= "(make-array Vector3f 4)"
           (convert-statement "new Vector3f[4];"))))

  (testing "Can correctly convert a unary expression"
    (is (= "(not readyToLaunch)"
           (convert-expression "!readyToLaunch")))
    (is (= "(bit-not 0)"
           (convert-expression "~0")))
    (is (= "(inc x)"
           (convert-expression "x++")))
    (is (= "(inc x)"
           (convert-expression "++x"))))

  (testing "Can convert a ternary if"
    (is (= "(if (= b 0) x y)"
           (convert-expression "b == 0 ? x : y"))))

  (testing "Can perform a casting operation"
    (is (= "(cast String o)"
           (convert-expression "(String) o"))))

  (testing "Can perform an instanceof"
    (is (= "(instance? Car c)"
           (convert-expression "c instanceof Car"))))

  (testing "Can convert a lambda"
    (is (= "(fn [a b] (+ a b))"
           (convert-expression "(a, b) -> a + b"))))

  (testing "Can convert an equality test"
    (is (= "(= window nil)"
           (convert-expression "(window == null)"))))

  (testing "Can convert an if statement"
    (is (= "(if (= window nil) (throw (RuntimeException. \"Failure!\")) 1)"
           (convert-statement if-else-statement))))

  (testing "Can correctly convert a this expression"
    (is (= "(.fireTheMissiles this)"
           (convert-expression "this.fireTheMissiles()"))))

  (testing "Can correctly convert a switch statement"
    (is (= switch-output
           (convert-statement switch-statement))))

  (testing "Can correctly convert a primitive"
    (is (= "(def ^float[] normals (make-array float 12))"
           (convert-statement "float[] normals = new float[12];"))))

  (testing "Can correctly convert a primitive array type"
    (is (= "(def normals (float-array [0 0 1 0 0 1 0 0 1 0 0 1]))"
           (convert-statement "normals = new float[]{0,0,1, 0,0,1, 0,0,1, 0,0,1};"))))

  (testing "Can correctly convert a method call to 'this'"
    (is (= "(.doIt this x)"
           (convert-statement "doIt(x);"))))

  (testing "Can correctly convert a method call"
    (is (= "(.accelerate car speed)"
           (convert-statement "car.accelerate(speed);"))))

  (testing "Can correctly convert a static method call"
    (is (= "(Log/info item)"
           (convert-statement "Log.info(item);"))))

  (testing "Can correctly convert a foreach statement"
    (is (= for-each-statement-clj
           (convert-statement for-each-statement))))

  ;;"mesh.setBuffer(Type.Normal, 3, BufferUtils.createFloatBuffer(normals));"])

  ;;(testing "Can correctly convert an assertion"
  ;;  (is (= "(assert nullsRemoved)"
  ;;         (convert-statement "assert nullsRemoved;")))

    ;;(is (= "" (convert-statement assert-statement))))

  )

#_(deftest ^:test-refresh/focus focused)

(deftest next

  (testing "Can convert a settings example"
    (is (= ""
           (convert-main settings-block))))

  (testing "Can convert a string concat"
    (is (= "(def ^String s (str \"Sum of \" a \" + \" b \" returned wrong sum \" result))"
           (convert-statement string-concat-statement))))

  (testing "Can convert a binary expression"
    (is (= "(println \"Hello LWJGL\" (Version/getVersion) \"!\")"
           (convert-statement "System.out.println(\"Hello LWJGL \" + Version.getVersion() + \"!\");"))))

  (testing "Can convert a setBuffer call"
    (is (= "(.setBuffer mesh Type/Position 3 (BufferUtils/createFloatBuffer vertices))"
           (convert-statement "mesh.setBuffer(Type.Position, 3, BufferUtils.createFloatBuffer(vertices));"))))

   (testing "Can convert an android example" (is (= "(.setBuffer mesh Type/Position 3 (BufferUtils/createFloatBuffer vertices))" (convert-main android-prefs))))
  )
