Ext.define('uber.view.tutor.TutorReviewGrid',{
	extend: 'Ext.grid.Panel',
	xtype: 'tutorreviewgrid',
	layout: 'fit',
	itemTpl: [
		'<div class="line-wrap">' +
			'<div class="contents-wrap">' +
			    '<div class="shared-by"><a href="#">Lorem ipsum dolor sit amet</a></div>' +
			    '<div>{content}</div>' +
			'</div>' +
		'</div>' ,
	      
		
		"<div class='review sub-comments'>" +
        "<div class='content-wrap'>" +
            "<div>" +
                "<span class='from-now'><span class='x-fa fa-clock-o'></span>" + record.data.createDate + "</span>" +
                "<h4>User:" + name + "</h4>" +
                "<h4>Rating:" + rating + "/5 </h4>" +
            "</div>" +
            "<div class='content'>Comment:" + comment + "</div>" +
            "<div class='like-comment-btn-wrap'></div>" +
            "</div>" +
        "</div>";
	          ]
	
});