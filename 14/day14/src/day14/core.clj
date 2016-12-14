(ns day14.core
  (:require [clojure.string :as str] [digest]))

(def inp "ngcjuoqr")

(def hsh (memoize (fn [n] (digest/md5 (str inp n)))))
(def hsh2017 (memoize (fn [n] (nth (iterate digest/md5 (str inp n)) 2017))))
(defn hshes [] (map #(vec [% (hsh %)]) (range)))
(defn hshes2017 [] (map #(vec [% (hsh2017 %)]) (range)))

(defn triple-seq [[n x]]
  (loop [s x c nil]
    (if (or c (< (count s) 3)) [n c]
      (recur (rest s) (if (apply = (take 3 s)) (first s) nil)))))

(defn search1k [f [i c]]
  (let [s (str/join [c c c c c]) m (+ i 1000)]
    (loop [n (inc i) found nil]
      (if (or found (> n m)) found
        (recur (inc n) (if (str/includes? (f n) s) [i n]))))))

(defn candidates [f]
  (filter #(some? (second %)) (map triple-seq (f))))

(defn part-one []
  (first (nth (filter some? (map #(search1k hsh %) (candidates hshes))) 63)))

(defn part-two []
  (first (nth (filter some? (map #(search1k hsh2017 %) (candidates hshes2017))) 63)))
