package com.yy.stock.bot.amazonbot.engine.stocker;

public class CartUnit {
//    private List<AddCartRequestModel> generAddCartRequest() {
//        var supplier = stockRequest.getSupplier();
//        AddCartRequestModel addCartRequestModel = new AddCartRequestModel();
//        addCartRequestModel.setItemId(supplier.getApiItemId());
//        addCartRequestModel.setSkuId(supplier.getApiSkuId());
//        addCartRequestModel.setQuantity(stockRequest.getOrderInfo().getQuantity());
//        List<AddCartRequestModel> addCartRequest = Collections.singletonList(addCartRequestModel);
//        return addCartRequest;
//    }
//
//    public void cartAdd(List<AddCartRequestModel> model) throws InterruptedException {
////        String paramMap = "[{\"itemId\":\"2199811436\",\"skuId\":\"14121812860\",\"quantity\":8}]";
//
//        while (cartCount() > 0) {
//            cartClear();
//            Thread.sleep(1000);
//        }
//
//        HttpEntity<List<AddCartRequestModel>> entity = new HttpEntity<>(model, savedHeaders);
//
//        HttpEntity<CartApiResponseModel<AddCartRespModule>> response = restTemplate.exchange(urls.addToCartApi, HttpMethod.POST, entity, new ParameterizedTypeReference<>() {
//        });
//        System.out.println(response);
//    }
//
//    public int cartCount() {
//        HttpEntity entity = new HttpEntity<>(savedHeaders);
//
//        HttpEntity<CartApiResponseModel<CartCountRspModel>> response = restTemplate.exchange(urls.cartCountApi, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
//        });
//        CartCountRspModel model = response.getBody().getModule();
//        return model.getCartNum();
//    }
//
//    public boolean cartClear() {
//        getDriver().get(urls.cartPage);
//
//        WebElement selectAllButton = SeleniumHelper.getByXpath(getDriver(), xpaths.getSelectAllCartItemsButton());
//        selectAllButton.click();
//
//        // 等待全选框被选中
//        new WebDriverWait(getDriver(), Duration.ofSeconds(6))
//                .until(ExpectedConditions.elementSelectionStateToBe(selectAllButton, true));
//
//        WebElement delteButton = SeleniumHelper.getByXpath(getDriver(), xpaths.getDeleteAllCartItemsButton());
//        delteButton.click();
//
//        WebElement confirmDeleteButton = SeleniumHelper.getByXpath(getDriver(), xpaths.getDeleteCartItemsConfirmButton());
//        confirmDeleteButton.click();
//
//        WebElement emptyTips = SeleniumHelper.getByXpath(getDriver(), xpaths.getCartEmptyTipsDiv());
//        if (emptyTips.getText().contains("Your cart is empty")) {
//            return true;
//        }
//        return false;
//    }

}
