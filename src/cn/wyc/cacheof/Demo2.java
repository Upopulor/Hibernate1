package cn.wyc.cacheof;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;
import cn.wyc.helloWorld.User;

public class Demo2 {
	//1 �������ʱʹ��save������persist����������
	//persist����������JPA�ӿڣ�save����������Hibernate�ӿڣ�����һ�������ú����ֲ�һ��
	@Test
	public void fun2() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User u1 = new User();
		u1.setName("�������");
		session.save(u1);   //insert������ӡ----Ϊ�˻��id
		session.persist(u1);   //insert������ӡ----Ϊ�˻��id
		//--------------------
		bt.commit();             
		session.close();      
	}
	//HQL��ѯ�Ƿ�ʹ��һ������ ��
	@Test
	public void fun3() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		List<User> list = session.createQuery("from User").list();
		List<User> list2 = session.createQuery("from User").list();
		//��ӡ������ͬ�ģ�����HQL����ʹ��1������
		//--------------------
		bt.commit();             
		session.close();      
	}
	//HQL���������ѯʱ����ѯ����Ƿ����뻺�棿��
	@Test
	public void fun4() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		List<User> list = session.createQuery("from User").list();
		User u  = (User) session.get(User.class,2);
		//ֻ��ӡһ����HQL��ѯ��������˻���
		//--------------------
		bt.commit();             
		session.close();      
	}
	//SQL���������ѯ1ʱ����ѯ����Ƿ����뻺�棿��
	@Test
	public void fun5() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		List<User> list = session.createSQLQuery("select * from user_h").addEntity(User.class).list();
		User u  = (User) session.get(User.class,2);
		//ֻ��ӡһ����SQL��ѯ��������˻���
		//--------------------
		bt.commit();             
		session.close();      
	}
	//SQL���������ѯ2ʱ����ѯ����Ƿ����뻺�棿��
	//����Ѷ����װ��User������ô��������1������ģ����򲻻�
	@Test
	public void fun6() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		List list = session.createSQLQuery("select * from user_h").list();
		User u  = (User) session.get(User.class,2);
		//��ӡ������SQL��ѯ���û�н����˻���
		//--------------------
		bt.commit();             
		session.close();      
	}
}
