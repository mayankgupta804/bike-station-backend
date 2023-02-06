(ns bike-station-backend.handler
  (:require [compojure.core :refer [defroutes GET PUT POST DELETE]]
            [compojure.route :as route]
            [bike-station-backend.service.station :as station-service] 
            [bike-station-backend.service.rent :as rent-service] 
            [bike-station-backend.domain.rent.rent-repository :as rent-repository]
            [bike-station-backend.db.postgres-rent-repository :as postgres-rent-repository]
            [bike-station-backend.db.postgres-config :as postgres-config]
            [ring.middleware.defaults :refer [api-defaults wrap-defaults]]
            [ring.middleware.json :refer [wrap-json-body]]))

(def db-spec postgres-config/pg-client-opts)

(def the-rent-repo (postgres-rent-repository/new-cargo-repository db-spec))

(defn set-rent-repo-implementation! 
  [] 
  (rent-repository/set-implementation! the-rent-repo))

(defroutes app-routes
  (GET "/" [] "Welcome to the bike station app")
  (GET "/stations/" [] (station-service/get-stations))
  (GET "/rents/" [] (rent-service/get-rents)) 
  (GET "/rents/:id{[0-9]+}" [id] (rent-service/find-rent id))
  (POST "/rents/" [_ :as {body :body}] (rent-service/create-new-rent! body))
  (PUT "/rents/:id{[0-9]+}" [id :as {body :body}] (rent-service/update-rent! id body))
  (DELETE "/rents/:id{[0-9]+}" [id] (rent-service/delete-rent! id))
  (route/not-found "Not Found"))

(def app
  (-> (wrap-defaults app-routes api-defaults)
      wrap-json-body))
