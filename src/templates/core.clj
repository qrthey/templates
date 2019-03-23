(ns templates.core
  (:require [clojure.string]))

(defn- fill-out-string
  [s [match replacement]]
  (clojure.string/replace s match (str replacement)))

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

    (and (symbol? template) (= (name template) (first replacement-task)))
    (second replacement-task)
    
    :default template))

(defn template->values
  "Takes a template that can be any clojure datastructure, a mach which
  can be a string or a regex, and a sequence of replacement items.
  Returns per replacement item a value that is an exact copy of the
  template, except for strings, keywords and pottentially symbols.
  
  For strings, clojure.string/replace is used to create a copy with
  the match replaced in the resulting strig with the string value of
  the current replacement item.

  For keywords, both the namespace and the name are treated as strings
  and then recomposed in a new keyword. Non namespaced keywords are
  supported as well.

  For symbols, a check is made to see if the name of the symbol
  exactly matches the match, and if so, it gets substituted by the
  replacement item, otherwise the original symbol is kept in the
  resulting value."
  [template match replacements]
  (map #(fill-out-template template [match %])
       replacements))
