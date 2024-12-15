provider "aws" {
  region = var.region
}

# Create a VPC
resource "aws_vpc" "cog_microservices_vpc" {
  cidr_block           = var.vpc_cidr
  enable_dns_support   = true
  enable_dns_hostnames = true
  tags = {
    Name    = "COG Microservices VPC"
    Project = "cog_microservices"
  }
}

resource "aws_security_group" "cog_microservices_sg" {
  name        = "cog_microservices_sg"
  description = "Allow SSH, HTTP, HTTPS, SMTP, SMTPS, and Custom ports"
  vpc_id      = aws_vpc.cog_microservices_vpc.id

  # Allow SSH access from anywhere
  ingress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 22
    to_port     = 22
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Allow HTTP access from anywhere
  ingress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 80
    to_port     = 80
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Allow HTTPS access from anywhere
  ingress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 443
    to_port     = 443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Allow Kubernetes API access from anywhere (port 6443)
  ingress {
    from_port   = 6443
    to_port     = 6443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 6443
    to_port     = 6443
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Allow custom port ranges 3000-10000
  ingress {
    from_port   = 3000
    to_port     = 10000
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 3000
    to_port     = 10000
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Allow custom port ranges 30000-32767
  ingress {
    from_port   = 30000
    to_port     = 32767
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 30000
    to_port     = 32767
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Allow all outbound traffic
  egress {
    from_port   = 0
    to_port     = 0
    protocol    = "-1"
    cidr_blocks = ["0.0.0.0/0"]
  }

  # Allow TCP and UDP traffic 5000-5200 for Traccar
  ingress {
    from_port   = 5000
    to_port     = 5200
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }
  ingress {
    from_port   = 5000
    to_port     = 5200
    protocol    = "udp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 5000
    to_port     = 5200
    protocol    = "tcp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  egress {
    from_port   = 5000
    to_port     = 5200
    protocol    = "udp"
    cidr_blocks = ["0.0.0.0/0"]
  }

  tags = {
    Name    = "COG Microservices Security Group"
    Project = "cog_microservices"
  }
}


# K8S Subnet
resource "aws_subnet" "cog_microservices_k8s_subnet" {
  vpc_id                  = aws_vpc.cog_microservices_vpc.id
  cidr_block              = "10.0.1.0/24"
  map_public_ip_on_launch = true

  tags = {
    Name    = "k8s-subnet"
    Project = "cog_microservices"
  }
}

# Traccar Subnet (New subnet for Traccar server)
resource "aws_subnet" "cog_microservices_traccar_subnet" {
  vpc_id                  = aws_vpc.cog_microservices_vpc.id
  cidr_block              = "10.0.2.0/24"
  map_public_ip_on_launch = true

  tags = {
    Name    = "traccar-subnet"
    Project = "cog_microservices"
  }
}

# Create an Internet Gateway
resource "aws_internet_gateway" "cog_microservices_igw" {
  vpc_id = aws_vpc.cog_microservices_vpc.id

  tags = {
    Name    = "COG Microservices IGW"
    Project = "cog_microservices"
  }
}

# Create a Route Table
resource "aws_route_table" "cog_microservices_rt" {
  vpc_id = aws_vpc.cog_microservices_vpc.id

  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.cog_microservices_igw.id
  }

  tags = {
    Name    = "COG Microservices Route Table"
    Project = "cog_microservices"
  }
}

resource "aws_route_table_association" "cog_microservices_rt_assoc" {
  subnet_id      = aws_subnet.cog_microservices_k8s_subnet.id
  route_table_id = aws_route_table.cog_microservices_rt.id
}

resource "aws_route_table_association" "cog_microservices_rt_assoc_traccar" {
  subnet_id      = aws_subnet.cog_microservices_traccar_subnet.id
  route_table_id = aws_route_table.cog_microservices_rt.id
}

# Create a Key Pair
resource "aws_key_pair" "cog_microservices_key_pair" {
  key_name   = "cog_microservices_key_pair"
  public_key = file("~/.ssh/id_rsa_cog_microservice.pub")
}

# Create an EC2 Instance for Kubernetes Cluster
resource "aws_instance" "cog_microservices_k8s_instance" {
  count                  = 3
  ami                    = var.ami_id
  instance_type          = var.instance_type
  key_name               = aws_key_pair.cog_microservices_key_pair.key_name
  subnet_id              = aws_subnet.cog_microservices_k8s_subnet.id
  vpc_security_group_ids = [aws_security_group.cog_microservices_sg.id]

  tags = {
    Name    = "k8s-${count.index == 0 ? "master" : "worker-${count.index}"}"
    Role    = count.index == 0 ? "master" : "worker"
    Project = "cog_microservices"
  }
}


# Create an EC2 Instance for Traccar Server
resource "aws_instance" "traccar_instance" {
  ami                    = var.ami_id
  instance_type          = var.instance_type
  key_name               = aws_key_pair.cog_microservices_key_pair.key_name
  subnet_id              = aws_subnet.cog_microservices_traccar_subnet.id
  vpc_security_group_ids = [aws_security_group.cog_microservices_sg.id]

  tags = {
    Name    = "Traccar-Server"
    Project = "cog_microservices"
  }
}

# Output
output "instance_ips" {
  description = "IP addresses of the Kubernetes master and worker instances"
  value = {
    master  = aws_instance.cog_microservices_k8s_instance[0].public_ip
    worker1 = aws_instance.cog_microservices_k8s_instance[1].public_ip
    worker2 = aws_instance.cog_microservices_k8s_instance[2].public_ip
    traccar = aws_instance.traccar_instance.public_ip
  }
}

