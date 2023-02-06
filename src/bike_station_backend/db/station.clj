(ns bike-station-backend.db.station
  (:require [clojure.java.jdbc :as client]))

(def pg-client-opts {:dbtype "postgresql"
                        :dbname "bike_station"
                        :port "5432"
                        :user "bike_station"
                        :password "secret"
                        :host "localhost"})

(defn get-all-stations
  []
  (client/query pg-client-opts ["SELECT * FROM stations;"]))

(defn get-all-rents
  []
  (client/query pg-client-opts ["SELECT * FROM rents;"]))

(defn create-new-rental
  [data]
  (client/insert! pg-client-opts :rents data))

(defn update-existing-rental
  [column-name->rent-id data]
  (client/update! pg-client-opts :rents data column-name->rent-id))

(defn delete-active-rental
  [column-name->rent-id]
  (client/delete! pg-client-opts :rents column-name->rent-id))


;; (client/insert! pg-db-client-opts :stations {:id 1 :name "Indiranagar" :location "[40.7, -74]" :available_bikes_quantity 10})
