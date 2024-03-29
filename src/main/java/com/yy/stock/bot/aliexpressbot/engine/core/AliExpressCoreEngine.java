package com.yy.stock.bot.aliexpressbot.engine.core;

import com.yy.stock.bot.engine.core.CoreEngine;

public abstract class AliExpressCoreEngine extends CoreEngine {
    public void solveLoginCaptcha() throws InterruptedException {
        Thread.sleep(5000);
        driverEngine.getExecutor().switchToFrame("baxia-dialog-content");
        var capButtonXpath = "nc_1_n1z";
        var capButton = driverEngine.getExecutor().getById(capButtonXpath);
        var smallSliding = capButton.getSize();
        var bigSlidingDiv = driverEngine.getExecutor().getByClassName("nc-lang-cnt").getSize();
        var bigSlidingX = bigSlidingDiv.getWidth();
        var slidingDistance = bigSlidingX - smallSliding.getWidth();
        var xs = new int[]{30,69, 87, 25,40,40,20,20,26,20};
        var ys = new int[]{0, 0, 0, 0, 0, 0, 0,0, 0,0};
        driverEngine.getExecutor().dragAndDropBy(capButton, xs, ys);
        Thread.sleep(1000);
        driverEngine.getExecutor().switchToDefaultContent();
        Thread.sleep(1000);
    }


    @Override
    public void solvePayCaptcha() throws InterruptedException {
        solveLoginCaptcha();
    }
}
