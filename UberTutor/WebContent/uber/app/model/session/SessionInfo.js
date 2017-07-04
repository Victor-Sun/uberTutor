Ext.define('uber.model.session.SessionInfo',{
	extend: 'Ext.data.Model',
	fields: [
		{ name: 'CREATE_DATE', type: 'date', convert:function(v,record){
			debugger;
				if (v == null) {
					return "";
				} else {
					return Ext.Date.format(new Date(v), 'Y-m-d');
				}
			}
		},
		{ name: 'UPDATE_DATE', type: 'date', convert:function(v,record){
				if (v == null) {
					return "";
				} else {
					return Ext.Date.format(new Date(v), 'Y-m-d');
				}
			}
		},
		{ name: 'CANCEL_DATE', type: 'date', convert:function(v,record){
				if (v == null) {
					return "";
				} else {
					return Ext.Date.format(new Date(v), 'Y-m-d');
				}
			}
		},
		{ name: 'CLOSE_DATE', type: 'date', convert:function(v,record){
				if (v == null) {
					return "";
				} else {
					return Ext.Date.format(new Date(v), 'Y-m-d');
				}
				
			}
		},
		{ name: 'PENDING_DATE', type: 'date', convert:function(v,record){
				if (v == null) {
					return "";
				} else {
					return Ext.Date.format(new Date(v), 'Y-m-d');
				}
				
			}
		},
		{ name: 'PROCESS_DATE', type: 'date', convert:function(v,record){
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