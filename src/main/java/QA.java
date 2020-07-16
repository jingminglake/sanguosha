public class QA {
    public QA(String Q, String A) {
        this.Q = Q;
        this.A = A;
    }
    private String Q;
    private String A;

    public String getQ() {
        return Q;
    }

    public void setQ(final String q) {
        Q = q;
    }

    public String getA() {
        return A;
    }

    public void setA(final String a) {
        A = a;
    }

    @Override public String toString() {
        return "Q:" + Q + '\n' +
                "A:" + A + '\n';
    }
}
