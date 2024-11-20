/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package BITalino;

import javax.bluetooth.*;


/**
 *
 * @author Andreoti
 */
public class BluetoothDeviceScan { // hecho de prueba para ver si funcionaba
    public static void main(String[] args) {
        try {
            // Obtener el dispositivo Bluetooth local
            LocalDevice localDevice = LocalDevice.getLocalDevice();
            System.out.println("Nombre del dispositivo Bluetooth: " + localDevice.getFriendlyName());

            // Iniciar el escaneo
            DiscoveryAgent agent = localDevice.getDiscoveryAgent();
            System.out.println("Buscando dispositivos Bluetooth cercanos...");
            
            // Obtener los dispositivos conocidos
            RemoteDevice[] devices = agent.retrieveDevices(DiscoveryAgent.PREKNOWN);
            if (devices == null || devices.length == 0) {
                System.out.println("No se encontraron dispositivos cercanos.");
            } else {
                for (RemoteDevice device : devices) {
                    try {
                        System.out.println("Dispositivo encontrado: " + device.getFriendlyName(true)
                                + " (" + device.getBluetoothAddress() + ")");
                    } catch (Exception e) {
                        System.out.println("No se pudo obtener el nombre del dispositivo: " + device.getBluetoothAddress());
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Error durante la búsqueda de dispositivos.");
            e.printStackTrace();
        }
    }
    
}
