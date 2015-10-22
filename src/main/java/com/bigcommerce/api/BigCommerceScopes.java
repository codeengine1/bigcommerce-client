package com.bigcommerce.api;

import com.bigcommerce.api.category.Category;
import com.bigcommerce.api.product.Product;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author <a href="mailto:d@davemaple.com">David Maple</a>
 */
public class BigCommerceScopes {

	private static final Logger LOGGER = LoggerFactory.getLogger(BigCommerceScopes.class);
	private final Map<Class<? extends BigCommerceEntity>, Scope> entityScopes;

	public BigCommerceScopes() {
		ImmutableMap.Builder<Class<? extends BigCommerceEntity>, Scope> builder = ImmutableMap.builder();
		builder.put(Product.class, new Scope(Product.class, "products"))
				.put(Category.class, new Scope(Category.class, "categories"));
		entityScopes = builder.build();
	}

	private static class Scope {
		private final Class<? extends BigCommerceEntity> _entityClass;
		private final String _queryScope;

		public Scope(Class<? extends BigCommerceEntity> entityClass, String queryScope) {
			this._entityClass = entityClass;
			this._queryScope = queryScope;
		}

		public Class<? extends BigCommerceEntity> getEntityClass() {
			return _entityClass;
		}

		public String getQueryScope() {
			return _queryScope;
		}
	}

	public String getQueryScope(Class<? extends BigCommerceEntity> entityClass) {
		if (!entityScopes.containsKey(entityClass)) {
			LOGGER.error("BigCommerce Scope %s not found!", entityClass.getSimpleName());
			return null;
		}

		return entityScopes.get(entityClass).getQueryScope();
	}
}
