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
 * ��������
 * @author xd
 *
 */
public class Ca1 {
	//�໺��
	@Test
	public void cA1() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		@SuppressWarnings("unused")
		Customer c = (Customer) session.get(Customer.class, 2);
		session.clear();//���1������
		Customer c2 = (Customer) session.get(Customer.class, 2);//�����ﲻ��ӡ��֤���Ӷ�������ȡ������
		System.out.println(c == c2);//false 
		//���������ڻ�������ʱ�����Զ������ʽ����ģ�����������ݵ�ɢ�У����ʱ����1����������װ�ɶ������治�ܻ����������Ƕ��󣬸��������ú�ᱻ�޸�
		
		//--------------------
		bt.commit();             
		session.close();      
	}
	//���ϻ���
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
		session.clear();//���1������
		Customer c2 = (Customer) session.get(Customer.class, 5);//�����ﲻ���ӡ����Ϊʹ���˼��ϻ���
		//ע�⣬���û�м��ϼ����������<class-cache usage="read-only" class="cn.wyc.relate.Order"/>
		//��ô��ִ������ʱ���ǻ��ӡ��ѯ��䣬����һ�β�ѯ���и����Ĳ�ѯ��䣬������2�����Լ��ϻ������зŵ������id��Ȼ��
		//��ʱͨ��idȡ���ݿ��ң������order���໺���� ���Ͳ�ȥ���ݿ�����
		for(Order o1 : c2.getOrders()) {  
			System.out.println(o1.getName());
		}
		//--------------------
		bt.commit();             
		session.close();      
	}
	//��ѯ����-��HQL����ѯ�Ļ���
	@Test
	public void cA3() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		Query query = session.createQuery("from Customer");
		//ʹ�ò�ѯ����
		//�ڲ�ѯʱ���ȴ�2������ȡ�����ȡ������ִ����䣬��������������ѯ������
		//ʹ�ò�ѯ����ʱ�������Ҳ��id��ҲӦ�Ȼ���ʹ�õĶ����������Customer������Ҳ���ӡ
		query.setCacheable(true);
		List<Customer> list = query.list();
		session.clear();
		//�������ʹ��select c from customer c������ǲ���ӡ---�������HQL��Ӧ��SQL���
		Query query2 = session.createQuery("from Customer");//��ѯ�����ʱ��û�д�ӡ���
		List<Customer> list2 = query.list();
		//--------------------
		bt.commit();             
		session.close();      
	}
}
