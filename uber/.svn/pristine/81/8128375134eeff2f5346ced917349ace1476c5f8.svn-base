/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// Applied override provided by sencha
// https://www.sencha.com/forum/showthread.php?310677
Ext.define('Sch.patches.Scroller', {
    extend      : 'Sch.util.Patch',

    target      : 'Ext.scroll.Scroller',

    minVersion  : '6.0.2',
    maxVersion  : '6.0.3',

    applyFn : function () {
        var overrides = {
            destroy : function () {
                clearTimeout(this.restoringTimer);
                this.callParent(arguments);
            },
            privates    : {
                restoreState: function () {
                    var me = this,
                        el = me.getScrollElement(),
                        dom;

                    if (el) {
                        dom = el.dom;

                        // Only restore state if has been previously captured! For example,
                        // floaters probably have not been hidden before initially shown.
                        if (me.trackingScrollTop !== undefined) {
                            // If we're restoring the scroll position, we don't want to publish
                            // scroll events since the scroll position should not have changed
                            // at all as far as the user is concerned, so just do it silently
                            // while ensuring we maintain the correct internal state. 50ms is
                            // enough to capture the async scroll events, anything after that
                            // we re-enable.
                            me.restoring = true;
                            me.restoringTimer = Ext.defer(function () {
                                me.restoring = false;
                            }, 50);
                            dom.scrollTop = me.trackingScrollTop;
                            dom.scrollLeft = me.trackingScrollLeft;
                        }
                    }
                }
            }
        };

        // https://www.sencha.com/forum/showthread.php?312703
        // Using method from 6.0.1
        if (Ext.isIE9m) {
            overrides.privates.updateSpacerXY = function(pos) {
                var spacer = this.getSpacer();
                if (this.getRtl && this.getRtl()) {
                    spacer.rtlSetLocalXY(pos.x, pos.y);
                } else {
                    spacer.setLocalXY(pos.x, pos.y);
                }
            };
        }

        Ext.ClassManager.get(this.target).override(overrides);
    }
});