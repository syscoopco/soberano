#! /bin/bash

set -e  # Exit on error

# Check if running as root
if [ "$EUID" -ne 0 ]; then 
    echo "Please run as root (use sudo)"
    exit 1
fi

# PostgreSQL 18 Installation Script for Ubuntu 24.04
echo "=== PostgreSQL 18 Installation for Ubuntu 24.04 ==="

# Install PostgreSQL 18
echo "Installing PostgreSQL 18..."
apt update
apt install -y wget curl gnupg2 lsb-release

# Add PostgreSQL repository
echo "Adding PostgreSQL repository..."
sh -c 'echo "deb [signed-by=/usr/share/keyrings/postgresql.gpg] https://apt.postgresql.org/pub/repos/apt $(lsb_release -cs)-pgdg main" > /etc/apt/sources.list.d/pgdg.list'

# Add GPG key
wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | gpg --dearmor -o /usr/share/keyrings/postgresql.gpg

# Install PostgreSQL 18
apt update
apt install -y postgresql-18 postgresql-client-18

# Start and enable PostgreSQL service
echo "Starting PostgreSQL service..."
systemctl start postgresql
systemctl enable postgresql

# Wait a moment for service to be ready
sleep 3

# Check service status
echo "PostgreSQL service status:"
systemctl status postgresql --no-pager | head -10

# Set password for postgres user
echo "Setting password for postgres database user..."
read -sp "Enter password for postgres user (or press Enter for random password): " USER_INPUT
echo

if [ -z "$USER_INPUT" ]; then
    # Generate random password
    POSTGRES_PASSWORD=$(openssl rand -base64 16)
    echo "Generated random password"
else
    POSTGRES_PASSWORD=$USER_INPUT
fi

# Set the password using psql
sudo -u postgres psql -c "ALTER USER postgres WITH PASSWORD '$POSTGRES_PASSWORD';"

# Enable password authentication (modify pg_hba.conf)
echo "Configuring password authentication..."
sed -i 's/peer\|md5/trust/g' /etc/postgresql/18/main/pg_hba.conf
sed -i 's/local.*all.*all.*peer/local   all             all                                     trust/' /etc/postgresql/18/main/pg_hba.conf

# Restart PostgreSQL to apply changes
systemctl restart postgresql

echo "=========================================="
echo "PostgreSQL 18 Installation Complete!"
echo "PostgreSQL User: postgres"
echo "Password: $POSTGRES_PASSWORD"
echo "=========================================="
echo ""
echo "To connect: psql -U postgres"
echo "Service: postgresql"
echo "Data directory: /var/lib/postgresql/18/main"
echo "Config directory: /etc/postgresql/18/main"

echo "=== Installing pgAdmin 4 ==="

# Install prerequisites
echo "Installing prerequisites..."
apt install -y software-properties-common apt-transport-https

# Add pgAdmin repository
echo "Adding pgAdmin repository..."
curl -fsSL https://www.pgadmin.org/static/packages_pgadmin_org.pub | gpg --dearmor -o /usr/share/keyrings/pgadmin.gpg
echo "deb [signed-by=/usr/share/keyrings/pgadmin.gpg] https://ftp.postgresql.org/pub/pgadmin/pgadmin4/apt/$(lsb_release -cs) pgadmin4 main" > /etc/apt/sources.list.d/pgadmin4.list

# Update package list with new repositories
echo "Updating package list with new repositories..."
apt update

# Install desktop mode
echo "Installing pgAdmin 4 (desktop mode)..."
apt install -y pgadmin4-desktop

# Display installation summary
echo -e "\n=========================================="
echo "pgAdmin 4 INSTALLATION COMPLETE!"
echo "=========================================="
echo "Version: $(pgadmin4 --version 2>/dev/null || echo "Latest")"
echo "=========================================="

# Install ifconfig and Tomcat 11 on Ubuntu 24.04 with minor version selection
echo "=== Installing net-tools and Tomcat 11 ==="

# Display available Tomcat 11 versions
echo ""
echo "Available Tomcat 11 minor versions (stable releases):"
echo "  11.0.0  - Initial release"
echo "  11.0.1  - Maintenance"
echo "  (Check https://tomcat.apache.org/download-11.cgi for latest)"
echo ""

# Ask for minor version
read -p "Enter Tomcat 11 minor version (e.g., 11.0.0 or 11.0.1): " TOMCAT_VERSION

# Validate version input
if [[ ! "$TOMCAT_VERSION" =~ ^11\.[0-9]+\.[0-9]+$ ]]; then
    echo "Invalid version format. Must start with 11.x.x"
    echo "Examples: 11.0.0, 11.0.1"
    exit 1
fi

# Extract major version to ensure it's 11
TOMCAT_MAJOR=$(echo "$TOMCAT_VERSION" | cut -d. -f1)
if [ "$TOMCAT_MAJOR" -ne 11 ]; then
    echo "Error: This script only installs Tomcat 11. You entered version: $TOMCAT_VERSION"
    exit 1
fi

# Confirm installation
echo ""
echo "Proceeding with installation of:"
echo "  - ifconfig (net-tools)"
echo "  - Tomcat $TOMCAT_VERSION"
read -p "Continue? (y/n): " CONTINUE

if [[ ! "$CONTINUE" =~ ^[Yy]$ ]]; then
    echo "Installation cancelled."
    exit 0
fi

# 1. Install ifconfig (net-tools)
echo "Installing ifconfig (net-tools)..."
apt install -y net-tools

# Verify ifconfig installation
echo -e "\nVerifying ifconfig installation:"
ifconfig --version 2>/dev/null || echo "ifconfig installed successfully"

# 2. Install Java (Tomcat 11 requires Java 17+)
echo -e "\nInstalling Java (OpenJDK 17)..."
apt install -y openjdk-17-jdk

# Verify Java installation
echo -e "\nJava version:"
java --version

# 3. Create Tomcat user
echo -e "\nCreating tomcat system user..."
if ! id -u tomcat >/dev/null 2>&1; then
    useradd -r -m -U -d /opt/tomcat -s /bin/false tomcat
    echo "Tomcat user created"
else
    echo "Tomcat user already exists"
fi

# 4. Download and install Tomcat 11
echo -e "\nInstalling Tomcat $TOMCAT_VERSION..."
TOMCAT_DIR="/opt/tomcat"

# Create Tomcat directory
mkdir -p "$TOMCAT_DIR"

# Construct download URL for Tomcat 11
# Format: https://downloads.apache.org/tomcat/tomcat-11/v11.0.0/bin/apache-tomcat-11.0.0.tar.gz
VERSION_PATH="v$TOMCAT_VERSION"
DOWNLOAD_URL="https://downloads.apache.org/tomcat/tomcat-11/${VERSION_PATH}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz"

echo "Download URL: $DOWNLOAD_URL"

# Download Tomcat
echo "Downloading Tomcat ${TOMCAT_VERSION}..."
if ! wget -q --spider "$DOWNLOAD_URL"; then
    echo "Warning: Cannot verify URL existence. Attempting download anyway..."
fi

if ! wget -q "$DOWNLOAD_URL" -O /tmp/tomcat.tar.gz; then
    echo "Failed to download Tomcat $TOMCAT_VERSION from primary URL"
    echo "Trying archive URL..."
    
    # Try archive URL
    ARCHIVE_URL="https://archive.apache.org/dist/tomcat/tomcat-11/${VERSION_PATH}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz"
    echo "Trying: $ARCHIVE_URL"
    
    if ! wget -q "$ARCHIVE_URL" -O /tmp/tomcat.tar.gz; then
        echo "Failed to download Tomcat $TOMCAT_VERSION"
        echo "Please verify the version exists at:"
        echo "https://tomcat.apache.org/download-11.cgi"
        echo ""
        echo "Available versions might include:"
        echo "  11.0.0, 11.0.1"
        exit 1
    fi
fi

# Extract Tomcat
echo "Extracting Tomcat..."
tar -xzf /tmp/tomcat.tar.gz -C /opt/tomcat --strip-components=1

# Set permissions
echo "Setting permissions..."
chown -R tomcat:tomcat "$TOMCAT_DIR"
chmod -R 755 "$TOMCAT_DIR"
chmod +x "$TOMCAT_DIR"/bin/*.sh

# 5. Create systemd service for auto-start
echo -e "\nCreating systemd service for auto-start..."
JAVA_HOME="/usr/lib/jvm/java-17-openjdk-amd64"

cat > /etc/systemd/system/tomcat.service << EOF
[Unit]
Description=Apache Tomcat $TOMCAT_VERSION
After=network.target

[Service]
Type=forking
User=tomcat
Group=tomcat

Environment="JAVA_HOME=$JAVA_HOME"
Environment="CATALINA_PID=${TOMCAT_DIR}/temp/tomcat.pid"
Environment="CATALINA_HOME=${TOMCAT_DIR}"
Environment="CATALINA_BASE=${TOMCAT_DIR}"

ExecStart=${TOMCAT_DIR}/bin/startup.sh
ExecStop=${TOMCAT_DIR}/bin/shutdown.sh

Restart=on-failure
RestartSec=10

[Install]
WantedBy=multi-user.target
EOF

# 6. Enable and start Tomcat service
echo "Enabling Tomcat service..."
systemctl daemon-reload
systemctl enable tomcat
systemctl start tomcat

# Wait a moment for service to start
echo "Waiting for Tomcat to start..."
sleep 5

# 7. Configure firewall (if ufw is active)
echo -e "\nConfiguring firewall..."
if systemctl is-active --quiet ufw; then
    ufw allow 8080/tcp
    ufw allow 8443/tcp
    echo "Firewall rule added for porta 8080 and 8443"
fi

# 9. Display installation summary
echo -e "\n=========================================="
echo "INSTALLATION COMPLETE!"
echo "=========================================="
echo "ifconfig: installed"
echo "Tomcat Version: $TOMCAT_VERSION"
echo "Installation directory: $TOMCAT_DIR"
echo "Service: tomcat (auto-start enabled)"
echo "User: tomcat"
echo "Java Home: $JAVA_HOME"
echo ""

# Check if Tomcat is running
if systemctl is-active --quiet tomcat; then
    echo -e "Tomcat Status: \033[0;32mRUNNING\033[0m"
    
    # Try to get listening port
    if netstat -tlnp 2>/dev/null | grep :8080 | grep -q java; then
        echo "Tomcat is listening on port 8080"
    fi
else
    echo -e "Tomcat Status: \033[0;31mNOT RUNNING\033[0m"
    echo "Check logs: tail -f $TOMCAT_DIR/logs/catalina.out"
fi

echo ""
IP_ADDRESS=$(hostname -I | awk '{print $1}' 2>/dev/null || echo "localhost")
echo "Test URLs:"
echo "  - Tomcat Home: http://$IP_ADDRESS:8080"
echo "  - Manager App: http://$IP_ADDRESS:8080/manager/html"
echo ""
echo "Management Commands:"
echo "  sudo systemctl start tomcat"
echo "  sudo systemctl stop tomcat"
echo "  sudo systemctl restart tomcat"
echo "  sudo systemctl status tomcat"
echo "  View logs: tail -f $TOMCAT_DIR/logs/catalina.out"
echo ""
echo "To verify ifconfig: ifconfig -a"
echo "=========================================="

# Cleanup
rm -f /tmp/tomcat.tar.gz

# Optional: Show quick verification
echo -e "\nQuick verification:"
echo "ifconfig output (first interface):"
ifconfig | head -20
echo ""
echo "Tomcat service status:"
systemctl status tomcat --no-pager | head -10

# Install latest ApacheDS using .bin installer on Ubuntu 24.04
echo "=== Installing Latest ApacheDS with .bin Installer ==="

wget -q https://downloads.apache.org/directory/apacheds/dist/2.0.0.AM27/apacheds-2.0.0.AM27-64bit.bin
chmod a+x apacheds-2.0.0.AM27-64bit.bin

./apacheds-2.0.0.AM27-64bit.bin

# Create systemd service for auto-start
echo -e "\nCreating systemd service for auto-start..."
cat > /etc/systemd/system/apacheds.service << EOF
[Unit]
Description=ApacheDS LDAP Server 2.0.0.AM27
After=network.target
Wants=network.target

[Service]
Type=forking
User=apacheds
Group=apacheds

ExecStart=/etc/init.d/apacheds-2.0.0.AM28-SNAPSHOT-default start
ExecStop=/etc/init.d/apacheds-2.0.0.AM28-SNAPSHOT-default stop
ExecReload=/etc/init.d/apacheds-2.0.0.AM28-SNAPSHOT-default restart

RestartSec=10
Restart=always

[Install]
WantedBy=multi-user.target
EOF

# Enable and start ApacheDS service
echo "Enabling ApacheDS service..."
systemctl daemon-reload
systemctl enable apacheds

echo "Starting ApacheDS service..."
systemctl start apacheds

# Wait for service to start
echo "Waiting for ApacheDS to start..."
for i in {1..20}; do
    if systemctl is-active --quiet apacheds || netstat -tln 2>/dev/null | grep -q :10389; then
        echo "ApacheDS started successfully!"
        break
    fi
    echo -n "."
    sleep 3
done

# Install LDAP utils
echo "Installing LDAP utilities..."
apt install -y ldap-utils

# Install Apache 2 web server and PHP
echo "Install Apache 2 web server and PHP..."
apt -y install apache2
systemctl start apache2
systemctl enable apache2

apt install -y php php-common php-mysql php-cgi php-mbstring php-curl php-gd php-xml php-xmlrpc php-pear
apt install -y libapache2-mod-php php-intl php-zip php-bz2 php-json php-bcmath php-gmp

a2enmod env rewrite dir mime headers setenvif ssl
systemctl restart apache2

# Install MySQL
apt install -y mysql-server
mysql_secure_installation




