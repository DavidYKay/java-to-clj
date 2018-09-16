(ns java-to-clj.web.views
  (:require [hiccup.core :refer [html]]))

(defn main [& {:keys [java clj]}]
  (html
   [:html
    [:head
     [:link {:href "https://cdnjs.cloudflare.com/ajax/libs/bulma/0.7.1/css/bulma.min.css"
             :type "text/css" :rel "stylesheet"}]]
    [:body
     [:section.section
      [:div.container
       [:h1.title.is-1 "Java -> Clojure"]
       [:form
        {:method "post", :action "/"}
        [:div.columns
         [:div.column
          [:nav.level
           [:div.level-item.has-text-centered
            [:div [:p.title.is-2 "Java"]]]]
          [:textarea.textarea
           {:wrap "soft" :cols "50" :rows "20" :name "java"
            :placeholder "myObject.doIt()"}
           java]]
         [:div.column
          [:nav.level
           [:div.level-item.has-text-centered
            [:div [:p.title.is-2 "Clojure"]]]]
          [:textarea.textarea
           {:wrap "soft" :cols "50" :rows "20" :name "clj"
            :placeholder "(.doIt myObject)"}
           clj]]]
        [:button.button.is-primary.is-large.is-fullwidth
         {:value "Submit" :type "submit"}
         "Convert"]]]]]]))


