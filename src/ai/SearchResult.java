package ai;

/*
    Search Result class hold information for necessary tree search return
 */
public class SearchResult {
    private long nodeVisited;
    private Node result;
    private boolean isOptimal;
    private float optimalFoundingTime;

    public SearchResult(long nodeVisited, Node result, boolean isOptimal, float optimalFoundingTime) {
        this.nodeVisited = nodeVisited;
        this.result = result;
        this.isOptimal = isOptimal;
        this.optimalFoundingTime = optimalFoundingTime;
    }

    public float getOptimalFoundingTime() {
        return optimalFoundingTime;
    }

    public void setOptimalFoundingTime(float optimalFoundingTime) {
        this.optimalFoundingTime = optimalFoundingTime;
    }

    public long getNodeVisited() {
        return nodeVisited;
    }

    public void setNodeVisited(long nodeVisited) {
        this.nodeVisited = nodeVisited;
    }

    public Node getResult() {
        return result;
    }

    public void setResult(Node result) {
        this.result = result;
    }

    public boolean isOptimal() {
        return isOptimal;
    }

    public void setOptimal(boolean optimal) {
        isOptimal = optimal;
    }
}
