/*

Ext Gantt Pro 4.2.7
Copyright(c) 2009-2016 Bryntum AB
http://bryntum.com/contact
http://bryntum.com/license

*/
// https://www.sencha.com/forum/showthread.php?310238-Tooltip-should-not-ignore-mouseover-event-on-touch-devices
Ext.define('Sch.patches.ToolTip', {
    extend  : 'Sch.util.Patch',
    
    target     : 'Ext.tip.ToolTip',
    
    minVersion : '6.0.0',

    applyFn : function () {
        var overrides = {
            setTarget: function(target) {
                var me = this,
                    t = Ext.get(target),
                    tg;

                if (me.target) {
                    tg = Ext.get(me.target);
                    me.mun(tg, {
                        mouseover: me.onTargetOver,
                        tap: me.onTargetOver,
                        mouseout: me.onTargetOut,
                        mousemove: me.onMouseMove,
                        scope: me
                    });
                }

                me.target = t;
                if (t) {
                    me.mon(t, {
                        mouseover: me.onTargetOver,
                        tap: me.onTargetOver,
                        mouseout: me.onTargetOut,
                        mousemove: me.onMouseMove,
                        scope: me
                    });
                }
                if (me.anchor) {
                    me.anchorTarget = me.target;
                }
            }
        };

        if (Ext.getVersion().isLessThan('6.0.2')) {
            overrides.afterSetPosition = function(x, y) {
                var me = this;
                me.callParent(arguments);
                if (me.anchor) {
                    if (!me.anchorEl.isVisible()) {
                        me.anchorEl.show();
                    }
                    // Sync anchor after it's visible, otherwise it'll be misplaced. Fixed in 6.0.2
                    // 1202_dragcreator
                    me.syncAnchor();
                } else {
                    me.anchorEl.hide();
                }
            };
        }

        Ext.ClassManager.get(this.target).override(overrides);
    }
});