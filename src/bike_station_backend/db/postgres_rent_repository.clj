(ns bike-station-backend.db.postgres-rent-repository
  (:refer-clojure :exclude [find])
    (:require [bike-station-backend.domain.rent.rent-repository :refer [RentRepository]]
              [bike-station-backend.domain.rent.rent :refer [map->Rent]]
              [clojure.set :refer [rename-keys]]
              [bike-station-backend.db.postgres-config :refer [pg-client-opts]]
              [clojure.java.jdbc :as db-client]))

(defn rows->rents 
  [rows]
  (->> rows
      (map #(select-keys % [:id :origin_station_id :bike_number :destination_station_id :start_time :end_time :is_active]))
      (map #(rename-keys % {:id :rent-id :origin_station_id :origin-station-id :bike_number :bike-number 
                            :destination_station_id :destination-station-id :start_time :start-time
                            :end_time :end-time :is_active :is-active}))
      (map #(map->Rent %))))

(defn row->rent 
  [row]
  (-> row
      (select-keys [:id :origin_station_id :bike_number :destination_station_id :start_time :end_time :is_active])
      (rename-keys {:id :rent-id :origin_station_id :origin-station-id :bike_number :bike-number
                    :destination_station_id :destination-station-id :start_time :start-time
                    :end_time :end-time :is_active :is-active})
      map->Rent))

(defn rent->row
  [rent version]
  (-> rent
      (select-keys [:id :origin-station-id :bike-number :destination-station-id :start-time :end-time :is-active])
      (rename-keys {:rent-id :id :origin-station-id :origin_station_id :bike-number :bike_number
                    :destination-station-id :destination_station_id :start-time :start_time
                    :end-time :end_time :is-active :is_active})
      (assoc :version version)))


(defn get-all
  [postgres-client-config]
  (let [rows (db-client/query postgres-client-config ["SELECT * FROM rents;"])]
    (when (> (count rows) 0)
      (rows->rents rows))))

(defn find
  [postgres-client-config rent-id]
  (let [row (-> (db-client/query postgres-client-config ["SELECT * FROM rents WHERE id=?;" rent-id] )
                first)]
    (when row
      {:version (:version row) :rent (row->rent row)})))

(defn add!
  [postgres-client-config rent]
  (let [rent-id (-> (db-client/insert! postgres-client-config :rents (rent->row rent 1))
                    first
                    :id)]
    rent-id))

(defn update!
  [postgres-client-config version rent-id a-rent]
  "will implement later"
  )

(defn delete!
  [postgres-client-config rent-id]
  "will implement later")

(defn new-cargo-repository
  [postgres-client-config]
  (reify RentRepository
    (-get-all [_] (get-all postgres-client-config))
    (-find [_ rent-id] (find postgres-client-config rent-id))
    (-add! [_ rent] (add! postgres-client-config rent)) 
    (-delete! [_ rent-id] (delete! postgres-client-config rent-id))
    (-update! [_ concurrency-version rent-id rent] (update! postgres-client-config concurrency-version rent-id rent))))

(comment 
  (find pg-client-opts 2)
  (db-client/query pg-client-opts ["SELECT * FROM rents WHERE id=?;" 1])
  (get-all pg-client-opts)
  (add! pg-client-opts {:origin-station-id 2 :bike-number 456 :destination-station-id 3})
  )