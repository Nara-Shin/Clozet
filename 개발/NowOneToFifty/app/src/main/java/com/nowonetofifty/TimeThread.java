package com.nowonetofifty;




import android.os.Handler;




public class TimeThread extends Thread{




 Handler handler;

 boolean isRun = true;

 boolean isWait = false;

 //������

 public TimeThread(Handler handler){

  this.handler = handler;

  

 }

 //������ �Ͻ� ���� Ȥ�� ����� �ϴ� �޼ҵ�

 public void pauseNResume(boolean isWait){

  synchronized (this) {

   this.isWait=isWait;

   notify();

  }

 }

 //������ ���� ���� ��Ű�� �޼ҵ�

 public void stopForever(){

  synchronized (this) {

   isRun = false;

   notify();

  }

 }

 

 //������ ��ü(�ѹ� ���� thread�� �ٽ� ��������. �ٽ� ��ü�� ������ؼ� ����ؾ���)

 public void run(){

  while(isRun){

   try{

    //���ֱ� 10/1000 �ʾ� ����.

    Thread.sleep(10);

    

   }catch (Exception e) {

    // TODO: handle exception

   }

   if(isWait){//��ž�϶� isWait�� true�� �ٲ��ش�.

    try{

     synchronized (this) {//�����尡 �����߿� ���� �ٲ�� �⵿�� �� ����� �����Ƿ� ����ȭ ������ ���ִ� �͵��� �ִ�.

      wait(); //notify�� ������ ��ٸ���.

     }

    }catch (Exception e) {

     // TODO: handle exception

    }

   }

   

   //�ڵ鷯�� �޼����� ������

   handler.sendEmptyMessage(0);

  }

 }

 

}