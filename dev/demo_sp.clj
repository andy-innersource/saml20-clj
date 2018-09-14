(ns demo-sp (:gen-class)
  (:require [saml20-clj.sp :as sp]
            [saml20-clj.shared :as shared]
            [saml20-clj.xml :as xml]
            [saml20-clj.routes :as sr]
            [compojure.core :refer [defroutes routes GET POST]]
            [compojure.handler :as handler]
            [hiccup.page :refer [html5]]
            [ring.adapter.jetty :refer [run-jetty]]
            [clojure.edn :as edn]))

(defn template-page [title & contents]
  (html5
    [:html
     [:head
      [:link {:rel "stylesheet" :href "//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css"}]
      [:title title]]
     [:body.container
      [:h1 "Demo Service Provider"]
      [:p.lead "You can get the SAML metadata " [:a {:href "/saml/meta"} "here"]]
      contents]]))

(defn login-page []
  (template-page "Login"
                 [:a.btn.btn-primary {:href "login"} "Login to IdP"]))

(defn debug-page [saml-info]
  (let [attrs (-> saml-info :assertions first :attrs)
        status (dissoc saml-info :assertions) ]
    (template-page "SAML Debug page"
                   [:h2 "SAML response"]
                   [:table.table.table-striped
                    (map (fn [[k v]]
                           [:tr [:td k] [:td v]]) status)]
                   [:h2 "You 've been authenticated as"]
                   [:table.table.table-striped
                    (map (fn [[k v]]
                           [:tr [:td k] [:td v]]) attrs) ])))

(defroutes main-routes
  (GET "/" {session :session}
       (if-let [saml-info (:saml session)]
         (debug-page (:saml session))
         (login-page)))
  (GET "/login" [] (sr/redirect-to-saml "/")))

(defn whats-my-ip
  "A hack, to get the externally visible IP address of the current host
   using http://checkip.amazonaws.com"
  []
  (clojure.string/trim (slurp "http://checkip.amazonaws.com")))

(defn -main []
  (let [config (edn/read-string (slurp "config.edn")) ;;TODO fix NPE if wrong alias
        ;;base-uri (str "http://" (whats-my-ip) ":" port)
        saml-routes (sr/saml-routes config)
        app (routes saml-routes #'main-routes)]
    (println "Starting server at" (:base-uri config))
    (run-jetty (handler/site app) {:port (:port config)})) )
