# templates

Produces values by filling out templates.

## Usage

```clojure
(template->values 
  {:nested [{:data :structure-__XXX__-here}
            {:with :more [:nested {:in-string "string-__XXX__-key"}] :v}]}
            "__XXX__"
            ["reinhardt""ionesco" "jung"])

;; ==>

({:nested [{:data :structure-reinhardt-here}
           {:with :more, [:nested {:in-string "string-reinhardt-key"}] :v}]}
 {:nested [{:data :structure-ionesco-here}
           {:with :more, [:nested {:in-string "string-ionesco-key"}] :v}]}
 {:nested [{:data :structure-jung-here}
           {:with :more, [:nested {:in-string "string-jung-key"}] :v}]})
```
## License

Copyright © 2019 Kurt Heyrman

This program and the accompanying materials are made available under
the terms of the [included license](./LICENSE).
