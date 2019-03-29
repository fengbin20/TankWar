package com.modle.wall;

import com.util.ImageUtil;

public class BrickWall extends Wall {

	/**墙块的构造方法
	 * 
	 * @param x 初始化横坐标
	 * @param y 初始化纵坐标
	 */
	public BrickWall(int x, int y) {
		super(x, y, ImageUtil.BRICKWALL_IMAGE_URL);
		// TODO Auto-generated constructor stub
	}

}
