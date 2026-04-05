#!/bin/bash
# OpenJDK 17 (Temurin) Installation Script for Debian 13 Trixie
# Run with: sudo bash install_openjdk17_trixie.sh

set -e  # Exit on error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

echo -e "${GREEN}=== OpenJDK 17 (Temurin) Installation for Debian 13 Trixie ===${NC}"

# Check if running as root
if [[ $EUID -ne 0 ]]; then
   echo -e "${RED}This script must be run as root. Use: sudo bash $0${NC}"
   exit 1
fi

# Update system
echo -e "${YELLOW}[1/6] Updating system packages...${NC}"
apt update && apt upgrade -y

# Install prerequisites
echo -e "${YELLOW}[2/6] Installing prerequisites...${NC}"
apt install -y ca-certificates curl gpg

# Add Adoptium GPG key
echo -e "${YELLOW}[3/6] Adding Adoptium GPG key...${NC}"
curl -fsSL https://packages.adoptium.net/artifactory/api/gpg/key/public | \
    gpg --dearmor -o /usr/share/keyrings/adoptium.gpg

# Add Adoptium repository for Trixie
echo -e "${YELLOW}[4/6] Adding Adoptium repository for Trixie...${NC}"
cat <<EOF | tee /etc/apt/sources.list.d/adoptium.sources
Types: deb
URIs: https://packages.adoptium.net/artifactory/deb
Suites: trixie
Components: main
Architectures: $(dpkg --print-architecture)
Signed-By: /usr/share/keyrings/adoptium.gpg
EOF

# Update package list with new repository
echo -e "${YELLOW}[5/6] Updating package list...${NC}"
apt update

# Install Temurin 17 JDK
echo -e "${YELLOW}[6/6] Installing Temurin 17 JDK...${NC}"
apt install -y temurin-17-jdk

# Verify installation
echo -e "${YELLOW}Verifying installation...${NC}"
if java -version 2>&1 | grep -q "Temurin-17\|version \"17"; then
    echo -e "${GREEN}✓ OpenJDK 17 (Temurin) installed successfully!${NC}"
    java -version
else
    echo -e "${RED}✗ Installation verification failed${NC}"
    exit 1
fi

# Configure as default Java version
echo -e "${YELLOW}Configuring as default Java version...${NC}"
if command -v update-alternatives &> /dev/null; then
    # Find Temurin 17 path
    TEMURIN_PATH=$(update-alternatives --list java | grep -i temurin-17 | head -1)
    if [ -n "$TEMURIN_PATH" ]; then
        update-alternatives --set java "$TEMURIN_PATH" 2>/dev/null || true
        echo -e "${GREEN}✓ Java 17 set as default${NC}"
    fi
fi

# Set JAVA_HOME
echo -e "${YELLOW}Setting JAVA_HOME...${NC}"
JAVA_HOME=$(dirname $(dirname $(readlink -f $(which java))))

# Add to /etc/environment
if ! grep -q "JAVA_HOME=" /etc/environment 2>/dev/null; then
    echo "JAVA_HOME=\"$JAVA_HOME\"" >> /etc/environment
    echo -e "${GREEN}✓ JAVA_HOME added to /etc/environment${NC}"
fi

# Add to .bashrc for root
if ! grep -q "JAVA_HOME=" /root/.bashrc 2>/dev/null; then
    echo "export JAVA_HOME=$JAVA_HOME" >> /root/.bashrc
    echo 'export PATH=$PATH:$JAVA_HOME/bin' >> /root/.bashrc
    echo -e "${GREEN}✓ JAVA_HOME added to /root/.bashrc${NC}"
fi

echo -e "${GREEN}=== Installation Complete! ===${NC}"
echo -e "${BLUE}Java Version:${NC}"
java -version
echo -e "${BLUE}JAVA_HOME:${NC} $JAVA_HOME"
echo -e "${YELLOW}Note: You may need to restart your terminal or run 'source ~/.bashrc'${NC}"