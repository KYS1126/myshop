package com.example.myshop.repositoty;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EnumType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.myshop.constant.ItemSellStatus;
import com.example.myshop.entity.Item;

//DAO의 역할을 한다.
//JpaRepository: 기본적인 CRUD 및 페이지 처리를 위한 메소드가 정의가 되어있다.
//JpaRepository<사용할 엔티티 클래스, 기본키 타입>
public interface ItemRepository extends JpaRepository<Item, Long>{
	//select * from item where item_nm = ?
	List<Item> findByItemNm(String itemNm);
	
	//select * from item where item_nm = ? or item_detail = ?
	List<Item> findByItemNmOrItemDetail(String itemNm, String itemDetail);
	
	//select * from item where price < ?
	List<Item> findByPriceLessThan(Integer price);  
	
	//select * from item where price < ? order by price desc
	List<Item> findByPriceLessThanOrderByPriceDesc(Integer price);
	
	/*
	//itemNm이 “테스트 상품1” 이고 ItemSellStatus가 Sell인 레코드를 구하는 Junit 테스트 코드를 완성하시오.
	List<Item> findByItemNmAndItemSellStatus(String itemNm, ItemSellStatus ItemSellStatus);
	//price가 10004~ 10008 사이인 레코드를 구하는 Junit 테스트 코드를 완성하시오.
	List<Item> findByPriceBetween(Integer Prive, Integer Price);
	//regTime이 2023-1-1 12:12:44 이후의 레코드를 구하는 Juinit 테스트 코드를 완성하시오.
	List<Item> findByRegTimeGreaterThan(LocalDateTime RegTime);
	//itemSellStatus가 null이 아닌 레코드를 구하는 Juinit 테스트 코드를 완성하시오.
	List<Item> findByItemSellStatusIsNotNull();
	//itemDetail이 설명1로 끝나는 레코드를 구하는 Junit 테스트 코드를 완성하시오.
	List<Item> findByItemDetailLike(String title);
	*/
	
//	@Query("select i from 필드  i where i.  필드     ")
	@Query("select i from Item i where i.itemDetail like %:itemDetail% order by i.price desc")
	List<Item> findByItemDetail(@Param("itemDetail") String itemDetail); //변수를 Param의 이름을 지정해서 쿼리문에 대입해준다.
	
	
	//네이티브 쿼리는 엔티티와 맞추는게 아니라 db에 저장된 것과 맞춰야한다. 스네이크로 자동 변환되기 때문에 주의.
	@Query(value = "select * from item i where i.item_detail like %:itemDetail% order by i.price desc", nativeQuery = true)
	List<Item> findByItemDetailByNative(@Param("itemDetail") String itemDetail);
	
	
	/*
	@Query("select i from Item i where i.price >= :price")
	List<Item> findByItemPrice(@Param("price") Integer price);
	@Query("select i from Item i where i.itemNm = :itemNm and i.itemSellStatus = :itemSellStatus")
	List<Item> findByitemNm(@Param("itemNm")String itemNm, @Param("itemSellStatus")ItemSellStatus itemSellStatus);
	*/
	
}
