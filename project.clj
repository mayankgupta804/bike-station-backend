(defproject bike-station-backend "0.1.0-SNAPSHOT"
  :description "REST APIs for an imaginary bike renting service."
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.10.0"]
                 [compojure "1.6.1"]
                 [ring/ring-json "0.5.1"]
                 [ring/ring-defaults "0.3.2"]
                 [org.clojure/java.jdbc "0.7.12"]
                 [org.postgresql/postgresql "42.5.1"]
                 [clj-postgresql "0.7.0"]
                 [cheshire "5.11.0"]]
  :plugins [[lein-ring "0.12.5"]]
  :ring {:handler bike-station-backend.handler/app
         :init   bike-station-backend.handler/set-rent-repo-implementation!}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.2"]]}})
