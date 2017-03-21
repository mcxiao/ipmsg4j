package com.github.mcxiao.ipmsg.filter;

import com.github.mcxiao.ipmsg.PacketFilter;
import com.github.mcxiao.ipmsg.util.ObjectUtil;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */

public abstract class AbstractListFilter implements PacketFilter {
    
    protected final List<PacketFilter> filters;
    
    protected AbstractListFilter() {
        filters = new ArrayList<>();
    }
    
    protected AbstractListFilter(@NotNull PacketFilter... filters) {
        ObjectUtil.requiredNonNull(filters, "Params must not be null.");
        for (PacketFilter filter : filters) {
            ObjectUtil.requiredNonNull(filter, "Params must not be null.");
        }
        this.filters = new ArrayList<>(Arrays.asList(filters));
    }
    
    public void addFilter(@NotNull PacketFilter filter) {
        ObjectUtil.requiredNonNull(filter, "Params must not be null.");
        filters.add(filter);
    }
    
}
