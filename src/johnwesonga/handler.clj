(ns johnwesonga.handler
    (:require [reitit.ring :as ring]
              [johnwesonga.controller :as ctl]
              [ring.middleware.json :refer [wrap-json-response wrap-json-body]]))

(defn app
 [db]
  (ring/ring-handler
    (ring/router
      [
          ["/healthz" {:get (fn [_] {:status 200 :body "healthy"})}]
          ["/" {:handler #'ctl/index}]
      ]
      {:data {:middleware [wrap-json-response
                   wrap-json-body]}})
      (ring/routes
            (ring/create-resource-handler
                {:path "/"})
           (ring/create-default-handler
            {:not-found (constantly {:status 404 :body "Not found"})}))))