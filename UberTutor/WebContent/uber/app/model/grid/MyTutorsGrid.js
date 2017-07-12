

Ext.define('uber.model.grid.MyTutorsGrid', {
    extend: 'uber.model.Base',
    alias: 'model.myTutorsGrid',
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
        }
 	],
});