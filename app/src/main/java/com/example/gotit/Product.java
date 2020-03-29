package com.example.gotit;

import com.parse.ParseClassName;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseUser;
import com.example.gotit.Store;

@ParseClassName("Product")
public class Product extends ParseObject {

    private Number cartQuantity = 1;

    public static final String KEY_PRODUCT = "objectId";
    public static final String KEY_BRAND = "bra_id";
    public static final String KEY_STORE = "sto_id";
    public static final String KEY_TYPE = "pro_type";
    public static final String KEY_SKU_NUM = "pro_sku_num";
    public static final String KEY_PRICE = "pro_price";
    public static final String KEY_NAME = "pro_name";
    public static final String KEY_QUANTITY = "pro_quantity";
    public static final String KEY_IMAGE ="img";

    public Product() {super();}

    public ParseUser getProductId() { return getParseUser(KEY_PRODUCT); }
    public void setProductId(ParseUser productId){ put(KEY_PRODUCT, productId); }

    public ParseUser getBrandId() { return getParseUser(KEY_BRAND); }
    public void setBrandId(ParseUser brandId){ put(KEY_BRAND, brandId); }

    public ParseUser getStoreId() { return getParseUser(KEY_STORE); }


    public String getProductType(){ return getString(KEY_TYPE); }
    public void setProductType(String type){ put(KEY_TYPE, type); }

    public Number getSkuNum(){ return getNumber(KEY_SKU_NUM); }
    public void setSkuNum(Number skunum){ put(KEY_SKU_NUM, skunum); }

    public Number getProductPrice(){ return getNumber(KEY_PRICE); }
    public void setPrice(Number price){ put(KEY_PRICE, price); }

    public String getProductName(){ return getString(KEY_NAME); }
    public void setProductName(String name){ put(KEY_NAME, name); }

    public Number getProductQuantity(){ return getNumber(KEY_QUANTITY); }
    public void setProductQuantity(Number quantity){ put(KEY_QUANTITY, quantity); }

    public ParseFile getImage() { return getParseFile(KEY_IMAGE); }
    public void setImage(ParseFile image){ put(KEY_IMAGE, image); }

    public Number getcartQuantity() { return cartQuantity; }
    public void setcartQuantity(Number count){ cartQuantity = count; }

}
