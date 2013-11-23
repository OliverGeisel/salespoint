/*
 * Copyright 2013 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.salespointframework.spring.converter;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;
import org.salespointframework.AbstractIntegrationTests;
import org.salespointframework.core.catalog.Catalog;
import org.salespointframework.core.catalog.Product;
import org.salespointframework.core.money.Money;
import org.salespointframework.core.quantity.Units;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for {@link JpaEntityConverter}.
 *
 * @author Oliver Gierke
 */
public class JpaEntityConverterIntegrationTests extends AbstractIntegrationTests {

	@Autowired JpaEntityConverter converter;
	@Autowired Catalog catalog;
	
	@Test
	public void convertsStringIdToProduct() {
		
		Product product = new Product("iPad", new Money(400), Units.METRIC);
		String identifier = product.getIdentifier().getIdentifier();
		
		catalog.add(product);
		
		Object result = converter.convert(identifier, TypeDescriptor.valueOf(String.class), TypeDescriptor.valueOf(Product.class));
		assertThat(result, is((Object) product));
	}
}