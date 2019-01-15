package cn.wyc.relate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
/**
 * 测试加载策略
 */
import cn.wyc.Utils.HibernateUtils;

public class TestLoad1 {
	//类级别加载
	//class lazy属性 
	//   默认true 在load获得时会返回代理对象不查询数据库，使用时才查询
	//      false 在load方法执行时就发送SQL查询语句，与get方法一致
	@Test
	public void fun1() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		Customer c = (Customer) session.load(Customer.class, 2);
		System.out.println(c.getName());
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
}
