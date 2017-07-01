Ext.define('uber.model.test.News', {
    extend: 'uber.model.Base',

    fields: [
//        'type',
//        { name: 'date', type: 'date', dateFormat: 'Y-m-d H:i:s' },
		{ name: 'subject', type: 'string' },        
		{ name: 'createDate', type: 'date', convert:function(v,record){
	    		if (v == null) {
	    			return "";
	    		} else {
	    			return Ext.Date.format(new Date(v), 'Y-m-d');
	    		}
    		}
        },
        { name: 'studentName', type: 'string' },
        { name: 'requestTitle', type: 'string' },
        { name: 'subjectDescription', type: 'string' }
    ]
});
