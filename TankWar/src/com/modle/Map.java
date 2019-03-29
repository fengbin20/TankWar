package com.modle;

import java.awt.List;
import java.util.*;
import java.util.ArrayList;

import com.modle.wall.BrickWall;
import com.util.MapIo;

public class Map {
	private static List<Wall> walls= new ArrayList<>();//地图中所有墙块的集合
	
	private Map() {
		// TODO Auto-generated constructor stub
	}
/**
 * 获取地图对象
 * 返回 指定关卡地图对象
 * @return
 */
private static Map getMap() {
	// TODO Auto-generated method stub
	walls.clear();//墙块集合清空
	walls.addAll(MapIo.readMap(level));//读取指定关卡的模块集合
	//基地砖墙
	for(int a=347;a<=407;a+=20){//循环基地砖块的横坐标
		for(int b=512;b<=572;b+=20){//循环机子砖墙的纵坐标
			if(a>=367&&a<=387&&b>532){//如果墙块与基地发生重合
				continue;//执行下一次循环
				
			}else{
				walls.add(new BrickWall(a,b));//墙块集合中添加墙块
			}
		}
		
	}
		return new Map();//返回心的地图对象
}

/**获取地图对象
 * @param level
 * 关卡数
 * @return
 */
public static Map getMap(int level) {
	return getMap(String.valueOf(level));
}

/**获取地图对象中所有墙块
 * @return 墙块集合
 */
public List<Wall> getWalls(){
	return walls;
}
}
