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
	boolean nodeType;//�Ƿ���Ҷ�ӽڵ�,ture��Ҷ�ڵ㣬false����
	boolean isData;//�з���λ���������ݵ㻹��ƽ��ֵtrue����λ����FALSE���ǣ�
	double splitInt;//�ָ����λ��
	int splitDemin;//�ָ��ά��
	KdTreeNode left;//������
	KdTreeNode right;//����
	KdTreeNode parent;
	
}
