variable "region" {
  default = "eu-west-3"
}

variable "vpc_cidr" {
  default = "10.0.0.0/16"
}


variable "ami_id" {
  description = "AMI ID for EC2 instances"
  default     = "ami-09be70e689bddcef5"
}

variable "instance_type" {
  default = "t2.medium"
}