package test;

import java.util.StringTokenizer;

	class WordCount {
		
		public static void main(String[] args) {
			String str = "hello korea hello java hello jsp hello oracle";
			// 위의 문자열의 각 단어의 빈도수를 구하여 출력하는 프로그램을 작성합니다.
			String arr[] = new String[100]; //[hello] [korea] [] [] [][]...[]
			int cnt[] = new int[100];   //[2]   [1] [] [] [][]...[]
			int n=0; // 배열에서 index를 진행할 변수
			// 중복되지 않는다면 n번째 데이터를 넣고 n을 증가시킨다
			StringTokenizer st = new StringTokenizer(str, " "); // " " 는 생략해도됨 메뉴얼에 나옴
			
			while (st.hasMoreTokens()){
				String s = st.nextToken();
				// 만약 중복되면 중복되는 배열에 index를 알고 cnt[index]를 1증가한다.
				int i  = 0;
				for (i = 0 ; i<n ; i++ ){
					if (s. equals(arr[i])){ //토큰에서 뽑아온 글자가 배열에 이미 있는지 검사하는것
						break;
					}
				}
				if (i < n){
					cnt[i]++;
				}

				if (n == 0 || i == n){
					arr[n] = s;
					cnt[n] = 1;
					n++;
				}
			}
			for (int i =0 ; i<n ; i++ ){
				System.out.println(arr[i] + "==>" + cnt[i]);
			}
		}
	}
