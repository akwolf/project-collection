
package com.justsy.jws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "deleteDevice", namespace = "http://jws.justsy.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "deleteDevice", namespace = "http://jws.justsy.com/", propOrder = {
    "encryptStr",
    "deviceId"
})
public class DeleteDevice {

    @XmlElement(name = "encryptStr", namespace = "")
    private String encryptStr;
    @XmlElement(name = "deviceId", namespace = "")
    private String deviceId;

    /**
     * 
     * @return
     *     returns String
     */
    public String getEncryptStr() {
        return this.encryptStr;
    }

    /**
     * 
     * @param encryptStr
     *     the value for the encryptStr property
     */
    public void setEncryptStr(String encryptStr) {
        this.encryptStr = encryptStr;
    }

    /**
     * 
     * @return
     *     returns String
     */
    public String getDeviceId() {
        return this.deviceId;
    }

    /**
     * 
     * @param deviceId
     *     the value for the deviceId property
     */
    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

}
