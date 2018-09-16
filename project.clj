(defproject java-to-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 ;; https://mvnrepository.com/artifact/com.github.javaparser/javaparser-core
                 [com.github.javaparser/javaparser-core "3.6.20"]
                 ]
  :repl-options {:init-ns java-to-clj.core}
  )
