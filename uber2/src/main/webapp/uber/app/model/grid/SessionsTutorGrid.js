Ext.define('uber.model.grid.SessionsTutorGrid', {
    extend: 'uber.model.Base',
    alias: 'model.sessionsTutorGrid',
    fields: [
     	{ name: 'createDate', type: 'date', 
     		convert:function(v,record){
        		if (v == null || v == "") {
        			return "";
        		} else {
        			return Ext.Date.format(new Date(v), 'Y-m-d');
        		}
        	}
        },
     	{ name: 'studentName', type: 'string' },
     	{ name: 'category', type: 'string' },
     	{ name: 'subject', type: 'string' },
     	{ name: 'status', type: 'string' }
 	],
});