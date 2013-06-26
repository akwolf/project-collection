package com.akwolf.rbac.model;

import java.util.ArrayList;
import java.util.List;

public class PagerModel<E> {
	private int total;

	private List<E> rows = new ArrayList<E>();

	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public List<E> getRows() {
		return rows;
	}

	public void setRows(List<E> rows) {
		this.rows = rows;
	}

}
