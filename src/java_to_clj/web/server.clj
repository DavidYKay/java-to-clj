(ns java-to-clj.web.server
  (:require [compojure.core :refer [defroutes GET POST]]
            [compojure.route :as route]
            [java-to-clj.convert :refer [convert-main]]
            [java-to-clj.web.views :as views]
            [ring.middleware.params :refer [wrap-params]]
            ))

(defroutes handler
  (GET "/" []
       (views/main))

  (POST "/" {:keys [java]}
        ;; TODO: grab original from POST
        (views/main {:java java
                     :clj (convert-main java)}))
  (route/not-found "<h1>Page not found</h1>"))


(def app
  (wrap-params handler))
