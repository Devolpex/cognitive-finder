# Docker Setup for Multi-Service Application

This document explains the services configured in the `compose.yml` file and their roles in the multi-service application. Each service, its configurations, and networking details are outlined.

---

## **Services**

### **PostgreSQL**
- **Purpose**: Provides a PostgreSQL database for storing application data.
- **Configuration**:
  - **Image**: `postgres`
  - **Environment Variables**:
    - `POSTGRES_USER`: `user`
    - `POSTGRES_PASSWORD`: `password`
    - `PGDATA`: `/var/lib/postgresql/data`
  - **Volumes**:
    - Stores PostgreSQL data persistently in `postgresql-data`.
    - Executes initialization script from `./docker/init.sql`.
  - **Ports**: Exposes PostgreSQL on `5432`.
  - **Network**: Connected to `cog-net`.

---

### **MongoDB**
- **Purpose**: Provides a NoSQL database for unstructured or semi-structured data.
- **Configuration**:
  - **Image**: `mongo`
  - **Environment Variables**:
    - `MONGO_INITDB_ROOT_USERNAME`: `user`
    - `MONGO_INITDB_ROOT_PASSWORD`: `password`
  - **Volumes**:
    - Stores MongoDB data persistently in `mongodb-data`.
    - Executes initialization script from `./docker/init.js`.
  - **Ports**: Exposes MongoDB on `27020`.
  - **Network**: Connected to `cog-net`.

---

### **Consul**
- **Purpose**: Provides service discovery and configuration management.
- **Configuration**:
  - **Image**: `consul:1.15.4`
  - **Command**: Runs as a server with a web UI and DNS.
  - **Environment Variables**:
    - `CONSUL_BIND_INTERFACE`: `eth0`
  - **Volumes**: Persistent data storage in `consul-data`.
  - **Ports**:
    - `8500`: Consul HTTP API and Web UI.
    - `8600/udp`: Consul DNS for service discovery.
  - **Network**: Connected to `cog-net`.

---

### **Keycloak MySQL**
- **Purpose**: MySQL database for Keycloak's data storage.
- **Configuration**:
  - **Image**: `mysql:8`
  - **Environment Variables**:
    - `MYSQL_ROOT_PASSWORD`: `root`
    - `MYSQL_DATABASE`: `db_keycloak`
    - `MYSQL_USER`: `user-dev`
    - `MYSQL_PASSWORD`: `password`
  - **Volumes**: Persistent data storage in `keycloak-mysql-data`.
  - **Ports**: Exposes MySQL on `3310`.
  - **Network**: Connected to `cog-net`.

---

### **Keycloak**
- **Purpose**: Provides Identity and Access Management (IAM) features.
- **Configuration**:
  - **Image**: `quay.io/keycloak/keycloak:26.0.7`
  - **Command**: Runs Keycloak in development mode.
  - **Environment Variables**:
    - Admin credentials: `KC_BOOTSTRAP_ADMIN_USERNAME` and `KC_BOOTSTRAP_ADMIN_PASSWORD`.
    - Database configuration: `KC_DB`, `KC_DB_USERNAME`, `KC_DB_PASSWORD`, etc.
  - **Ports**: Exposes Keycloak on `8181`.
  - **Dependencies**: Starts after `keycloak-mysql`.
  - **Network**: Connected to `cog-net`.

---

### **Traccar MySQL**
- **Purpose**: MySQL database for Traccar's data storage.
- **Configuration**:
  - **Image**: `mysql:8.0`
  - **Environment Variables**:
    - `MYSQL_ROOT_PASSWORD`: `root`
    - `MYSQL_DATABASE`: `cog_traccar_db`
    - `MYSQL_USER`: `traccar`
    - `MYSQL_PASSWORD`: `traccar`
  - **Volumes**:
    - Persistent data storage in `traccar_mysql`.
    - Executes initialization script from `./docker/init-traccar.sql`.
  - **Ports**: Exposes MySQL on `3311`.
  - **Network**: Connected to `cog-net`.

---

### **Traccar**
- **Purpose**: Open-source GPS tracking system for device management.
- **Configuration**:
  - **Image**: `traccar/traccar:latest`
  - **Ports**:
    - `8082`: Traccar web UI.
    - `6000-6150`: GPS device communication (mapped to `5000-5150` internally).
  - **Volumes**:
    - Logs stored persistently in `traccar_logs`.
    - Configuration file mounted from `./docker/traccar.xml` as read-only.
  - **Environment Variables**:
    - Allocates 1GB of memory for the JVM with `JAVA_OPTS=-Xms1g -Xmx1g`.
  - **Dependencies**: Starts after `traccar_mysql`.
  - **Network**: Connected to `cog-net`.

---

## **Networks**
- **Name**: `cog-net`
- **Driver**: Bridge
- **Purpose**: Enables seamless communication between all services.

---

## **Volumes**
- **Persistent Volumes**:
  - `postgresql-data`: Stores PostgreSQL data.
  - `mongodb-data`: Stores MongoDB data.
  - `consul-data`: Stores Consul service data.
  - `keycloak-mysql-data`: Stores Keycloak MySQL data.
  - `traccar_mysql`: Stores Traccar MySQL data.
  - `traccar_logs`: Stores Traccar logs.

---

## **Usage Instructions**
1. **Start the Services**:
   Run the following command to start all services:
   ```bash
   docker-compose up -d
   ```

2. **Stop the Services**:
   Shut down all running services:
   ```bash
   docker-compose down
   ```

3. **Verify Services**:
   - Access PostgreSQL on port `5432`.
   - Access MongoDB on port `27020`.
   - Access Consul UI at [http://localhost:8500](http://localhost:8500).
   - Access Keycloak at [http://localhost:8181](http://localhost:8181).
   - Access Traccar at [http://localhost:8082](http://localhost:8082).

4. **Custom Initialization**:
   - Modify `init.sql` or `init.js` for PostgreSQL and MongoDB initialization.
   - Update `traccar.xml` for Traccar-specific configuration.
