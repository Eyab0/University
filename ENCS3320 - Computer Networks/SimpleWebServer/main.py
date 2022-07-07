from datetime import datetime
from socket import *  # Include Python’s socket library.

# reserve port 9000 on the computer
PORT = 9000
# Create a TCP socket instance with pass two parameters:
# AF_INET: IPv4
# SOCK_STREAM: connection-oriented TCP protocol.
serverSocket = socket(AF_INET, SOCK_STREAM)
# Associates the socket with its local address,
# allowing clients to connect to the server using that address.
serverSocket.bind(("", PORT))
# Server begins listening for  incoming TCP requests ,where 1 represents the number of incoming connections.
serverSocket.listen(1)
print(">> The server is ready to receive..")

# Infinite loop until it interrupted or an error occurs
while True:
    # Server waits on accept() for incoming requests, new socket created on return .
    connectionSocket, address = serverSocket.accept()
    # Receive data from the server and decode it to obtain the string.
    sentence = connectionSocket.recv(1024).decode()

    # Getting the IP address & the port number and printing them
    ipAddress = address[0]
    connectionSocketPort = address[1]
    print("IP Address: " + str(ipAddress))
    print("Port: " + str(connectionSocketPort))
    header = ''
    requestFile = sentence.split(' ')[1]
    requestedFileName = requestFile.lstrip('/')  # Removing the first( / )to get the requested file name .
    try:
        # print(requestedFileName + "\n\n\n")
        if requestedFileName == '' or requestedFileName == 'en':
            # Load main_en.html file
            requestFile = open('main_en.html', 'rb')  # Open and read the requested file in byte format.
            web_Page = requestFile.read()
            requestFile.close()
            connectionSocket.send(f"HTTP/1.1 200 OK\r\n".encode())
            header = 'Content-Type: text/html \r\n'
            connectionSocket.send(header.encode())
            connectionSocket.send(f"\r\n".encode())
            connectionSocket.send(web_Page)  # Send the final response with all parts of header.
            connectionSocket.close()  # To closes a connectionSocket socket.

        elif requestedFileName == 'ar':
            # Load main_ar.html file
            requestFile = open('main_ar.html', 'rb')  # Open and read the requested file in byte format.
            web_Page = requestFile.read()
            requestFile.close()
            connectionSocket.send(f"HTTP/1.1 200 OK\r\n".encode())
            header = 'Content-Type: text/html \r\n'
            connectionSocket.send(header.encode())
            connectionSocket.send(f"\r\n".encode())
            connectionSocket.send(web_Page)  # Send the final response with all parts of header.
            connectionSocket.close()  # To closes a connectionSocket socket.
        elif requestedFileName == 'go':
            connectionSocket.send("HTTP/1.1 307 Temporary Redirect \r\n".encode())
            connectionSocket.send(b"location: https://www.google.com\r\n")
            connectionSocket.close()
            continue
        elif requestedFileName == 'bzu':
            connectionSocket.send("HTTP/1.1 307 Temporary Redirect \r\n".encode())
            connectionSocket.send(b"location: https://www.birzeit.edu/ar\r\n")
            connectionSocket.close()
            continue
        elif requestedFileName == 'cn':
            connectionSocket.send("HTTP/1.1 307 Temporary Redirect \r\n".encode())
            connectionSocket.send(b"location: https://edition.cnn.com/\r\n")
            connectionSocket.close()
            continue

        # Accepting different file formats
        elif requestedFileName.endswith(".html"):
            # If the request is a .html then the server should send the html file with Content-Type: text/html.
            requestFile = open(requestedFileName, 'rb')  # Open and read the requested file in byte format.
            web_Page = requestFile.read()
            requestFile.close()
            connectionSocket.send(f"HTTP/1.1 200 OK\r\n".encode())
            header = 'Content-Type: text/html \r\n'
            connectionSocket.send(header.encode())
            connectionSocket.send(f"\r\n".encode())
            connectionSocket.send(web_Page)  # Send the final response with all parts of header.
            connectionSocket.close()  # To closes a connectionSocket socket.

        elif requestedFileName.endswith(".css"):
            # If the request is a .css then the server should send the css file with Content-Type: text/css.
            try:
                reqFile = open(requestedFileName, "rb")
            except Exception as exp:
                reqFile = open('style.css', "rb")
            web_Page = reqFile.read()
            reqFile.close()
            connectionSocket.send(f"HTTP/1.1 200 OK\r\n".encode())
            header = 'Content-Type: text/css \r\n'
            connectionSocket.send(header.encode())
            connectionSocket.send(f"\r\n".encode())
            connectionSocket.send(web_Page)  # Send the final response with all parts of header.
            connectionSocket.close()  # To closes a connectionSocket socket.

        elif requestedFileName.endswith(".png"):
            # If the request is a .png then the server should send the png image with Content-Type: image/png.

            connectionSocket.send(f"HTTP/1.1 200 ok \r\n".encode())
            try:
                reqFile = open(requestedFileName, "rb")
            except Exception as exp:
                reqFile = open('images/birzeit_logo.png', "rb")
            response = reqFile.read()
            header = 'Content-Type: image/png \r\n'
            connectionSocket.send(header.encode())
            connectionSocket.send(f"\r\n".encode())
            connectionSocket.send(response)
            connectionSocket.close()

        elif requestedFileName.endswith(".jpg"):
            # If the request is a .jpg then the server should send the jpg image with Content-Type: image/jpg.
            connectionSocket.send(f"HTTP/1.1 200 ok \r\n".encode())
            try:
                reqFile = open(requestedFileName, "rb")
            except Exception as exp:
                reqFile = open('images/EyabGhafre.jpg', "rb")
            response = reqFile.read()
            connectionSocket.send("Content-Type: image/jpg \r\n".encode())
            connectionSocket.send(f"\r\n".encode())
            connectionSocket.send(response)
            connectionSocket.close()
        else:
            raise error

    except Exception as somethingDontWorked:
        print("Error !")
        # If the request is wrong or the file doesn’t exist the server should return a 404 HTML webpage
        header = 'HTTP/1.1 404 Not Found\n\n'
        file = open('404_Page.html', 'rb')
        # Set the Data
        web_Page = file.read()
        file.close()

        # put the addresses
        c_ip = str(address[0])
        c_port = str(address[1])
        s_ip = str(connectionSocket.getsockname()[0])
        s_port = str(connectionSocket.getsockname()[1])
        web_Page = web_Page.replace(b'{{c_ip}}', bytes(c_ip, 'UTF-8'))
        web_Page = web_Page.replace(b'{{c_port}}', bytes(c_port, 'UTF-8'))
        web_Page = web_Page.replace(b'{{s_ip}}', bytes(s_ip, 'UTF-8'))
        web_Page = web_Page.replace(b'{{s_port}}', bytes(s_port, 'UTF-8'))

        connectionSocket.send("HTTP/1.1 404 Not Found\r\n".encode())
        connectionSocket.send("Content-Type: text/html \r\n".encode())
        connectionSocket.send("\r\n".encode())
        connectionSocket.send(web_Page)
        connectionSocket.close()  # To closes a connectionSocket socket.

    # Print the HTTP request on the terminal window.
    print(sentence.strip())
    print(header.strip())
    print(datetime.now().strftime("%a, %d %b %Y %H:%M:%S %Z"))
    print("\n\n\n")
