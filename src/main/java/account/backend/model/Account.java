package account.backend.model;

public class Account {

    private long id;
    private double saldo;

    public Account(double saldo) {
        this.saldo = saldo;
    }

    public Account() {  }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
}
