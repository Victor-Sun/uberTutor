//Objective of page:
//Showing the student before hiring tutor
//The Bio
//Feedback from other students
//The Categories and Subjects the tutor teaches

Ext.define('uber.view.tutor.TutorProfile',{
	extend: 'Ext.panel.Panel',
	xtype: 'tutorprofile',
	
	layout: {
		type: 'vbox',
		align: 'stretch'
	},
//	cls: 'uber-panel',
	controller: 'tutorprofile',
	items: [{
		xtype: 'panel',
		flex: 1,
//		cls: 'uber-panel-inner',
//		margin: 50,
		layout: {
			type: 'vbox',
			align: 'stretch'
		},
		items: [{
			xtype: 'component',
			html: '<h2>Tutor Profile</h2>'
		},{
			xtype: 'container',
			layout: {
				type: 'hbox',
				algin: 'stretch'
			},
			items: [{
				xtype: 'container',
                margin: 20,
                cls: 'shadow image-container',
                items: [{
                	xtype: 'image',
	                width: 80,
	                height: 80,
                }]
			},{
				xtype: 'container',
				layout: {
            		type: 'vbox',
                },
                items: [{
                	xtype: 'component',
                	html: '<h3>Username</h3>'
                },{
                	xtype: 'container',
                	margin: 5,
                	layout: {
                		type: 'vbox',
                		
                	},
                	items: [{
                		xtype: 'rating',
                		limit: '5',
                		rounding: '0.5',
                		value: '4'
                	},{
                		html: 'rated by xxx people'
                	}]
                }]
			}]
		},{
			xtype: 'container',
			scrollable: 'y',
			flex: 1,
			layout: {
				type: 'vbox',
				align: 'stretch'
			},
			margin: 15,
			items: [{
				xtype: 'container',
				layout: {
					type: 'vbox',
					align: 'stretch'
				},
				items: [{
					xtype: 'textarea',
					labelAlign: 'top',
					readOnly: true,
					fieldLabel: 'Tutor Bio',
					height: 125
				}]
			},{
				//Use grid tree to display category and subjects?
//				border: true,
//				flex: 1,
//				html: 'Category and Subjects taught by tutor'
//				xtype: 'fieldset',
//				title: 'Categories & Subjects',
//				layout: {
//					type: 'vbox',
//					align: 'stretch'
//				},
//				items: [{
//					
//				}]
				xtype: 'grid',
				height: 250,
				columns: [{
					text: 'Category',
					flex: 1
				},{
					text: 'Subject',
					flex: 1
				}]
			},{
//				xtype: 'tutorreviewgrid',
				height: 250,
				border: true,
				html: 'Student Reviews and ratings'
			}]
		}],
//		dockedItems: [{
//			xtype: 'toolbar',
//			dock: 'bottom',
//			items: [{
//				xtype: 'button',
//				text: 'Connect',
//				handler: 'request',
//				
//			}]
//		}]
	}]
});