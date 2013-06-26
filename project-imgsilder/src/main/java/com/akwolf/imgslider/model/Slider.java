package com.akwolf.imgslider.model;

/**
 * 领域模型，用来描述一张图片
 * 
 * @author zhangh
 *
 */
public class Slider {
	private int id;

	private String name;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Slider [id=" + id + ", name=" + name + "]";
	}

}
