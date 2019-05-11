package AFAS_PACK;
 
 
import java.io.IOException;
import java.util.Date;
import java.util.Random;
 
 
public class AFAS {
	    //鱼群数目
		private int fishNum;
		//尝试次数
		private int tryTime;
		Fish maxfish;

		//人工鱼群、范围内最佳鱼，遍历时的下一条鱼
		Fish[] fishPopulation;
		Fish bestfish;
		Fish[] nextFish;

		
		public AFAS(){
			
		}
		public AFAS(int fishNum, int tryTime) throws IOException 
		{
			super();
			this.fishNum = fishNum;
			this.tryTime = tryTime;

			fishPopulation = new Fish[fishNum];
			nextFish = new Fish[3];
			maxfish = new Fish();

			init();
		}
		
		private void init() throws IOException {
			for(int i=0;i<fishNum;i++)
			{
				fishPopulation[i] = new Fish();
			}
			for(int i = 0; i < 3; i++)
				nextFish[i] = new Fish();
			bestfish = new Fish();
			bestfish.fit = -999999;
		}
		
		public void doAFAS(int GenNum) throws IOException 
		{  
			long startTime = new Date().getTime();
			int count = 1;             				//计算查找次数
			
//			System.out.println("initial status: "+count+"");
//			
//			 for(int i = 1; i <= louvreData.numberOfNodes; i++)
//		     {
//		    	 System.out.println("Node["+i+"] = "+louvreData.NodesOfLouvre[i].NumberOfPeople);
//		     }
			
			while(count<=GenNum)
			{
				 System.out.println("==GenNum : "+count+" ==");
			     for(int i=0; i<fishNum; i++)
			     {
			    	 prey(i);
			         swarm(i);
			         follow(i);
			         bulletin(i);
			         if(louvreData.NodesOfLouvre[fishPopulation[i].NodeID].NodeType != TYPE.NORMAL)
			         {
			        	 System.out.println("fish["+i+"]已经到达出口，"+"totalTime = "+fishPopulation[i].totalTime+"  Xnode = "+fishPopulation[i].Xnode+"\n");
			         }

			     }
//			     for(int i = 1; i <= louvreData.numberOfNodes; i++)
//			     {
//			    	 System.out.println("Node["+i+"] = "+louvreData.NodesOfLouvre[i].NumberOfPeople);
//			     }
//			     
			     
				 System.out.println();
				 count++;
 
			}
			long endTime = new Date().getTime();
			System.out.println("本程序运行计时： "+(endTime-startTime)+" 毫秒。");
		}
			
		
		private void bulletin(int fish_i) throws IOException {
			//到达出口
			if(louvreData.NodesOfLouvre[fishPopulation[fish_i].NodeID].NodeType != TYPE.NORMAL)
				return;

			maxfish = nextFish[0];
			for(int j=0;j<3;j++)
			{
				if(nextFish[j].fit>maxfish.fit)
				{
					maxfish = nextFish[j];
				}
			}
			if(maxfish.fit<fishPopulation[fish_i].fit)
			{
				return ;
			}
			
			fishPopulation[fish_i].totalTime += maxfish.totalTime;
			louvreData.NodesOfLouvre[fishPopulation[fish_i].NodeID].NumberOfPeople -= 1;
			fishPopulation[fish_i].NodeID = maxfish.NodeID;
			fishPopulation[fish_i].Xnode.add(fishPopulation[fish_i].NodeID);
			fishPopulation[fish_i].fit = maxfish.fit;
			fishPopulation[fish_i].visaulNode.clear();
			fishPopulation[fish_i].visaulNode = maxfish.visaulNode;
			louvreData.NodesOfLouvre[fishPopulation[fish_i].NodeID].NumberOfPeople+=1;
			
			
			if(maxfish.fit>bestfish.fit)
				bestfish = maxfish;
		}
		
		
		private void follow(int fish_i) throws IOException {
			double maxY = 0;
			int index = 0;
			//如果已经位于电梯或者扶梯的则不考虑
			if(louvreData.NodesOfLouvre[fishPopulation[fish_i].NodeID].NodeType == TYPE.NORMAL)
			{
				fishPopulation[fish_i].updateVisual();
				if(!fishPopulation[fish_i].visaulNode.isEmpty())
				{
					for(int i = 0; i < fishPopulation[fish_i].visaulNode.size(); i++)
					{
						if(maxY < louvreData.NodesOfLouvre[fishPopulation[fish_i].visaulNode.get(i)].YOfFood)
						{
							maxY = louvreData.NodesOfLouvre[fishPopulation[fish_i].visaulNode.get(i)].YOfFood;
							index = fishPopulation[fish_i].visaulNode.get(i);
						}
					}
					nextFish[2].totalTime = louvreData.matrixOfLouvre[fishPopulation[fish_i].NodeID][index] / fishPopulation[fish_i].step;
					nextFish[2].NodeID = index;
					nextFish[2].updateFitness();
					nextFish[2].updateVisual();
				}
			}
				
			
		}
		private void swarm(int fish_i) throws IOException {//swam  start
			//如果已经位于电梯或者扶梯的则不考虑
			int maxNum = -9999;
			int index = 0;
			if(louvreData.NodesOfLouvre[fishPopulation[fish_i].NodeID].NodeType == TYPE.NORMAL)
			{
				fishPopulation[fish_i].updateVisual();
				if(!fishPopulation[fish_i].visaulNode.isEmpty())
				{
					for(int i = 0; i < fishPopulation[fish_i].visaulNode.size(); i++)
					{
						if(maxNum < louvreData.NodesOfLouvre[fishPopulation[fish_i].visaulNode.get(i)].NumberOfPeople)
						{
							maxNum = louvreData.NodesOfLouvre[fishPopulation[fish_i].visaulNode.get(i)].NumberOfPeople;
							index = fishPopulation[fish_i].visaulNode.get(i);
						}
					}
					
					nextFish[1].totalTime = louvreData.matrixOfLouvre[fishPopulation[fish_i].NodeID][index] / fishPopulation[fish_i].step;
					nextFish[1].NodeID = index;
					nextFish[1].updateFitness();
					nextFish[1].updateVisual();
				}
			}

		} //swam  end
			
			
		//prey start
		private void prey(int fish_i) throws IOException  {
			Random rand =new Random();
			//如果已经位于电梯或者扶梯的则不考虑
			if(louvreData.NodesOfLouvre[fishPopulation[fish_i].NodeID].NodeType == TYPE.NORMAL)
			{
				fishPopulation[fish_i].updateVisual();
				if(!fishPopulation[fish_i].visaulNode.isEmpty())
				{
					boolean flag = false;
					for(int i = 0; i < tryTime; i++)
					{
						int randNum = rand.nextInt(fishPopulation[fish_i].visaulNode.size());
						int nextNum = fishPopulation[fish_i].visaulNode.get(randNum);
						
						if(louvreData.NodesOfLouvre[nextNum].YOfFood > fishPopulation[fish_i].fit)
						{
							nextFish[0].totalTime = louvreData.matrixOfLouvre[fishPopulation[fish_i].NodeID][nextNum] / fishPopulation[fish_i].step;
							nextFish[0].NodeID = nextNum;
							nextFish[0].updateFitness();
							nextFish[0].updateVisual();
							flag = true;
							break;
						}
					}
					if(!flag)
					{
						int randNum = rand.nextInt(fishPopulation[fish_i].visaulNode.size());	
						int nextNum = fishPopulation[fish_i].visaulNode.get(randNum);
						nextFish[0].totalTime = louvreData.matrixOfLouvre[fishPopulation[fish_i].NodeID][nextNum] / fishPopulation[fish_i].step;
						nextFish[0].NodeID = nextNum;
						nextFish[0].updateFitness();
						nextFish[0].updateVisual();
					}
				}
			}
		}
}
