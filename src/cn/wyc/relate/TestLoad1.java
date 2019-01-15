package cn.wyc.relate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
/**
 * ���Լ��ز���
 */
import cn.wyc.Utils.HibernateUtils;

public class TestLoad1 {
	//�༶�����
	//class lazy���� 
	//   Ĭ��true ��load���ʱ�᷵�ش�����󲻲�ѯ���ݿ⣬ʹ��ʱ�Ų�ѯ
	//      false ��load����ִ��ʱ�ͷ���SQL��ѯ��䣬��get����һ��
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
