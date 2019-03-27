(ns templates.core-test
  (:require [clojure.test :refer :all]
            [templates.core :refer :all]))

(deftest template->values-test
  (testing "Strings, keywords and symbols are handled correctly."
    (is (= '([:a :deeply
              {:nested [:data-structure
                        {:id 1,
                         :status review,
                         :prop-age "val recorded = 20",
                         :verified? :yeah}]}]
             [:a :deeply
              {:nested [:data-structure
                        {:id 2,
                         :status review,
                         :prop-phone "val recorded = 20 30 40 50",
                         :verified? :nope}]}]
             [:a :deeply
              {:nested [:data-structure
                        {:id 3,
                         :status review,
                         :prop-last-gps-position "val recorded = 298EW9879DDK",
                         :verified? :nope}]}])

           (template->values 
            [:a :deeply
             {:nested [:data-structure
                       {:id '<<ID>>
                        :status 'review
                        :prop-<<PROP-NAME>> "val recorded = <<PROP-VAL>>"
                        :verified? '<<IS-VERIFIED?>>}]}]
            [{"<<ID>>" 1
              "<<PROP-NAME>>" "age"
              "<<PROP-VAL>>" 20
              "<<IS-VERIFIED?>>" :yeah}
             {"<<ID>>" 2
              "<<PROP-NAME>>" "phone"
              "<<PROP-VAL>>" "20 30 40 50"
              "<<IS-VERIFIED?>>" :nope}
             {"<<ID>>" 3
              "<<PROP-NAME>>" "last-gps-position"
              "<<PROP-VAL>>" "298EW9879DDK"
              "<<IS-VERIFIED?>>" :nope}])))))
