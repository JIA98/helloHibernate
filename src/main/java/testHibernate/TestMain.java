package testHibernate;

import java.io.Serializable;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class TestMain {

	private static SessionFactory sessionFactory;

	public static void main(String[] args) {

		/*
		 * Configuration conf = new Configuration();
		 * conf.configure("hibernate.cfg.xml");  //명확하게 하기 위해서 안에 값을 넣어준다ㅡ 
		 *  //conf객체에 있는 buildSessionFactory메소드를  사용하여 sessionFactory를 생성
		 *  sessionFactory = conf.buildSessionFactory();  // sessionFactory하나 생성
		 */
		Serializable id;

		// sessionFactory 생성 
		sessionFactory = new Configuration().configure().buildSessionFactory();  // 한줄로 쓰는 것이 더 깔끔해보인다. 

		Category category1 = new Category();
		category1.setName("컴퓨터");

		Category category2 = new Category();
		category2.setName("자동차");

		Product product1 = new Product(); // 객체를 하나 생성
		product1.setName("notebook1");
		product1.setPrice(2000);
		product1.setDescription("Awesome notebook!!!");

		product1.setCategory(category1); // notebook1은 category1에 속한다는 reference
		category1.getProducts().add(product1); // 양방향이 될 수 있도록 한다.

		Product product2 = new Product(); // 객체를 하나 생성
		product2.setName("notebook2");
		product2.setPrice(20000);
		product2.setDescription("Awesome notebook2!!!");

		product2.setCategory(category1);
		category1.getProducts().add(product2); // 양방향이 될 수 있도록 한다.

		Product product3 = new Product();
		product3.setName("sonata");
		product3.setPrice(3000000);
		product3.setDescription("Popular car");

		product3.setCategory(category2);
		category1.getProducts().add(product3); // 양방향이 될 수 있도록 한다.

		// 객체를 실제 DB에 저장하기 위해서 session을 먼저 만든다.
		Session session = sessionFactory.openSession(); // session생성

		Serializable cateId1 = 0;
		try {
			// tx 시작
			Transaction tx = session.beginTransaction();

			cateId1 = session.save(category1); // cascade로 인해 product1,2 함께 저장
			session.save(category2);

			//session.delete(category1); // ->product 1,2 함께 제거됨
			
//			/*
//			 * // ... 작업 진행 all or nothing 
//			 * // id = session.save(product); // 객체를 자동적으로 저장
//			 * //instead of sql statement 
//			 * session.save(product1); // instead of sql, sql 문을 몰라도 객체지향적으로 프로그래밍이 가능하다
//			 * statement session.save(product2); session.save(product3);
//			 * 
//			 * // product 3 -> category2 // product 1, product 2 -> category 1 
//			 * // if delete product 1 -> product2는 없는 category를 가리키게 된다
//			 * // product2를 저장하면 category1은 다시 저장해야하는데 오류가 발생한다.
//			 * //session.delete(product1); //-> error
//			 * 
//			 * product1.setCategory(null); // category와의 connection을 끊어버리고
//			 * session.delete(product1); //product1을 제거하면 product2는 안전
//			 * session.delete(product3); //product를 제거하면 category를 가리키고 있어 category도 같이 제거가된다. (cascade에 의해)
//			 */			
//			Product savedProduct = session.get(Product.class, id);  //id에 해당되는 레코드를 읽어서 객체로 넘어온다
//			System.out.println(savedProduct);

//			Query<Product> theQuery = session.createQuery("from Product order by name", Product.class); // HQL
//																										// product테이블
//			List<Product> products = theQuery.getResultList(); // read
//			System.out.println(products);

			tx.commit();

		} catch (Exception exc) {
			exc.printStackTrace();

		} finally {

			session.close();
		}
	
		Session session1 = sessionFactory.openSession();
		Transaction tx1 = session1.beginTransaction();
		
		Category aCategory = session1.get(Category.class, cateId1);
		// category를 읽고 난 다음에 Lazy 속성을 주어 product라는 정보는 들어가지 않는다.
		// EAGER 속성을 주면  product까지 다 가져온다.
		
		Set<Product> products = aCategory.getProducts(); // 실제 product에 접근을 할때 가져온다.
		
		for(Product p : products)
			System.out.println(p.getName());
		
		tx1.commit();
		session1.close();
		
		
		sessionFactory.close();
	}

}

//sessionFactory를 이용해서 session을 만들고 객체를 저장한다.
