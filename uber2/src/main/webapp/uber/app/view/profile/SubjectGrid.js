Ext.define('uber.view.profile.SubjectGrid',{
	extend: 'Ext.grid.Grid',
	xtype: 'subjectgrid',
	height: 250,
	layout: 'fit',
	columns: [
      { text: 'subject', dataIndex: 'subject', minWidth: 460 }
    ],
	store: {
		fields: ['subject' ],
	    data: [
	        { 'subject': 'Physics' },
	        { 'subject': 'Calculus' },
	        { 'subject': 'Literature' }
	    ]
	}
});