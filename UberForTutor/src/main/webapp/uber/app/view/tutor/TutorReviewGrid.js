Ext.define('uber.view.tutor.TutorReviewGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'tutorreviewgrid',
	cls: 'tutor-review-grid',
	layout: 'fit',
	
	store:{
		data: [{
			name: "Nora Watson",
			date: "2015/08/23 19:15:00",
			rating: "4",
			content: "Lorem ipsum dolor sit amet. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
		}],
	},
	initComponent: function() {
		var me = this;
		
		this.columns = [{
			xtype: 'templatecolumn',
			flex: 1,
			tpl: [
				"<div class='review'>" +
					"<div class='content-wrap'>" + //ENTIRE CONTAINER
					    "<div class='information-panel'>" +
					        "<div class='review-row-one'>" +
					        	"<div class='user-panel'>" +
					        		"<span><i class='x-fa fa-user'></i>{name}</span>" +
				        		"</div>" +
				        		"<div class='date-panel'>" +
				        			"<span><i class='x-fa fa-clock-o'></i>{date}</span>" +
					        	"</div>" +
					        	"<div class='rating-panel'>" +
					        		"<span><i class='x-fa fa-star'></i>{rating}/5 </span>" +
				        		"</div>" +
				        	"</div>" +
					    "</div>" +
					    "<div class='comment-panel'>{content}</div>" +
					"</div>" +
				"</div>",
			]
		}]
		this.callParent(arguments);
	}
});