package com.modle;

import java.awt.List;
import java.util.*;
import java.util.ArrayList;

import com.modle.wall.BrickWall;
import com.util.MapIo;

public class Map {
	private static List<Wall> walls= new ArrayList<>();//��ͼ������ǽ��ļ���
	
	private Map() {
		// TODO Auto-generated constructor stub
	}
/**
 * ��ȡ��ͼ����
 * ���� ָ���ؿ���ͼ����
 * @return
 */
private static Map getMap() {
	// TODO Auto-generated method stub
	walls.clear();//ǽ�鼯�����
	walls.addAll(MapIo.readMap(level));//��ȡָ���ؿ���ģ�鼯��
	//����שǽ
	for(int a=347;a<=407;a+=20){//ѭ������ש��ĺ�����
		for(int b=512;b<=572;b+=20){//ѭ������שǽ��������
			if(a>=367&&a<=387&&b>532){//���ǽ������ط����غ�
				continue;//ִ����һ��ѭ��
				
			}else{
				walls.add(new BrickWall(a,b));//ǽ�鼯�������ǽ��
			}
		}
		
	}
		return new Map();//�����ĵĵ�ͼ����
}

/**��ȡ��ͼ����
 * @param level
 * �ؿ���
 * @return
 */
public static Map getMap(int level) {
	return getMap(String.valueOf(level));
}

/**��ȡ��ͼ����������ǽ��
 * @return ǽ�鼯��
 */
public List<Wall> getWalls(){
	return walls;
}
}
