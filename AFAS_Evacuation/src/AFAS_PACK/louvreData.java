package AFAS_PACK;

/*
1. 输入数据的处理 done!
2. 食物浓度处理     done!
3. 各点人数密度、通道密度（人工鱼生成的时候决定） done!
4. Gen迭代，每条人工鱼依次执行prey、follow、等。选择进入下一个节点 done!
5. 计算时间，如果下一个节点或边人数过多，那么等待，否则边长度/速度 done!
6. 速度更新，速度设定为Gen迭代次数的函数，逐渐变慢。
7. 保存经过的序列
8. 输出结果

*/


public class louvreData {
	static final int numberOfNodes = 32;
	//static final double[][] graphOfLouvre;
	/** 邻接矩阵 */
	static double[][] matrixOfLouvre;
	static double[][] shortPath;
	static nodeInfo[] NodesOfLouvre;
    /** 表示正无穷 */
	static final double MAX_WEIGHT = (double)(Integer.MAX_VALUE / 2);
	
	static final double distanceOfNodes = 10.0f;
	static final int numberOfExits = 10;
	static final int sigmaOfDisabled = 10001; //残疾人概率千分之一

	static 
	{
		matrixOfLouvre = new double[numberOfNodes+1][numberOfNodes+1];
		shortPath = new double[numberOfNodes+1][numberOfNodes+1];
		NodesOfLouvre = new nodeInfo[numberOfNodes+1];
		
		for(int i = 1; i<= numberOfNodes; i++)
		{
			NodesOfLouvre[i] = new nodeInfo();
			for(int j = 1; j<= numberOfNodes; j++)
				matrixOfLouvre[i][j] = MAX_WEIGHT;
			NodesOfLouvre[i].NodeID = i;
			NodesOfLouvre[i].NodeType = TYPE.NORMAL;
		}
			
		
		matrixOfLouvre[1][2] = matrixOfLouvre[2][1] = (Math.random()+0.5)*10;
		matrixOfLouvre[2][3] = matrixOfLouvre[3][2] = (Math.random()+0.5)*10;
		matrixOfLouvre[3][4] = matrixOfLouvre[4][3] = (Math.random()+0.5)*10;
		matrixOfLouvre[4][5] = matrixOfLouvre[5][4] = (Math.random()+0.5)*10;
		matrixOfLouvre[5][6] = matrixOfLouvre[6][5] = (Math.random()+0.5)*10;
		matrixOfLouvre[6][7] = matrixOfLouvre[7][6] = (Math.random()+0.5)*10;
		matrixOfLouvre[6][8] = matrixOfLouvre[8][6] = (Math.random()+0.5)*10;
		matrixOfLouvre[7][8] = matrixOfLouvre[8][7] = (Math.random()+0.5)*10;
		matrixOfLouvre[7][12] = matrixOfLouvre[12][7] = (Math.random()+0.5)*10;
		matrixOfLouvre[8][9] = matrixOfLouvre[9][8] = (Math.random()+0.5)*10;
		matrixOfLouvre[9][10] = matrixOfLouvre[10][9] = (Math.random()+0.5)*10;
		matrixOfLouvre[9][4] = matrixOfLouvre[4][9] = (Math.random()+0.5)*10;
		matrixOfLouvre[4][10] = matrixOfLouvre[10][4] = (Math.random()+0.5)*10;
		matrixOfLouvre[10][11] = matrixOfLouvre[11][10] = (Math.random()+0.5)*10;
		matrixOfLouvre[11][12] = matrixOfLouvre[12][11] = (Math.random()+0.5)*10;
		matrixOfLouvre[12][13] = matrixOfLouvre[13][12] = (Math.random()+0.5)*10;
		matrixOfLouvre[12][7] = matrixOfLouvre[7][12] = (Math.random()+0.5)*10;
		matrixOfLouvre[4][14] = matrixOfLouvre[14][4] = (Math.random()+0.5)*10;
		matrixOfLouvre[14][15] = matrixOfLouvre[15][14] = (Math.random()+0.5)*10;
		matrixOfLouvre[15][16] = matrixOfLouvre[16][15] = (Math.random()+0.5)*10;
		matrixOfLouvre[16][17] = matrixOfLouvre[17][16] = (Math.random()+0.5)*10;
		matrixOfLouvre[17][18] = matrixOfLouvre[18][17] = (Math.random()+0.5)*10;
		matrixOfLouvre[18][19] = matrixOfLouvre[19][18] = (Math.random()+0.5)*10;
		matrixOfLouvre[19][20] = matrixOfLouvre[20][19] = (Math.random()+0.5)*10;
		matrixOfLouvre[20][21] = matrixOfLouvre[21][20] = (Math.random()+0.5)*10;
		matrixOfLouvre[21][22] = matrixOfLouvre[22][21] = (Math.random()+0.5)*10;
		matrixOfLouvre[22][17] = matrixOfLouvre[17][22] = (Math.random()+0.5)*10;
		matrixOfLouvre[17][20] = matrixOfLouvre[20][17] = (Math.random()+0.5)*10;
		matrixOfLouvre[15][23] = matrixOfLouvre[23][15] = (Math.random()+0.5)*10;
		matrixOfLouvre[23][25] = matrixOfLouvre[25][23] = (Math.random()+0.5)*10;
		matrixOfLouvre[23][24] = matrixOfLouvre[24][23] = (Math.random()+0.5)*10;
		matrixOfLouvre[23][28] = matrixOfLouvre[28][23] = (Math.random()+0.5)*10;
		matrixOfLouvre[28][27] = matrixOfLouvre[27][28] = (Math.random()+0.5)*10;
		matrixOfLouvre[28][26] = matrixOfLouvre[26][28] = (Math.random()+0.5)*10;
		matrixOfLouvre[28][31] = matrixOfLouvre[31][28] = (Math.random()+0.5)*10;
		matrixOfLouvre[26][29] = matrixOfLouvre[29][26] = (Math.random()+0.5)*10;
		matrixOfLouvre[29][30] = matrixOfLouvre[30][29] = (Math.random()+0.5)*10;
		matrixOfLouvre[30][32] = matrixOfLouvre[32][30] = (Math.random()+0.5)*10;
		matrixOfLouvre[30][31] = matrixOfLouvre[31][30] = (Math.random()+0.5)*10;
		
		NodesOfLouvre[4].NodeType = TYPE.ESCALERA;
		NodesOfLouvre[14].NodeType = TYPE.ESCALERA;
		NodesOfLouvre[15].NodeType = TYPE.ESCALERA;
		NodesOfLouvre[16].NodeType = TYPE.ESCALERA;
		NodesOfLouvre[18].NodeType = TYPE.ESCALERA;
		
		NodesOfLouvre[6].NodeType = TYPE.ELEVADOR;
		NodesOfLouvre[7].NodeType = TYPE.ELEVADOR;
		NodesOfLouvre[8].NodeType = TYPE.ELEVADOR;
		NodesOfLouvre[23].NodeType = TYPE.ELEVADOR;
		NodesOfLouvre[26].NodeType = TYPE.ELEVADOR;
		
		int exitNodes[] = {4, 6, 7, 8, 14, 15, 16, 18, 23, 26};

		for(int i = 1; i<= numberOfNodes; i++)
			for(int j = 1; j<= numberOfNodes; j++)
				shortPath[i][j] = matrixOfLouvre[i][j];
		
	    
	    //Floyd最短路算法,求任意两点之间的最短路
		for(int k=1;k<=numberOfNodes;k++)
			for(int i=1;i<=numberOfNodes;i++)
				for(int j=1;j<=numberOfNodes;j++)
		        	shortPath[i][j]=Math.min(shortPath[i][j],shortPath[i][k]+shortPath[k][j]);
		
		for(int i = 1; i <= numberOfNodes; i++)
		{
			double Y = (double)Integer.MAX_VALUE;
			for(int j = 0; j < numberOfExits; j++)
			{
				if(Y > shortPath[NodesOfLouvre[i].NodeID][exitNodes[j]])
					Y = shortPath[NodesOfLouvre[i].NodeID][exitNodes[j]];
			}
			NodesOfLouvre[i].YOfFood = 200.0f - Y;
		}
	
	
	}
	

}
