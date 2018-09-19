(ns java-to-clj.convert
  (:require
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
      to-clj))

(defn convert-statement [s]
  (-> s
      parse-statement
      to-clj))

(defn convert-block [s]
  (-> s
      parse-block
      to-clj))

(defn convert-main [s]
  (convert-block (str "{\n" s "\n}")))
