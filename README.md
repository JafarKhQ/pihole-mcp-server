# Pi-hole MCP Server
STDIO MCP Server for Pi-hole in Java

## Features
- Core AI tools
    - `Restart Pi-hole FTL Service` — restart the Pi-hole FTL DNS service to apply changes or recover.
    - `Get Pi-hole DNS Blocking Status` — retrieve current blocking mode and remaining timer.
    - `Set Pi-hole DNS Blocking Status` — enable/disable DNS blocking; optionally set a disable duration (seconds).
    - `Add New Domain` — add domains to allowlist/denylist (supports `exact` and `regex` kinds) with validation and optional comment.
    - `Retrive Pi-hole Statistics` — fetch statistics like total queries, blocked queries, and top domains.
    - `Retrive Pi-hole System Information` — get system info including CPU load, memory usage, blocking percentage, and uptime.

## Running the Server
To run the MCP server, you need to have Java 21 installed on your machine.

1. Download the Pi-hole MCP server JAR file from the [releases page]()
2. Add MCP server configuration to your AI application client.

JSON configuration example:
```json
{
  "pihole-mcp-server-java": {
    "command": "java",
    "args": [
      "-jar",
      "/absolute/path/to/pihole-mcp.jar"
    ],
    "env": {
      "PIHOLE_PORT": "8080",
      "PIHOLE_HOST": "192.168.1.2",
      "PIHOLE_APP_PASSWORD": "your_app_password"
    }
  }
}
```
YAML configuration example:
```yaml
name: pihole-mcp-server-java
version: 0.0.1
schema: v1
mcpServers:
  - name: pihole-mcp-server-java
    command: java
    args:
      - -jar
      - /absolute/path/to/pihole-mcp.jar
    env: {
      "PIHOLE_PORT": "8080",
      "PIHOLE_HOST": "192.168.1.2",
      "PIHOLE_APP_PASSWORD": "your_app_password"
    }
```
## Environment Variables
Make sure to set the following environment variables:
- `PIHOLE_PORT`: (Optional) The port on which your Pi-hole instance is running (default is 80).
- `PIHOLE_HOST`: The IP address or hostname of your Pi-hole instance.
- `PIHOLE_APP_PASSWORD`: The application password for your Pi-hole instance.

## Building from Source
### Pre requisites:
- Java JDK 21
- Maven

### To build the Pi-hole MCP server from source, follow these steps:
1. Clone/Download the repository.
2. Build the project using Maven:
   ```bash
   mvn clean package
   ```
3. Run the server using the command mentioned above.

## License
This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details
