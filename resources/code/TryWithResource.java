try ( MemoryStack stack = stackPush() ) {
  IntBuffer pWidth = stack.mallocInt(1);
  IntBuffer pHeight = stack.mallocInt(1);
  glfwGetWindowSize(window, pWidth, pHeight);
}
