package ai.Strategies;

import ai.Node;

import java.util.ArrayList;

public class IDSStrategy extends DFSStrategy {
    private Node initialState;
    private int length;
    private int maxLength = 31;

    public IDSStrategy(Node initialState) {
        super();
        this.initialState = initialState;
        this.length = 0;
    }

    @Override
    public boolean isExpandable(Node current) {
        return !(current.getDepthLevel() == length);
    }

    @Override
    public void notExpandable(ArrayList<Node> frontier) {
        if (frontier.size() == 0 && length < maxLength) {
            frontier.add(initialState);
            this.length++;
        }
    }

    @Override
    public String toString() {
        return "IDS";
    }
}
