try {
    bw.write(content);
} catch (IOException e) {
    e.printStackTrace();
} catch (SQLException se) {
    se.printStackTrace();
} finally {
    bw.close();
}
