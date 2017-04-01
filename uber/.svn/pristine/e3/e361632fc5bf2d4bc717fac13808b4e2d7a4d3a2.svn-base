/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/*
@class Gnt.Tooltip
@extends Ext.ToolTip
@private

Internal tooltip class showing task start/end/duration information for a single task.
*/
Ext.define("Gnt.Tooltip", {
    extend      : 'Ext.tip.ToolTip',
    alias       : 'widget.gantt_task_tooltip',

    requires    : ['Ext.Template'],

    mixins      : ['Gnt.mixin.Localizable'],

    /*
     * @cfg {Object} l10n
     * A object, purposed for the class localization. Contains the following keys/values:

            - startText       : 'Starts: ',
            - endText         : 'Ends: ',
            - durationText    : 'Duration:'
     */

    /*
     * @cfg {String} mode Either "startend" - showing start date and end date, or "duration" to show start date and duration
     */
    mode            : 'startend',
    anchor          : 'bottom',

    autoHide        : false,
    maskOnDisable   : false,
    taskElAlign     : 'bl-tl?',

    /*
     * @cfg {Ext.Template} template An HTML snippet used for the tooltip display.
     * In "startend" mode, it will receive a data object containing "startText", "endText" and "task" (the entire task) properties.
     * In "duration" mode, it will receive a data object containing "startText", "duration", "unit" and "task" (the entire task) properties.
     */
    template : null,

    gantt    : null,

    initComponent : function() {
        this.rtl = this.gantt.rtl;

        this.startLabel     = this.L('startText');
        this.label2Text     = this.mode === 'duration' ? this.L('durationText') : this.L('endText');

        if (!this.template) {
            this.template = new Ext.Template(
                '<div class="sch-timetipwrap {cls}">' +
                    '<table cellpadding="0" cellspacing="0">' +
                        '<tpl if="value1"><tr><td class="sch-gantt-tip-desc">{label1}</td><td class="sch-gantt-tip-value">{value1}</td></tr></tpl>' +
                        '<tr><td class="sch-gantt-tip-desc">{label2}</td><td class="sch-gantt-tip-value">{value2}</td></tr>' +
                    '</table>' +
                '</div>'
            ).compile();
        }


        this.callParent(arguments);

        this.update(this.template.apply({ value1 : '', value2 : '' }));
        this.addCls('gnt-tooltip');
    },

    updateContent : function (start, end, valid, taskRecord) {
        var content;

        if (this.mode === 'duration') {
            content = this.getDurationContent(start, end, valid, taskRecord);
        } else {
            content = this.getStartEndContent(start, end, valid, taskRecord);
        }

        this.update(content);
    },


    // private
    getStartEndContent : function(start, end, valid, taskRecord) {
        var gantt       = this.gantt,
            startText   = start && gantt.getFormattedDate(start),
            endText;

        if (start) {
            if(end - start > 0) {
                endText     = gantt.getFormattedEndDate(end, start);
            } else{
                endText     = startText;
            }
        } else {
            // Single point in time
            endText   = gantt.getFormattedEndDate(end);
        }

        var retVal = {
            cls         : valid ? 'sch-tip-ok' : 'sch-tip-notok',
            label2      : this.label2Text,
            value2      : endText,
            task        : taskRecord
        };

        if (start) {
            retVal.label1      = this.startLabel;
            retVal.value1      = start && gantt.getFormattedDate(start);
        }

        return this.template.apply(retVal);
    },


    getDurationContent : function(start, end, valid, taskRecord) {
        var unit        = taskRecord.getDurationUnit() || Sch.util.Date.DAY;
        var duration    = taskRecord.calculateDuration(start, end, unit);

        return this.template.apply({
            cls         : valid ? 'sch-tip-ok' : 'sch-tip-notok',
            label1      : this.startLabel,
            value1      : this.gantt.getFormattedDate(start),
            label2      : this.label2Text,
            value2      : parseFloat(Ext.Number.toFixed(duration, 1)) + ' ' + Sch.util.Date.getReadableNameOfUnit(unit, duration > 1),
            task        : taskRecord
        });
    },


    showBy : function(el, xPos) {
        this.setTarget(el);

        this.callParent([el, this.taskElAlign, [-18, -6]]);

        if (typeof xPos === 'number') {
            this.setX(xPos - 18); // offset to account for arrow offset from tip element left side
        }
    }
});
