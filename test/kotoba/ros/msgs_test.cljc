(ns kotoba.ros.msgs-test
  (:require [clojure.test :refer [deftest is testing]]
            [kotoba.ros.msgs :as msgs]))

(deftest time-roundtrip
  (let [t {:sec 1720000000 :nanosec 123456789}]
    (is (= t (msgs/decode-time (msgs/encode-time t))))))

(deftest header-roundtrip
  (let [h {:stamp {:sec 1720000000 :nanosec 0} :frame_id "base_link"}]
    (is (= h (msgs/decode-header (msgs/encode-header h)))))
  (testing "empty frame_id"
    (let [h {:stamp {:sec 0 :nanosec 0} :frame_id ""}]
      (is (= h (msgs/decode-header (msgs/encode-header h)))))))

(deftest bool-roundtrip
  (is (= {:data true} (msgs/decode-bool (msgs/encode-bool {:data true}))))
  (is (= {:data false} (msgs/decode-bool (msgs/encode-bool {:data false})))))

(deftest vector3-roundtrip
  (let [v {:x 1.0 :y -2.5 :z 0.0}]
    (is (= v (msgs/decode-vector3 (msgs/encode-vector3 v))))))

(deftest twist-roundtrip
  (let [t {:linear {:x 0.5 :y 0.0 :z 0.0} :angular {:x 0.0 :y 0.0 :z 1.2}}]
    (is (= t (msgs/decode-twist (msgs/encode-twist t))))))

(deftest twist-stamped-roundtrip
  (let [ts {:header {:stamp {:sec 100 :nanosec 200} :frame_id "odom"}
            :twist {:linear {:x 1.0 :y 0.0 :z 0.0} :angular {:x 0.0 :y 0.0 :z 0.1}}}]
    (is (= ts (msgs/decode-twist-stamped (msgs/encode-twist-stamped ts))))))

(deftest joy-roundtrip
  (testing "typical PS5 DualSense-shaped joy message"
    (let [j {:header {:stamp {:sec 5 :nanosec 0} :frame_id "joy"}
             :axes [(float 0.0) (float 1.0) (float -1.0) (float 0.5)]
             :buttons [0 1 0 0 1]}]
      (is (= j (msgs/decode-joy (msgs/encode-joy j))))))
  (testing "empty sequences"
    (let [j {:header {:stamp {:sec 0 :nanosec 0} :frame_id ""}
             :axes []
             :buttons []}]
      (is (= j (msgs/decode-joy (msgs/encode-joy j)))))))
