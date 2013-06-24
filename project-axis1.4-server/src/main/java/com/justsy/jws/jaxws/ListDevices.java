
package com.justsy.jws.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "listDevices", namespace = "http://jws.justsy.com/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "listDevices", namespace = "http://jws.justsy.com/")
public class ListDevices {

    @XmlElement(name = "encryptStr", namespace = "")
    private String encryptStr;

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

}
