package ai.Strategies;

import ai.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DFSStrategy implements IStrategy {

    @Override
    public void addFrontier(ArrayList<Node> frontier, ArrayList<Node> children) {
        children = children.stream().sorted(Comparator.comparing(pawn -> pawn.getFrom() + pawn.getTo())).collect(Collectors.toCollection(ArrayList::new));
        frontier.addAll(0, children);
    }

    @Override
    public boolean isExpandable(Node current) {
        return true;
    }

    @Override
    public void notExpandable(ArrayList<Node> frontier) {
        return;
    }
    @Override
    public String toString() {
        return "DFS";
    }
}
