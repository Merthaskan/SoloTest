package ai;

import ai.Strategies.*;

import java.util.*;

public class SoloTest {
    public static void main(String[] args) {
        //Creating 2d Pawn Array for board
        Pawn[][] board = new Pawn[7][7];
        //Initialize board as the sheet
        fillBoard(board);
        //Creating initial state which has first board position and root of the decision tree
        Node initialState = new Node(0, 0, null, board, 0);
        //Creating search algorithms and add them to the array
        IStrategy[] strategies = {new BFSStrategy(), new DFSStrategy(), new IDSStrategy(initialState), new DFSRandomSelectStrategy(), new DFSHeuristicStrategy()};
        //Creating menu
        while (true) {
            System.out.println("Please select algorithm and enter working minutes");
            for (int i = 0; i < strategies.length; i++)
                System.out.println(String.format("%d - %s", i, strategies[i]));
            System.out.println("5 - Exit");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter an index: ");
            int algorithmIndex = scanner.nextInt();
            if (algorithmIndex >= 5 || algorithmIndex < 0)
                System.exit(0);

            System.out.print("Enter an minutes: ");
            int minutes = scanner.nextInt();
            //Using tree search algorithm with search algorithms
            SearchResult result = treeSearch(initialState, strategies[algorithmIndex], minutes * 60 * 1000);
            //Creating stack for printing path initial to result order
            Stack<Node> pathStack = new Stack<>();
            Node goalNode = result.getResult();
            while (goalNode != null) {
                pathStack.push(goalNode);
                goalNode = goalNode.getParent();
            }
            System.out.println(String.format("The search method: %s", strategies[algorithmIndex]));
            System.out.println(String.format("The time limit: %s minutes", minutes));
            printPath(pathStack, result.getNodeVisited(), result.isOptimal(), result.getOptimalFoundingTime());
        }
    }

    //Method for printing result path
    private static void printPath(Stack<Node> pathStack, long totalNode, boolean isOptimal, float elapsedTime) {
        Node node = null;
        while (!pathStack.empty()) {
            node = pathStack.pop();
            System.out.println("Depth Level: " + node.getDepthLevel());
            if (node.getTo() != 0 && node.getFrom() != 0)
                System.out.println(String.format("Move %s to %s", node.getFrom(), node.getTo()));

            printBoard(node.getBoard());
            System.out.println("---------------------");
        }
        System.out.println("Total Visited Node: " + totalNode);

        if (isOptimal) {
            System.out.println("Optimum solution found.");
            System.out.println(String.format("Elapsed Time: %s minutes", elapsedTime / (60 * 1000)));
        } else
            System.out.println(String.format("Sub-optimum Solution Found with %d remaining pegs", 32 - node.getDepthLevel()));

    }

    //Method for printing board and pawns
    private static void printBoard(Pawn[][] board) {
        for (Pawn[] pawns : board) {
            for (Pawn pawn : pawns) {
                if (pawn == null)
                    System.out.print(" ");
                else if (pawn.isEmpty())
                    System.out.print("O");
                else if (!pawn.isEmpty())
                    System.out.print("X");
            }
            System.out.println();
        }
    }

    //Tree search algorithm
    private static SearchResult treeSearch(Node initialState, IStrategy strategy, long finishTime) {
        //Initialize frontier as list
        ArrayList<Node> frontier = new ArrayList<>();
        //Adding starting node
        frontier.add(initialState);
        //Taking starting time for time limitation to the stop the algorithm
        long startingTime = new Date().getTime();
        long takedTime = startingTime;
        //Holding Sub-Optimal result
        Node deepestResult = null;
        //Holds expanded node number
        long nodeExpanded = 0;
        //Loop iterates until frontier empty or time limit reach
        while (!frontier.isEmpty() && ((takedTime - startingTime) < finishTime)) {
            //Taking the node from head of list
            Node current = frontier.get(0);
            //Remove the node from list
            frontier.remove(0);
            //Checks the node is goal or not
            if (checkGoal(current.getBoard()))
                return new SearchResult(nodeExpanded, current, true, (new Date().getTime() - startingTime)); // return Optimal result
            //Checks the depth level and update sub optimal result
            if (deepestResult == null || deepestResult.getDepthLevel() < current.getDepthLevel()) {
                deepestResult = current;
            }

            //Finding children of node and add the frontier based on search algorithm behaviour
            expand(current, strategy, frontier);
            //Takes time for checking the time limit
            takedTime = new Date().getTime();
            //Increase the expanded node count
            nodeExpanded++;
        }
        //Return sub-optimal solution
        return new SearchResult(nodeExpanded, deepestResult, false, 0);
    }

    private static void expand(Node node, IStrategy strategy, ArrayList<Node> frontier) {
        //Is expandable methods checks node children should be found or not (for IDS is important)
        if (strategy.isExpandable(node)) {
            //Finds children of the node
            ArrayList<Node> children = getChildren(node);
            //Add children to the frontier based on strategy
            strategy.addFrontier(frontier, children);
        } else {
            //IDS loads the initial state for returning the beginning other algorithms do nothing
            strategy.notExpandable(frontier);
        }
    }

    //Method for finding empty locations and possible movements and then return children list
    private static ArrayList<Node> getChildren(Node node) {
        Pawn[][] board = node.getBoard();
        ArrayList<Node> movements = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                Pawn pawn = board[i][j];
                if (pawn != null && pawn.isEmpty()) {
                    if (i - 2 >= 0 && board[i - 2][j] != null && !board[i - 2][j].isEmpty() && !board[i - 1][j].isEmpty()) {
                        Pawn[][] newBoard = cloneArray(board);
                        Pawn predator = newBoard[i - 2][j];
                        predator.setEmpty(true);
                        Pawn victim = newBoard[i - 1][j];
                        victim.setEmpty(true);
                        Pawn empty = newBoard[i][j];
                        empty.setEmpty(false);
                        Node child = new Node(predator.getNumber(), empty.getNumber(), node, newBoard, node.getDepthLevel() + 1);
                        movements.add(child);
                    }
                    if (j - 2 >= 0 && board[i][j - 2] != null && !board[i][j - 2].isEmpty() && !board[i][j - 1].isEmpty()) {
                        Pawn[][] newBoard = cloneArray(board);
                        Pawn predator = newBoard[i][j - 2];
                        predator.setEmpty(true);
                        Pawn victim = newBoard[i][j - 1];
                        victim.setEmpty(true);
                        Pawn empty = newBoard[i][j];
                        empty.setEmpty(false);
                        Node child = new Node(predator.getNumber(), empty.getNumber(), node, newBoard, node.getDepthLevel() + 1);
                        movements.add(child);
                    }
                    if (j + 2 <= 6 && board[i][j + 2] != null && !board[i][j + 2].isEmpty() && !board[i][j + 1].isEmpty()) {
                        Pawn[][] newBoard = cloneArray(board);
                        Pawn predator = newBoard[i][j + 2];
                        predator.setEmpty(true);
                        Pawn victim = newBoard[i][j + 1];
                        victim.setEmpty(true);
                        Pawn empty = newBoard[i][j];
                        empty.setEmpty(false);
                        Node child = new Node(predator.getNumber(), empty.getNumber(), node, newBoard, node.getDepthLevel() + 1);
                        movements.add(child);
                    }
                    if (i + 2 <= 6 && board[i + 2][j] != null && !board[i + 2][j].isEmpty() && !board[i + 1][j].isEmpty()) {
                        Pawn[][] newBoard = cloneArray(board);
                        Pawn predator = newBoard[i + 2][j];
                        predator.setEmpty(true);
                        Pawn victim = newBoard[i + 1][j];
                        victim.setEmpty(true);
                        Pawn empty = newBoard[i][j];
                        empty.setEmpty(false);
                        Node child = new Node(predator.getNumber(), empty.getNumber(), node, newBoard, node.getDepthLevel() + 1);
                        movements.add(child);
                    }
                }
            }
        }
        return movements;
    }

    //Method for clone board reference to a new board
    private static Pawn[][] cloneArray(Pawn[][] src) {
        int length = src.length;
        Pawn[][] target = new Pawn[length][src[0].length];
        for (int i = 0; i < length; i++) {
            target[i] = Arrays.stream(src[i])
                    .map(point -> point == null ? null : new Pawn(point))
                    .toArray(Pawn[]::new);
        }
        return target;
    }

    //Method for check node is goal or not
    private static boolean checkGoal(Pawn[][] board) {
        boolean isGoal = true;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == 0 || i == 1 || i == 5 || i == 6) {
                    if (j == 0 || j == 1 || j == 5 || j == 6) {
                        isGoal = (board[i][j] == null) && isGoal;
                    } else {
                        isGoal = board[i][j].isEmpty() && isGoal;
                    }
                } else {
                    if (i == 3 && j == 3)
                        isGoal = !board[i][j].isEmpty() && isGoal;
                    else
                        isGoal = board[i][j].isEmpty() && isGoal;
                }
            }
        }
        return isGoal;
    }

    //Initialize the board to starting position
    private static void fillBoard(Pawn[][] board) {
        int numerator = 1;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (i == 0 || i == 1 || i == 5 || i == 6) {
                    if (j == 0 || j == 1 || j == 5 || j == 6) {
                        board[i][j] = null;
                    } else {
                        Pawn pawn = new Pawn();
                        pawn.setNumber(numerator++);
                        pawn.setEmpty(false);
                        board[i][j] = pawn;
                    }
                } else {
                    Pawn pawn = new Pawn();
                    pawn.setNumber(numerator++);
                    board[i][j] = pawn;
                    if (i == 3 && j == 3)
                        pawn.setEmpty(true);
                    else
                        pawn.setEmpty(false);
                }
            }
        }
    }
}