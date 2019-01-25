package ai.Strategies;

import ai.Node;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class BFSStrategy implements IStrategy {

    //BFS adds children to the end of frontier
    @Override
    public void addFrontier(ArrayList<Node> frontier, ArrayList<Node> children) {
        children = children.stream().sorted(Comparator.comparing(pawn -> pawn.getFrom() + pawn.getTo())).collect(Collectors.toCollection(ArrayList::new));
        frontier.addAll(children);
    }

    //This method is critical for IDS algorithm BFS algorithm gives always permission to expand
    @Override
    public boolean isExpandable(Node current) {
        return true;
    }

    //This method is critical for IDS algorithm BFS algorithm do not use
    @Override
    public void notExpandable(ArrayList<Node> frontier) {
        return;
    }

    @Override
    public String toString() {
        return "BFS";
    }
}
