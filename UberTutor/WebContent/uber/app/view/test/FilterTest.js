Ext.define('uber.view.test.FilterTest',{
	extend: 'Ext.panel.Panel',
	
	initComponent: function () {
		var me = this;
		this.items = [{
			xtype: 'form',
			items: [{
				
			}]
		},{
			xtype: 'grid',
			columns: [{
				xtype: 'templatecolumn',
				tpl: [
		        '<div class="text-wrapper">' +
//				            '<div class="news-icon {type}">&nbsp;</div>' +
		            '<div class="news-data">' +
//				                '<div class="news-picture"><img src="resources/icons/{image}"></div>' +
		                '<div class="news-content">' +
		                    '<div class="news-title">{requestTitle}</div>' +
		                    '<div class="news-small">by <span class="news-author">{studentName}</span>' +
		                    '<span class="x-fa fa-calendar"></span>{createDate}' +
		                    '<span class="x-fa fa-book"></span>{subject}</div>' +
		                    '<div class="news-paragraph news-paragraph-simple" {expanded}>{subjectDescription:ellipsis(130, true)}</div>' +
//				                    '<div class="news-toggle expand" {expanded}><span>EXPAND</span>' +
//				                    '<img src="resources/icons/expand-news.png"></div>' +
		                '</div>' +
		            '</div>' +
		        '<div>',
		        ]
			}]
		}];
		this.callParent(arguments);
	}
});