package com.maison.mabs.sotd.infrastructure.config.datasource;

import org.jspecify.annotations.Nullable;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.concurrent.atomic.AtomicBoolean;

public class ReadWriteRoutingDataSource extends AbstractRoutingDataSource {

	private final AtomicBoolean replicaHealth = new AtomicBoolean(true);

	@Override
	protected @Nullable Object determineCurrentLookupKey() {
		boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
		if (isReadOnly && this.replicaHealth.get()) {
			return DataSourceType.REPLICA;
		}
		return DataSourceType.PRIMARY;
	}

	public enum DataSourceType {

		/**
		 * Represents the primary data source type. Typically used for transactional write
		 * operations or read-write operations where the primary database is the default
		 * target.
		 */
		PRIMARY,

		/**
		 * Represents the replica data source type. Commonly used for read-only operations
		 * to distribute load and reduce stress on the primary database. By routing read
		 * operations to the replica, the application can achieve better scalability and
		 * performance in handling database queries.
		 */
		REPLICA

	}

	public boolean isReplicationDown() {
		return !this.replicaHealth.get();
	}

	public void setReplicationHealth(boolean isHealthy) {
		// Todo: Add replication health job.
		this.replicaHealth.set(isHealthy);
	}

}
