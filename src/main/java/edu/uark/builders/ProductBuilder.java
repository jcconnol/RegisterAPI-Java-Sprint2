package edu.uark.builders;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;

public class ProductBuilder {
	//Entity products
	public static edu.uark.models.entities.ProductEntity buildProductEntity() {
		return (new edu.uark.models.entities.ProductEntity()).
			setLookupCode(RandomStringUtils.randomAlphanumeric(PRODUCT_LOOKUP_CODE_LENGTH)).
			setCount(MIN_PRODUCT_COUNT + random.nextInt(MAX_PRODUCT_COUNT - MIN_PRODUCT_COUNT));
	}
	
	public static List<edu.uark.models.entities.ProductEntity> buildManyProductEntities() {
		List<edu.uark.models.entities.ProductEntity> products = new LinkedList<edu.uark.models.entities.ProductEntity>();
		int listLength = MIN_PRODUCT_LIST_LENGTH + random.nextInt(MAX_PRODUCT_LIST_LENGTH - MIN_PRODUCT_LIST_LENGTH);
		
		for (int i = 0; i < listLength; i++) {
			products.add(buildProductEntity());
		}
		
		return products;
	}
	
	//Serializable products
	public static edu.uark.models.api.Product buildNewApiProduct() {
		return (new edu.uark.models.api.Product()).
			setLookupCode(RandomStringUtils.randomAlphanumeric(PRODUCT_LOOKUP_CODE_LENGTH)).
			setCount(MIN_PRODUCT_COUNT + random.nextInt(MAX_PRODUCT_COUNT - MIN_PRODUCT_COUNT));
	}
	
	public static List<edu.uark.models.api.Product> buildManyNewApiProducts() {
		List<edu.uark.models.api.Product> products = new LinkedList<edu.uark.models.api.Product>();
		int listLength = MIN_PRODUCT_LIST_LENGTH + random.nextInt(MAX_PRODUCT_LIST_LENGTH - MIN_PRODUCT_LIST_LENGTH);
		
		for (int i = 0; i < listLength; i++) {
			products.add(buildNewApiProduct());
		}
		
		return products;
	}
	
	public static edu.uark.models.api.Product buildApiProduct() {
		return (new edu.uark.models.api.Product()).
			setId(UUID.randomUUID()).
			setLookupCode(RandomStringUtils.randomAlphanumeric(PRODUCT_LOOKUP_CODE_LENGTH)).
			setCount(MIN_PRODUCT_COUNT + random.nextInt(MAX_PRODUCT_COUNT - MIN_PRODUCT_COUNT));
	}
	
	public static edu.uark.models.api.Product buildModifiedApiProduct(edu.uark.models.entities.ProductEntity productEntity) {
		return (new edu.uark.models.api.Product(productEntity)).
			setCount(productEntity.getCount() + 25);
	}
	
	public static List<edu.uark.models.api.Product> buildManyApiProducts() {
		List<edu.uark.models.api.Product> products = new LinkedList<edu.uark.models.api.Product>();
		int listLength = MIN_PRODUCT_LIST_LENGTH + random.nextInt(MAX_PRODUCT_LIST_LENGTH - MIN_PRODUCT_LIST_LENGTH);
		
		for (int i = 0; i < listLength; i++) {
			products.add(buildApiProduct());
		}
		
		return products;
	}
	
	private static Random random;
	
	private static final int MIN_PRODUCT_COUNT = 50;
	private static final int MAX_PRODUCT_COUNT = 500;
	private static final int MIN_PRODUCT_LIST_LENGTH = 2;
	private static final int MAX_PRODUCT_LIST_LENGTH = 10;
	private static final int PRODUCT_LOOKUP_CODE_LENGTH = 20;
	
	static {
		random = new Random(System.currentTimeMillis());
	}
}