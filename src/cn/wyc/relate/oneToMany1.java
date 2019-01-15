package cn.wyc.relate;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;


public class oneToMany1 {
	//一对多关系的保存
	//打印5条语句 前3条insert--保存对象，维护外键 后俩条--维护外键
	//维护了两次，解决：单纯的指定关系由其中一方维护，另一份不维护关系，
	//这里外键维护的放弃只能有非外键所在对象放弃,设置customer的inverse为true，外键由order维护，只打印3条insert
	//此时也就不需要c.getOrders().add(o1); //维护关系
	@Test
	public void fun1() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		Customer c = new Customer();
		c.setName("tom");
		Order o1 = new Order();
		o1.setName("飞机");
		Order o2 = new Order();
		o2.setName("火箭");
		//c.getOrders().add(o1); //维护关系
		//c.getOrders().add(o2); //维护关系
		o1.setCustomer(c);     //维护关系
		o2.setCustomer(c);     //维护关系
		session.save(c);
		session.save(o1);
		session.save(o2);
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
	//多表关系中的删除
	//删除用户时会先移出customer中引用的外键，然后再删除customer
	//结论：维护一方的对象时，会自动维护另一方的关系
	//如果将customer的Iverse设置为true会报错，因为customer不维护外键，那么就不能直接删除
	//如果仍然想删，见下，先设置o.setCustomer(null)
	@Test
	public void fun2() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		Customer c = (Customer) session.get(Customer.class, 1);
		Set<Order> orders = c.getOrders();
		for (Order o : orders) {
			o.setCustomer(null);//设置订单不属于任何用户
		}
		session.delete(c);
		//--------------------
		session.getTransaction().commit();        
		session.close();      
	}
}
