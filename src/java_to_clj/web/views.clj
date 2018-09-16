(ns java-to-clj.web.views
  (:require [hiccup.core :refer [html]]))

(defn main [& {:keys [java clj]}]
  (html
   [:html
    [:head
     [:link {:href "https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.1/css/bulma.min.css"
             ;;"css/bulma.css"
             :type "text/css" :rel "stylesheet"}]]
    [:body
     [:section.section
      [:div.container
       [:form
        {:method "post", :action "/"}
        [:div.columns
         [:div.column
          [:span {:class "tag"} "Java"]
          [:textarea
           {:wrap "soft" :cols "50" :rows "20" :name "java"}
           java]]
         [:div.column
          [:span {:class "tag"} "Clojure"]
          [:textarea
           {:wrap "soft" :cols "50" :rows "20" :name "clj"}
           clj]]]
        [:button {:class "button"
                  :value "Submit"
                  :type "submit"}
         "Convert"
         ]]]]]]))


