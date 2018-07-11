package cn.sxt.game;


import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Date;
 
public class ThunderCross extends Frame {
	Date startTime = new Date();    //��Ϸ��ʼʱ��
	Date endTime;  //��Ϸ����ʱ��
	
    
	
	
	class KeyMonitor extends KeyAdapter{
		
		@Override
		public void keyPressed(KeyEvent e) {
			p.addDierection( e);
					}

		@Override
		public void keyReleased(KeyEvent e) {
			p.minusDierection( e);
		}
		
		
	}
	
	
	 class PaintThread extends Thread {
	        public void run(){
	            while(true){
	                repaint();
	                try {
	                    Thread.sleep(40); //1s = 1000ms
	                } catch (InterruptedException e) {
	                    e.printStackTrace();
	                }   
	            }
	        }
	    }  
	//������ͼƬ��ɻ�ͼƬ����Ϊ��Ա����
    java.awt.Image bgImg = GameUtil.getImage("images/bg.jpg");
    java.awt.Image planeImg = GameUtil.getImage("images/plant.png");
	
    Plane p=new Plane(planeImg, 250, 250);
   
    Shell[] shells=new Shell[50];
    Explode bao;
    
    
    
    //paint���������ǣ������������ڼ��ڲ����ݡ���ϵͳ�Զ����á�
    @Override
    public void paint(Graphics g) {  
        g.drawImage(bgImg, 0, 0, null);
       p.drawself(g);
       for(int i=0;i<shells.length;i++) {
    	   shells[i].draw(g);
    	   boolean peng = shells[i].getRect().intersects(p.getRect());
    	   if(peng) {
    		   p.live=false;
    		   if (bao==null) {
    			   bao=new Explode(p.x, p.y);
    		   }
    		   bao.draw(g);
				
    	   }
    	   
       }
       if(!p.live){
           if(endTime==null){
               endTime = new Date();
           }
           int period = (int)((endTime.getTime()-startTime.getTime())/1000);
           printInfo(g, "ʱ�䣺"+period+"��", 50, 120, 260, Color.white);
       }
   }
    public void printInfo(Graphics g,String str,int size,int x,int y,Color color){
        Color c = g.getColor();
        g.setColor(color);
        Font f = new Font("����",Font.BOLD,size);
        g.setFont(f);
        g.drawString(str,x,y);
        g.setColor(c);
    }  
       
    
    public void launchFrame(){
        //����Ϸ���ڴ�ӡ����
        setTitle("��ѧ��ѧԱ_����Գ��Ʒ");
        //����Ĭ�ϲ��ɼ�����Ϊ�ɼ�
        setVisible(true);
        //���ڴ�С�����500���߶�500
        setSize(Constant.GAME_WIDTH, Constant.GAME_WIDTH);
        //�������ϽǶ��������λ��
        setLocation(300, 300);
         
        //���ӹرմ��ڼ����������û�������Ͻǹر�ͼ�꣬���Թر���Ϸ����
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        new PaintThread().start();
        addKeyListener(new KeyMonitor());
        
        
        for(int i=0;i<shells.length;i++) {
        	shells[i]=new Shell();
        }
    }
     
    public static void main(String[] args) {
    	ThunderCross f = new ThunderCross();
        f.launchFrame();
        
    }
    private Image offScreenImage = null;
    
    public void update(Graphics g) {
        if(offScreenImage == null)
            offScreenImage = this.createImage(500,500);//������Ϸ���ڵĿ�Ⱥ͸߶�
         
        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }  
}