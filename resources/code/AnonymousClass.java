new VoidVisitorAdapter<Object>() {
    @Override
    public void visit(MethodCallExpr n, Object arg) {
        super.visit(n, arg);
        System.out.println(" [L " + n.getBeginLine() + "] " + n);
    }
}.visit(JavaParser.parse(file), null);
