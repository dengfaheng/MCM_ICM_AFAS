package AFAS_PACK;

enum TYPE{NORMAL,EXIT, ELEVADOR, ESCALERA, ELEVADORFORDISABLED};

public class nodeInfo {
	int NodeID;         //�ڵ�ID
	TYPE NodeType;      //�ڵ�����
	int NumberOfPeople;  //�ڵ�����
	int CapacityOfPeople; //�ڵ�����
	float sigma1; //�ڵ�ӵ���� = NumberOfPeople / CapacityOfPeople
	double YOfFood;  //ʳ��Ũ�� 
	public nodeInfo(){
		NodeID = NumberOfPeople = 0;
		CapacityOfPeople = 100;
		NodeType = TYPE.NORMAL;
		sigma1 = 0;
		YOfFood = 0;
	}
}
