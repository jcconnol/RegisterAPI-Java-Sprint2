package edu.uark;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;

import java.util.UUID;

import org.easymock.EasyMockSupport;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import edu.uark.builders.ProductBuilder;
import edu.uark.commands.products.ProductDeleteCommand;
import edu.uark.controllers.exceptions.NotFoundException;
import edu.uark.models.entities.ProductEntity;
import edu.uark.models.repositories.interfaces.ProductRepositoryInterface;

public class ProductDeleteCommandTest extends EasyMockSupport {
	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Test
	public void productNotFound() {
		ProductRepositoryInterface productRepository = this.createMock(ProductRepositoryInterface.class);
		
		expect(productRepository.get(EMPTY_UUID))
			.andReturn((ProductEntity)null);
		replay(productRepository);
		
		exception.expect(NotFoundException.class);
		(new ProductDeleteCommand())
			.setProductId(EMPTY_UUID)
			.execute();
		verify(productRepository);
	}

	@Test
	public void productDeleted() {
		ProductEntity productEntity = ProductBuilder.buildProductEntity();
		ProductRepositoryInterface productRepository = this.createMock(ProductRepositoryInterface.class);
		
		productEntity.save();
		
		expect(productRepository.get(productEntity.getId()))
			.andReturn(productEntity);
		replay(productRepository);
		
		(new ProductDeleteCommand())
			.setProductId(productEntity.getId())
			.setProductRepository(productRepository)
			.execute();
		verify(productRepository);
	}

	private static final UUID EMPTY_UUID = new UUID(0, 0);

}