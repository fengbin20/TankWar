package com.util;

import java.awt.List;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Properties;

import com.modle.wall.BrickWall;
import com.modle.wall.Wall;
import com.type.WallType;

public class MapIo {
	public static final String DATA_PATH="map/data/";//ǽ���ļ���ַ
	public static final String IMAGE_PATH="map/data/";//ǽͼƬ�ĵ�ַ
	public static final String DATA_SUFIX=".map";//ǽ�����ݣ�ǽ�İڷ�����
	public static final String IMAGE_SUFIX=".jpg";//ǽ��ͼƬ��׺
	
	
//	��ȡָ�����Ƶ�ͼ������ǽ��ļ���
//	
	public static List<Wall> readMap(String mapName){
		File file =new File(DATA_PATH+mapName+DATA_SUFIX);
		return readMap(file);
	}


private static List<Wall> readMap(File file) {
	
	Properties pro= new Properties();//properties��ȡ�����ļ��õ�
	List<Wall> walls= new ArrayList<>();//����һ����ǽ��ļ���
		
		pro.load(new FileInputStream(file));
		String brickStr =(String) pro.get(WallType.brick.name());
		String grassStr =(String) pro.get(WallType.grass.name());
		String riverStr =(String) pro.get(WallType.iron.name());
		String ironStr =(String) pro.get(WallType.river.name());
		if (brickStr!=null) {//���ǽ�����
			walls.addAll(readWall(brickStr,WallType.brick));
		}
		if (grassStr!=null) {//����ݵش���
			walls.addAll(readWall(grassStr,WallType.brick));
		}
		if (riverStr!=null) {//�����������
			walls.addAll(readWall(riverStr,WallType.brick));
		}
		if (ironStr!=null) {//�����ǽ����
			walls.addAll(readWall(ironStr,WallType.brick));
		}
	return null;
}
/**
 * @param data
 * @param type
 * @return ǽ�鼯��
 */
private static List<Wall> readWall(String data,WallType type) {
	// TODO Auto-generated method stub
	String walls[]=data.split(";");//ʹ�ã��ָ��ַ�
	Wall wall;//����ǽ�����
	List<Wall> w=new LinkedList<>();//����ǽ�鼯��
	switch (type) {//�ж�ǽ������
	case brick://�����שǽ
		for(String wStr:walls){//�����ָ���
			String axes[]=wStr.split(",");//ʹ�ã��ָ��ַ���
			//����ǽ����󣬷ָ�ĵ�һ��ֵΪ�����꣬�ָ�ĵڶ���ֵΪ������
			wall=new BrickWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));
			w.add(wall);//��������Ӵ�ǽ��
		}
		break;
	case river:
		for(String wStr:walls){//�����ָ���
			String axes[]=wStr.split(",");//ʹ�ã��ָ��ַ���
			//����ǽ����󣬷ָ�ĵ�һ��ֵΪ�����꣬�ָ�ĵڶ���ֵΪ������
			wall=new BrickWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));
			w.add(wall);//��������Ӵ�ǽ��
		}
		break;
	case grass:
		for(String wStr:walls){//�����ָ���
			String axes[]=wStr.split(",");//ʹ�ã��ָ��ַ���
			//����ǽ����󣬷ָ�ĵ�һ��ֵΪ�����꣬�ָ�ĵڶ���ֵΪ������
			wall=new BrickWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));
			w.add(wall);//��������Ӵ�ǽ��
		}
		break;
	case iron:
		for(String wStr:walls){//�����ָ���
			String axes[]=wStr.split(",");//ʹ�ã��ָ��ַ���
			//����ǽ����󣬷ָ�ĵ�һ��ֵΪ�����꣬�ָ�ĵڶ���ֵΪ������
			wall=new BrickWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));
			w.add(wall);//��������Ӵ�ǽ��
		}
		break;
	default:
		break;
	}
	return w;//����ǽ�鼯��
}
	
}
