package cn.wyc.relateOneToOne;

import org.hibernate.Session;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;
//一对一操作使用外键
public class DemoIt {
	//保存1对1
	@Test
	public void forone1() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//--------------------
		Company com = new Company();
		com.setName("拳头");
		Address add = new Address();
		add.setName("上海");
		//注意，在一对一使用外键时，外键所在的对象(addr)才能维护关系，而另一方无法维护关系
		add.setCompany(com);
		//com.setAddress(add);
		session.save(com);
		session.save(add);
		//--------------------
		session.getTransaction().commit();             
		session.close();      
	}
	//查询1对1
	//一定会使用表关联查询
	@Test
	public void forone2() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//--------------------
		Company com = (Company) session.get(Company.class, 1);
		System.out.println(com);
		//--------------------
		session.getTransaction().commit();             
		session.close();      
	}
}
