package ru.kirsanov.mdbo.metamodel.constraint;

import ru.kirsanov.mdbo.metamodel.entity.Table;

public class PrimaryKey extends AbstractConstraint implements Constraint{
	private String name;

	public PrimaryKey(Table table, String name) {
		super( table, null );
	}

	@Override
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
