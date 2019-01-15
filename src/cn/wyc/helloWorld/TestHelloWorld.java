package cn.wyc.helloWorld;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.Test;

public class TestHelloWorld {
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
		user.setName("tfdsf2");
		user.setPassword("12345");
		session.save(user);
		//5 �ر���Դ
		session.close();
		sf.close();
	}
}

