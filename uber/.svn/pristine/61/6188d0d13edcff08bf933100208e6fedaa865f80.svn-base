/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
/**
 * @private
 *
 * @class Gnt.plugin.exporter.mixin.DependencyPainter
 *
 * This mixin class handles the rendering of dependencies for the exporters.
 *
 */
Ext.define ('Gnt.plugin.exporter.mixin.DependencyPainter', {

    fillRecordRelatedBoxes : function (task) {
        if (task.hasDependencies()) {
            var me      = this,
                view    = me.normalView,
                painter = view.getDependencyView().painter;

            var box = painter.getItemBox(me.normalView, task);

            if (box) {
                // WARNING view.bufferedRenderer.bodyTop is private
                // dependency painter doesn't take bufferedRenderer.bodyTop into account
                // but we need to do it

                if (view.bufferedRenderer) {
                    box.top += view.bufferedRenderer.bodyTop;
                    box.bottom += view.bufferedRenderer.bodyTop;
                }
                // we want all tasks to be considered as visible
                box.rendered = true;

                me.eventBoxes[task.internalId] = box;
            }
        }
    },

    onClassMixedIn : function (targetClass) {
        // We need to override existing "fillRecordRelatedBoxes" method
        // By default mixins do not allow this that's why we use "onClassMixedIn"
        targetClass.addMember('fillRecordRelatedBoxes', this.prototype.fillRecordRelatedBoxes);
    }

});
