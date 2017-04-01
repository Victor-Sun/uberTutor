/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 @class Gnt.panel.Timeline
 @extends Ext.Panel

 A container for the {@link Gnt.panel.TimelineScheduler project timeline panel} instance. It shows a high level timeline of important tasks and includes labels
 with the timespan start and end dates. Using it with your Gantt chart is very simple:

     new Ext.Viewport({
         layout : 'border',
         items : [
             {
                 xtype       : 'advanced-timeline',
                 region      : 'north',
                 taskStore   : yourTaskStore
             },
             {
                 xtype       : 'ganttpanel',
                 region      : 'center',
                 taskStore   : yourTaskStore
             }
         ]
     });

 {@img gantt/images/timeline-panel.png}

 */
Ext.define("Gnt.panel.Timeline", {
    extend : 'Ext.Panel',

    requires : [
        'Ext.form.field.Display',
        'Gnt.panel.TimelineScheduler'
    ],

    mixins : ['Gnt.mixin.Localizable'],
    alias  : 'widget.gantt_timeline',

    layout : {
        type  : 'hbox',
        align : 'stretch'
    },

    bodyPadding : '10 0 20 0',
    height      : 190,
    labelWidth  : 100,
    taskStore   : null,
    scheduler   : null,

    /**
     * @cfg {String} schedulerClass The class name that will be instantiated as the timeline component.
     */
    schedulerClass : 'Gnt.panel.TimelineScheduler',

    initComponent : function () {
        this.addCls('sch-gantt-timeline');

        this.scheduler = Ext.create(this.schedulerClass, {
            taskStore : this.taskStore,
            flex      : 1
        });

        this.scheduler.on('viewchange', this.onTimespanChange, this);

        this.items = this.buildItems();

        this.callParent(arguments);
    },

    buildItems : function () {
        return [
            {
                xtype      : 'displayfield',
                fieldLabel : this.L('start'),
                labelAlign : 'top',
                itemId     : 'startlabel',
                cls        : 'sch-gantt-timeline-label sch-gantt-timeline-left-label',
                width      : this.labelWidth
            },
            this.scheduler,
            {
                xtype      : 'displayfield',
                fieldLabel : this.L('end'),
                labelAlign : 'top',
                itemId     : 'endlabel',
                cls        : 'sch-gantt-timeline-label sch-gantt-timeline-right-label',
                width      : this.labelWidth
            }
        ];
    },


    getStartLabel : function () {
        return this.startLabel || (this.startLabel = this.down('#startlabel'));
    },


    getEndLabel : function () {
        return this.endLabel || (this.endLabel = this.down('#endlabel'));
    },


    onTimespanChange : function () {
        var start = this.scheduler.getStart();
        var end   = this.scheduler.getEnd();

        this.getStartLabel().setValue(Ext.Date.format(start, this.L('format')));
        this.getEndLabel().setValue(Ext.Date.format(end, this.L('format')));
    }

});

