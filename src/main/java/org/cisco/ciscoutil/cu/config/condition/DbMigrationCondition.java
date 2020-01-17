package org.cisco.ciscoutil.cu.config.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class DbMigrationCondition implements Condition {

	private static Logger LOGGER = LoggerFactory.getLogger(JavaMelodyConfigurationCondition.class);

	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		LOGGER.info("DbMigrationCondition called");
		Environment environment = context.getEnvironment();
		String prop = environment.getProperty("db.migration.enabled");
		boolean result = false;
		if (null != prop && prop.equals("true")) {
			result = true;
		}
		return result;
		
	}

}
