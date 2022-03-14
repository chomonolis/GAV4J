package yokono.gav.main;

import java.util.Scanner;

import yokono.gav.datastructer.BNode;
import yokono.gav.datastructer.GraphElements;
import yokono.gav.datastructer.Node;

public class TestGraphInput {
	public static GraphElements TestInput() {
		@SuppressWarnings("resource")
		Scanner sc = new Scanner(System.in);
		String line = sc.nextLine();
		String[] ll = line.split(" ");
		int n = Integer.parseInt(ll[0]), m = Integer.parseInt(ll[1]), st = Integer.parseInt(ll[2]);
		GraphElements g = new GraphElements(n, new BNode(-100), st);
		for(Node node : g.graph) {
			node.px = Math.random(); node.py = Math.random();
		}
		for(int i = 0; i < m; i++) {
			line = sc.nextLine();
			ll = line.split(" ");
			int a = Integer.parseInt(ll[0]), b = Integer.parseInt(ll[1]);
			a--; b--;
			g.addPath(a, b, 1);
			g.addPath(b, a, 1);
		}
		return g;
	}
}
