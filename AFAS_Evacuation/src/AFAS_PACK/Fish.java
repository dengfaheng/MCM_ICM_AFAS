package AFAS_PACK;
 
import java.io.IOException;
import java.util.Random;
import java.util.ArrayList;

public class Fish {
	public int NodeID;               //ÿ�������ڽڵ��ID
	public ArrayList<Integer> Xnode;     //�������뾭���Ľڵ�
	public double totalTime;         //�������ʱ��
	public float step;                 //�ƶ�����
	public double fit;                //�����Ӧֵ��ʳ��Ũ��
	public ArrayList<Integer> visaulNode;  //ÿ������Ұ�ڵĽڵ�

	
 
	public Fish() throws IOException {
		super();
		Xnode = new ArrayList<>();
		visaulNode = new ArrayList<>();
		
		Random rand =new Random();
		int i;
		i=rand.nextInt(louvreData.sigmaOfDisabled);
		if(i >= 0 && i <= 10)
			this.step = 0.3f; //�м���
		else
			this.step = 0.5f; //������
		
		this.NodeID = rand.nextInt(louvreData.numberOfNodes)+1; //����ֲ�
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
