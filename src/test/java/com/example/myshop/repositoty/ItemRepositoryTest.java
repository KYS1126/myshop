package com.example.myshop.repositoty;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EnumType;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.event.annotation.PrepareTestInstance;

import com.example.myshop.constant.ItemSellStatus;
import com.example.myshop.entity.Item;
import com.example.myshop.entity.Member;
import com.example.myshop.entity.QItem;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryFactory;
//import com.querydsl.core.util.StringUtils;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import org.thymeleaf.util.StringUtils;

@SpringBootTest
//h2 DB 연결하기
@TestPropertySource(locations="classpath:application-test.properties")
class ItemRepositoryTest {

	//의존성 주입
	@Autowired
	ItemRepository itemRepository;
	

//	@Test //메소드 위에 써주기
//	@DisplayName("상품 저장 테스트") 
	
	/*
	public void createItemTest() {
		Item item = new Item();
		item.setItemNm("테스트 상품");
		item.setPrice(10000);
		item.setItemDetail("테스트 상품 상세설명");
		item.setItemSellStatus(ItemSellStatus.SELL);
		item.setStockNumber(100);
		item.setRegTime(LocalDateTime.now()); 
		item.setUpdateTime(LocalDateTime.now());
		
		Item savedItem = itemRepository.save(item); //데이터 insert
		
		System.out.println(savedItem.toString());
	}
	*/
	
	public void createItemTest() {
		for (int i=1; i<=10; i++) {
			Item item = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세설명" + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
//			item.setUpdateTime(LocalDateTime.now());
			Item savedItem = itemRepository.save(item); //데이터 insert
		}
	}
	
	public void createItemTest2() {
		for (int i=1; i<=5; i++) {
			Item item = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세설명" + i);
			item.setItemSellStatus(ItemSellStatus.SELL);
			item.setStockNumber(100);
			item.setRegTime(LocalDateTime.now());
//			item.setUpdateTime(LocalDateTime.now());
			Item savedItem = itemRepository.save(item); //데이터 insert
		}
		
		for (int i=6; i<=10; i++) {
			Item item = new Item();
			item.setItemNm("테스트 상품" + i);
			item.setPrice(10000 + i);
			item.setItemDetail("테스트 상품 상세설명" + i);
			item.setItemSellStatus(ItemSellStatus.SOLD_OUT);
			item.setStockNumber(0);
			item.setRegTime(LocalDateTime.now());
//			item.setUpdateTime(LocalDateTime.now());
			Item savedItem = itemRepository.save(item); //데이터 insert
		}
		
	}
	//정보! @Test가 안붙어있으면 실행이 안된다. 테스트에선
	@DisplayName("상품명 조회 테스트")
	public void findByItemNmTset() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemNm("테스트 상품1");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@DisplayName("상품명, 상품상세설명 or 테스트")
	public void findByItemNmorItemDetailTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemNmOrItemDetail("테스트 상품1", "테스트 상품 상세설명5");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@DisplayName("가격 LessThan 테스트")
	public void findByPriceLessThanTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByPriceLessThan(10005);
		for (Item item : itemList) {
			System.out.println("가격 LessThan 테스트"+item.toString());
		}
	}
	
	@DisplayName("가격 내림차순 조회 테스트")
	public void findByPriceLessThanOrderByPriceDescTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByPriceLessThanOrderByPriceDesc(10005);
		for (Item item : itemList) {
			System.out.println("가격 내림차순 조회 테스트"+item.toString());
		}
	}
	
	/*
	@DisplayName("퀴즈 1번")
	public void findByItemNmAndItemSellStatusTest () {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemNmAndItemSellStatus("테스트 상품1", ItemSellStatus.SELL);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	////price가 10004~ 10008 사이인 레코드를 구하는 Junit 테스트 코드를 완성하시오.
	@DisplayName("퀴즈 2번")
	public void findByPriceBetweenTest () {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByPriceBetween(10004,10008);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//regTime이 2023-1-1 12:12:44 이후의 레코드를 구하는 Juinit 테스트 코드를 완성하시오.
	@DisplayName("퀴즈 3번")
	public void findByRegTimeGreaterThanTest () {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByRegTimeGreaterThan(LocalDateTime.of(2023, 1, 1, 12, 12,44,0000));
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@Test
	@DisplayName("퀴즈 4번")
	public void findByItemSellStatusNotTest () {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemSellStatusNot(null);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@DisplayName("퀴즈 4번")
	public void findByItemSellStatusNotTest () {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemSellStatusIsNotNull();
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//itemDetail이 설명1로 끝나는 레코드를 구하는 Junit 테스트 코드를 완성하시오.
	@DisplayName("퀴즈 5번")
	public void findByItemDetailLikeTest () {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetailLike("%1");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	*/
	@Test
	@DisplayName("@Query를 이용한 상품 조회 테스트")
	public void findByItemDetailTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세설명");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@Test
	@DisplayName("@Query를 이용한 상품 조회 테스트")
	public void findByItemDetailTest11() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetail("테스트 상품 상세설명");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	
	@DisplayName("@native Query를 이용한 상품 조회 테스트")
	public void findByItemDetailByNativeTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemDetailByNative("테스트 상품 상세설명");
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	/*
	@DisplayName("퀴즈 1번")
	public void findByItemPriceTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByItemPrice(10005);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@DisplayName("퀴즈 2번")
	public void findByitemNmTest() {
		this.createItemTest();
		List<Item> itemList = itemRepository.findByitemNm("테스트 상품1",ItemSellStatus.SELL);
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	*/
	
	//DSL 사용 준비
	@PersistenceContext //영속성 컨텍스트를 사용하기 위해 선언
	EntityManager em; //엔티티 매니저 가져오기
	
	@DisplayName("querydsl 조회 테스트")
	public void queryDslTest() {
		this.createItemTest();
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 사용하기 위한 객체
		QItem qItem = QItem.item;
		 //select * from item where itemSellStatus = SELL and itemDetail like %테스트 상품상세설명% order by desc;
		JPAQuery<Item> query = qf.selectFrom(qItem)
								 .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
								 .where(qItem.itemDetail.like("%테스트 상품 상세설명"))
								 .orderBy(qItem.price.desc());
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@DisplayName("querydsl 조회 테스트")
	public void queryDslTest3() {
		this.createItemTest2();
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 사용하기 위한 객체
		QItem qItem = QItem.item;
		Pageable page = PageRequest.of(0, 2); //of(조회할 페이지의 번호, 한페이지당 조회할 데이터의 갯수)
		
		 //select * from item where itemSellStatus = SELL and itemDetail like %테스트 상품상세설명% order by desc;
		JPAQuery<Item> query = qf.selectFrom(qItem) 
								 .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL))
								 .where(qItem.itemDetail.like("%테스트 상품 상세설명"))
								 .where(qItem.price.gt(10003))
								 .offset(page.getOffset())
								 .limit(page.getPageSize());
								
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	//검색기능 만들기
//	@DisplayName("querydsl 조회 테스트2")
//	public void queryDslTest2() {
//		this.createItemTest2();
//		BooleanBuilder b = new BooleanBuilder(); //쿼리에 들어갈 조건을 만들어주는 빌더
//		QItem item = QItem.item;
//		String itemDetail = "테스트 상품 상세 설명";
//		int price = 10003;
//		String itemSellStat = "SELL";
//		
//		
//		b.and(item.itemDetail.like("%" + itemDetail + "%"));
//		b.and(item.price.gt(price));  //gt = price > 10003
//		
//		//값만 비교해주는 애
//		if(StringUtils.equals(itemSellStat, ItemSellStatus.SELL)) {
//			b.and(item.itemSellStatus.eq(ItemSellStatus.SELL));
//		}
//		
//		Pageable page = PageRequest.of(0, 5); //of(조회할 페이지의 번호, 한 페이지당 조회할 데이터의 갯수)
//		Page<Item> itemPageResult = itemRepository.findAll(b, page);
//	}
	
	@DisplayName("querydsl 퀴즈 1번")
	public void queryDslQ1Test() {
		this.createItemTest();
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 사용하기 위한 객체
		QItem qItem = QItem.item;
		 //select * from item where itemSellStatus = SELL and itemDetail like %테스트 상품상세설명% order by desc;
		JPAQuery<Item> query = qf.selectFrom(qItem)
								 .where(qItem.itemNm.eq("테스트 상품1"))
								 .where(qItem.itemSellStatus.eq(ItemSellStatus.SELL));
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@DisplayName("querydsl 퀴즈 2번")
	public void queryDslQ2Test() {
		this.createItemTest();
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 사용하기 위한 객체
		QItem qItem = QItem.item;
		 //select * from item where itemSellStatus = SELL and itemDetail like %테스트 상품상세설명% order by desc;
		JPAQuery<Item> query = qf.selectFrom(qItem)
								 .where(qItem.price.between(10004, 10008));
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@DisplayName("querydsl 퀴즈 3번")
	public void queryDslQ3Test() {
		this.createItemTest();
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 사용하기 위한 객체
		QItem qItem = QItem.item;
		 //select * from item where itemSellStatus = SELL and itemDetail like %테스트 상품상세설명% order by desc;
		JPAQuery<Item> query = qf.selectFrom(qItem)
								 .where(qItem.regTime.after(LocalDateTime.of(2023, 1, 1, 12, 12,44,0000)));
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
	@DisplayName("querydsl 퀴즈 4번")
	public void queryDslQ4Test() {
		this.createItemTest();
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 사용하기 위한 객체
		QItem qItem = QItem.item;
		 //select * from item where itemSellStatus = SELL and itemDetail like %테스트 상품상세설명% order by desc;
		JPAQuery<Item> query = qf.selectFrom(qItem)
								 .where(qItem.itemSellStatus.isNotNull());
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	@DisplayName("querydsl 퀴즈 5번")
	public void queryDslQ5Test() {
		this.createItemTest();
		JPAQueryFactory qf = new JPAQueryFactory(em); //쿼리를 동적으로 사용하기 위한 객체
		QItem qItem = QItem.item;
		 //select * from item where itemSellStatus = SELL and itemDetail like %테스트 상품상세설명% order by desc;
		JPAQuery<Item> query = qf.selectFrom(qItem)
								 .where(qItem.itemDetail.like("%설명1"));
		List<Item> itemList = query.fetch();
		
		for (Item item : itemList) {
			System.out.println(item.toString());
		}
	}
	
//	@DisplayName("연습 예제")
//	public void createItemTest00() {
//			Member member = new Member();
//			member.setName("김용수");
//			member.setAge("1");
//			member.setGender("남자");
//			member.setJob("학생");
//	}
//	
}
