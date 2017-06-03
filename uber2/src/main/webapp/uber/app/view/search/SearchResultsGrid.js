Ext.define('uber.view.search.SearchResultsGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'searchresultgrid',
	
	flex: 1,
//	layout: 'fit',
	store: {
		fields: [ 'name'],
		data: [
		     { name: 'Charles'},
		     { name: 'Phillip'}
       ]
	},
	columns: [{
		text: 'Name',
		dataIndex: 'name',
		flex: 1
	},{
		text: 'Second Column',
		flex: 1
	},{
		xtype: 'actioncolumn',
		items: [{
			iconCls: 'x-fa fa-user',
			margin: 5,
			tooltip: 'Details',
			handler: function() {
				Ext.create('Ext.window.Window',{
					header: false,
					width: 900,
					height: 700,
					layout: 'fit',
					items: [{
						xtype: 'tutorprofile',
						title: 'Profile'
					}],
					dockedItems: [{
						xtype: 'toolbar',
						dock: 'bottom',
						items: [{
							xtype: 'button',
							text: 'Close',
							handler: function () {
								var window = this.up('window');
								window.close();
							}
						}]
					}]
				}).show();
			}
		},{
			iconCls: 'x-fa fa-envelope-o',
			margin: 5,
			tooltip: 'Request',
			handler: function() {
				Ext.create('Ext.window.Window',{
					header: false,
					width: 900,
					height: 700,
					items: [{
						xtype: 'makerequest'
					}],
					dockedItems: [{
						xtype: 'toolbar',
						dock: 'bottom',
						items: [{
							xtype: 'button',
							text: 'Submit',
//							handler: function () {
//								
//								var this = me;
//								var window = this.up('window');
//								
//								var main = this.view.up('app-main');
//								var mainCard = me.lookupReference('mainCardPanel')
//						        var mainLayout = mainCard.getLayout();
//						        var card = mainCard.setActiveItem('tutorregistration');
//							
//							}
						},'->',{
							xtype: 'button',
							text: 'Cancel',
							handler: function () {
								var window = this.up('window');
								window.close();
							}
						}]
					}]
				}).show();
			}
		}]
	}],
	
//	listeners: {
//		celldblclick: 'onCelldblclick'
//	}
});