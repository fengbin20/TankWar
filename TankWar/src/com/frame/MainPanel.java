package com.frame;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MainPanel extends JFrame {
	/**
	 * ���췽��
	 */
	public MainPanel() {
		// TODO Auto-generated constructor stub
		setTitle("̹�˴�ս");//��������
		setSize(800,600);//���ڿ��
		setResizable(false);//���ɵ�����С
		Toolkit tool=Toolkit.getDefaultToolkit();//����ϵͳĬ��������߰�
		Dimension d=tool.getScreenSize();//��ȡ��Ļ�ߴ磬����һ����ά�������
		//������������Ļ�м���ʾ
		setLocation((d.width-getWidth())/2,(d.height-getHeight())/2);//�ƶ���ǰ�����һ���µ�λ��
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//�رմ����޲���
		addListener();//��Ӽ����¼�
		
		setPanel(new LoginPanel(this));//��ӵ�½���
	}

	/**
	 * �����¼�
	 */
	private void addListener() {
		// TODO Auto-generated method stub
		addWindowFocusListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				int closeCode = JOptionPane.showConfirmDialog(MainPanel.this, "�Ƿ��˳�","��ʾ",//�����Ĵ������壬��ʾ���ݣ��Լ��ɵ����ť
						JOptionPane.YES_NO_CANCEL_OPTION);//����ѡ��Ի��򣬲���¼�û�ѡ��
				if(closeCode==JOptionPane.YES_OPTION){//����û�ѡ��ȷ��
					System.exit(0);//�رճ���
				}
			}
		});
		
	}
	/**�����������е����
	 * @param panel
	 */
	public void setPanel(JPanel panel){
		Container c= getContentPane();//��ȡ����������
		c.removeAll();//�Ƴ��������������
		c.add(panel);//����������
		c.validate();//����������֤�������
	}
	
}
