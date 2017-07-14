Ext.define('uber.util.Util', {

    statics : {

        required: '<span style="color:red;font-weight:bold" data-qtip="Required"> *</span>',

        decodeJSON : function (text) {
            var result = Ext.JSON.decode(text, true);

            if (!result){
                result = {};
                result.success = false;
                result.data = text;
            }

            return result;
        },

        showErrorMsg: function (text) {

            Ext.Msg.show({
                title:'Error!',
                msg: text,
                icon: Ext.Msg.ERROR,
                buttons: Ext.Msg.OK,
                width: 400,
                height: 200
            });
        },
        
        showInfoMsg: function (text) {

            Ext.Msg.show({
                title:'Information!',
                msg: text,
                icon: Ext.Msg.INFO,
                buttons: Ext.Msg.OK,
                width: 400,
                height: 200
            });
        },
        
        showToast: function(text) {
        	Ext.create({
        		xtype: 'toast',
        		html: text,
        		align: 't',
        		minWidth: 400
        	}).show();
        },

        handleFormFailure: function(action){
            var me = this;
            var result = uber.util.Util.decodeJSON(action.response.responseText);

            switch (action.failureType) {
                case Ext.form.action.Action.CLIENT_INVALID:
                    me.showErrorMsg('Form fields may not be submitted with invalid values');
                    break;
                case Ext.form.action.Action.CONNECT_FAILURE:
                    me.showErrorMsg(action.response.responseText);
                    break;
                case Ext.form.action.Action.SERVER_INVALID:
                    me.showErrorMsg(result.data);
            }
        },
        
        handleRequestFailure: function(response, opts) {
            var me = this;
            var result = uber.util.Util.decodeJSON(response.responseText);
            me.showErrorMsg(result.data);
        },
        
        // 控制画面中button、CheckBox权限（权限CODE Array）
        setPrivilegeByItem: function(target, privilegeItem) {
        	if (privilegeItem) {
        		for (var i = 0; i < privilegeItem.length; i++) {
            		var item = privilegeItem[i];
            	    if (target.getComponent('BTN' + item)) {
            	    	target.getComponent('BTN' + item).show();
            	    } else if (target.getComponent('CHK' + item)) {
            	    	target.getComponent('CHK' + item).show();
            	    }
            	}
        	}
        },
        
        // 控制画面中button权限
        setPrivilegeByAll: function(target, projectPrivilege) {
        	if (projectPrivilege) {
	    		for (key in projectPrivilege) {
					if (key == 'id') {
						continue;
					}
					var privilegeItem = projectPrivilege[key];
					this.setPrivilegeByItem(target, privilegeItem);
				}
	    	}
        },
        
        // 取得指定权限CODE对应权限有无
        getPrivilege: function(privilegeCode, projectPrivilege, vehicleId) {
        	var privilege = false;
			// 如果存在车型ID、则按照车型ID查找对应权限
			if (vehicleId) {
				var privilegeItem = projectPrivilege[vehicleId];
				if (privilegeItem != null) {
					privilege = privilegeItem.indexOf(privilegeCode) >= 0;
				}
			} else {
				// 如果不存在车型ID、则循环所有车型，只要有一个车型具备权限，则认为具备对应权限
				for (key in projectPrivilege) {
					if (key == 'id') {
						continue;
					}
					var privilegeItem = projectPrivilege[key];
					if (privilegeItem != null &&
							privilegeItem.indexOf(privilegeCode) >= 0) {
						privilege = true;
						break;
					}
				}
			}
			// true：具备权限、false：不具备权限
			return privilege;
        },
        
//        //url 为 rpt=pm/projectNodeListReport.brt&xlsbtn&params=programId=
//        openReport:function(url){
//        	Ext.Ajax.request({
//        		url:'/UberForTutor/com/report!getReportAddress.action',
//    			scope: me,
//    			success: function(response, opts) {
//    		    	var obj = Ext.decode(response.responseText);
//    				if (obj.success) {
//    					if (obj.data.reportAddress) {
//    						var openUrl = obj.data.reportAddress + '/report/ReportEmitter?rfscache=true&' + url;
//    						if(url.indexOf('params') != -1){
//    							openUrl = openUrl+";userId="+obj.data.userId;
//    						}else{
//    							openUrl = openUrl +"&params=userId="+obj.data.userId;
//    						}
//    						window.open(openUrl);
//    					} else {
//    						uber.util.Util.showErrorMsg('不能获取报表Server的地址！');
//    					}
//    		        } else {
//    		        	uber.util.Util.handleRequestFailure(response);
//    		        }
//    		    },
//    			failure: function(response, opts) {
//    		    	uber.util.Util.handleRequestFailure(response);
//    		    }
//        	});
//        },
//        
        // 格式化时间yyyy/mm/dd
        formatDate: function(date, formatter) {
        	if (date == '' || date == null) {
        		return date;
        	}
        	if (! formatter) {
        		formatter = '/';
        	}
        	var month = date.getMonth() + 1 + '';
        	if (month.length == 1) {
        		month = '0' + month;
        	}
        	var day = date.getDate() + '';
        	if (day.length == 1) {
        		day = '0' + day;
        	}
        	return date.getFullYear() + formatter + month + formatter + day;
        },
        
        // 计算两个时间差（日数 date1 - date2）
        getTotalDays: function(date1, date2) {
    	  var days = Math.ceil(
    	      (Date.parse(date1) - Date.parse(date2)) / (24 * 60 * 60 * 1000) + 1) - 1; 

    	  return days;
    	}
    }
});