package test;
import java.util.ArrayList;

import algorithms.*;

public class test1 {
	public int a = 10;
	public void test() {
		// TODO Auto-generated method stub
		String x = "xxxx";
		String y = null;
		String z = "zzzz";

		ArrayList<String> array = new ArrayList<String>();
		array.add(x);
		System.out.println(array.get(0));
		x = z;
		System.out.println(array.get(0));
		
	}
	
	public void func(Integer d){
		a = d;
	}

}
