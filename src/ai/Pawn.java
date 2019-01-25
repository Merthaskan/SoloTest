package ai;

/*
    Pawn is the location of the board
 */
public class Pawn {
    private int number;
    private boolean isEmpty;

    public Pawn() {
    }

    public Pawn(Pawn pawn) {
        this.number = pawn.getNumber();
        this.isEmpty = pawn.isEmpty();
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }
}
