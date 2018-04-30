package edu.uark;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.uark.builders.ProductBuilder;
import edu.uark.commands.products.ProductCreateCommand;
import edu.uark.controllers.exceptions.ConflictException;
import edu.uark.controllers.exceptions.UnprocessableEntityException;
import edu.uark.models.api.Product;
import edu.uark.models.entities.ProductEntity;
import edu.uark.models.repositories.ProductRepository;
import edu.uark.models.repositories.interfaces.ProductRepositoryInterface;

public class ProductCreateCommandTest extends EasyMockSupport {
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() {
		this.successfullySavedProductId = EMPTY_UUID;
	}

	@Test
	public void blankLookupCodeUnprocessable() {
		Product apiProduct = ProductBuilder.buildNewApiProduct();
		apiProduct.setLookupCode(StringUtils.EMPTY);

		exception.expect(UnprocessableEntityException.class);
		(new ProductCreateCommand())
			.setApiProduct(apiProduct)
			.execute();
	}

	@Test
	public void lookupCodeExistsConflict() {
		Product apiProduct = ProductBuilder.buildNewApiProduct();
		ProductEntity queriedEntity = new ProductEntity(apiProduct);
		ProductRepositoryInterface productRepository = this.createMock(ProductRepositoryInterface.class);
		
		expect(productRepository.byLookupCode(apiProduct.getLookupCode()))
			.andReturn(queriedEntity);
		replay(productRepository);
		
		exception.expect(ConflictException.class);
		(new ProductCreateCommand())
			.setApiProduct(apiProduct)
			.setProductRepository(productRepository)
			.execute();
		verify(productRepository);
	}

	@Test
	public void saveSuccess() {
		Product apiProduct = ProductBuilder.buildNewApiProduct();
		ProductRepositoryInterface productRepository = this.createMock(ProductRepositoryInterface.class);
		
		expect(productRepository.byLookupCode(apiProduct.getLookupCode()))
			.andReturn((ProductEntity)null);
		replay(productRepository);
		
		Product savedProductDetails = (new ProductCreateCommand())
			.setApiProduct(apiProduct)
			.setProductRepository(productRepository)
			.execute();
		verify(productRepository);
		
		assertNotNull("Retrieved product is defined", savedProductDetails);
		assertNotEquals("Record ID is not defined", EMPTY_UUID.toString(), savedProductDetails.getId().toString());
		assertEquals("Retrieved product count data is not valid", apiProduct.getCount(), savedProductDetails.getCount());
		assertEquals("Retrieved product lookupcode data is not valid", apiProduct.getLookupCode(), savedProductDetails.getLookupCode());
		
		this.successfullySavedProductId = savedProductDetails.getId();
	}

	@After
	public void tearDown() {
		if (!EMPTY_UUID.equals(this.successfullySavedProductId)) {
			ProductEntity savedProduct = (new ProductRepository()).get(this.successfullySavedProductId);
			if (savedProduct != null) {
				savedProduct.delete();
			}
		}
	}

	private static final UUID EMPTY_UUID = new UUID(0, 0);
	
	private UUID successfullySavedProductId;

}