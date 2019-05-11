package AFAS_PACK;
 
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

public class Fish {
	public int NodeID;               //每条鱼所在节点的ID
	public ArrayList<Integer> Xnode;     //保留逃离经过的节点
	public double totalTime;         //逃离的总时间
	public float step;                 //移动步长
	public double fit;                //鱼的适应值，食物浓度
	public ArrayList<Integer> visaulNode;  //每条鱼视野内的节点

	
 
	public Fish() throws IOException {
		super();
		Xnode = new ArrayList<>();
		visaulNode = new ArrayList<>();
		
		Random rand =new Random();
		int i;
		i=rand.nextInt(louvreData.sigmaOfDisabled);
		if(i >= 0 && i <= 10)
			this.step = 0.3f; //残疾人
		else
			this.step = 0.5f; //正常人
		
		this.NodeID = rand.nextInt(louvreData.numberOfNodes)+1; //随机分布
		this.totalTime = 0.0f;
		this.fit = louvreData.NodesOfLouvre[this.NodeID].YOfFood;
		this.Xnode.add(this.NodeID);
		this.visaulNode.clear();
		for(i = 1; i <= louvreData.numberOfNodes; i++)
		{
			if(louvreData.matrixOfLouvre[this.NodeID][i] < louvreData.MAX_WEIGHT)
				this.visaulNode.add(i);
		}
		louvreData.NodesOfLouvre[this.NodeID].NumberOfPeople+=1;
		
	}
	
	public void updateFitness()
	{
		this.fit = louvreData.NodesOfLouvre[this.NodeID].YOfFood;
	}
	
	public void updateVisual()
	{
		this.visaulNode.clear();
		for(int i = 1; i <= louvreData.numberOfNodes; i++)
		{
			if(louvreData.matrixOfLouvre[this.NodeID][i] < louvreData.MAX_WEIGHT)
				this.visaulNode.add(i);
		}
		
	}
	

}
