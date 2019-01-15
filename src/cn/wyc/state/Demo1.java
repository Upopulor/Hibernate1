package cn.wyc.state;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;
import cn.wyc.helloWorld.User;
/**
 * ���������״̬
 * @author xd
 *
 */
public class Demo1 {
	//��ʾ����״̬
	@Test
	public void fun1() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User u = new User();     //˲ʱ״̬
		u.setName("�ư���");       //˲ʱ״̬
		u.setPassword("wwwww");  //˲ʱ״̬
		session.save(u);         //�־�״̬ ������save�����ݿ���û�ж�Ӧ��¼���������ջ�ͬ�������ݿ⵱�У����Ի��ǳ־�״̬
		//--------------------
		bt.commit();             //�־�״̬
		session.close();         //����״̬
	}
	//����״̬��ת��
	//˲ʱ-->�־�
	@Test
	public void fun2() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User u = new User();     //˲ʱ״̬
		u.setName("�ư���");       //˲ʱ״̬
		u.setPassword("wwwww");  //˲ʱ״̬
		session.save(u);         /*�־�״̬ save������ʹ���������ɲ��ԣ�ΪUserָ��id��
		����ʹ��MySQL�������������ӡinsert��䣻������޸�native��Ϊincrement����ô�����ѯid��
		���Ի���select maxid����䣬��ʱsave��Ҫ��ͨ��insert���id������������commit��*/
		//--------------------
		bt.commit();             //�־�״̬
		session.close();         //����״̬
	}
	//˲ʱ-->����
	@Test
	public void fun3() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User u = new User();     //˲ʱ״̬
		u.setId(2);              //����״̬
		//--------------------
		bt.commit();             //�־�״̬
		session.close();         //����״̬
	}
	//�־�-->˲ʱ1
	//�־� �й�����id
	//˲ʱ �޹�����id
	@Test
	public void fun4() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User user = (User) session.get(User.class, 2);  //�־�״̬
		//--------------------
		bt.commit();             //�־�״̬
		session.close();         //����״̬
		user.setId(null);        //˲ʱ״̬
	} 
	//�־�-->˲ʱ2
	//�־� �й�����id
	//˲ʱ �޹�����id
	@Test
	public void fun5() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User user = (User) session.get(User.class, 2);  //�־�״̬
		session.evict(user); //��user������session�Ĺ������
		user.setId(null);    //˲ʱ״̬
  		//--------------------
		bt.commit();             //�־�״̬
		session.close();         //����״̬
	} 
	//�־�-->����
	//�־� �й�����id
	//���� �޹�����id
	@Test
	public void fun6() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User user = (User) session.get(User.class, 2);  //�־�״̬
		session.evict(user); //��user������session�Ĺ������  ����״̬
  		//--------------------
		bt.commit();             //�־�״̬
		session.close();         //����״̬
	} 
	//����-->˲ʱ
	//���� �޹�����id
	//˲ʱ �޹�����id
	@Test
	public void fun7() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User user = (User) session.get(User.class, 2);  //�־�״̬
		session.evict(user); //��user������session�Ĺ������  ����״̬
		user.setId(null);  //˲ʱ״̬
  		//--------------------
		bt.commit();             //�־�״̬
		session.close();         //����״̬
	} 
	//����-->�־�
	//���� �޹�����id
	//�־� �й�����id
	@Test
	public void fun8() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User user = (User) session.get(User.class, 2);  //�־�״̬
		session.evict(user); //����״̬
		session.update(user); //�־�״̬
  		//--------------------
		bt.commit();             //�־�״̬
		session.close();         //����״̬
	} 
	//����״̬��ʲô��?
	// �־�״̬,����ʹ��Hibernate��Ҫ��Ϊ�˳־û����ǵ�����.
	// ���ڶ����״̬,��������������Ҫͬ�������ݿ������,����װ���ɳ־�״̬
	//�־û�״̬�ص�: Hibernate���Զ����־û�״̬����ı仯ͬ�������ݿ���.
	
	public void fun9(){
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//------------------------------------------------
			//ͨ��get����,�õ��־�״̬����
		User u= (User) session.get(User.class, 1); // �־�״̬
		
		//u.setName("jerry");//�־�״̬
		
		u.setId(3);//��session���������Ķ����ID,�������޸�.
		
		session.update(u);// ����=> ��ΪHibernate���Զ����־û�״̬����ı仯ͬ�������ݿ���.
		
		//----------------------------------------------------
		session.getTransaction().commit(); // �־�״̬ -> ��ӡupdate���
		session.close(); // ˲ʱ״̬
	}
}












