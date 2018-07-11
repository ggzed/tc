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
	Date startTime = new Date();    //游戏起始时刻
	Date endTime;  //游戏结束时刻
	
    
	
	
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
	//将背景图片与飞机图片定义为成员变量
    java.awt.Image bgImg = GameUtil.getImage("images/bg.jpg");
    java.awt.Image planeImg = GameUtil.getImage("images/plant.png");
	
    Plane p=new Plane(planeImg, 250, 250);
   
    Shell[] shells=new Shell[50];
    Explode bao;
    
    
    
    //paint方法作用是：画出整个窗口及内部内容。被系统自动调用。
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
           printInfo(g, "时间："+period+"秒", 50, 120, 260, Color.white);
       }
   }
    public void printInfo(Graphics g,String str,int size,int x,int y,Color color){
        Color c = g.getColor();
        g.setColor(color);
        Font f = new Font("宋体",Font.BOLD,size);
        g.setFont(f);
        g.drawString(str,x,y);
        g.setColor(c);
    }  
       
    
    public void launchFrame(){
        //在游戏窗口打印标题
        setTitle("尚学堂学员_程序猿作品");
        //窗口默认不可见，设为可见
        setVisible(true);
        //窗口大小：宽度500，高度500
        setSize(Constant.GAME_WIDTH, Constant.GAME_WIDTH);
        //窗口左上角顶点的坐标位置
        setLocation(300, 300);
         
        //增加关闭窗口监听，这样用户点击右上角关闭图标，可以关闭游戏程序
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
            offScreenImage = this.createImage(500,500);//这是游戏窗口的宽度和高度
         
        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }  
}