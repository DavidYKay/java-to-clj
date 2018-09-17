// Get the thread stack and push a new frame
try ( MemoryStack stack = stackPush() ) {
  IntBuffer pWidth = stack.mallocInt(1); // int*
  IntBuffer pHeight = stack.mallocInt(1); // int*

  // Get the window size passed to glfwCreateWindow
  glfwGetWindowSize(window, pWidth, pHeight);

  // Get the resolution of the primary monitor
  GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

  // Center the window
  glfwSetWindowPos(
    window,
    (vidmode.width() - pWidth.get(0)) / 2,
    (vidmode.height() - pHeight.get(0)) / 2
  );
} // the stack frame is popped automatically
