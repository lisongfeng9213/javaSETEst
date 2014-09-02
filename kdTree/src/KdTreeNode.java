public class KdTreeNode {

	public KdTreeNode(boolean nodeType, boolean isData, double splitInt,
			int splitDemin, KdTreeNode left, KdTreeNode right, KdTreeNode parent) {
		super();
		this.nodeType = nodeType;
		this.isData = isData;
		this.splitInt = splitInt;
		this.splitDemin = splitDemin;
		this.left = left;
		this.right = right;
		this.parent = parent;
	}
	
	public KdTreeNode(){
		
	}
	
	
	Pair pair;
	boolean nodeType;//是否是叶子节点,ture是叶节点，false不是
	boolean isData;//切分中位数点是数据点还是平均值true是中位数，FALSE不是；
	double splitInt;//分割的中位数
	int splitDemin;//分割的维度
	KdTreeNode left;//左子树
	KdTreeNode right;//右树
	KdTreeNode parent;
	
}
