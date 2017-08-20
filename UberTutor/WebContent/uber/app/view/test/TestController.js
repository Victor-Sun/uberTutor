Ext.define('uber.view.test.TestController', {
    extend: 'Ext.app.ViewController',
    alias: 'controller.test',

    onConfirmClick: function (grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
    	debugger;
        Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?', function (btn, text) {
        	if ( btn == 'yes' ) {
        		
        	} else {
        		doNothing;
        	}
        }, this);
    },
    
    detailClick: function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
//    	console.log("RequestId:" + rowIndex.data.requestId);
    	Ext.create('uber.view.session.SessionInfoWindow',{requestId: rowIndex.data.requestId}).show();
    },
});