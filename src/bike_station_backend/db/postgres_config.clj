(ns bike-station-backend.db.postgres-config)


(def pg-client-opts {:dbtype "postgresql"
                     :dbname "bike_station"
                     :port "5432"
                     :user "bike_station"
                     :password "secret"
                     :host "localhost"})
