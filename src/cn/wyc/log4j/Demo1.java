package cn.wyc.log4j;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;
import cn.wyc.helloWorld.User;

public class Demo1 {
	@Test
	public void fun00() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		//User u1 = (User) session.get(User.class, 2); //����select��䣬�����ݿ�ȡ�����󣬳־û�����->�浽������
		//User u2 = (User) session.get(User.class, 2); //�ٴβ�ѯʱ����ӻ����в��ң����ᷢ��select���
		
		//--------------------
		bt.commit();             
		session.close();      
	}
}
