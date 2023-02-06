(ns bike-station-backend.service.rent
  (:require [bike-station-backend.domain.rent.rent :as rent]
            [bike-station-backend.domain.rent.rent-repository :as rent-repository]
            [cheshire.core :as json]
            [ring.util.response :refer [response header]]))

(defn get-rents
  []
  (-> (rent-repository/get-all)
      json/generate-string
      response
      (header "Content-Type" "application/json; charset=utf-8")))

(defn find-rent
[rent-id]
  (-> (rent-repository/find rent-id)
      json/generate-string
      response
      (header "Content-Type" "application/json; charset=utf-8")))

(defn create-new-rent!
  [& args]
  (let [new-rent (apply rent/create-new-rent args)]
    (-> (rent-repository/add! new-rent)
        json/generate-string
        response
        (header "Content-Type" "application/json; charset=utf-8"))))

(defn update-rent!
  [rent-id data] 
  (-> (rent-repository/update! rent-id data)
      json/generate-string
      response
      (header "Content-Type" "application/json; charset=utf-8")))

(defn delete-rent!
  [rent-id]
  (-> (rent-repository/delete! rent-id)
      json/generate-string
      response
      (header "Content-Type" "application/json; charset=utf-8")))