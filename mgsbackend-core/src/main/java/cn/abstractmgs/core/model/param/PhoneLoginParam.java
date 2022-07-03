package cn.abstractmgs.core.model.param;

import lombok.Data;

@Data
public class PhoneLoginParam {

    String phoneNumber;

    String sms;

    String vc;
}
