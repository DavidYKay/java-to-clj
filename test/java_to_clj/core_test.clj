(ns java-to-clj.core-test
  (:require [clojure.test :refer :all]
            [java-to-clj.core :refer :all]))

(deftest convert
  (testing "Can convert the first line of the block"
    (is (=
         1
         1))))
