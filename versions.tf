terraform {
  required_providers {
    digitalocean = {
      source = "digitalocean/digitalocean"
    }
    ovh = {
      source = "ovh/ovh"
    }
  }
  required_version = ">= 0.14"
}