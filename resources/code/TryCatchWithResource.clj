(try
  (let [^MemoryStack stack (.stackPush)]
    (.mallocInt stack 1)
    (.write bw content)
    (catch IOException e
      (.printStackTrace e))
    (finally
      (.close stack))))

