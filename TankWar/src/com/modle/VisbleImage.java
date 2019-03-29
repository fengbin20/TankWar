package com.modle;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class VisbleImage {

		public int x;//ͼ�������
		public int y;//ͼ��������
		int width;//ͼ���
		int height;//ͼ���
		BufferedImage image;//ͼ�����

		
		//���췽��
		public VisbleImage(int x,int y,int width,int height){
			this.x=x;
			this.y=y;
			this.width=width;
			this.height=height;
			image= new BufferedImage(width, height,BufferedImage.TYPE_INT_BGR );
			
			
		}
		
		//���췽��
		public VisbleImage(int x,int y,String url){
			this.x=x;
			this.y=y;
			try {
				image=ImageIO.read(new File(url));//��ȡ��·����ͼƬ����
				this.width=image.getWidth();
				this.height=image.getHeight();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		//��ȡͼƬ  
		public BufferedImage getImage(){
			return image;
		}
//		����ͼƬ ����ʾ��ͼƬ
		public void setImage(BufferedImage image) {
			this.image=image;
		}
		
//		����ͼƬ
		public void setImage(String url) {
			try {
				this.image= ImageIO.read(new File(url));//��ȡָ��λ�õ�ͼƬ
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
//		hit�ж��Ƿ�������ײ  �ཻtrui ����false  Ŀ��ͼƬ
		public boolean hit(VisbleImage v) {
			return hit(v.getBounds());
		}
		
		/**Ŀ��ı߽� �Ƿ�����ײ
		 * @param Rectangle ���α߽����Ƿ�����ײ
		 * @return
		 */
		public boolean hit(Rectangle r){//���οռ���Rectangle
			if(r==null){
				return false;
			}else{
				return getBounds().intersects(r);//�������ߵı߽��Ƿ������ཻ
			}
		}
		public Rectangle getBounds(){//Rectangle ���εı߽����
			return new Rectangle(x, y, width, height);//����һ������Ϊxy��λ�ÿ��Ϊwh�ľ��α߽���󲢷���
			
		}
		public int getWidth(){
			return width;
		}
		public void setWidth(int width) {
			this.width=width;
		}
		public int getHeight() {
			return height;
			
		}
		public void setHeight(int height) {
			this.height=height;
			
		}
}
