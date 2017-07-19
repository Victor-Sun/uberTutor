Ext.define('uber.model.grid.TutorRegistrationGrid', {
    extend: 'uber.model.Base',
    alias: 'model.tutorRegistrationGrid',
    
    fields: [
//        // the 'name' below matches the tag name to read
        { name: 'categoryTitle'},
        { name: 'subjectTitle'},
        { name: 'createDate', type: 'date', 
        	convert:function(v,record){
        		if (v == null || v == "") {
        			return "";
        		} else {
        			return Ext.Date.format(new Date(v), 'Y-m-d');
        		}
        		
        	}
        },
		{ name: 'description '}
    ],
});