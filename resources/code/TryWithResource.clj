(let [^MemoryStack stack (.stackPush)]
  (.mallocInt stack 1)
  (.close stack))
