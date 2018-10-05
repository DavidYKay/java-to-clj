(ns java-to-clj.convert
  (:require
   [cljfmt.core :refer [reformat-string]]
   [clojure.string :as str]
   [java-to-clj.protocols :refer [to-clj]]
   [java-to-clj.parse :refer [parse-block parse-expression parse-statement]]
   [java-to-clj.arrays]
   [java-to-clj.expressions]
   [java-to-clj.statements]
   [java-to-clj.misc]
   ))


(defn convert-expression [s]
  (-> s
      parse-expression
      to-clj
      ;;reformat-string
      ))

(defn convert-statement [s]
  (-> s
      parse-statement
      to-clj
      reformat-string
      ))

(defn convert-block [s]
  (-> s
      parse-block
      to-clj
      ;;reformat-string
      ))

(defn convert-main [s]
  (->
    (str "{\n" s "\n}")
    convert-block
    ;;reformat-string
    ))
