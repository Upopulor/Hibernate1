package cn.wyc.relatemanytomany;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import cn.wyc.Utils.HibernateUtils;

public class Demo1 {
	//����ѧ��--ͨ��ѧ������γ̣���ѧ��ά�����
	// Student inverse=false cascade=save-update
	// Course inverse=true
	@Test
	public void Tee1() {   
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		Student u = new Student();
		u.setName("����");
		Student u1 = new Student();
		u1.setName("ޱ��");
		Course c = new Course();
		c.setName("��ţ");
		Course c1 = new Course();
		c1.setName("��ʯ");
		u.getCourses().add(c);    //ά����ϵ����������
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
