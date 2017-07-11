Ext.define('uber.view.search.Search',{
	extend: 'Ext.panel.Panel',
	xtype: 'search',
	
	controller: 'search',
	
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
	cls: 'uber-panel',
	initComponent: function() {
		var categoryStore = Ext.create('uber.store.category.Category');
		var subjectStore = Ext.create('uber.store.subject.Subject');
		
		categoryStore.load();
		
		var category = Ext.create('Ext.form.field.ComboBox',{
			itemId: 'category',
			fieldLabel: 'Category',
            labelAlign: 'top',
            triggerAction: 'all',
            store: categoryStore,
            displayField: 'title',
            valueField: 'id',
            queryModel: 'local',
            editable:false,
            name: 'category',
            listeners: {
//            	click: function () {
//            		categoryStore.load();
//            	},
                change: function (combo, newValue, oldValue, eOpts) {
                    subjectStore.load({params:{categoryId:newValue}});
                    subject.clearValue();
                }
            }  
		});
		var subject = Ext.create('Ext.form.field.ComboBox',{
			itemId: 'subject',
			fieldLabel: 'Subject',
            labelAlign: 'top',
            triggerAction: 'all',
            store: subjectStore,
            displayField: 'title',
            valueField: 'id',
            queryMode:'local',
            editable:false,
            name:'subject'
		});
		
		this.items = [{
			xtype: 'panel',
			cls: 'uber-panel-inner',
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			items: [{
				xtype: 'container',
				flex: 1,
				cls: 'search-container',
				layout: {
					type: 'vbox',
					align: 'stretch'
				},
				defaults: {
					margin: 5,
				},
				items: [{
					xtype: 'container',
					items: [{
						html: '<h2>Search</h2>'
					},{
						html: '<ul>' +
						'<li>Select Category and Subject</li>' +
						'<li>Press search to begin search</li>' +
						'</ul>',
					}]
				},{
					xtype: 'form',
					layout: 'hbox',
					defaults: {
//						xtype: 'combobox',
//						labelAlign: 'top',
//					 	flex: 1,
						margin: 5,
//						anchor: '100%'
					},
					items: [category,subject],
					dockedItems: [{
						xtype: 'toolbar',
						dock: 'bottom',
						items: [{
							// onclick: sends subjectId/userId to backend
							xtype: 'button',
							text: 'search',
							handler: 'searchresults'
						}]
					}]
				}]
			}]
		}];
		this.callParent(arguments);
	}
});