package ai.Strategies;

import ai.Node;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class DFSRandomSelectStrategy extends DFSStrategy {
    @Override
    public void addFrontier(ArrayList<Node> frontier, ArrayList<Node> children) {
        Collections.shuffle(children);
        frontier.addAll(0, children);
    }
    @Override
    public String toString() {
        return "DFS with Random Node Selection";
    }
}
