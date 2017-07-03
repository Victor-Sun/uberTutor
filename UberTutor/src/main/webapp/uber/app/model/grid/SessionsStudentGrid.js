Ext.define('uber.model.grid.SessionsStudentGrid', {
    extend: 'uber.model.Base',
    alias: 'model.sessionsStudentGrid',
    fields: [
     	{ name: 'createDate', type: 'date', 
        	convert:function(v,record){
        		var date = record.data.createDate;
        		if ( date== null || date == "") {
        			return "";
        		} else {
        			return Ext.Date.format(new Date(date), 'Y-m-d');
        		}
        		
        	}
        },
     	{ name: 'tutorName', type: 'string', 
        	convert: function(v, record){
        		if (record.data.tutorName == null) {
        			return "";
        		}
     		} 
        },
     	{ name: 'category', type: 'string' },
     	{ name: 'subject', type: 'string' },
     	{ name: 'status', type: 'string', 
     		convert: function(v, record){
        		if (record.data.status == null) {
        			return "";
        		}
     		} 
     	}
 	],
});