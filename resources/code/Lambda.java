// Setup a key callback. It will be called every time a key is pressed, repeated or released.
glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
  if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
    glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
});

