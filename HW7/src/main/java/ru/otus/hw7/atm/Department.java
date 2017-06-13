package ru.otus.hw7.atm;

public class Department extends java.util.Observable {

    public int getBalance() {
        Notify notify = new Notify(ATMCommand.COUNT_BALANCE);
        setChanged();
        notifyObservers(notify);
        return notify.getBalance();
    }

    public void restoreState() {
        setChanged();
        notifyObservers(new Notify(ATMCommand.RESTORE_STATE));
    }

    public static class Notify {
        private ATMCommand command;
        private int balance;

        public Notify(ATMCommand command) {
            this.command = command;
            this.balance = 0;
        }

        public ATMCommand getCommand() {
            return command;
        }

        public int getBalance() {
            return balance;
        }

        public void addBalance(int add) {
            balance += add;
        }

    }

}
