(try
  (.write bw content)
  (catch IOException e
    (.printStackTrace e))
  (catch SQLException se
    (.printStackTrace se))
  (finally
    (.close bw)))

