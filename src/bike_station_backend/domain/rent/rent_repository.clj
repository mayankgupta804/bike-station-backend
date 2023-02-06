(ns bike-station-backend.domain.rent.rent-repository
  (:refer-clojure :exclude [find]))

(defprotocol RentRepository 
  (-get-all [this])
  (-find [this rent-id])
  (-add! [this a-rent])
  (-delete! [this rent-id])
  (-update! [this concurrency-version rent-id a-rent]))

(defn set-implementation!
  [impl]
  (def get-all 
    (partial -get-all impl))
  (def find 
    (partial -find impl))
  (def add! 
    (partial -add! impl))
  (def delete!
    (partial -delete! impl))
  (def update! 
    (partial -update! impl)))