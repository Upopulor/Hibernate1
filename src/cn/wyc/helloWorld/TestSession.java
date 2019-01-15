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
	//Session对象，用于操作数据库
	//增
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
		user.setName("压缩");
		user.setPassword("5555");
		session.save(user);
		//5 关闭资源
		session.close();
		sf.close();
	}
	//改
	@Test
	public void fun2() {
		//1 读取配置文件
		Configuration conf = new Configuration().configure();
		//2 根据配置文件 创建factory
		SessionFactory sf = conf.buildSessionFactory();
		//3 通过factory获得操作数据库的session对象
		Session session = sf.openSession();	
		//打开事务
		Transaction ta = session.beginTransaction();
		
		//4 查询出要修改的对象
		User user = (User) session.get(User.class, 1);
		//System.out.println(user);
		//5 在查询结果上修改
		user.setName("卡兹克");
		session.update(user);
		
		ta.commit();
		session.close();
		sf.close();	
	}
	//删除
	@Test
	public void fun3() {
		//1 读取配置文件
		Configuration conf = new Configuration().configure();
		//2 根据配置文件 创建factory
		SessionFactory sf = conf.buildSessionFactory();
		//3 通过factory获得操作数据库的session对象
		Session session = sf.openSession();	
		//打开事务
		Transaction ta = session.beginTransaction();
		
		//4 根据ID删除
		User user = (User) session.get(User.class, 1);
		session.delete(user);
		
		ta.commit();	
		session.close();
		sf.close();	
	}
	//查询  get load
	@Test
	public void fun4() {
		//1 读取配置文件
		Configuration conf = new Configuration().configure();
		//2 根据配置文件 创建factory
		SessionFactory sf = conf.buildSessionFactory();
		//3 通过factory获得操作数据库的session对象
		Session session = sf.openSession();	
		//打开事务
		Transaction ta = session.beginTransaction();
		
		//查询1
//		User user = (User) session.get(User.class, 2);
//		System.out.println(user);
		User user = (User) session.load(User.class, 2);
		System.out.println(user);
		/**
		 *  如果把此部分查询放到commit下面，会发现get可以取到，但是load取不到，因为load的话，session关闭了，代理对象无法调用session查询数据库
		 * get: get方法调用时立刻发送SQL语句查询
		 * load：调用时没有查询数据库，当我们需要使用该对象的时候，才查询数据
		 */
		//
		ta.commit();	
		session.close();
		sf.close();	
	}
	//查询 所以对象1
	@Test
	public void fun5() {
		//1 读取配置文件
		Configuration conf = new Configuration().configure();
		//2 根据配置文件 创建factory
		SessionFactory sf = conf.buildSessionFactory();
		//3 通过factory获得操作数据库的session对象
		Session session = sf.openSession();	
		//打开事务
		Transaction ta = session.beginTransaction();
		
		//HQL语言
		//createQuery 传入hql语言
		Query cq = session.createQuery("from cn.wyc.helloWorld.User");
		//list 将语句执行，并返回结果
		List<User> list = cq.list();
		System.out.println(list);
		
		ta.commit();	
		session.close();
		sf.close();	
	}
	//查询 所以对象2
	@Test
	public void fun6() {
		//1 读取配置文件
		Configuration conf = new Configuration().configure();
		//2 根据配置文件 创建factory
		SessionFactory sf = conf.buildSessionFactory();
		//3 通过factory获得操作数据库的session对象
		Session session = sf.openSession();	
		//打开事务
		Transaction ta = session.beginTransaction();
		
		// createCriteria查询 hibernate独创的面向对象的查询 无语句
		Criteria cc = session.createCriteria(User.class);
		List<User> list = cc.list();
		System.out.println(list);
		
		ta.commit();	
		session.close();
		sf.close();	
	}
	//查询 所以对象3
	@Test
	public void fun7() {
		//1 读取配置文件
		Configuration conf = new Configuration().configure();
		//2 根据配置文件 创建factory
		SessionFactory sf = conf.buildSessionFactory();
		//3 通过factory获得操作数据库的session对象
		Session session = sf.openSession();	
		//打开事务
		Transaction ta = session.beginTransaction();
		
		//原生SQL查询
		SQLQuery cs = session.createSQLQuery("select * from user_h");
		//将查询结果封装到指定对象中,如果没有这句，需要使用注释的for循环遍历加到Object数组中打印
		cs.addEntity(User.class);
		//这里不是面向对象的，所以不能加泛型
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
