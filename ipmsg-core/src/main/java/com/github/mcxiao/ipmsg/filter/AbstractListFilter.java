package com.github.mcxiao.ipmsg.filter;

import com.github.mcxiao.ipmsg.PacketFilter;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 *
 */

public abstract class AbstractListFilter implements PacketFilter {
    
    protected final List<PacketFilter> filters;
    
    protected AbstractListFilter() {
        filters = new ArrayList<>();
    }
    
    protected AbstractListFilter(@NotNull PacketFilter... filters) {
        Objects.requireNonNull(filters, "Params must not be null.");
        for (PacketFilter filter : filters) {
            Objects.requireNonNull(filter, "Params must not be null.");
        }
        this.filters = new ArrayList<>(Arrays.asList(filters));
    }
    
    public void addFilter(@NotNull PacketFilter filter) {
        Objects.requireNonNull(filter, "Params must not be null.");
        filters.add(filter);
    }
    
}
