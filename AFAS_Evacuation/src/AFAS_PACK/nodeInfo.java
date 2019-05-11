package AFAS_PACK;

enum TYPE{NORMAL,EXIT, ELEVADOR, ESCALERA, ELEVADORFORDISABLED};

public class nodeInfo {
	int NodeID;         //节点ID
	TYPE NodeType;      //节点类型
	int NumberOfPeople;  //节点人数
	int CapacityOfPeople; //节点容量
	float sigma1; //节点拥挤度 = NumberOfPeople / CapacityOfPeople
	double YOfFood;  //食物浓度 
	public nodeInfo(){
		NodeID = NumberOfPeople = 0;
		CapacityOfPeople = 100;
		NodeType = TYPE.NORMAL;
		sigma1 = 0;
		YOfFood = 0;
	}
}
