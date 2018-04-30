package edu.uark;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.lang3.StringUtils;
import org.easymock.EasyMockSupport;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.uark.builders.ProductBuilder;
import edu.uark.commands.products.ProductByLookupCodeQuery;
import edu.uark.controllers.exceptions.NotFoundException;
import edu.uark.controllers.exceptions.UnprocessableEntityException;
import edu.uark.models.api.Product;
import edu.uark.models.entities.ProductEntity;
import edu.uark.models.repositories.interfaces.ProductRepositoryInterface;

public class ProductByLookupCodeQueryTest extends EasyMockSupport {
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void blankLookupCodeUnprocessable() {
		exception.expect(UnprocessableEntityException.class);
		(new ProductByLookupCodeQuery())
			.setLookupCode(StringUtils.EMPTY)
			.execute();
	}

	@Test
	public void missingLookupCodeNotFound() {
		String lookupCode = "zzzzzzzzzzzzNotReal1";
		ProductRepositoryInterface productRepository = this.createMock(ProductRepositoryInterface.class);
		
		expect(productRepository.byLookupCode(lookupCode)).andReturn((ProductEntity)null);
		replay(productRepository);
		
		exception.expect(NotFoundException.class);
		Product product = (new ProductByLookupCodeQuery())
			.setLookupCode(lookupCode)
			.setProductRepository(productRepository)
			.execute();
		verify(productRepository);

		assertNotNull("Retrieved product is defined", product);
	}

	@Test
	public void validLookupCodeReturns() {
		ProductEntity queriedEntity = ProductBuilder.buildProductEntity();
		ProductRepositoryInterface productRepository = this.createMock(ProductRepositoryInterface.class);
		
		expect(productRepository.byLookupCode(queriedEntity.getLookupCode()))
			.andReturn(queriedEntity);
		replay(productRepository);
		
		Product product = (new ProductByLookupCodeQuery())
			.setLookupCode(queriedEntity.getLookupCode())
			.setProductRepository(productRepository)
			.execute();
		verify(productRepository);

		assertNotNull("Retrieved product is defined", product);
		assertEquals("Retrieved product count data is not valid", queriedEntity.getCount(), product.getCount());
		assertEquals("Retrieved product lookupcode data is not valid", queriedEntity.getLookupCode(), product.getLookupCode());
	}

}
