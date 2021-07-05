package stdDraw;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
;

public class servers2 {
	
	//A = Sarray size =A*A , B=Attack per day
	int A ;
	int B ;
	static int C ;
	String[][] Sarray;
	String[][] Check;
	int[][] Check1 ;	
	static int Day = 0 ;
	int GreenS ;
	int RedS ;
	double ArraySize;
	int[] dx = {1, -1, 0, 0};
	int[] dy = {0, 0, 1, -1};
	int nx;
	int ny;
	int Sum;
	
	int NowClusterSum;
	ArrayList<Integer> Cluster = new <Integer>ArrayList();
	ArrayList<Integer> CCluster = new <Integer>ArrayList();


	//constructor
	servers2(int A,int B,int C){
		this.A=A;
		this.B=B;
		this.C=C;
		this.ArraySize=A*A;
		Sarray=new String[A][A];
		Check=new String[A][A];
		Check1=new int[A][A];
		Day=0;
	}
	//get first array
	public void GetSarray() {
		for(int i = 0 ; i<Sarray.length ;i++) {
			for(int j = 0 ; j<Sarray.length ; j++) {
				Sarray[i][j]="O";
			}
		}
	}
	//Sarray 복사
	public void GetCheck(){
		for(int i = 0 ; i<Sarray.length ;i++) {
			for(int j = 0 ; j<Sarray.length ; j++) {
				Check[i][j]=Sarray[i][j];
			}
		}
	}
	public void GetCheck1(){
		for(int i = 0 ; i<Sarray.length ;i++) {
			for(int j = 0 ; j<Sarray.length ; j++) {
				Check1[i][j]=0;
			}
		}
	}
	
	
	//Server Attack!!
	public void Attack() {
		for (int i = 0 ; i < B ; i++) {
			int atpointX = (int) (Math.random()*A);
			int atpointY = (int) (Math.random()*A);
			//if location is O , 
			if(Sarray[atpointX][atpointY]=="O") {
				Sarray[atpointX][atpointY]="8";	
			//Attack Other	

			} else i--;
		}	
	}
	//Green : existance server , Red = dead server
	public void SumServer() {
		for(int i = 0 ; i<Sarray.length ;i++) {
			for(int j = 0 ; j<Sarray.length ; j++) {
				if(Sarray[i][j]=="O") {
					GreenS+=1;
				} else RedS+=1;
			}
		}
	}
	//Check by Previous dead server
	public void AttackCheck() {
		for(int i = 0 ; i<Sarray.length ;i++) {
			for(int j = 0 ; j<Sarray.length ; j++) {
				if(Sarray[i][j]=="@"||Sarray[i][j]=="8") {
					Sarray[i][j]="X";
				}
			}
		}
	}
	
	//BFS

	public boolean isvisited(int x , int y) {
		if (Check[x][y]!="O") {
			return false;
		}
		Queue<Integer> BF = new LinkedList<>();
		BF.offer(x);
		BF.offer(y);
		while(!BF.isEmpty()) {
			int inputX = BF.poll();
			int inputY = BF.poll();
			
			//전에 방문한 노드면 패스!!
			if (Check[inputX][inputY] == "1") {
			continue;
			}
			// fill
			Check[inputX][inputY] = "1";
			NowClusterSum=NowClusterSum+1;

			for (int k = 0; k < 4; k++) {					
				int nx = inputX + dx[k];
                int ny = inputY + dy[k];
                // if it is out of bounce and it was filled
                if (nx >= 0 && nx < A && ny >= 0 && ny < A && Check[nx][ny] == "O" ) {
                	
                	BF.offer(nx);
			        BF.offer(ny);
	            }   
	        }
		}
		
		
		return true;	
			
}

	//
public void BFS(){
	//초기화
	int n = 0;
	Cluster.clear();
	
		for (int i=0 ; i < Check.length ; i++) {
			for (int j=0 ; j < Check.length ; j++) {
				NowClusterSum=0;
				if(isvisited(i,j)==true) {
					n++;
					
					Cluster.add(NowClusterSum);
					//클러스터 초기화 하고 복사해서 정렬
					CCluster.clear();
					CCluster.addAll(Cluster);
					Collections.sort(CCluster);
					//탐색 완료된곳이면 체크1에 n번쨰 집을 입력
					for(int a=0 ; a <Check.length;a++) {
						for(int b=0 ; b <Check.length;b++) {
							//탐색 완료 된 곳에 F 체크
							if(Check[a][b]=="1") {
								Check[a][b]="F";
							}
							if(Check[a][b]=="F") {
								
								Check1[a][b]=n;
							
								
							}
							if(Check[a][b]=="F") {
								Check[a][b]="V";
							}
						}
					}
					
					
				}
			}
		}
		//클러스터 검사
				if(Cluster.size()!=1) {
					
					while(true) {
						//랜덤으로 돌려야 함. for문 쓰면 같은값 나왔을때 무조건 앞에 들어간 것부터 없앰
						int a=(int)(Math.random()*Cluster.size());
						//선택한 클러스터가 가장 클경우(배열의 오름차순에서 맨 마지막 값을 가져오면 가장 큰 값이나 다름없음)
						if(Cluster.get(a)==CCluster.get(CCluster.size()-1)) {
							for(int c=0 ; c <Check.length;c++) {
								for(int d=0 ; d <Check.length;d++) {
									//가장 큰 클러스터소속이 아닐경우와 공격당한 곳이 아닐경우"@"표시
									if((Check1[c][d]!=a+1)&&(Check1[c][d]!=0)) {
										Sarray[c][d]="@";
										
									}
								}
							}
							//브레이크 안하면 동등한 값이 또나오면 그것도 반복문 돌려버림
							break ;
						}
						
					}
				}
		}
	//delay time
	 public static void delay() {
	  try { Thread.sleep(C*1000); } catch (Exception e) { System.out.println(" SOme problem"+e); }
	 }
	 
	//play!!
	 public void output() {
		 
		//Protect OOB
		while(Day<(ArraySize)-1){
			Day++;
			System.out.println("--------------------------------------------------------------");
			AttackCheck();
			Attack();	
			GetCheck();
			GetCheck1();
			BFS();
			SumServer();
			double min = 0 ; 
			double max = Sarray.length;
			StdDraw.clear();
			StdDraw.setScale(min-(max/5),max+(max/5));
			for(int i = 0 ; i<Sarray.length ; i++) {
				for(int j = 0 ; j<Sarray.length ; j++) {
					System.out.print(Sarray[i][j]);
					//해당 노드가 까만색일 경우
					if(Sarray[i][j].equals("O")) {
						StdDraw.setPenColor(StdDraw.BLACK);
						StdDraw.filledSquare(j, max-i, 0.5);
					
					}else if(Sarray[i][j].equals("X")) {
						StdDraw.setPenColor(StdDraw.RED);
						StdDraw.filledSquare(j, max-i, 0.5);
					
					}else if(Sarray[i][j].equals("@")) {
						StdDraw.setPenColor(StdDraw.ORANGE);
						StdDraw.filledSquare(j, max-i, 0.5);
					
					}else if(Sarray[i][j].equals("8")) {
						StdDraw.setPenColor(StdDraw.YELLOW);
						StdDraw.filledSquare(j, max-i, 0.5);
					}
				}
				System.out.println();
			}
			System.out.println();
			
			System.out.println("서버 공격 "+Day+"일차");
			System.out.println("현재 생존 서버 : "+ GreenS +", 총 죽은서버 : "+RedS+", 서버무력화율 : "+(int)((RedS/(ArraySize))*100)+"%");
			System.out.println();
			if((RedS/ArraySize)>=0.6) {
				break;
			}
			GreenS =0;
			RedS =0;
			delay();
			
		}		
	}
	public static void main(String[] args) {
		//input
		int length = 0;
		int Apd = 0;
		int inputt =0;
		int Play =0 ;
		double DayAver = 0 ;
		double DaySum = 0;
		int Second;
		double ASd = 0;
		Scanner scan = new Scanner(System.in);
		while(true) {
			
			try {
				System.out.println("시뮬레이션 케이스를 입력하세요");
				System.out.println("1번 : 5(행렬의 길이) , 1(하루당 공격할 서버 개수)");
				System.out.println("2번 : 10(행렬의 길이) , 5(하루당 공격할 서버 개수)");
				System.out.println("3번 : 100(행렬의 길이) , 100(하루당 공격할 서버 개수)");
				System.out.println("4번 : 200(행렬의 길이) , 400(하루당 공격할 서버 개수)");
				inputt = scan.nextInt();
			} catch (Exception e) {
				System.out.println("잘 못 입력하셨습니다.");
				continue;
			}
			try {
				System.out.println("몇 번 실행할까요");
				Play = scan.nextInt();
			} catch (Exception e) {
				System.out.println("잘 못 입력하셨습니다.");
				continue;
			}
			try {
				System.out.println("간격 몇 초로 할까요");
				Second = scan.nextInt();
			} catch (Exception e) {
				System.out.println("잘 못 입력하셨습니다.");
				continue;
			}
			break;
		}
		switch (inputt) {
		case 1 : 
			length = 5;
			Apd = 1;
			break;
		case 2 : 
			length = 10;
			Apd = 5;
			break;
		case 3 : 
			length = 100;
			Apd = 100;
			break;
		case 4 : 
			length = 200;
			Apd = 400;
			break;
		}
		
		
		for(int i=0 ; i<Play;i++) {
			
			ASd=ASd+1;
			servers2 S = new servers2(length,Apd,Second);
			S.GetSarray();
			S.output();
			DaySum=DaySum+Day;
			DayAver=DaySum/ASd;
			System.out.println("--------------------------------------------------------------");
			System.out.println("평균 무력화 일수 "+DayAver+"실행횟수"+Play);
			
		}



			
	}
}