package junit.test;

public class HadoopTest 
{
	public static void main(String[] args) 
	{
		String s = "hdfs://master:9000/cutter_point";
		//����Ҫ��ȡ�����/cutter_point
		System.out.println(s.substring(s.indexOf("9000") + 4));
	}
}
