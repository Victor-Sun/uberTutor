Ext.define('uber.view.test.TestRequest',{
	extend: 'Ext.panel.Panel',
	xtype: 'testRequest',
	layout: 'border',
	
	controller: 'search',
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
	initComponent: function () {
		var me = this;
		var searchGrid = Ext.create('uber.view.search.SearchGrid');
//		var fullname = Ext.ComponentQuery.query('#userNameItemId')[0].getText();
		var subjectsLoad = Ext.Ajax.request({
			url: '/UberTutor/main/tutor-subject-register!displayUserSubjects.action',
			method: 'GET',
//			params: {
//				fullname: Ext.ComponentQuery.query('#userNameItemId')[0].getText()
//			},
			success: function(response, opts) {
//				debugger;
//				var subjectJSON = response.responseText;
//				var subjectDecode = Ext.util.JSON.decode(response.responseText);
//				var subjectData = subjectDecode.data;
				// for each item in the returned data 
//				for (var i = 0; i < subjectDataItems.length; i++) {
//					var item = subjectData[i].items;
//					//create tab for each subject
////					Ext.create('uber.view.search.SearchGrid',{
////						title: 'item.subjectTitle,
////					});
//				} 
			},
			failure: function(response, opts) {
			}
		});
		
		this.items = [{
			xtype: 'panel',
			flex: 1,
			cls: 'uber-panel-inner',
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			items: [{
				xtype: 'container',
				items: [{
					html: '<h2>Search</h2>'
				}]
			},{
				xtype: 'searchgrid',
				//change to tabpanel and have searchgrid dynamically added in 
//				xtype: 'tabpanel',
//				flex: 1,
//				layout: 'fit',
//				itemId: 'searchTab',
//				items: [{
//					title: 'Subject 1',
//					
//				},{
//					title: 'Subject 2',
//				},{
//					title: 'Subject 3',
//				}]
			}]
		}]
		this.callParent(arguments);
	}
})