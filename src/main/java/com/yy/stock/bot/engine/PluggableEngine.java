package com.yy.stock.bot.engine;

import com.yy.stock.bot.engine.core.CoreEngine;

public interface PluggableEngine {
    void plugIn(CoreEngine coreEngine);
}
