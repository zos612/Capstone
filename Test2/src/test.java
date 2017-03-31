import java.util.StringTokenizer;

public class test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		StringTokenizer tok	= new StringTokenizer("1	2	3	4	-5", "\t");
		String key = tok.nextToken();
		String value = "";
		while (tok.hasMoreTokens()) {
			value += tok.nextToken() + "\n";
		}
		System.out.println(value);
	}

}
