package cn.wyc.relatemanytomany;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import cn.wyc.Utils.HibernateUtils;

public class Demo1 {
	//保存学术--通过学生保存课程，由学生维护外键
	// Student inverse=false cascade=save-update
	// Course inverse=true
	@Test
	public void Tee1() {   
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		Student u = new Student();
		u.setName("大嘴");
		Student u1 = new Student();
		u1.setName("薇恩");
		Course c = new Course();
		c.setName("老牛");
		Course c1 = new Course();
		c1.setName("锤石");
		u.getCourses().add(c);    //维护关系，级联保存
		u.getCourses().add(c1);
		u1.getCourses().add(c);
		u1.getCourses().add(c1);
		session.save(u);
		session.save(u1);
		//--------------------
		bt.commit();             
		session.close();      
	}  
}
