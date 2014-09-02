import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.Stack;

public class KdTree {

	/**
	 * @param args
	 */
	static int k = 4;
	static Data trainData;
	static int colLen;
	static int rowLen;

	public static void main(String[] args) throws Exception {
		new CopyOfGenerateTrainTest().splitData("data/iris.data");
		trainData = new FileRead("data/train.data").readData();

		colLen = trainData.getX().length;
		rowLen = trainData.getX()[0].length;

		Set<Pair> sets = new HashSet<Pair>();

		for (int i = 0; i < colLen; i++) {
			sets.add(new Pair(trainData.getX()[i], trainData.getY()[i]));
		}

		KdTreeNode root = null;
		
		KdTreeNode rootKdTreeNode = KdTree.bulidKdTree(root, sets);
		Stack<KdTreeNode> set = new Stack<KdTreeNode>();
		double []x = {4.9,3.1,1.5,0.1};
		int y = 1;
		System.out.println("split:"+rootKdTreeNode.splitDemin);
		set.add(rootKdTreeNode);
		KdTreeNode minNode = findNearst(rootKdTreeNode,
				set, new Pair(x,y));
		System.out.println(minNode.pair.getY());

	}

	public static KdTreeNode bulidKdTree(KdTreeNode root, Set<Pair> set) {
		if (root == null) {
			root = new KdTreeNode();
		}
		int size = set.size();
		if (size == 1) {
			root.nodeType = true;
			System.out.println("nodeTypeTrue");
			root.isData = true;
			for (Pair a : set) {
				root.pair = a;
				// System.out.println("root.splitInt:"+root.splitInt);
				// System.out.println(a.getY());
			}
			return root;
		}
		if (size == 0) {
			return null;
		}

		double[] sumsquare = new double[k];
		double[] mean = new double[k];
		Iterator<Pair> it = set.iterator();
		for (int i = 0; i < set.size(); i++) {
			Pair pair = it.next();
			for (int j = 0; j < k; j++) {
				mean[j] += pair.getX()[j];
			}
		}
		for (int j = 0; j < k; j++) {
			mean[j] /= set.size();
		}
		it = set.iterator();
		for (int i = 0; i < set.size(); i++) {
			Pair pair = it.next();
			for (int j = 0; j < k; j++) {
				sumsquare[j] += Math.pow((pair.getX()[j] - mean[j]), 2);
			}
		}

		int max = Integer.MIN_VALUE;
		int maxValue = 0;

		for (int i = 0; i < k; i++) {
			if (sumsquare[i] > max) {
				maxValue = i;
			}
		}

		root.splitDemin = maxValue;

		double[] dim = new double[set.size()];

		it = set.iterator();
		for (int i = 0; i < set.size(); i++) {
			dim[i] = it.next().getX()[maxValue];
		}

		Arrays.sort(dim);

		double middle = 0;

		if (dim.length % 2 == 0) {
			middle = (dim[(dim.length - 1) / 2] + dim[(dim.length - 1) / 2 + 1]) / 2;
			root.isData = false;
			// System.out.println("isdata:"+root.isData);
			root.splitInt = middle;
			root.nodeType = false;
		} else {
			middle = dim[(dim.length - 1) / 2];
			root.isData = true;
			// System.out.println("isdata:"+root.isData);
			root.splitInt = middle;
			root.nodeType = false;
			Pair pair;
			it = set.iterator();
			while (it.hasNext()) {
				pair = it.next();
				if (pair.getX()[maxValue] == middle) {
					// System.out.println("middle:"+middle);
					root.pair = pair;
					break;
				}
			}
		}
		it = set.iterator();
		Set<Pair> set1 = new HashSet<Pair>();
		Set<Pair> set2 = new HashSet<Pair>();

		if (root.isData == true) {
			while (it.hasNext()) {
				Pair pair = it.next();
				if (pair.getX()[maxValue] <= root.pair.getX()[maxValue]
						&& pair != root.pair) {
					set1.add(pair);

				} else if (pair.getX()[maxValue] > root.pair.getX()[maxValue]
						&& pair != root.pair) {
					set2.add(pair);

				}

			}
		} else if (root.isData == false) {
			while (it.hasNext()) {
				Pair pair = it.next();
				if (pair.getX()[maxValue] <= middle) {
					set1.add(pair);
				} else {
					set2.add(pair);
				}
			}
		}
		KdTreeNode rootLeft = null;
		KdTreeNode rootRight = null;
		// System.out.println("size:"+set.size());
		// System.out.println("size1:"+set1.size());
		// System.out.println("size2:"+set2.size());
		root.left = bulidKdTree(rootLeft, set1);
		root.right = bulidKdTree(rootRight, set2);
		return root;
	}

	public static KdTreeNode findNearst(KdTreeNode root,
			Stack<KdTreeNode> stack, Pair pair) {
		System.out.println("stackSize:"+stack.size());
		KdTreeNode node = stack.firstElement();
		while (!node.nodeType) {
			if (node.pair.getX()[node.splitDemin] > pair.getX()[node.splitDemin]) {
				stack.add(node.left);
			} else {
				stack.add(node.right);
			}
			System.out.println("size:"+stack.size());
			node = stack.firstElement();
			System.out.println(node.nodeType);
		}

		KdTreeNode minNode = stack.firstElement();
		double minDis = pair.distance(minNode.pair);

		while (stack.firstElement() != root) {
			KdTreeNode node2 = stack.pop();
			if (node2.parent.left == node2) {
				if (node2.parent.right.pair.distance(pair) < minDis + 0.01) {
					Stack<KdTreeNode> stack2 = new Stack<KdTreeNode>();
					stack2.add(node2.parent.right);
					KdTreeNode kd = findNearst(node2.parent.right, stack2, pair); 
					if(pair.distance(minNode.pair) > kd.pair.distance(pair)){
						minNode = kd;
						minDis = kd.pair.distance(pair);
					}
					
				}else if (node2.parent.right.pair.distance(pair) < minDis + 0.01){
					Stack<KdTreeNode> stack2 = new Stack<KdTreeNode>();
					stack2.add(node2.parent.right);
					KdTreeNode kd = findNearst(node2.parent.left, stack2, pair); 
					if(pair.distance(minNode.pair) > kd.pair.distance(pair)){
						minNode = kd;
						minDis = kd.pair.distance(pair);
					}
				}
			}
		}

		return minNode;
	}
}
