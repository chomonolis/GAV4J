package yokono.gav.datastructer;

public class DoubleNode {
	public Node node1, node2;

	public DoubleNode(Node node1, Node node2) {
		if(node1.number > node2.number) {
			Node tmp = node1;
			node1 = node2;
			node2 = tmp;
		}
		this.node1 = node1;
		this.node2 = node2;
	}
}
