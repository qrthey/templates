(ns templates.core
  (:require [clojure.string]))

(defn- fill-out-string
  [s replacements]
  (reduce
   (fn [s [match replacement]]
     (clojure.string/replace s match (str replacement)))
   s
   replacements))

(defn- fill-out-keyword
  [kw replacements]
  (let [filler #(fill-out-string % replacements)
        nspace (namespace kw)
        n (name kw)]
    (if nspace
      (keyword (filler nspace) (filler n))
      (keyword (filler n)))))

(defn- fill-out-template
  [template replacements]
  (cond
    (sequential? template)
    (vec (map #(fill-out-template % replacements) template))

    (associative? template)
    (into {}
          (map (fn [[k v]]
                 [(fill-out-template k replacements)
                  (fill-out-template v replacements)])
               template))

    (string? template)
    (fill-out-string template replacements)

    (keyword? template)
    (fill-out-keyword template replacements)

    (and (symbol? template) (contains? replacements (name template)))
    (get replacements (name template))
    
    :default template))

(defn template->values
  "Takes a template that can be any clojure datastructure and a sequence
  of replacement maps. A replacement map describes replacement values
  to create a value from the template. Keys in a replacement map must
  be strings.

  Returns per replacement map a value that is an exact copy of the
  template, except for strings, keywords and pottentially symbols.

  Strings are transformed with clojure.string/replace for each
  key/value pair in a replacement map. The key in the pair is used as
  the match value while the string representation of the value is used
  as the replacement.
  
  For keywords, both the namespace and the name are treated as strings
  and then recomposed in a new keyword. Non namespaced keywords are
  supported as well.

  For symbols, a check is made to see if the name of the symbol
  exactly matches a key in the replacement map, and if so, it gets
  substituted by the value for that key, otherwise the original symbol
  is kept in the resulting value."
  [template replacements]
  (map #(fill-out-template template %)
       replacements))

