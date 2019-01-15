package cn.wyc.relateOneToOne;

import org.hibernate.Session;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;
//һ��һ����ʹ�����
public class DemoIt {
	//����1��1
	@Test
	public void forone1() {
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//--------------------
		Company com = new Company();
		com.setName("ȭͷ");
		Address add = new Address();
		add.setName("�Ϻ�");
		//ע�⣬��һ��һʹ�����ʱ��������ڵĶ���(addr)����ά����ϵ������һ���޷�ά����ϵ
		add.setCompany(com);
		//com.setAddress(add);
		session.save(com);
		session.save(add);
		//--------------------
		session.getTransaction().commit();             
		session.close();      
	}
	//��ѯ1��1
	//һ����ʹ�ñ������ѯ
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
