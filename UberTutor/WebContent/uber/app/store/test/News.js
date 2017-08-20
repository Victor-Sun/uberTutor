Ext.define('uber.store.test.News', {
    extend: 'Ext.data.Store',
    alias: 'store.news',
    autoLoad: true,
    model: 'uber.model.test.News',
    remoteFilter: true,

//    proxy: {
//        type: 'memory',
//        reader: 'array',
//        data: [
//            // id, subject, date, studentName, requestTitle, subjectDescription
//            
//            [ 7,  "algebra", "5/30/2013",  "Denise Lawrence",  "TITLE ONE", "THIS IS A TEST" ],
//            [ 8,  "algebra", "11/26/2012", "Maria Young",      "TITLE TWO", "THIS IS A TEST TWO" ],
//            [ 9,  "algebra", "5/5/2013",   "Jose Dean",        "TITLE THREE", "THIS IS A TEST THREE" ],
//            
//            [ 10,  "calculus", "5/30/2013",  "Denise Lawrence",  "TITLE FOUR", "THIS IS A TEST FOUR" ],
//            [ 11,  "calculus", "11/26/2012", "Maria Young",      "TITLE FIVE", "THIS IS A TEST FIVE" ],
//            [ 12,  "calculus", "5/5/2013",   "Jose Dean",        "TITLE SIX", "THIS IS A TEST SIX" ],
//        ]
//    }

	proxy: {
	    type: 'ajax',
	    url: '/UberTutor/main/my-session!displayTutorSessions.action',
	    reader: {
	        type: 'json',
	        rootProperty: 'data',
	        totalProperty: 'total'
	    },
//	    writer: {
//            allowSingle: false,
//            writeAllFields: true,
//            type: 'json'
//        }
	},
//	listeners: {
//		load: {
//			fn: function (store, records , successful , operation , eOpts )  {
////				console.log(store.data.items);
////				var store = this.getData();
////				var range = this.getRange();
////				console.log(range);
//				var datar = new Array();
//		        var jsonDataEncode = "";
//		        var records = store.getRange();
//		        for (var i = 0; i < records.length; i++) {
//		            datar.push(records[i].data);
//		        }
//		        jsonDataEncode = Ext.util.JSON.encode(datar);
//		        console.log(jsonDataEncode);
//		        return jsonDataEncode;
//		        
//			}
//		}
//	}
});
