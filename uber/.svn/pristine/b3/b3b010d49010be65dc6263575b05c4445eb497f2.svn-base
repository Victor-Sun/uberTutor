/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
Ext.define('Robo.action.flat.Update', {
    extend          : 'Robo.action.Base',

    requires        : [
        'Ext.Array'
    ],
    
    inheritableStatics : {
        CUSTOMLY_PROCESSED : {}
    },

    config : {
        record     : null,
        fieldNames : null
    },

    oldValues       : null,
    newValues       : null,

    
    constructor : function(config) {
        var me = this;

        me.callParent([config]);
        me.initConfig(config);
        me.saveValues();
    },

    
    saveValues : function() {
        var me              = this,
            record          = me.getRecord(),
            fieldNames      = me.getFieldNames();

        if (fieldNames) {
            me.oldValues    = Ext.Array.map(fieldNames, function (fieldName) {
                return me.processSavingOldValue(fieldName, record);
            });

            me.newValues    = Ext.Array.map(fieldNames, function (fieldName) {
                return me.processSavingNewValue(fieldName, record);
            });
        }
    },

    
    undo : function () {
        var CPM,
            me          = this,
            record      = me.getRecord(),
            fieldNames  = me.getFieldNames(),
            setObj;

        if (fieldNames) {

            CPM = me.self.CUSTOMLY_PROCESSED;

            record.beginEdit();

            setObj = Robo.util.Array.reduce(fieldNames, function(prev, curr, i) {
                var processedVal;

                // we'll be a bit defensive
                if (curr) {
                    processedVal = me.processRestoringValue(me.oldValues[ i ], curr, record, 'undo');
                    
                    if (processedVal !== CPM) {
                        prev[ curr ] = processedVal;
                    }
                }

                return prev;
            }, {});

            record.set(setObj);

            record.endEdit();
        }
    },

    redo : function () {
        var CPM,
            me          = this,
            record      = me.getRecord(),
            fieldNames  = me.getFieldNames(),
            setObj;

        if (fieldNames) {

            CPM = me.self.CUSTOMLY_PROCESSED;

            record.beginEdit();

            setObj = Robo.util.Array.reduce(fieldNames, function(prev, curr, i) {
                var processedVal;

                // we'll be a bit defensive
                if (curr) {
                    processedVal = me.processRestoringValue(me.newValues[ i ], curr, record, 'redo');
                    
                    if (processedVal !== CPM) {
                        prev[ curr ] = processedVal;
                    }
                }

                return prev;
            }, {});

            record.set(setObj);

            record.endEdit();
        }
    },

    /**
     * @method
     */
    processSavingOldValue : function (fieldName, record) {
        //                 our own implementation for Ext4 || Ext6 implementation
        var previousValues      = record.previous || record.previousValues 
        
        if (previousValues && previousValues.hasOwnProperty(fieldName))
            return previousValues[ fieldName ]
        else
            if (record.editMementoFix) {
                previousValues  = record.editMementoFix.previousValues || record.editMementoFix.data
                
                return previousValues[ fieldName ]
            }
            else
                throw "Can not get previous value"
    },

    /**
     * @method
     */
    processSavingNewValue : function (fieldName, record) {
        return record.get(fieldName);
    },

    /**
     * @method
     */
    processRestoringValue : Ext.identityFn,
    
    
    getTitle : function () {
        var record      = this.getRecord()
        var fieldNames  = this.getFieldNames()
        
        if (record.getTitle) return "Edit of " + fieldNames[ 0 ] + " for " + record.getTitle(this)
            
        if (record.modelName) return "Edit of " + record.modelName + " " + record.getId()
        
        return ''
    }
    
});
