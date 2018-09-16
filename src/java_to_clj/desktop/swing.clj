(ns java-to-clj.desktop.swing
  (:require
   [seesaw.core :refer [invoke-later pack! show! frame]]
   ))

(defn -main [& args]
  (invoke-later
   (-> (frame :title "Hello",
              :content "Hello, Seesaw",
              :on-close :exit)
       pack!
       show!)))
