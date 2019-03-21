(ns templates.core-test
  (:require [clojure.test :refer :all]
            [templates.core :refer :all]))

(deftest a-test
  (testing "Some values are correctly produced"
    (is (= '({:nested [{:data :structure-reinhardt-here}
                       {:with :more, [:nested {:in-string "string-reinhardt-key"}] :v}]}
             {:nested [{:data :structure-ionesco-here}
                       {:with :more, [:nested {:in-string "string-ionesco-key"}] :v}]}
             {:nested [{:data :structure-jung-here}
                       {:with :more, [:nested {:in-string "string-jung-key"}] :v}]})

           (template->values 
            {:nested [{:data :structure-__XXX__-here}
                      {:with :more [:nested {:in-string "string-__XXX__-key"}] :v}]}
            "__XXX__"
            ["reinhardt" "ionesco" "jung"])))))




