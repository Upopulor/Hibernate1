package cn.wyc.relate;

import java.util.HashSet;
import java.util.Set;

public class Customer {
	private Integer cid;
	private String name;
	//��1��һ������ʾ�ж��һ��������---ʹ�ü���
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
