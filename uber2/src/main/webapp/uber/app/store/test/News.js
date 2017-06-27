Ext.define('uber.store.test.News', {
    extend: 'Ext.data.Store',
    alias: 'store.news',

    model: 'uber.model.test.News',
    remoteFilter: true,

//    proxy: {
//        type: 'memory',
//        reader: 'array',
//        data: [
//            // id, type, date, time, author, image, title, paragraph
//            
//            [ 7,  "algebra", "5/30/2013",  "10:00 PM", "Denise Lawrence",  "photo-2.png", "TITLE ONE", "THIS IS A TEST" ],
//            [ 8,  "algebra", "11/26/2012", "09:09 AM", "Maria Young",      "photo-3.png", "TITLE TWO", "THIS IS A TEST TWO" ],
//            [ 9,  "algebra", "5/5/2013",   "11:23 PM", "Jose Dean",        "photo-2.png", "TITLE THREE", "THIS IS A TEST THREE" ],
//            
//            [ 10,  "calculus", "5/30/2013",  "10:00 PM", "Denise Lawrence",  "photo-2.png", "TITLE FOUR", "THIS IS A TEST FOUR" ],
//            [ 11,  "calculus", "11/26/2012", "09:09 AM", "Maria Young",      "photo-3.png", "TITLE FIVE", "THIS IS A TEST FIVE" ],
//            [ 12,  "calculus", "5/5/2013",   "11:23 PM", "Jose Dean",        "photo-2.png", "TITLE SIX", "THIS IS A TEST SIX" ],
//        ]
//    }

	proxy: {
	    type: 'ajax',
	    url: '/uber2/main/my-session!displayTutorSessions.action',
	    reader: {
	        type: 'json',
	        rootProperty: 'data',
	        totalProperty: 'total'
	    }
	}
});
