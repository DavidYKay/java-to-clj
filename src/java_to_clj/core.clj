(ns java-to-clj.core
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [java-to-clj.convert]
            [java-to-clj.protocols :refer [to-clj]]
            [java-to-clj.parse :refer [parse-block parse-statement]]
            [seesaw.core :refer [invoke-later pack! show! frame]]
            )
  (:import
   [com.github.javaparser JavaParser]
   [java.util Optional]
   [com.github.javaparser.ast CompilationUnit]
   ))

(def block-str (slurp (io/resource "code/Block.java")))
(def raw-block-str (slurp (io/resource "code/BlockWithDots.java")))

(def hello-expression "(x == 1)")
(def hello-statement "Geometry coloredMesh = new Geometry (\"ColoredMesh\", cMesh);")

(def block (parse-block block-str))

;;(def statement (parse-statement "Geometry coloredMesh = new Geometry (\"ColoredMesh\", cMesh);"))
;;(def statement (parse-statement hello-statement))
;;
;;(def statements (->> block
;;                     .getChildNodes))
;;
;;statements
;;
;;(def variable-declarator
;;  (->> statement
;;       .getChildNodes
;;       first
;;       .getVariables
;;       first))
;;
;;variable-declarator
;;
;;(def initializer (-> (.getInitializer variable-declarator)
;;                     .get))
;;initializer


(defn -main [& args]
  (invoke-later
   (-> (frame :title "Hello",
              :content "Hello, Seesaw",
              :on-close :exit)
       pack!
       show!)))

;;(to-clj variable-declarator)
;;(convert-block block)
;;(->> (.getStatements block) first)
;;(to-clj initializer)
