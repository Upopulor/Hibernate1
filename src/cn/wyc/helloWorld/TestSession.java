package cn.wyc.helloWorld;

import java.util.Arrays;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.Test;

public class TestSession {
	//Session�������ڲ������ݿ�
	//��
	@Test
	public void fun1() {
		//1 ��ȡ�����ļ�
		Configuration conf = new Configuration().configure();
		//2 ���������ļ� ����factory
		SessionFactory sf = conf.buildSessionFactory();
		//3 ͨ��factory��ò������ݿ��session����
		Session session = sf.openSession();
		//4 �������ݿ�
		User user = new User();
		user.setName("ѹ��");
		user.setPassword("5555");
		session.save(user);
		//5 �ر���Դ
		session.close();
		sf.close();
	}
	//��
	@Test
	public void fun2() {
		//1 ��ȡ�����ļ�
		Configuration conf = new Configuration().configure();
		//2 ���������ļ� ����factory
		SessionFactory sf = conf.buildSessionFactory();
		//3 ͨ��factory��ò������ݿ��session����
		Session session = sf.openSession();	
		//������
		Transaction ta = session.beginTransaction();
		
		//4 ��ѯ��Ҫ�޸ĵĶ���
		User user = (User) session.get(User.class, 1);
		//System.out.println(user);
		//5 �ڲ�ѯ������޸�
		user.setName("���ȿ�");
		session.update(user);
		
		ta.commit();
		session.close();
		sf.close();	
	}
	//ɾ��
	@Test
	public void fun3() {
		//1 ��ȡ�����ļ�
		Configuration conf = new Configuration().configure();
		//2 ���������ļ� ����factory
		SessionFactory sf = conf.buildSessionFactory();
		//3 ͨ��factory��ò������ݿ��session����
		Session session = sf.openSession();	
		//������
		Transaction ta = session.beginTransaction();
		
		//4 ����IDɾ��
		User user = (User) session.get(User.class, 1);
		session.delete(user);
		
		ta.commit();	
		session.close();
		sf.close();	
	}
	//��ѯ  get load
	@Test
	public void fun4() {
		//1 ��ȡ�����ļ�
		Configuration conf = new Configuration().configure();
		//2 ���������ļ� ����factory
		SessionFactory sf = conf.buildSessionFactory();
		//3 ͨ��factory��ò������ݿ��session����
		Session session = sf.openSession();	
		//������
		Transaction ta = session.beginTransaction();
		
		//��ѯ1
//		User user = (User) session.get(User.class, 2);
//		System.out.println(user);
		User user = (User) session.load(User.class, 2);
		System.out.println(user);
		/**
		 *  ����Ѵ˲��ֲ�ѯ�ŵ�commit���棬�ᷢ��get����ȡ��������loadȡ��������Ϊload�Ļ���session�ر��ˣ���������޷�����session��ѯ���ݿ�
		 * get: get��������ʱ���̷���SQL����ѯ
		 * load������ʱû�в�ѯ���ݿ⣬��������Ҫʹ�øö����ʱ�򣬲Ų�ѯ����
		 */
		//
		ta.commit();	
		session.close();
		sf.close();	
	}
	//��ѯ ���Զ���1
	@Test
	public void fun5() {
		//1 ��ȡ�����ļ�
		Configuration conf = new Configuration().configure();
		//2 ���������ļ� ����factory
		SessionFactory sf = conf.buildSessionFactory();
		//3 ͨ��factory��ò������ݿ��session����
		Session session = sf.openSession();	
		//������
		Transaction ta = session.beginTransaction();
		
		//HQL����
		//createQuery ����hql����
		Query cq = session.createQuery("from cn.wyc.helloWorld.User");
		//list �����ִ�У������ؽ��
		List<User> list = cq.list();
		System.out.println(list);
		
		ta.commit();	
		session.close();
		sf.close();	
	}
	//��ѯ ���Զ���2
	@Test
	public void fun6() {
		//1 ��ȡ�����ļ�
		Configuration conf = new Configuration().configure();
		//2 ���������ļ� ����factory
		SessionFactory sf = conf.buildSessionFactory();
		//3 ͨ��factory��ò������ݿ��session����
		Session session = sf.openSession();	
		//������
		Transaction ta = session.beginTransaction();
		
		// createCriteria��ѯ hibernate�������������Ĳ�ѯ �����
		Criteria cc = session.createCriteria(User.class);
		List<User> list = cc.list();
		System.out.println(list);
		
		ta.commit();	
		session.close();
		sf.close();	
	}
	//��ѯ ���Զ���3
	@Test
	public void fun7() {
		//1 ��ȡ�����ļ�
		Configuration conf = new Configuration().configure();
		//2 ���������ļ� ����factory
		SessionFactory sf = conf.buildSessionFactory();
		//3 ͨ��factory��ò������ݿ��session����
		Session session = sf.openSession();	
		//������
		Transaction ta = session.beginTransaction();
		
		//ԭ��SQL��ѯ
		SQLQuery cs = session.createSQLQuery("select * from user_h");
		//����ѯ�����װ��ָ��������,���û����䣬��Ҫʹ��ע�͵�forѭ�������ӵ�Object�����д�ӡ
		cs.addEntity(User.class);
		//���ﲻ���������ģ����Բ��ܼӷ���
//		List<Object[]> list = cs.list();
//		for (Object[] object : list) {
//			System.out.println(Arrays.toString(object));
//		}
		List<User> list = cs.list();
		System.out.println(list);
		ta.commit();	
		session.close();
		sf.close();	
	}
}
