package AFAS_PACK;
 
import java.io.IOException;
 
public class mainTest {
 
	/**
	 * @param args
	 * @throws IOException
	 * @author sun 
	 */
	public static void main(String[] args) throws IOException {

		AFAS run = new AFAS(1000,3);
  		run.doAFAS(10);//括号内为迭代次数
	}
 
}

