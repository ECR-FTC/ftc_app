package org.eastcobbrobotics.ftc.relic;

import android.content.Context;
import android.net.DhcpInfo;
import android.net.Network;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * Created by ECR FTC on 11/28/17.
 * <p>
 * This class provides a way to broadcast trace / status / debugging messages
 * over UDP, a connectionless protocol. Listeners can pick up and display
 * these messages to show what's happening on the robot.
 */


public class Tracecaster {

    private static final String TAG = "Tracecaster";
    private static final String DEFAULT_BROADCAST = "192.168.49.255";

    protected int port;
    protected DatagramSocket sock;
    protected InetAddress broadcast;


    public Tracecaster(Context appContext, int thePort) {
        port = thePort;
        try {
            broadcast = getBroadcast();

            // Now open up the socket and put it in broadcast mode.
            sock = new DatagramSocket(port);
            sock.setBroadcast(true);
            Log.i(TAG, String.format("Broadcasting to %s on port %d",
                    broadcast, port));

        } catch (Exception e) {
            sock = null;
            logError("Error opening broadcast socket", e);
        }
    }


    public void post(String msg) {
        if (sock != null) {
            DatagramPacket packet = new DatagramPacket(
                    msg.getBytes(),
                    msg.length(),
                    broadcast,
                    port
            );
            try {
                sock.send(packet);
            } catch (IOException e) {
                logError("Failed to post", e);
                sock = null; // if we fail, don't bother trying again
            }
        }


    }

    protected static InetAddress getBroadcast() {
        try {
            Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
            while (nis.hasMoreElements()) {
                NetworkInterface ni = nis.nextElement();
                if (!ni.isLoopback()) {
                    for (InterfaceAddress ia : ni.getInterfaceAddresses()) {
                        InetAddress bcast = ia.getBroadcast();
                        if (bcast != null) {
                            return bcast;
                        }
                    }
                }
            }
            Log.e(TAG, String.format("Falling back to default broadcast %s", DEFAULT_BROADCAST));
            return InetAddress.getByName(DEFAULT_BROADCAST);
        } catch (Exception e) {
            logError("Error getting broadcast address", e);
            return null;
        }
    }

    protected static void logError(String msg, Exception e) {
        Log.e(TAG, String.format("%s - %s", msg, e));
    }
}
