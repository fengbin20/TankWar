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
	public static final String DATA_PATH="map/data/";//墙的文件地址
	public static final String IMAGE_PATH="map/data/";//墙图片的地址
	public static final String DATA_SUFIX=".map";//墙的数据，墙的摆放数据
	public static final String IMAGE_SUFIX=".jpg";//墙的图片后缀
	
	
//	获取指定名称地图的所有墙块的集合
//	
	public static List<Wall> readMap(String mapName){
		File file =new File(DATA_PATH+mapName+DATA_SUFIX);
		return readMap(file);
	}


private static List<Wall> readMap(File file) {
	
	Properties pro= new Properties();//properties读取数据文件用的
	List<Wall> walls= new ArrayList<>();//创建一个总墙块的集合
		
		pro.load(new FileInputStream(file));
		String brickStr =(String) pro.get(WallType.brick.name());
		String grassStr =(String) pro.get(WallType.grass.name());
		String riverStr =(String) pro.get(WallType.iron.name());
		String ironStr =(String) pro.get(WallType.river.name());
		if (brickStr!=null) {//如果墙块存在
			walls.addAll(readWall(brickStr,WallType.brick));
		}
		if (grassStr!=null) {//如果草地存在
			walls.addAll(readWall(grassStr,WallType.brick));
		}
		if (riverStr!=null) {//如果河流存在
			walls.addAll(readWall(riverStr,WallType.brick));
		}
		if (ironStr!=null) {//如果铁墙存在
			walls.addAll(readWall(ironStr,WallType.brick));
		}
	return null;
}
/**
 * @param data
 * @param type
 * @return 墙块集合
 */
private static List<Wall> readWall(String data,WallType type) {
	// TODO Auto-generated method stub
	String walls[]=data.split(";");//使用；分割字符
	Wall wall;//创建墙块对象
	List<Wall> w=new LinkedList<>();//创建墙块集合
	switch (type) {//判断墙块类型
	case brick://如果是砖墙
		for(String wStr:walls){//遍历分割结果
			String axes[]=wStr.split(",");//使用，分割字符串
			//创建墙块对象，分割的第一个值为横坐标，分割的第二个值为纵坐标
			wall=new BrickWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));
			w.add(wall);//集合中添加此墙块
		}
		break;
	case river:
		for(String wStr:walls){//遍历分割结果
			String axes[]=wStr.split(",");//使用，分割字符串
			//创建墙块对象，分割的第一个值为横坐标，分割的第二个值为纵坐标
			wall=new BrickWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));
			w.add(wall);//集合中添加此墙块
		}
		break;
	case grass:
		for(String wStr:walls){//遍历分割结果
			String axes[]=wStr.split(",");//使用，分割字符串
			//创建墙块对象，分割的第一个值为横坐标，分割的第二个值为纵坐标
			wall=new BrickWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));
			w.add(wall);//集合中添加此墙块
		}
		break;
	case iron:
		for(String wStr:walls){//遍历分割结果
			String axes[]=wStr.split(",");//使用，分割字符串
			//创建墙块对象，分割的第一个值为横坐标，分割的第二个值为纵坐标
			wall=new BrickWall(Integer.parseInt(axes[0]), Integer.parseInt(axes[1]));
			w.add(wall);//集合中添加此墙块
		}
		break;
	default:
		break;
	}
	return w;//返回墙块集合
}
	
}
