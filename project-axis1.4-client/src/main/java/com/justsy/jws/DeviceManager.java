/**
 * DeviceManager.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.justsy.jws;

public interface DeviceManager extends java.rmi.Remote {
    public java.lang.String listDevices(java.lang.String encryptStr) throws java.rmi.RemoteException;
    public java.lang.String deviceAction(java.lang.String encryptStr, java.lang.String deviceId, java.lang.String deviceAction) throws java.rmi.RemoteException;
    public java.lang.String deviceLogs(java.lang.String encryptStr, java.lang.String deviceId) throws java.rmi.RemoteException;
    public java.lang.String deleteDevice(java.lang.String encryptStr, java.lang.String deviceId) throws java.rmi.RemoteException;
}
