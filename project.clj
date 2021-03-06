(defproject java-to-clj "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [com.github.javaparser/javaparser-core "3.6.20"]

                 [cljfmt "0.6.1"]

                 ;; Web UI
                 [compojure "1.6.1"]
                 [hiccup "1.0.5"]]

  :plugins [[lein-ring "0.12.4"]]
  :ring {:handler java-to-clj.web.server/app}

  :repl-options {:init-ns java-to-clj.core}
  )
