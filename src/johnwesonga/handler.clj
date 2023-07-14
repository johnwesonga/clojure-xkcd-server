(ns johnwesonga.handler
  (:require [reitit.ring :as ring]
            [muuntaja.core :as m]
            [reitit.ring.middleware.muuntaja :as muuntaja]
            [johnwesonga.controller :as ctl]
            [ring.logger :as logger]
            [taoensso.timbre :as log]
            [ring.middleware.json :refer [wrap-json-response wrap-json-body]]))

(defn app
  [db]
  (ring/ring-handler
   (ring/router
    [["/healthz" {:get (fn [_] {:status 200 :body "healthy"})}]
     ["/" {:handler #'ctl/index}]]
    {:data {:muuntaja m/instance
      :middleware [muuntaja/format-response-middleware
                   logger/wrap-with-logger
                  ]}})
   (ring/routes
    (ring/create-resource-handler
     {:path "/"})
    (ring/create-default-handler
     {:not-found (constantly {:status 404 :body "Not found"})}))))