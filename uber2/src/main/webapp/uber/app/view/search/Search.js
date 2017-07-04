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
			id: 'category',
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
                change: function (combo, newValue, oldValue, eOpts) {
                    subjectStore.load({params:{categoryId:newValue}});
                    subject.clearValue();
                }
            }  
		});
		var subject = Ext.create('Ext.form.field.ComboBox',{
			id: 'subject',
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
						html: '<h2>Search for a Tutor</h2>'
					},{
						html: '<ul>' +
						'<li>Select Category and Subject</li>' +
						'<li>Press search to begin tutor search</li>' +
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
					items: [subject,category],
					dockedItems: [{
						xtype: 'toolbar',
						dock: 'bottom',
						items: [{
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