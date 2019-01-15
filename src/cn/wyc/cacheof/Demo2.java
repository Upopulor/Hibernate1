package cn.wyc.cacheof;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;
import cn.wyc.helloWorld.User;

public class Demo2 {
	//1 保存对象时使用save方法和persist方法的区别
	//persist方法来自于JPA接口，save方法来自于Hibernate接口，功能一样，推敲后名字不一样
	@Test
	public void fun2() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User u1 = new User();
		u1.setName("马尔扎哈");
		session.save(u1);   //insert方法打印----为了获得id
		session.persist(u1);   //insert方法打印----为了获得id
		//--------------------
		bt.commit();             
		session.close();      
	}
	//HQL查询是否使用一级缓存 不
	@Test
	public void fun3() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		List<User> list = session.createQuery("from User").list();
		List<User> list2 = session.createQuery("from User").list();
		//打印两条相同的，所以HQL不会使用1级缓存
		//--------------------
		bt.commit();             
		session.close();      
	}
	//HQL语句批量查询时，查询结果是否会进入缓存？会
	@Test
	public void fun4() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		List<User> list = session.createQuery("from User").list();
		User u  = (User) session.get(User.class,2);
		//只打印一条，HQL查询结果进入了缓存
		//--------------------
		bt.commit();             
		session.close();      
	}
	//SQL语句批量查询1时，查询结果是否会进入缓存？是
	@Test
	public void fun5() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		List<User> list = session.createSQLQuery("select * from user_h").addEntity(User.class).list();
		User u  = (User) session.get(User.class,2);
		//只打印一条，SQL查询结果进入了缓存
		//--------------------
		bt.commit();             
		session.close();      
	}
	//SQL语句批量查询2时，查询结果是否会进入缓存？是
	//如果把对象封装到User对象，那么对象会放入1级缓存的，否则不会
	@Test
	public void fun6() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		List list = session.createSQLQuery("select * from user_h").list();
		User u  = (User) session.get(User.class,2);
		//打印两条，SQL查询结果没有进入了缓存
		//--------------------
		bt.commit();             
		session.close();      
	}
}
