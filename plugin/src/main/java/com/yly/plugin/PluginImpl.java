package com.yly.plugin;

import com.yly.comm.Common;

public class PluginImpl implements Common {
    @Override
    public int test(int a, int b) {
        return a + b;
    }
}
