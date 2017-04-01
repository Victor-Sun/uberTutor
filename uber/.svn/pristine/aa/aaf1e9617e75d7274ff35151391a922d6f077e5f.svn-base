/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

 @class Gnt.field.Note
 @extends Ext.form.field.Picker

 A specialized field to be used for editing in the {@link Gnt.column.Note} column.

 */

Ext.define('Gnt.field.Note', {
    extend              : 'Ext.form.field.Picker',

    alias               : ['widget.notefield', 'widget.noteeditor'],
    alternateClassName  : 'Gnt.widget.NoteField',

    requires            : ['Ext.form.field.HtmlEditor'],

    mixins              : ['Gnt.field.mixin.TaskField', 'Gnt.mixin.Localizable'],

    matchFieldWidth     : false,
    editable            : false,

    /**
     * @cfg {Object} pickerConfig Configuration of the field picker (Ext.form.field.HtmlEditor instance)
     */
    pickerConfig        : null,

    /**
     * @cfg {Function} previewFn
     * Function to return raw field value. If not provided the field uses text stripped of tags
     */
    previewFn           : null,
    /**
     * @cfg {Function} previewFnScope
     * Scope for {!link #previewFn} function to return raw field value
     */
    previewFnScope      : null,

    taskField           : 'noteField',
    getTaskValueMethod  : 'getNote',
    setTaskValueMethod  : 'setNote',

    afterRender : function() {
        this.callParent(arguments);
        this.on('collapse', this.onPickerCollapse, this);
    },


    valueToVisible : function (value) {
        if (this.previewFn) {
            return this.previewFn.call(this.previewFnScope || this, value);
        } else {
            return Ext.util.Format.stripTags(value);
        }
    },


    createPicker: function() {
        var field = new Ext.form.field.HtmlEditor(Ext.apply({
            frame       : true,
            shadow      : false,
            floating    : true,
            height      : 200,
            width       : 300,
            listeners   : {
                change      : this.onPickerChange,
                initialize  : this.onPickerInit,
                scope       : this
            }
        }, this.pickerConfig || {}));

        return field;
    },


    onPickerInit : function (picker) {
        var body = picker.win.document.body;
        var me   = this;

        // https://www.sencha.com/forum/showthread.php?303342
        // disable iframe window focus to avoid picker collapse
        picker.win.focus = Ext.emptyFn;

        // Abort editing on ESC keypress
        picker.win.document.onkeydown = function(evt) {
            var extEv = new Ext.event.Event(evt);

            if (extEv.keyCode  === extEv.ESC) {
                me.collapse();
            }
        };
    },

    onPickerChange : function (picker, value) {
        this.setRawValue(this.valueToVisible(value));
    },

    getValue : function () {
        return this.getPicker().getValue();
    },

    setValue : function (value) {
        this.callParent([ this.valueToVisible(value) ]);

        this.getPicker().setValue(value);

        if (this.instantUpdate && !this.getSuppressTaskUpdate() && this.task) {
            this.applyChanges();
        }
    },

    onPickerCollapse : function() {
        this.setValue(this.getPicker().getValue());
    },

    onTriggerClick: function() {
        var me = this;

        if (!me.readOnly && !me.disabled) {
            if (me.isExpanded) {
                me.collapse();
            } else {
               me.expand();
            }
        }
    }
});
