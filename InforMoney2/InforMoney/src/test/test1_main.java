package test;
import java.util.ArrayList;

import test.test1;

public class test1_main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		/*test1 test1 = new test1();
		test1.test();*/
		String x = "xxxx";
		String y = null;
		String z = "zzzz";

		ArrayList<String> array = new ArrayList<String>();
		array.add(x);
		System.out.println(array.get(0));
		x = z;
		System.out.println(array.get(0));
	}



}
