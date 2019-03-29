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
	 * 构造方法
	 */
	public MainPanel() {
		// TODO Auto-generated constructor stub
		setTitle("坦克大战");//标题名字
		setSize(800,600);//窗口宽高
		setResizable(false);//不可调整大小
		Toolkit tool=Toolkit.getDefaultToolkit();//创建系统默认组件工具包
		Dimension d=tool.getScreenSize();//获取屏幕尺寸，赋给一个二维坐标对象
		//让主窗体在屏幕中间显示
		setLocation((d.width-getWidth())/2,(d.height-getHeight())/2);//移动当前组件到一个新的位置
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//关闭窗体无操作
		addListener();//添加监听事件
		
		setPanel(new LoginPanel(this));//添加登陆面板
	}

	/**
	 * 监听事件
	 */
	private void addListener() {
		// TODO Auto-generated method stub
		addWindowFocusListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e) {
				int closeCode = JOptionPane.showConfirmDialog(MainPanel.this, "是否退出","提示",//弹出的窗体主体，提示内容，以及可点击按钮
						JOptionPane.YES_NO_CANCEL_OPTION);//弹出选择对话框，并记录用户选择
				if(closeCode==JOptionPane.YES_OPTION){//如果用户选择确定
					System.exit(0);//关闭程序
				}
			}
		});
		
	}
	/**更换主容器中的面板
	 * @param panel
	 */
	public void setPanel(JPanel panel){
		Container c= getContentPane();//获取主容器对象
		c.removeAll();//移出容器中所有组件
		c.add(panel);//容器添加面板
		c.validate();//容器创新验证所有组件
	}
	
}
