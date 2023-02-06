(ns bike-station-backend.service.station
  (:require [bike-station-backend.db.station :as db-station-client]
            [cheshire.core :as json]
            [ring.util.response :refer [response header]]))

(defn get-stations
  []
  (-> db-station-client/get-all-stations
      json/generate-string
      response
      (header "Content-Type" "application/json; charset=utf-8")))
