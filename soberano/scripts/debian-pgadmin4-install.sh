#!/bin/bash
# pgAdmin 4 Installation Script for Debian 13 Trixie (Corregido)
# Run with: sudo bash install_pgadmin4.sh

set -e  # Exit on error

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

echo -e "${GREEN}=== pgAdmin 4 Installation Script for Debian 13 Trixie ===${NC}"

# Check if running as root
if [[ $EUID -ne 0 ]]; then
   echo -e "${RED}This script must be run as root. Use: sudo bash $0${NC}"
   exit 1
fi

# Update system
echo -e "${YELLOW}[1/6] Updating system packages...${NC}"
apt update && apt upgrade -y

# Install prerequisites (removidos software-properties-common que no existe en Trixie)
echo -e "${YELLOW}[2/6] Installing prerequisites...${NC}"
apt install -y curl gnupg apt-transport-https lsb-release ca-certificates

# Add pgAdmin public key
echo -e "${YELLOW}[3/6] Adding pgAdmin GPG key...${NC}"
curl -fsS https://www.pgadmin.org/static/packages_pgadmin_org.pub | \
    gpg --dearmor -o /usr/share/keyrings/packages-pgadmin-org.gpg

# Add pgAdmin repository for Trixie
echo -e "${YELLOW}[4/6] Adding pgAdmin APT repository for Trixie...${NC}"
sh -c 'echo "deb [signed-by=/usr/share/keyrings/packages-pgadmin-org.gpg] https://ftp.postgresql.org/pub/pgadmin/pgadmin4/apt/trixie pgadmin4 main" > /etc/apt/sources.list.d/pgadmin4.list'

# Update package list with new repository
echo -e "${YELLOW}[5/6] Updating package list...${NC}"
apt update

# Install pgAdmin4
echo -e "${YELLOW}[6/6] Installing pgAdmin4...${NC}"
echo -e "${GREEN}Select installation type:${NC}"
echo "1) Desktop mode only (pgadmin4-desktop)"
echo "2) Web mode only (pgadmin4-web)"
echo "3) Both desktop and web modes (pgadmin4)"
read -p "Enter choice [1-3] (default: 3): " choice

case $choice in
    1)
        apt install -y pgadmin4-desktop
        echo -e "${GREEN}pgAdmin4 Desktop installed successfully!${NC}"
        echo "Launch from application menu or run: pgadmin4"
        ;;
    2)
        apt install -y pgadmin4-web
        echo -e "${GREEN}pgAdmin4 Web installed successfully!${NC}"
        echo -e "${YELLOW}Configuring web server...${NC}"
        /usr/pgadmin4/bin/setup-web.sh
        echo "Access at: http://localhost/pgadmin4 or http://localhost:5050"
        ;;
    3)
        apt install -y pgadmin4
        echo -e "${GREEN}pgAdmin4 (both modes) installed successfully!${NC}"
        echo -e "${YELLOW}Configuring web server...${NC}"
        if [ -f /usr/pgadmin4/bin/setup-web.sh ]; then
            /usr/pgadmin4/bin/setup-web.sh
        else
            echo -e "${YELLOW}Web setup script not found. You may need to run it manually later.${NC}"
        fi
        echo "Desktop: Launch from application menu or run 'pgadmin4'"
        echo "Web: Access at http://localhost/pgadmin4 or http://localhost:5050"
        ;;
esac

echo -e "${GREEN}=== Installation Complete! ===${NC}"