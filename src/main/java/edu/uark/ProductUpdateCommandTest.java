package edu.uark;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.StringUtils;
import org.easymock.EasyMockSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.uark.builders.ProductBuilder;
import edu.uark.commands.products.ProductUpdateCommand;
import edu.uark.controllers.exceptions.NotFoundException;
import edu.uark.controllers.exceptions.UnprocessableEntityException;
import edu.uark.models.api.Product;
import edu.uark.models.entities.ProductEntity;
import edu.uark.models.repositories.interfaces.ProductRepositoryInterface;

public class ProductUpdateCommandTest extends EasyMockSupport {
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() {
		this.existingProductEntity = ProductBuilder.buildProductEntity();
		this.existingProductEntity.save();
	}

	@Test
	public void blankLookupCodeUnprocessable() {
		Product apiProduct = ProductBuilder.buildApiProduct();
		apiProduct.setLookupCode(StringUtils.EMPTY);

		exception.expect(UnprocessableEntityException.class);
		(new ProductUpdateCommand())
			.setProductId(apiProduct.getId())
			.setApiProduct(apiProduct)
			.execute();
	}

	@Test
	public void productNotFound() {
		Product apiProduct = ProductBuilder.buildNewApiProduct();
		ProductRepositoryInterface productRepository = this.createMock(ProductRepositoryInterface.class);
		
		expect(productRepository.get(apiProduct.getId()))
			.andReturn((ProductEntity)null);
		replay(productRepository);
		
		exception.expect(NotFoundException.class);
		(new ProductUpdateCommand())
			.setProductId(apiProduct.getId())
			.setApiProduct(apiProduct)
			.execute();
		verify(productRepository);
	}

	@Test
	public void updateSuccess() {
		Product apiProduct = ProductBuilder.buildModifiedApiProduct(this.existingProductEntity);
		ProductRepositoryInterface productRepository = this.createMock(ProductRepositoryInterface.class);
		
		expect(productRepository.get(apiProduct.getId()))
			.andReturn(this.existingProductEntity);
		replay(productRepository);
		
		Product updatedProductDetails = (new ProductUpdateCommand())
			.setProductId(apiProduct.getId())
			.setApiProduct(apiProduct)
			.setProductRepository(productRepository)
			.execute();
		verify(productRepository);
		
		assertNotNull("Retrieved product is defined", updatedProductDetails);
		assertEquals("Retrieved product record ID is not valid", updatedProductDetails.getId().toString(), this.existingProductEntity.getId().toString());
		assertEquals("Retrieved product count data is not valid", apiProduct.getCount(), updatedProductDetails.getCount());
		assertEquals("Retrieved product lookupcode data is not valid", apiProduct.getLookupCode(), updatedProductDetails.getLookupCode());
	}

	@After
	public void tearDown() {
		this.existingProductEntity.delete();
	}
	
	private ProductEntity existingProductEntity;

}