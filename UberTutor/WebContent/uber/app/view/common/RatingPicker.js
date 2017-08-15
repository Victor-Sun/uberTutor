Ext.define('uber.view.common.RatingPicker',{
	extend: 'Ext.ux.rating.Picker',
	xtype: 'ratingPicker',
	
//	initComponent: function() {
//        Ext.apply(this, {
//        	
//        });
//        this.callParent(arguments);
//    },
    
    onClick: function (event) {
    	var feedBackCheck = Ext.ComponentQuery.query('#hasFeedback')[0];
    	if (feedBackCheck.getValue() != "true") {
    		var value = this.valueFromEvent(event);
            this.setValue(value);
		} else {
			return false;
		}
    },
    
	onMouseEnter: function () {
		var feedBackCheck = Ext.ComponentQuery.query('#hasFeedback')[0];
		if (feedBackCheck.getValue() != "true") {
			this.element.addCls(this.overCls);
		} else {
			return false;
		}
	},
	
	onMouseLeave: function () {
		var feedBackCheck = Ext.ComponentQuery.query('#hasFeedback')[0];
		if (feedBackCheck.getValue() != "true") {
			this.element.removeCls(this.overCls);
		} else {
			return false;
		}
        
    },
 
    onMouseMove: function (event) {
    	var feedBackCheck = Ext.ComponentQuery.query('#hasFeedback')[0];
		if (feedBackCheck.getValue() != "true") {
			var value = this.valueFromEvent(event);
	        this.setTrackingValue(value);
		} else {
			return false;
		}
    },
});