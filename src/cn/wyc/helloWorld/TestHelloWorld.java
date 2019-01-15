package cn.wyc.helloWorld;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.classic.Session;
import org.junit.Test;

public class TestHelloWorld {
	@Test
	public void fun1() {
		//1 读取配置文件
		Configuration conf = new Configuration().configure();
		//2 根据配置文件 创建factory
		SessionFactory sf = conf.buildSessionFactory();
		//3 通过factory获得操作数据库的session对象
		Session session = sf.openSession();
		//4 操作数据库
		User user = new User();
		user.setName("tfdsf2");
		user.setPassword("12345");
		session.save(user);
		//5 关闭资源
		session.close();
		sf.close();
	}
}

