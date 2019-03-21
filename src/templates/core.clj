(ns templates.core
  (:require [clojure.string]))

(defn- fill-out-string
  [s [match replacement]]
  (clojure.string/replace s match replacement))

(defn- fill-out-keyword
  [kw replacement-task]
  (let [filler #(fill-out-string % replacement-task)
        nspace (namespace kw)
        n (name kw)]
    (if nspace
      (keyword (filler nspace) (filler n))
      (keyword (filler n)))))

(defn- fill-out-template
  [template replacement-task]
  (cond
    (sequential? template)
    (vec (map #(fill-out-template % replacement-task) template))

    (associative? template)
    (into {}
          (map (fn [[k v]]
                 [(fill-out-template k replacement-task)
                  (fill-out-template v replacement-task)])
               template))

    (string? template)
    (fill-out-string template replacement-task)

    (keyword? template)
    (fill-out-keyword template replacement-task)
    
    :default template))

(defn template->values
  [template match replacements]
  (map #(fill-out-template template [match %])
       replacements))
