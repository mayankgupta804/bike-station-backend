(ns bike-station-backend.domain.rent.rent)

(defrecord Rent [rent-id origin-station-id bike-number destination-station-id start-time end-time is-active])

(defn create-new-rent
  [& {:keys [origin-station-id bike-number destination-station-id] :as rent-data}]
  {:pre [(integer? origin-station-id)
         (integer? bike-number)
         (integer? destination-station-id)]}
  (map->Rent rent-data))

(defn update-rent
  [a-rent & {:keys [destination-station-id end-time is-active?]}]
  {:pre [(true? (:is-active a-rent))]}
  (let [updated-rent (cond-> a-rent
                       (integer? destination-station-id) (assoc :destination-station-id destination-station-id)
                       (inst? (java.time.LocalDate/now)) (assoc :end-time end-time)
                       (boolean? is-active?) (assoc :is-active? is-active?))]
    (map->Rent updated-rent)))
