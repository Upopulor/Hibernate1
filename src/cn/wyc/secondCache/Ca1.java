package cn.wyc.secondCache;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;
import cn.wyc.helloWorld.User;
import cn.wyc.relate.Customer;
import cn.wyc.relate.Order;
/**
 * 二级缓存
 * @author xd
 *
 */
public class Ca1 {
	//类缓存
	@Test
	public void cA1() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		@SuppressWarnings("unused")
		Customer c = (Customer) session.get(Customer.class, 2);
		session.clear();//清空1级缓存
		Customer c2 = (Customer) session.get(Customer.class, 2);//到这里不打印，证明从二级缓存取得数据
		System.out.println(c == c2);//false 
		//二级缓存在缓存数据时不是以对象的形式缓存的，缓存的是数据的散列，查的时候在1级缓存中组装成对象，里面不能缓存对象，如果是对象，给别人引用后会被修改
		
		//--------------------
		bt.commit();             
		session.close();      
	}
	//集合缓存
	@Test
	public void cA2() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		@SuppressWarnings("unused")
		Customer c = (Customer) session.get(Customer.class, 5);
		for(Order o : c.getOrders()) {
			System.out.println(o.getName());
		}
		session.clear();//清空1级缓存
		Customer c2 = (Customer) session.get(Customer.class, 5);//在这里不会打印，因为使用了集合缓存
		//注意，如果没有加上集合所属类的<class-cache usage="read-only" class="cn.wyc.relate.Order"/>
		//那么在执行以下时还是会打印查询语句，而且一次查询所有个数的查询语句，这里是2，所以集合缓存区中放的是类的id，然后
		//即时通过id取数据库找，如果有order的类缓存区 ，就不去数据库找了
		for(Order o1 : c2.getOrders()) {  
			System.out.println(o1.getName());
		}
		//--------------------
		bt.commit();             
		session.close();      
	}
	//查询缓存-对HQL语句查询的缓存
	@Test
	public void cA3() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		Query query = session.createQuery("from Customer");
		//使用查询缓存
		//在查询时会先从2级缓存取结果，取不到就执行语句，将结果放入二级查询缓存中
		//使用查询缓存时，缓存的也是id，也应先缓存使用的对象，如这里的Customer，否则也会打印
		query.setCacheable(true);
		List<Customer> list = query.list();
		session.clear();
		//如果这里使用select c from customer c结果还是不打印---缓存的是HQL对应的SQL语句
		Query query2 = session.createQuery("from Customer");//查询这里的时候没有打印语句
		List<Customer> list2 = query.list();
		//--------------------
		bt.commit();             
		session.close();      
	}
}
