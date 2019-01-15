package cn.wyc.state;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import cn.wyc.Utils.HibernateUtils;
import cn.wyc.helloWorld.User;
/**
 * 对象的三种状态
 * @author xd
 *
 */
public class Demo1 {
	//演示三种状态
	@Test
	public void fun1() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User u = new User();     //瞬时状态
		u.setName("菲奥娜");       //瞬时状态
		u.setPassword("wwwww");  //瞬时状态
		session.save(u);         //持久状态 调用完save，数据库中没有对应记录，但是最终会同步到数据库当中，所以还是持久状态
		//--------------------
		bt.commit();             //持久状态
		session.close();         //游离状态
	}
	//三种状态的转换
	//瞬时-->持久
	@Test
	public void fun2() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User u = new User();     //瞬时状态
		u.setName("菲奥娜");       //瞬时状态
		u.setPassword("wwwww");  //瞬时状态
		session.save(u);         /*持久状态 save方法会使用主键生成策略，为User指定id，
		这里使用MySQL主键自增，会打印insert语句；；如果修改native改为increment，那么它会查询id，
		所以会有select maxid的语句，此时save主要的通过insert获得id，真正保存是commit。*/
		//--------------------
		bt.commit();             //持久状态
		session.close();         //游离状态
	}
	//瞬时-->游离
	@Test
	public void fun3() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User u = new User();     //瞬时状态
		u.setId(2);              //游离状态
		//--------------------
		bt.commit();             //持久状态
		session.close();         //游离状态
	}
	//持久-->瞬时1
	//持久 有关联有id
	//瞬时 无关联无id
	@Test
	public void fun4() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User user = (User) session.get(User.class, 2);  //持久状态
		//--------------------
		bt.commit();             //持久状态
		session.close();         //游离状态
		user.setId(null);        //瞬时状态
	} 
	//持久-->瞬时2
	//持久 有关联有id
	//瞬时 无关联无id
	@Test
	public void fun5() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User user = (User) session.get(User.class, 2);  //持久状态
		session.evict(user); //将user对象与session的关联解除
		user.setId(null);    //瞬时状态
  		//--------------------
		bt.commit();             //持久状态
		session.close();         //游离状态
	} 
	//持久-->游离
	//持久 有关联有id
	//游离 无关联有id
	@Test
	public void fun6() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User user = (User) session.get(User.class, 2);  //持久状态
		session.evict(user); //将user对象与session的关联解除  游离状态
  		//--------------------
		bt.commit();             //持久状态
		session.close();         //游离状态
	} 
	//游离-->瞬时
	//游离 无关联有id
	//瞬时 无关联无id
	@Test
	public void fun7() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User user = (User) session.get(User.class, 2);  //持久状态
		session.evict(user); //将user对象与session的关联解除  游离状态
		user.setId(null);  //瞬时状态
  		//--------------------
		bt.commit();             //持久状态
		session.close();         //游离状态
	} 
	//游离-->持久
	//游离 无关联有id
	//持久 有关联有id
	@Test
	public void fun8() {
		Session session = HibernateUtils.openSession();
		Transaction bt = session.beginTransaction();
		//--------------------
		User user = (User) session.get(User.class, 2);  //持久状态
		session.evict(user); //游离状态
		session.update(user); //持久状态
  		//--------------------
		bt.commit();             //持久状态
		session.close();         //游离状态
	} 
	//三种状态有什么用?
	// 持久状态,我们使用Hibernate主要是为了持久化我们的数据.
	// 对于对象的状态,我们期望我们需要同步到数据库的数据,都被装换成持久状态
	//持久化状态特点: Hibernate会自动将持久化状态对象的变化同步到数据库中.
	
	public void fun9(){
		Session session = HibernateUtils.openSession();
		session.beginTransaction();
		//------------------------------------------------
			//通过get方法,得到持久状态对象
		User u= (User) session.get(User.class, 1); // 持久状态
		
		//u.setName("jerry");//持久状态
		
		u.setId(3);//与session建立关联的对象的ID,不允许修改.
		
		session.update(u);// 多余=> 因为Hibernate会自动将持久化状态对象的变化同步到数据库中.
		
		//----------------------------------------------------
		session.getTransaction().commit(); // 持久状态 -> 打印update语句
		session.close(); // 瞬时状态
	}
}












