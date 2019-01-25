package ai;

/*
    Node class has from number, to number, depthLevel, parent and board
    to and from using for movement sort
    parent using for finding result path
*/
public class Node {
    private int from;
    private int to;
    private int depthLevel;
    private Node parent;
    private Pawn[][] board;

    public Node(int from, int to, Node parent, Pawn[][] board, int depthLevel) {
        this.from = from;
        this.to = to;
        this.parent = parent;
        this.board = board;
        this.depthLevel = depthLevel;
    }

    public int getDepthLevel() {
        return depthLevel;
    }

    public void setDepthLevel(int depthLevel) {
        this.depthLevel = depthLevel;
    }

    public int getFrom() {
        return from;
    }

    public void setFrom(int from) {
        this.from = from;
    }

    public int getTo() {
        return to;
    }

    public void setTo(int to) {
        this.to = to;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public Pawn[][] getBoard() {
        return board;
    }

    public void setBoard(Pawn[][] board) {
        this.board = board;
    }
}
