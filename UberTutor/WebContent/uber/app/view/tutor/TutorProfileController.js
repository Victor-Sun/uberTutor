Ext.define('uber.view.tutor.TutorProfileController',{
	extend: 'Ext.app.ViewController',
    alias: 'controller.tutorprofile',
    
    request: function () {
    	var me = this;
    	var main = me.view.up('app-main');
    	var mainCard = main.lookupReference('mainCardPanel')
    	var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('makerequest');
    },
    
    onCelldblclick: function(grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
    	console.log('working');
    	var me = this;
    	var rec = grid.getStore().getAt(rowIndex);
    	console.log(rec.data);
    	var main = me.view.up('app-main');
    	var mainCard = main.lookupReference('mainCardPanel')
    	var mainLayout = mainCard.getLayout();
        var card = mainCard.setActiveItem('chat');
    }
})