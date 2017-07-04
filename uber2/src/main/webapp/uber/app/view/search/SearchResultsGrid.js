Ext.define('uber.view.search.SearchResultsGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'searchresultsgrid',
	
	flex: 1,
//	layout: 'fit',
	store: {
		fields: [ 'name', 'signupDate', 'rating', 'sucessfulRequests'],
		data: [
		     { name: 'Charles', signupDate: '6/7/2009', rating: '4', successfulRequests: '20'},
		     { name: 'Phillip', signupDate: '5/9/2005', rating: '4.5', successfulRequests: '45'}
       ]
	},
	columns: [{
		//displays :Name, Rating, Member Since, Successful Requests
		xtype: 'templatecolumn',
		align: 'left',
		flex: 1,
		tpl: [
			"<div class='session'>" +
			"<div class='session-frame' style='display: inline-block;'>" +
				"<div class='session-left' style='display: inline; float: left;'>" +
					"<ul style='list-style-type: none;'>" +
						"<li>Name: {name} </li>" +
						"<li>Member Since: {signupDate} </li>" +
					"</ul>" +
				"</div>" +
				"<div class='session-right' style='display: inline; float: right;'>" +
					"<ul style='list-style-type: none;'>" +
						"<li>Rating: {rating}/10</li>" +
						"<li>Successful Requests: {successfulRequests} </li>" +
					"</ul>" +
				"</div>" +
			"</div>" +
			"</div>",
	      ]
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
//		},{
//			iconCls: 'x-fa fa-envelope-o',
//			margin: 5,
//			tooltip: 'Request',
//			handler: function() {
//				Ext.create('Ext.window.Window',{
//					header: false,
//					width: 900,
//					height: 700,
//					items: [{
//						xtype: 'makerequest'
//					}],
//					dockedItems: [{
//						xtype: 'toolbar',
//						dock: 'bottom',
//						items: [{
//							xtype: 'button',
//							text: 'Submit',
////							handler: function () {
////								var this = me;
////								var window = this.up('window');
////								var main = this.view.up('app-main');
////								var mainCard = me.lookupReference('mainCardPanel')
////						        var mainLayout = mainCard.getLayout();
////						        var card = mainCard.setActiveItem('tutorregistration');
////							
////							}
//						},'->',{
//							xtype: 'button',
//							text: 'Cancel',
//							handler: function () {
//								var window = this.up('window');
//								window.close();
//							}
//						}]
//					}]
//				}).show();
//			}
//		},{
//			iconCls: 'x-fa fa-adjust',
//			margin: 5,
//			tooltip: 'Tutor Profile Test',
//			handler: function () {
//				var me = this;
//				var tabpanel = me.up('tabpanel');
//		    	var profile = Ext.create({
//		    		xtype: 'tutorprofile',
//		    		scrollable: 'y',
//		    		closable: true,
//		    		title: 'Tutor Profile',
//		    		dockedItems: [{
//		    			xtype: 'toolbar',
//		    			dock: 'top',
//		    			items: [{
//		    				xtype: 'button',
//		    				text: 'Request',
//		    				handler: function () {
//		    					Ext.create('Ext.window.Window',{
//		    						header: false,
//		    						width: 900,
//		    						height: 700,
//		    						layout: 'fit',
//		    						items: [{
//		    							xtype: 'makerequest',
//		    							title: 'Tutor Request'
//		    						}],
//		    						dockedItems: [{
//		    							xtype: 'toolbar',
//		    							dock: 'bottom',
//		    							items: [{
//		    								xtype: 'button',
//		    								text: 'Submit',
//		    								handler: function () {
//		    									var window = this.up('window');
//		    									window.close();
//		    								}
//		    							},'->',{
//		    								xtype: 'button',
//		    								text: 'Close',
//		    								handler: function () {
//		    									var window = this.up('window');
//		    									window.close();
//		    								}
//		    							}]
//		    						}]
//		    					}).show();
//		    				}
//		    			},'->',{
//		    				xtype: 'button',
//		    				text: 'Close',
//		    				handler: function () {
//		    					var tab = this.up('tutorprofile');
//		    					tab.close();
//		    				}
//		    			}]
//		    		}]
//		    	});
//		    	tabpanel.add(profile);
//		    	tabpanel.setActiveTab(profile);
//			}
		}]
	}],
	listeners: {
		celldblclick: 'onCelldblclick'
	}
});