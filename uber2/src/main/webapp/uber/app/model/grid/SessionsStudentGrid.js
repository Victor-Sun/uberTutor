Ext.define('uber.model.grid.SessionsStudentGrid', {
    extend: 'uber.model.Base',
    alias: 'model.sessionsStudentGrid',
    fields: [
     	{ name: 'CREATE_DATE', type: 'date', 
        	convert:function(v,record){
        		if (v == null) {
        			return "";
        		} else {
        			return Ext.Date.format(new Date(v), 'Y-m-d');
        		}
        		
        	}
        },
     	{ name: 'TUTOR_NAME', type: 'string', 
        	convert: function(v, record){
        		if (v == null) {
        			return "";
        		}
     		} 
        },
     	{ name: 'CATEGORY', type: 'string' },
     	{ name: 'SUBJECT', type: 'string' },
     	{ name: 'STATUS', type: 'string', 
     		convert: function(v, record){
        		if (v == null) {
        			return "";
        		}
     		} 
     	}
 	],
});