try {
    bw.write(content);
} catch (IOException e) {
    e.printStackTrace();
} catch (SQLException se) {
    e.printStackTrace();
} finally {
    bw.close();
}
