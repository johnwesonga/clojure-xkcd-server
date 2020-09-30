(ns johnwesonga.controller
  (:require [ring.util.response :as resp]
            [ring.util.http-response :refer :all]
            [clj-http.client :as client]
            [cheshire.core :refer :all]))

(defn index
  [req]
  (let [comic-number (rand-int 999)
        url (str "https://xkcd.com/" comic-number "/info.0.json")
        {:strs [img title]} (-> url
                                (client/get {:accept :json})
                                :body
                                parse-string)
        resp-body (format "<img src='%s' alt='%s'>", img, title)]
    (-> (resp/response resp-body)
        (resp/header "Content-Type" "text/html; charset=utf-8"))))