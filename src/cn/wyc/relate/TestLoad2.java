package cn.wyc.relate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;
/**
 * 集合(一对多):customer（1）
			set 
				lazy: 是否对set数据使用懒加载
					true:(默认值) 对集合使用才加载
					false: 集合将会被立即加载
					extra: 极其懒惰,如果使用集合时,之调用size方法查询数量, Hibernate会发送count语句,只查询数量.不加载集合内数据.
				fetch : 决定加载集合使用的sql语句种类
					select: (默认值) 普通select查询
					join: 表链接语句查询集合数据
					subselect: 使用子查询 一次加载多个Customer的订单数据

       fetch		lazy	结论
	 ------------------------------
		select  	    true	默认值, 会在使用集合时加载,普通select语句
		select		false	立刻使用select语句加载集合数据
		select		extra	会在使用集合时加载,普通select语句,如果只是获得集合的长度,会发送Count语句查询长度.
		join		    true	查询集合时使用表链接查询,会立刻加载集合数据
		join		    false	查询集合时使用表链接查询,会立刻加载集合数据
		join		    extra	查询集合时使用表链接查询,会立刻加载集合数据
		subselect	 true	会在使用集合时加载,子查询语句
		subselect  	false	会在查询用户时,立即使用子查询加载客户的订单数据
		subselect   extra	会在使用集合时加载,子查询语句,如果只是获得集合的长度,会发送Count语句查询长度.
------------------------------------------------------------------------------
（多对一）order:（多）
		lazy
 			false		加载订单时,会立即加载客户
 			proxy		看客户对象的类加载策略来决定
 			no-proxy : 不做研究. 
 		fetch=
			select  : (默认值)使用普通select加载
			join	: 使用表链接加载数据
	
	fetch			lazy			结果
---------------------------------------------------
	select			false			加载订单时,立即加载客户数据.普通select语句加载客户.
	select			proxy			类加载策略为:lazy=false 同上
												 lazy=true	加载订单时,先不加载客户数据.使用客户数据时才加载
	join			false			使用表链接查询订单以及对应客户信息.lazy属性无效
	join			proxy			使用表链接查询订单以及对应客户信息.lazy属性无效
-------------------------------------------------------------------------------------------

 * @author xd
 *
 */
public class TestLoad2 {
	//关联级别懒加载 <class  lazy="true | false">
	// 默认：对于那些与关联的数据，在使用时才会加载
	@Test
	public void fun1() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		Customer c = (Customer) session.get(Customer.class, 5);
		for(Order o : c.getOrders()) {
			System.out.println(o.getName());
		}
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
	//关联级别懒加载 在set设置lazy=false
	//set name="orders" inverse="false" lazy="true">
	@Test
	public void fun2() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		Customer c = (Customer) session.get(Customer.class, 5);
		for(Order o : c.getOrders()) {
			System.out.println(o.getName());
		}
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
}
