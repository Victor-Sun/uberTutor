Ext.define('uber.model.grid.TutorRegistrationGrid', {
    extend: 'uber.model.Base',
    alias: 'model.tutorRegistrationGrid',
    
    fields: [
//        // the 'name' below matches the tag name to read
        { name: 'CATEGORY_TITLE'},
        { name: 'SUBJECT_TITLE'},
        { name: 'CREATE_DATE', type: 'date', 
        	convert:function(v,record){
        		if (v == null || v == "") {
        			return "";
        		} else {
        			return Ext.Date.format(new Date(v.time), 'Y-m-d');
        		}
        		
        	}
        },
		{ name: 'DESCRIPTION '}
    ],
});