Ext.define('uber.view.tutor.TutorReviewGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'tutorreviewgrid',
	layout: 'fit',
	
//	viewModel: {
//		type: 'tutorreviewgrid'
//	},
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
			xtype: 'gridcolumn',
//			dataIndex: 'data',
			flex: 1,
			tpl: [
				//'<div class="line-wrap">' +
				//'<div class="contents-wrap">' +
				//    '<div class="shared-by"></div>' +
					
				//    '<div>{content}</div>' +
				//'</div>' +
				//'</div>' ,
				//"<span class='from-now'>" + record.data.createDate + "</span>" +
				//
				"<div class='review'>" +
				"<div class='content-wrap'>" + //ENTIRE CONTAINER
				    "<div class='information-panel'>" +
				        "<div><div class='user-panel'><span class='x-fa fa-user'></span>{username}</div><div class='date-panel'><span class='x-fa fa-clock-o'></span>{date}</div></div>" +
				        "<div class='rating-panel'>{rating}/5 </div>" +
				    "</div>" +
				    "<div class='comment-panel'>{comment}</div>" +
				"</div>" +
				"</div>",
			]
		}]
		this.callParent();
	}
});