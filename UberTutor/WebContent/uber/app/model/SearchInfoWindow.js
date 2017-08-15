Ext.define('uber.model.SearchInfoWindow',{
	extend: 'uber.model.Base',
	fields: [
		{ name: 'CREATE_DATE', type: 'date', convert:function(v,record){
				if (v == null) {
					return "";
				} else {
					return Ext.Date.format(new Date(v), 'Y-m-d');
				}
			}
		},
		{ name: 'DESCRIPTION '}
     ]
});