(ns templates.core-test
  (:require [clojure.test :refer :all]
            [templates.core :refer :all]))

(deftest template->values-test
  (testing "Strings and keywords are handled for matches."
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
            ["reinhardt" "ionesco" "jung"]))))
  (testing "Symbols matching exactly are handled."
    (is (= '({:nested [{:data :structure-120-here}
                       {:with :more,
                        [= :factor 120] :t,
                        [:nested {:in-string "string-120-key"}] :v}]}
             {:nested [{:data :structure-130-here}
                       {:with :more,
                        [= :factor 130] :t,
                        [:nested {:in-string "string-130-key"}] :v}]}
             {:nested [{:data :structure-140-here}
                       {:with :more,
                        [= :factor 140] :t,
                        [:nested {:in-string "string-140-key"}] :v}]})

           (template->values 
            {:nested [{:data :structure-__XXX__-here}
                      {:with :more
                       ['= :factor '__XXX__] :t
                       [:nested {:in-string "string-__XXX__-key"}] :v}]}
            "__XXX__"
            [120 130 140])))))
