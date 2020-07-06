package testHibernate;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;  //JPA annotation

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

@Entity
@Table(name="category")
public class Category {  //Unidirectional child(product) -> parent(category)
	
	@Id
	@GeneratedValue
	private int id;
	
	private String name;
	
	@OneToMany(mappedBy="category", cascade=CascadeType.ALL , fetch=FetchType.LAZY)  
	//여기에 f.k가 있다. category라고 하는 필드를 조회해서 밑의 collection을 채움
	// fetchType을 지정하면 category를 로드할 때 product까지 로드하지 않고
	// Lazy를 주어 product는 필요할 때 따로 로드하게 해준다.
	// OneToMany 뒤에가 Many면 자동으로 Lazy
	private Set<Product> products = new HashSet<Product>();

}
