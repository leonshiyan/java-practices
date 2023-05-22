package simpleServer;

import java.io.*;
import java.net.*;

public class SimpleServer {

    private static final int LISTENING_PORT = 8080;
    private static final String ROOT_DIRECTORY = "C:/WebServer/public";

    public static void main(String[] args) {
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(LISTENING_PORT);
        } catch (Exception e) {
            System.out.println("Failed to create listening socket.");
            return;
        }
        System.out.println("Listening on port " + LISTENING_PORT);
        try {
            while (true) {
                Socket connection = serverSocket.accept();
                System.out.println("\nConnection from " + connection.getRemoteSocketAddress());

                // Create a new thread to handle the connection
                ConnectionThread thread = new ConnectionThread(connection);
                thread.start();
            }
        } catch (Exception e) {
            System.out.println("Server socket shut down unexpectedly!");
            System.out.println("Error: " + e);
            System.out.println("Exiting.");
        }
    }

    private static class ConnectionThread extends Thread {
        private Socket connection;

        public ConnectionThread(Socket connection) {
            this.connection = connection;
        }

        public void run() {
            handleConnection(connection);
        }
    }

    private static void handleConnection(Socket connection) {
        try (
        		InputStream inStream = connection.getInputStream();
                OutputStream outStream = connection.getOutputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inStream));
                PrintWriter writer = new PrintWriter(outStream);
        ) {
            // Read the request from the input stream
            String requestLine = reader.readLine();
            if (requestLine == null) {
                return;
            }

            // Split the request line into tokens
            String[] requestTokens = requestLine.split("\\s+");
            if (requestTokens.length != 3) {
                sendErrorResponse(400, writer);
                return;
            }

            String method = requestTokens[0];
            String path = requestTokens[1];
            String protocol = requestTokens[2];

            // Check if the request method is GET
            if (!method.equals("GET")) {
                sendErrorResponse(501, writer);
                return;
            }

            // Check if the protocol is HTTP/1.1
            if (!protocol.equals("HTTP/1.1")) {
                sendErrorResponse(400, writer);
                return;
            }

            // Construct the file path
            String filePath = ROOT_DIRECTORY + path;
            File file = new File(filePath);

            // Check if the file exists and is readable
            if (!file.exists() || !file.canRead()) {
                sendErrorResponse(404, writer);
                return;
            }

            // Check if the file is a directory
            if (file.isDirectory()) {
                File indexFile = new File(file, "index.html");
                if (indexFile.exists() && indexFile.canRead()) {
                    file = indexFile;
                } else {
                    sendErrorResponse(403, writer);
                    return;
                }
            }

            // Send the response headers
            String mimeType = getMimeType(file.getName());
            writer.print("HTTP/1.1 200 OK\r\n");
            writer.print("Connection: close\r\n");
            writer.print("Content-Type: " + mimeType + "\r\n");
            writer.print("Content-Length: " + file.length() + "\r\n");
            writer.print("\r\n");
            writer.flush();

            // Send the file data
            sendFile(file, outStream);
        } catch (Exception e) {
            System.out.println("Error handling connection: " + e);
        } finally {
            try {
                connection.close();
            } catch (IOException e) {
                System.out.println("Error closing connection: " + e);
            }
        }
    }

    private static void sendErrorResponse(int errorCode, PrintWriter writer) {
        String status;
        String content;

        switch (errorCode) {
            case 400:
                status = "400 Bad Request";
                content = "<html><head><title>Error</title></head><body>"
                        + "<h2>Error: 400 Bad Request</h2>"
                        + "<p>The request syntax is invalid.</p>"
                        + "</body></html>";
                break;
            case 403:
                status = "403 Forbidden";
                content = "<html><head><title>Error</title></head><body>"
                        + "<h2>Error: 403 Forbidden</h2>"
                        + "<p>Access to the requested resource is forbidden.</p>"
                        + "</body></html>";
                break;
            case 404:
                status = "404 Not Found";
                content = "<html><head><title>Error</title></head><body>"
                        + "<h2>Error: 404 Not Found</h2>"
                        + "<p>The requested resource was not found on this server.</p>"
                        + "</body></html>";
                break;
            case 501:
                status = "501 Not Implemented";
                content = "<html><head><title>Error</title></head><body>"
                        + "<h2>Error: 501 Not Implemented</h2>"
                        + "<p>The requested method is not implemented.</p>"
                        + "</body></html>";
                break;
            default:
                status = "500 Internal Server Error";
                content = "<html><head><title>Error</title></head><body>"
                        + "<h2>Error: 500 Internal Server Error</h2>"
                        + "<p>An unexpected error occurred on the server.</p>"
                        + "</body></html>";
                break;
        }

        writer.print("HTTP/1.1 " + status + "\r\n");
        writer.print("Connection: close\r\n");
        writer.print("Content-Type: text/html\r\n");
        writer.print("\r\n");
        writer.print(content);
        writer.flush();
    }

    private static void sendFile(File file, OutputStream socketOut) throws IOException {
        try (
            InputStream fileIn = new BufferedInputStream(new FileInputStream(file));
            OutputStream out = new BufferedOutputStream(socketOut);
        ) {
            byte[] buffer = new byte[8192];
            int bytesRead;
            while ((bytesRead = fileIn.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            out.flush();
        }
    }

    private static String getMimeType(String fileName) {
        int pos = fileName.lastIndexOf('.');
        if (pos < 0)
            return "x-application/x-unknown";
        String ext = fileName.substring(pos + 1).toLowerCase();
        if (ext.equals("txt")) return "text/plain";
        else if (ext.equals("html")) return "text/html";
        else if (ext.equals("htm")) return "text/html";
        else if (ext.equals("css")) return "text/css";
        else if (ext.equals("js")) return "text/javascript";
        else if (ext.equals("java")) return "text/x-java";
        else if (ext.equals("jpeg")) return "image/jpeg";
        else if (ext.equals("jpg")) return "image/jpeg";
        else if (ext.equals("png")) return "image/png";
        else if (ext.equals("gif")) return "image/gif";
        else if (ext.equals("ico")) return "image/x-icon";
        else if (ext.equals("class")) return "application/java-vm";
        else if (ext.equals("jar")) return "application/java-archive";
        else if (ext.equals("zip")) return "application/zip";
        else if (ext.equals("xml")) return "application/xml";
        else if (ext.equals("xhtml")) return "application/xhtml+xml";
        else return "x-application/x-unknown";
    }
}
