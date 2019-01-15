package cn.wyc.cacheof;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;
import cn.wyc.helloWorld.User;

/**
 * session ����
 * @author xd
 *
 */
public class Demo1 {
	@Test
	public void fun1() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User u1 = (User) session.get(User.class, 2); //����select��䣬�����ݿ�ȡ�����󣬳־û�����->�浽������
		User u2 = (User) session.get(User.class, 2); //�ٴβ�ѯʱ����ӻ����в��ң����ᷢ��select���
		
		//--------------------
		bt.commit();             
		session.close();      
	}
	//session�����еĿ���
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
	//session�����еĿ���
	@Test
	public void fun3() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User u1 = new User();
		u1.setId(2);
		u1.setName("����");
		u1.setPassword("kuiuijh");
		session.update(u1);
		//--------------------
		bt.commit();             
		session.close();      
	}
}
