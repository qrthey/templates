(ns templates.core-test
  (:require [clojure.test :refer :all]
            [templates.core :refer :all]))

(deftest fill-out-template-test
  (testing "Strings, keywords and symbols are handled correctly."
    (is (= [:a :deeply
              {:nested [:data-structure
                        {:id 1,
                         :status 'review,
                         :prop-age "val recorded = 20",
                         :verified? :yeah}]}]

           (fill-out-template
            [:a :deeply
             {:nested [:data-structure
                       {:id '<<ID>>
                        :status 'review
                        :prop-<<PROP-NAME>> "val recorded = <<PROP-VAL>>"
                        :verified? '<<IS-VERIFIED?>>}]}]
            {"<<ID>>" 1
              "<<PROP-NAME>>" "age"
              "<<PROP-VAL>>" 20
              "<<IS-VERIFIED?>>" :yeah})))))
