package com.gnomon.intergration.vault.jcr;

import javax.jcr.Value;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import javax.jcr.nodetype.NodeTypeDefinition;
import javax.jcr.nodetype.PropertyDefinition;

public class DocumentNodeTypeDefinition implements NodeTypeDefinition {

	@Override
	public String getName() {
		return "gt:document";
	}

	@Override
	public String[] getDeclaredSupertypeNames() {
		return new String[]{"nt:file"};
	}

	@Override
	public boolean isAbstract() {
		return false;
	}

	@Override
	public boolean isMixin() {
		return false;
	}

	@Override
	public boolean hasOrderableChildNodes() {
		return false;
	}

	@Override
	public boolean isQueryable() {
		return true;
	}

	@Override
	public String getPrimaryItemName() {
		return null;
	}

	@Override
	public PropertyDefinition[] getDeclaredPropertyDefinitions() {
		// TODO Auto-generated method stub
		return new PropertyDefinition[]{new PropertyDefinition(){

			@Override
			public NodeType getDeclaringNodeType() {
//				NodeType nt = new NodeType(){};
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getName() {
				return "title";
			}

			@Override
			public boolean isAutoCreated() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isMandatory() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public int getOnParentVersion() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public boolean isProtected() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public int getRequiredType() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public String[] getValueConstraints() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public Value[] getDefaultValues() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isMultiple() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public String[] getAvailableQueryOperators() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public boolean isFullTextSearchable() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public boolean isQueryOrderable() {
				// TODO Auto-generated method stub
				return false;
			}}};
	}

	@Override
	public NodeDefinition[] getDeclaredChildNodeDefinitions() {
		// TODO Auto-generated method stub
		return null;
	}

}
