Ext.define('uber.model.grid.TutorRegistrationGrid', {
    extend: 'uber.model.Base',
    alias: 'model.tutorRegistrationGrid',
    
    fields: [
//        // the 'name' below matches the tag name to read
        { name: 'CATEGORY_TITLE'},
        { name: 'SUBJECT_TITLE'},
        { name: 'CREATE_DATE', type: 'date', 
        	convert:function(v,record){
        		return Ext.Date.format(new Date(v.time), 'Y-m-d');
        	}
        },
		{ name: 'DESCRIPTION '}
    ],
//    proxy: {
//    	reader: {
//    		type: 'json',
//    		getData:function(data){
//    			debugger;
//                for(i = 0; i < data.length; i++){
//                    data[i].CREATE_DATE.time = Ext.Date.format(new Date(data[i].CREATE_DATE.time), "Y-m-d");
//                }
//                return data;
//            }
//    	}
});