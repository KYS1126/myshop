package com.example.myshop.repositoty;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import com.example.myshop.DTO.ItemSearchDto;
import com.example.myshop.constant.ItemSellStatus;
import com.example.myshop.entity.Item;
import com.example.myshop.entity.QItem;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;

import groovy.util.OrderBy;

public class ItemRepositoryCustomImpl implements ItemRepositoryCustom{

	private JPAQueryFactory queryFactory;
	
	//의존성 주입 생성자 버젼
	public ItemRepositoryCustomImpl(EntityManager em) {
		this.queryFactory = new JPAQueryFactory(em);
	}
	
	private BooleanExpression regDtsAfter(String searchDateType) {
		LocalDateTime dateTime = LocalDateTime.now();
		
		//현재 날짜로 부터 이전 날짜를 구해준다.
		if(StringUtils.equals("all", searchDateType) || searchDateType == null) return null;
		else if (StringUtils.equals("1d", searchDateType)) dateTime = dateTime.minusDays(1);
		else if (StringUtils.equals("1w", searchDateType)) dateTime = dateTime.minusWeeks(1);
		else if (StringUtils.equals("1m", searchDateType)) dateTime = dateTime.minusMonths(1);
		else if (StringUtils.equals("6m", searchDateType)) dateTime = dateTime.minusMonths(6);
		
		return QItem.item.regTime.after(dateTime); //이후의 시간
	}
	
	private BooleanExpression searchSellStatusEq(ItemSellStatus searchSellsStatus) {
		return searchSellsStatus == null ? null : QItem.item.itemSellStatus.eq(searchSellsStatus);
	}
	
	//매개변수가 두개 있어야함
	private BooleanExpression searchByLike(String searchBy, String searchQuery) {
		if(StringUtils.equals("itemNm", searchBy)) {
			return QItem.item.itemNm.like("%" + searchQuery + "%");  //itemNm LIKE %청바지%
		} else if(StringUtils.equals("createdBy", searchBy)) {
			return QItem.item.createdBy.like("%" + searchQuery + "%");  //createdBy LIKE %test.com%
		}
		
		return null;
	}
	
	@Override
	public Page<Item> getAdminItemPage(ItemSearchDto itemSearchDto, Pageable pageable) {
		List<Item> content = queryFactory
				.selectFrom(QItem.item) //select * from item;
				.where(regDtsAfter(itemSearchDto.getSearchDateType()), //where reg_time = id
						searchSellStatusEq(itemSearchDto.getSearchSellStatus()), // and sell_status = ?
						searchByLike(itemSearchDto.getSearchBy(), itemSearchDto.getSearchQuery())) //and itemNm LIKE %검색어%
				.orderBy(QItem.item.id.desc())
				.offset(pageable.getOffset()) //데이터를 가져올 시작 index
				.limit(pageable.getPageSize()) //한번에 가지고 올 최대 개수
				.fetch();
		
		long total = content.size(); //전체 레코드 갯수
		
		return new PageImpl<>(content, pageable, total);		
	}

	
	
	
	
	
	
	
	
}
