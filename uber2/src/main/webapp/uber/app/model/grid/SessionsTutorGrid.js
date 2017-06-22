Ext.define('uber.model.grid.SessionsTutorGrid', {
    extend: 'uber.model.Base',
    alias: 'model.sessionsTutorGrid',
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
     	{ name: 'STUDENT_NAME', type: 'string' },
     	{ name: 'CATEGORY', type: 'string' },
     	{ name: 'SUBJECT', type: 'string' },
     	{ name: 'STATUS', type: 'string' }
 	],
});