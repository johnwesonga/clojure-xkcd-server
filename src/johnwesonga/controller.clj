(ns johnwesonga.controller
  (:require [ring.util.response :as resp]
            [ring.util.http-response :refer :all]
            [clj-http.client :as client]
            [cheshire.core :as cheshire]))

(defn index
  [req]
  (let [comic-number (rand-int 999)
        url (str "https://xkcd.com/" comic-number "/info.0.json")
        {:strs [img title]} (-> url
                                (client/get {:accept :json})
                                :body
                                cheshire/parse-string)
        resp-body (format "<img src=\"%s\" alt=\"%s\">", img, title)]
    (-> (ok resp-body)
        (resp/header "Content-Type" "text/html; charset=utf-8"))))