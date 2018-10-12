(loop []
  (when (< c 11)
    (println "Count is: " c)
    (inc c)
    (recur)))
