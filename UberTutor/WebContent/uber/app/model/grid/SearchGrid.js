Ext.define('uber.model.grid.SearchGrid',{
	extend: 'uber.model.Base',
    alias: 'model.searchGrid',
    fields: [
    	{ name: 'requestTitle', type: 'string' },
    	{ name: 'userFullname', type: 'string' },
    	{ name: 'subjectTitle', type: 'string' },
    	{ name: 'createDate', type: 'date', 
        	convert:function(v,record){
        		var date = record.data.createDate;
        		if ( date== null || date == "") {
        			return "";
        		} else {
        			return Ext.Date.format(new Date(date), 'Y-m-d');
        		}
        		
        	}
        }
	]
});