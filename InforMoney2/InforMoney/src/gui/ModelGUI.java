package gui;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Choice;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
 
import javax.swing.JButton;
import javax.swing.JPasswordField;
 
public class ModelGUI extends Frame implements ActionListener, ItemListener,Runnable{
 
 DTO dto = new DTO();//DTO 객체 생성
 
 TextField model; //모델명
 TextField image; //이미지
 TextArea spec;  //스펙
 Button b1; //전송버튼
 
 
 public ModelGUI(String str) {
  super(str);
 
  init();
  start();
 
  this.setLocation(100, 100); //프레임 시작위치
  super.setVisible(true); //실제로 프레임을 화면에 출력
  super.setSize(270, 200); //프레임의 처음 크기
  super.setResizable(true); //프레임 사이즈 조절
  
 
 }
 
 public void init() {
 
  Panel p = new Panel();
  setLayout(new BorderLayout());
  
  Label model_label = new Label("CATEGORY");
  
  Label image_label = new Label("IMAGE");
  Label spec_label = new Label("SPECIFICATION");
  b1 = new Button("search"); 
  b1.addActionListener(this); //버튼이벤트 
  
  
  model = new TextField(100);
  image = new TextField(300);
  
  spec = new TextArea(5,20);
  
  	
  GridLayout g = new GridLayout(4,2);
  p.setLayout(g);
  p.add(model_label);
  p.add(model);
  p.add(image_label);
  p.add(image);
  p.add(spec_label);
  p.add(spec);
  
  add("Center",p);
  add("South",b1);
  
  
  
 }
 
 public void start() {
  this.addWindowListener(new WindowAdapter() { // x를 눌렀을때 종료
   public void windowClosing(WindowEvent e) {
    System.exit(0);
   }
  });
 
 }
 
 public static void main(String[] args) {
 
  ModelGUI exam = new ModelGUI("기본프레임");
  
 }
 
 public void run() { //스레드 정의부분
  
 }
 
 public void itemStateChanged(ItemEvent e) { // 체크상태 확인
 
 
 }
 public void actionPerformed(ActionEvent e) { //액션이벤트
  
  Object obj = e.getSource();
 
  //아이디 체크버튼 눌렀을 떄
  if ((Button) obj == b1) {
   dto.setModel(model.getText()); //입력된 아이디를 가져와 dto에 저장
   dto.setUrl(image.getText());
   dto.setReview(spec.getText());  //입력된 자기소개를 가져와 dto에 저장
   
   
   
   try {
    insertDAO.create(dto);  //dto를 DAO에 넘겨준다.
   } catch (Exception e1) {
    // TODO Auto-generated catch block
    e1.printStackTrace();
   }
   
  }
 }
 
}
