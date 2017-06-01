package test;
import algorithms.*;

public class test1 {
	public int a = 10;
	public void test() {
		// TODO Auto-generated method stub
		String x = "xxxx";
		String y = null;
		String z = "zzzz";

		//System.out.println(x);
		/*y = x;
		System.out.println(y);
		y = z;
		System.out.println(y);
		z = "aaaa";
		System.out.println(y);*/
		//y = "yyyy";
		//System.out.println(z);
		System.out.println(a);
		func(11);
		System.out.println(a);
	}
	
	public void func(Integer d){
		a = d;
	}

}
