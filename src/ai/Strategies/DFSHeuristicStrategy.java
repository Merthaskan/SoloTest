package ai.Strategies;

import ai.Node;
import ai.Pawn;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;

public class DFSHeuristicStrategy extends DFSStrategy {
    //Algorithm checks board position is stuck or not. if it is stuck then removing the node
    @Override
    public void addFrontier(ArrayList<Node> frontier, ArrayList<Node> children) {
        children = children.stream().sorted(Comparator.comparing(x -> evaluate(x))).collect(Collectors.toCollection(ArrayList::new));
        frontier.addAll(0, children);
    }

    //Checks node boards if board has edge pawns node take 4 point for each of them
    private int evaluate(Node node) {
        Pawn[][] board = node.getBoard();
        int point = 0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Pawn pawn = board[i][j];
                if (pawn != null && !pawn.isEmpty()) {
                    if ((i == 0 && j == 2) || (i == 0 && j == 4)
                            || (i == 2 && j == 0) || (i == 4 && j == 0)
                            || (i == 6 && j == 2) || (i == 6 && j == 4)
                            || (i == 2 && j == 6) || (i == 4 && j == 6)) {
                        point += 4;
                    } else if ((i == 2 && j == 2) || (i == 2 && j == 4)
                            || (i == 4 && j == 2) || (i == 4 && j == 4)) {
                        point += 3;
                    }
                }
            }
        }
        return point;
    }

    @Override
    public String toString() {
        return "DFS with Heuristic";
    }
}
