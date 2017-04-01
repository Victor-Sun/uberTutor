/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**

@class Gnt.model.Project
@extends Gnt.model.Task

This class represents a single Project in your Gantt chart.

The inheritance hierarchy of this class includes {@link Gnt.model.Task}, {@link Sch.model.Customizable} and {@link Ext.data.Model} classes.
This class will also receive a set of methods and additional fields that stem from the {@link Ext.data.NodeInterface}.
Please refer to the documentation of those classes to become familiar with the base interface of this class.

By default, a Project has the following fields as seen below.

# Project Fields

- `Description` - the description of the project, this field maps to the task `Note` field
- `AllowDependencies` - this field indicates if the project tasks allowed to have dependencies with tasks external to the project

*/
Ext.define('Gnt.model.Project', {
    extend                  : 'Gnt.model.Task',

    alias                   : 'gntmodel.project',

    /**
     * @property {Boolean} isProject Indicates that this is a project.
     * Can be used in heterogeneous stores to distinguish project records from task ones.
     */
    isProject               : true,

    /**
     * @cfg {String} descriptionField The description of the project.
     */
    descriptionField        : 'Note',

    /**
     * @cfg {Boolean} allowDependenciesField The name of the field specifying if the task allows dependencies.
     */
    allowDependenciesField  : 'AllowDependencies',

    customizableFields      : [
        { name : 'Description', type : 'string' },
        { name : 'AllowDependencies', persist : false, type : 'bool', defaultValue : false }
    ],

    recognizedSchedulingModes : ['Normal'],
    convertEmptyParentToLeaf  : false,

    isEditable : function (fieldName) {
        // some fields doesn't make sense to edit for a project
        switch (fieldName) {
            case this.nameField:
            case this.startDateField:
            case this.endDateField:
            case this.readOnlyField:
            case this.durationField:
            case this.durationUnitField:
            case this.descriptionField:
            case this.allowDependenciesField:
                return this.callParent(arguments);
            default :
                return false;
        }
    }

    /*
     * @method setReadOnly
     * Sets if the given project is read only. All underlying tasks will be considered as read only as well.
     *
     * @param {String} value `True` to mark the project as read only.
     */

});
