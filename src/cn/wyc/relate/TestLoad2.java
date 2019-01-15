package cn.wyc.relate;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;
/**
 * ����(һ�Զ�):customer��1��
			set 
				lazy: �Ƿ��set����ʹ��������
					true:(Ĭ��ֵ) �Լ���ʹ�òż���
					false: ���Ͻ��ᱻ��������
					extra: ��������,���ʹ�ü���ʱ,֮����size������ѯ����, Hibernate�ᷢ��count���,ֻ��ѯ����.�����ؼ���������.
				fetch : �������ؼ���ʹ�õ�sql�������
					select: (Ĭ��ֵ) ��ͨselect��ѯ
					join: ����������ѯ��������
					subselect: ʹ���Ӳ�ѯ һ�μ��ض��Customer�Ķ�������

       fetch		lazy	����
	 ------------------------------
		select  	    true	Ĭ��ֵ, ����ʹ�ü���ʱ����,��ͨselect���
		select		false	����ʹ��select�����ؼ�������
		select		extra	����ʹ�ü���ʱ����,��ͨselect���,���ֻ�ǻ�ü��ϵĳ���,�ᷢ��Count����ѯ����.
		join		    true	��ѯ����ʱʹ�ñ����Ӳ�ѯ,�����̼��ؼ�������
		join		    false	��ѯ����ʱʹ�ñ����Ӳ�ѯ,�����̼��ؼ�������
		join		    extra	��ѯ����ʱʹ�ñ����Ӳ�ѯ,�����̼��ؼ�������
		subselect	 true	����ʹ�ü���ʱ����,�Ӳ�ѯ���
		subselect  	false	���ڲ�ѯ�û�ʱ,����ʹ���Ӳ�ѯ���ؿͻ��Ķ�������
		subselect   extra	����ʹ�ü���ʱ����,�Ӳ�ѯ���,���ֻ�ǻ�ü��ϵĳ���,�ᷢ��Count����ѯ����.
------------------------------------------------------------------------------
�����һ��order:���ࣩ
		lazy
 			false		���ض���ʱ,���������ؿͻ�
 			proxy		���ͻ����������ز���������
 			no-proxy : �����о�. 
 		fetch=
			select  : (Ĭ��ֵ)ʹ����ͨselect����
			join	: ʹ�ñ����Ӽ�������
	
	fetch			lazy			���
---------------------------------------------------
	select			false			���ض���ʱ,�������ؿͻ�����.��ͨselect�����ؿͻ�.
	select			proxy			����ز���Ϊ:lazy=false ͬ��
												 lazy=true	���ض���ʱ,�Ȳ����ؿͻ�����.ʹ�ÿͻ�����ʱ�ż���
	join			false			ʹ�ñ����Ӳ�ѯ�����Լ���Ӧ�ͻ���Ϣ.lazy������Ч
	join			proxy			ʹ�ñ����Ӳ�ѯ�����Լ���Ӧ�ͻ���Ϣ.lazy������Ч
-------------------------------------------------------------------------------------------

 * @author xd
 *
 */
public class TestLoad2 {
	//�������������� <class  lazy="true | false">
	// Ĭ�ϣ�������Щ����������ݣ���ʹ��ʱ�Ż����
	@Test
	public void fun1() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		Customer c = (Customer) session.get(Customer.class, 5);
		for(Order o : c.getOrders()) {
			System.out.println(o.getName());
		}
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
	//�������������� ��set����lazy=false
	//set name="orders" inverse="false" lazy="true">
	@Test
	public void fun2() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		Customer c = (Customer) session.get(Customer.class, 5);
		for(Order o : c.getOrders()) {
			System.out.println(o.getName());
		}
		//--------------------
		session.getTransaction().commit();          
		session.close();      
	}
}
