Ext.define('uber.view.test.TestRequestGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'testRequestGrid',
	itemId: 'testRequestGrid',
	layout: 'fit',
	requires: [
        'Ext.grid.filters.Filters',
        'Ext.grid.plugin.RowExpander'
    ],
	itemId: 'testRequestGrid',
//	emptyText: 'No Requests to display',
	flex: 1,
	multiColumnSort: true,
	columnLines: true,
    store: {
//    	model: 'uber.model.grid.SearchGrid',
    	fields: [
    		{ name: 'createDate', type: 'date', 
            	convert:function(v,record){
            		var date = record.data.createDate;
            		if ( date== null || date == "") {
            			return "";
            		} else {
            			return Ext.Date.format(new Date(date), "Y-m-d, g:i a");
            		}
            		
            	}
            }
    	],
    	sorters: [{
            property: 'createDate',
            direction: 'ASC'
        }],
    	data: [{
    		averageRating : null, 
    		createDate : 1500382705988, 
    		requestId : 22, 
    		requestTitle : "TEST 1", 
    		status : "OPEN", 
    		subjectId : 1, 
    		subjectTitle : "Algebra",  
    		tutorId : 2, 
    		userFullname : "student", 
    		userId : 3,
    		averageRating: 4,
    		subjectDescription: " HELLO THIS IS A TEST"
    	},{
    		averageRating : null, 
    		createDate : 1500382705988, 
    		requestId : 22, 
    		requestTitle : "TEST 1", 
    		status : "CANCELED", 
    		subjectId : 1, 
    		subjectTitle : "Algebra",  
    		tutorId : 2, 
    		userFullname : "student", 
    		userId : 3,
    		averageRating: 4,
    		subjectDescription: " HELLO THIS IS A TEST"
    	},{
    		averageRating : null, 
    		createDate : 1500382705988, 
    		requestId : 22, 
    		requestTitle : "TEST 2", 
    		status : "OPEN", 
    		subjectId : 1, 
    		subjectTitle : "Algebra",  
    		tutorId : 2, 
    		userFullname : "student", 
    		userId : 3,
    		averageRating: 4,
    		subjectDescription: " HELLO THIS IS A TEST"
    	},{
    		averageRating : null, 
    		createDate : 1500382705988, 
    		requestId : 22, 
    		requestTitle : "TEST 2", 
    		status : "OPEN", 
    		subjectId : 1, 
    		subjectTitle : "Calculus",  
    		tutorId : 2, 
    		userFullname : "student", 
    		userId : 3,
    		averageRating: 4,
    		subjectDescription: " HELLO THIS IS A TEST"
    	}] 
    },
	initComponent: function () {
		var me = this;
		var pluginExpanded = true;
//		store = Ext.create('uber.store.grid.SearchGrid');
		var page = 5;
		Ext.apply(this, {
			plugins: [{
				ptype: 'preview',
		        bodyField: 'subjectDescription',
		        expanded: pluginExpanded,
		        pluginId: 'preview'
		    },{
		    	ptype: 'gridfilters'
		    }],
		    columns: [{
				text: 'Title',
				dataIndex: 'requestTitle',
				align: 'left',
				flex: 2
			},{
				text: 'Student',
				dataIndex: 'userFullname',
				align: 'left',
				flex: 1,
//				filter: 'string'
			},{
				text: 'Category',
				dataIndex: 'categoryTitle',
				align: 'left',
				flex: 1,
				filter: 'string'
				
			},{
				text: 'Subject',
				dataIndex: 'subjectTitle',
				align: 'left',
				flex: 1,
				filter: 'string'
			},{
				text: 'Date',
				xtype: 'datecolumn',
				dataIndex: 'createDate',
				align: 'left',
				flex: 1,
				filter: true
			},{
				text: 'Average Rating',
				dataIndex: 'averageRating',
				align: 'left',
				flex: 1,
				filter: 'number'
			},{
				xtype: 'actioncolumn',
//				width: '150',
				items: [{
					xtype: 'button',
					iconCls: 'x-fa fa-info-circle',
					tooltip: 'detail',
					handler: function (gridview, rowIndex, colIndex, item, e, record, row) {
						Ext.create('uber.view.search.SearchInfoWindow',{requestId:record.data.requestId}).show();
					}
				},{
					xtype: 'button',
					iconCls: 'x-fa fa-check-circle',
					tooltip: 'accept',
					handler: function (grid, td, cellIndex, record, tr, rowIndex, e, eOpts) {
				    	var requestId = rowIndex.data.requestId;
				        Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?', function (btn, text) {
				        	if ( btn == 'yes' ) {
				        		Ext.Ajax.request({
				        			url: '/UberTutor/main/my-session!updateRequestToInProcess.action',
									params: {
										requestId:requestId,
									},
				            		scope: me,
				            		success: function (form, action, response) {
					    				Ext.getBody().unmask();
					    				window.close();
					    				grid.getStore().load();
					    			},
					    			failure: function (form, action, response) {
					    		    	var me = this;
					    		    	Ext.getBody().unmask();
					    		        var result = uber.util.Util.decodeJSON(action.response.responseText);
					    		        Ext.Msg.alert('Error', result.data, Ext.emptyFn);
					    			},
				            	});
				        	} else {
				        		doNothing;
				        	}
				        }, this);
				    },
				}]
			}],
			dockedItems: [{
				xtype: 'form',
				dock: 'top',
				items: [{
					xtype: 'toolbar',
					dock: 'top',
					defaults: {
						labelWidth: 75,
					},
					items: [{
						xtype: 'component',
						html: '<b>Filters:</b>'
					},' ',{
						xtype: 'combobox',
						fieldLabel: 'Category'
					},{
						xtype: 'combobox',
						fieldLabel: 'Subject'
					},{
						xtype: 'numberfield',
						fieldLabel: 'Rating'
					}]
//				},{
//					xtype: 'toolbar',
//					dock: 'top',
//					items: [{
//						xtype: 'component',
//						html: '<b>Sorting:</b>'
//					},' ',{
//						xtype: 'checkbox',
////						labelAlign: 'right',
//						boxLabel: 'Date'
//					},{
//						xtype: 'checkbox',
////						labelAlign: 'right',
//						boxLabel: 'Rating'
//					},'-',{
//						xtype: 'combobox',
//						store: {
//							fields: ['abbr','sort'],
//							data: [{'abbr': 'ASC','sort':'Ascending'},{'abbr': 'DESC','sort':'Descending'}]
//						},
//						displayField: 'sort',
//						valueField: 'abbr',
//						fieldLabel: 'Direction'
//					}]
				},{
					xtype: 'toolbar',
					dock: 'top',
					items: ['->',{
						xtype: 'button',
						text: 'Apply',
//						handler: function () {
//							var me = this;
//							Ext.getBody().mask('Validating... Please Wait...');
//							var form = me.view.down('toolbar').down('form').getForm();
//							var store = Ext.create(
//									);
////							store.getProxy().extraParams = 
//							
//						}
					},{
						xtype: 'button',
						text: 'Reset',
						handler: function () {
							var me = this;
							var form = me.up('toolbar').up('form').getForm();
							form.reset();
						}
//					},{
//						xtype: 'button',
//						text: 'show filter/sort menu',
//						handler: function () {
//							debugger;
//							var grid = Ext.ComponentQuery.query('#testRequestGrid')[0];
//							  // assuming that we need to expand the first column's menu
//							var column = grid.columns[0];
//							var hc = grid.view.headerCt;
//							hc.showMenuBy(column.el.dom, column)
//						}
					}]
				}]
			}],
			bbar: Ext.create('Ext.PagingToolbar', {
				store: me.store,
                displayInfo: true,
                displayMsg: 'Displaying requests {0} - {1} of {2}',
                emptyMsg: "No requests to display",
                items:[
                    '-', {
                    text: pluginExpanded ? 'Hide Preview' : 'Show Preview',
                    pressed: pluginExpanded,
                    enableToggle: true,
                    toggleHandler: function(btn, pressed) {
                        btn.up('grid').getPlugin('preview').toggleExpanded(pressed);
                        btn.setText(pressed ? 'Hide Preview' : 'Show Preview');
                    }
                }]
            }),
//            bbar: {
//            	items: [{
//            		xtype: 'component'
//            	}]
//            }
		});
		this.callParent();
	}, 
	afterRender: function(){
//		var menu = this.headerCt.getMenu();
//		menu.showMenuBy(column.el.dom, column);
        this.callParent(arguments);
        this.getStore().loadPage(1);
    },
//	listeners: {
//		celldblclick: function (gridview, rowIndex, colIndex, item, e, record, row) {
//			Ext.create('uber.view.search.SearchInfoWindow',{requestId:item.data.requestId}).show();
//		},
//	}
});