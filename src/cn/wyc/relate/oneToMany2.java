package cn.wyc.relate;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;


public class oneToMany2 {
	//1��������
	//ϣ���ڱ���customerʱ���Զ���δ�����Order�е�order����
	//cascade:save-update
	@Test
	public void fun1() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//--------------------
		Customer c = new Customer();
		c.setName("tom");
		Order o1 = new Order();
		o1.setName("�ɻ�");
		Order o2 = new Order();
		o2.setName("���");
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

	//�����޸�
	@Test
	public void fun2() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//--------------------
		Customer c = (Customer) session.get(Customer.class, 8);
		for(Order o : c.getOrders()) {
			o.setName("������");
		}
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
	//2����ɾ��
	//cascade="delete"ɾ��customerʱ���Ὣcustomer�µ�orderͬʱɾ��
	//inverse = false 6�����
	//inverse = true 5�����
	//ע�⣬delete�ڶ��һ�������ٵ�һ�����ǿ������õ�
	@Test
	public void fun3() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//--------------------
		Customer c = (Customer) session.get(Customer.class, 8);
		session.delete(c); //ɾ��customer ɾ����order ���������inverseΪfalse��select�󻹻��и�update�Ƚ��鷳
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
	//����ɾ��˫�����߶�������delete
	//����
	@Test
	public void fun4() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//--------------------
		Order o = (Order) session.get(Order.class, 9); //select
		session.delete(o); //delete��ǰOrder
						   //�ҵ������������Customerɾ�� select
						   //Customer�����˼���ɾ��--select�������Order
		                   //ɾ�����е�Order
		                   //ɾ��Customerɾ������������
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
	//3���� cascade="delete-orphan" ��û���κ��������aʱ��a�ᱻɾ��
	@Test
	public void fun5() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//--------------------
		Customer c = (Customer) session.get(Customer.class, 9);
		Iterator<Order> it = c.getOrders().iterator();
		//ɾ��Customer�µĶ���ʱ����ʹ��c.setOrder(null)
		while(it.hasNext()) {
			it.next();
			it.remove();
		}
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
}
