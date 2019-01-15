package cn.wyc.cacheof;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;
import cn.wyc.helloWorld.User;

/**
 * session 缓存
 * @author xd
 *
 */
public class Demo1 {
	@Test
	public void fun1() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User u1 = (User) session.get(User.class, 2); //发送select语句，从数据库取出对象，持久化对象->存到缓存中
		User u2 = (User) session.get(User.class, 2); //再次查询时。会从缓存中查找，不会发送select语句
		
		//--------------------
		bt.commit();             
		session.close();      
	}
	//session缓存中的快照
	@Test
	public void fun2() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User u1 = (User) session.get(User.class, 2); 
		session.update(u1);
		//--------------------
		bt.commit();             
		session.close();      
	}
	//session缓存中的快照
	@Test
	public void fun3() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User u1 = new User();
		u1.setId(2);
		u1.setName("奎因");
		u1.setPassword("kuiuijh");
		session.update(u1);
		//--------------------
		bt.commit();             
		session.close();      
	}
}
