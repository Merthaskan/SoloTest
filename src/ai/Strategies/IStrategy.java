package ai.Strategies;

import ai.Node;

import java.util.ArrayList;

/*
    IStrategy interface define a format for search algorithms
    this interface make possible polymorphism
 */
public interface IStrategy {
    void addFrontier(ArrayList<Node> frontier, ArrayList<Node> children);

    boolean isExpandable(Node current);

    void notExpandable(ArrayList<Node> frontier);
}
