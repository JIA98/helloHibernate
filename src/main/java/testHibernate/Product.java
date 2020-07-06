package testHibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

// 설정한 정보를 바탕으로 테이블이 설정되고 매핑이 이루어진다.
@Entity // 아래 항목을 테이블에 매핑한다.
@Table(name = "product") // 테이블의 이름을 지정
public class Product {

	@Id // primary key로 테이블에서 사용된다.
	@GeneratedValue // id를 자동으로 생성하라
	@Column(name = "product_id") // 컬럼이름 지정
	private int id;

	// column 이름을 지정해주지 않으면 필드이름과 동일하게 컬럼 이름 생성
	private String name;

	private int price;

	private String description;

	// Unidirectional OneToMany
	// child(product) -> parent(category)

	@ManyToOne // cascade는 parent쪽에 두어 삭제를 한다 -> 앞으로 category를 바탕으로 저장되거나 삭제가 이루어진다.
	// @ManyToOne(cascade=CascadeType.ALL) //cascade를 넣어주어 product가 저장될 때 category도
	// 또한 저장된다.
	@JoinColumn(name = "category_id") // f.k값으로 join column 사용
	private Category category;

}
