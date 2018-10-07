try ( MemoryStack stack = stackPush() ) {
    stack.mallocInt(1);
    bw.write(content);
} catch (IOException e) {
    e.printStackTrace();
}
