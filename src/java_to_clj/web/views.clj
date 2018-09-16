(ns java-to-clj.web.views
  (:require [hiccup.core :refer [html]]))

(defn main [{:keys [java clj]}]
  (html
   [:div
    [:textarea
     {:wrap "soft" :cols "50" :rows "5" :name "java"}
     java]
    [:textarea
     {:wrap "soft" :cols "50" :rows "5" :name "clj"}
     clj]]))
