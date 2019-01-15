package cn.wyc.relate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;


public class oneToMany2 {
	//1级联保存
	//希望在保存customer时，自动将未保存的Order中的order保存
	//cascade:save-update
	@Test
	public void fun1() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//--------------------
		Customer c = new Customer();
		c.setName("tom");
		Order o1 = new Order();
		o1.setName("飞机");
		Order o2 = new Order();
		o2.setName("火箭");
		c.getOrders().add(o1);
		c.getOrders().add(o2);
		o1.setCustomer(c);
		o2.setCustomer(c);
		session.save(c);
		//session.save(o1);
		//session.save(o2);
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}

	//级联修改
	@Test
	public void fun2() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//--------------------
		Customer c = (Customer) session.get(Customer.class, 8);
		for(Order o : c.getOrders()) {
			o.setName("啦啦啦");
		}
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
	//2级联删除
	//cascade="delete"删除customer时，会将customer下的order同时删除
	//inverse = false 6条语句
	//inverse = true 5条语句
	//注意，delete在多的一方或者少的一方都是可以配置的
	@Test
	public void fun3() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//--------------------
		Customer c = (Customer) session.get(Customer.class, 8);
		session.delete(c); //删除customer 删除俩order ，如果设置inverse为false，select后还会有个update比较麻烦
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
	//级联删除双向，两边都配置了delete
	//慎用
	@Test
	public void fun4() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//--------------------
		Order o = (Order) session.get(Order.class, 9); //select
		session.delete(o); //delete当前Order
						   //找到所有相关联的Customer删除 select
						   //Customer配置了级联删除--select找下面的Order
		                   //删除所有的Order
		                   //删除Customer删除了所有数据
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
	//3级联 cascade="delete-orphan" 当没有任何外界引用a时，a会被删除
	@Test
	public void fun5() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//--------------------
		Customer c = (Customer) session.get(Customer.class, 9);
		Iterator<Order> it = c.getOrders().iterator();
		//删除Customer下的订单时不能使用c.setOrder(null)
		while(it.hasNext()) {
			it.next();
			it.remove();
		}
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
}
