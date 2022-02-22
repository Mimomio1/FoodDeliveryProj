package com.cogent.fooddeliveryapp.utils;

import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.boot.model.naming.PhysicalNamingStrategy;
import org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

public class CustomNamingStrategy extends PhysicalNamingStrategyStandardImpl {

	@Override
	public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment content) {

		String newName = name.getText().concat("_tbl");
		
		return Identifier.toIdentifier(newName);
	}

}
