package cn.wyc.relate;

import java.util.HashSet;
import java.util.Set;

public class Customer {
	private Integer cid;
	private String name;
	//在1的一方，表示有多的一方的引用---使用集合
	private Set<Order> orders = new HashSet<Order>();
	
	public Integer getCid() {
		return cid;
	}
	public void setCid(Integer cid) {
		this.cid = cid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Set<Order> getOrders() {
		return orders;
	}
	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}
	
}
