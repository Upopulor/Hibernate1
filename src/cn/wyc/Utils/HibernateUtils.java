package cn.wyc.Utils;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {
	// 会话工厂，整个程序只有一份。
	private static SessionFactory sf;

	static{
		//1 加载配置
		Configuration config = new Configuration().configure();
		
		//2 获得工厂
		sf = config.buildSessionFactory();
//3 关闭虚拟机时,释放SessionFactory
			Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("虚拟机关闭!释放资源");
				sf.close();
			}
		}));
	}
	/**
	 * 获得一个新的session
	 * @return
	 */
	public static Session openSession(){
		return sf.openSession();
	}
	
	/**
	 * 获得当前线程中绑定session
	 * * 注意：必须配置
	 * @return
	 */
	public static Session getCurrentSession(){
		return sf.getCurrentSession();
	}
	
	
}
