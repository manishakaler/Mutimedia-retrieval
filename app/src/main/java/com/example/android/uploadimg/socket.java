package com.example.android.uploadimg;

import com.github.nkzawa.socketio.client.IO;
import com.github.nkzawa.socketio.client.Socket;

import java.net.URISyntaxException;

//import io.socket.client.IO;
//import io.socket.client.Socket;

public class socket {
    private Socket mSocket;
    {
        try {
            mSocket = IO.socket("http://172.30.8.74:8000"); //172.30.8.74
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
    public Socket getSocket() {
        return mSocket;
    }
}