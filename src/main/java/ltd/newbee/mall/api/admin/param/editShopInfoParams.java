package ltd.newbee.mall.api.admin.param;


import lombok.Data;

@Data
public class editShopInfoParams {

    private Long id;

    private String shopAvatar;

    private String shopName;

    private String shopDesc;

}
