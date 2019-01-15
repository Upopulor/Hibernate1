package cn.wyc.relate;

import java.util.Set;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;


public class oneToMany1 {
	//һ�Զ��ϵ�ı���
	//��ӡ5����� ǰ3��insert--�������ά����� ������--ά�����
	//ά�������Σ������������ָ����ϵ������һ��ά������һ�ݲ�ά����ϵ��
	//�������ά���ķ���ֻ���з�������ڶ������,����customer��inverseΪtrue�������orderά����ֻ��ӡ3��insert
	//��ʱҲ�Ͳ���Ҫc.getOrders().add(o1); //ά����ϵ
	@Test
	public void fun1() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		Customer c = new Customer();
		c.setName("tom");
		Order o1 = new Order();
		o1.setName("�ɻ�");
		Order o2 = new Order();
		o2.setName("���");
		//c.getOrders().add(o1); //ά����ϵ
		//c.getOrders().add(o2); //ά����ϵ
		o1.setCustomer(c);     //ά����ϵ
		o2.setCustomer(c);     //ά����ϵ
		session.save(c);
		session.save(o1);
		session.save(o2);
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
	//����ϵ�е�ɾ��
	//ɾ���û�ʱ�����Ƴ�customer�����õ������Ȼ����ɾ��customer
	//���ۣ�ά��һ���Ķ���ʱ�����Զ�ά����һ���Ĺ�ϵ
	//�����customer��Iverse����Ϊtrue�ᱨ����Ϊcustomer��ά���������ô�Ͳ���ֱ��ɾ��
	//�����Ȼ��ɾ�����£�������o.setCustomer(null)
	@Test
	public void fun2() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		Customer c = (Customer) session.get(Customer.class, 1);
		Set<Order> orders = c.getOrders();
		for (Order o : orders) {
			o.setCustomer(null);//���ö����������κ��û�
		}
		session.delete(c);
		//--------------------
		session.getTransaction().commit();        
		session.close();      
	}
}
