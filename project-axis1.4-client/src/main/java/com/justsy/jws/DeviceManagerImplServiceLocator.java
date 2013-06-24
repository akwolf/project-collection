/**
 * DeviceManagerImplServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.justsy.jws;

public class DeviceManagerImplServiceLocator extends org.apache.axis.client.Service implements com.justsy.jws.DeviceManagerImplService {

    public DeviceManagerImplServiceLocator() {
    }


    public DeviceManagerImplServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public DeviceManagerImplServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for DeviceManagerImplPort
    private java.lang.String DeviceManagerImplPort_address = "http://realmdm.gtja.net:80/JustsyApp/deviceManager";

    public java.lang.String getDeviceManagerImplPortAddress() {
        return DeviceManagerImplPort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String DeviceManagerImplPortWSDDServiceName = "DeviceManagerImplPort";

    public java.lang.String getDeviceManagerImplPortWSDDServiceName() {
        return DeviceManagerImplPortWSDDServiceName;
    }

    public void setDeviceManagerImplPortWSDDServiceName(java.lang.String name) {
        DeviceManagerImplPortWSDDServiceName = name;
    }

    public com.justsy.jws.DeviceManager getDeviceManagerImplPort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(DeviceManagerImplPort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getDeviceManagerImplPort(endpoint);
    }

    public com.justsy.jws.DeviceManager getDeviceManagerImplPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.justsy.jws.DeviceManagerImplPortBindingStub _stub = new com.justsy.jws.DeviceManagerImplPortBindingStub(portAddress, this);
            _stub.setPortName(getDeviceManagerImplPortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setDeviceManagerImplPortEndpointAddress(java.lang.String address) {
        DeviceManagerImplPort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.justsy.jws.DeviceManager.class.isAssignableFrom(serviceEndpointInterface)) {
                com.justsy.jws.DeviceManagerImplPortBindingStub _stub = new com.justsy.jws.DeviceManagerImplPortBindingStub(new java.net.URL(DeviceManagerImplPort_address), this);
                _stub.setPortName(getDeviceManagerImplPortWSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("DeviceManagerImplPort".equals(inputPortName)) {
            return getDeviceManagerImplPort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://jws.justsy.com/", "DeviceManagerImplService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://jws.justsy.com/", "DeviceManagerImplPort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        
if ("DeviceManagerImplPort".equals(portName)) {
            setDeviceManagerImplPortEndpointAddress(address);
        }
        else 
{ // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
