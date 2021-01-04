variable "do_token" {}
variable "ovh_app_key" {}
variable "ovh_app_secret" {}
variable "ovh_consumer_key" {}

provider "digitalocean" {
  token = var.do_token
}

provider "ovh" {
  endpoint           = "ovh-eu"
  application_key    = var.ovh_app_key
  application_secret = var.ovh_app_secret
  consumer_key       = var.ovh_consumer_key
}

resource "digitalocean_ssh_key" "default" {
  name       = "terraform"
  public_key = file("/home/noah/.ssh/id_slashsg.pub")
}

resource "digitalocean_droplet" "main" {
	image    = "ubuntu-20-10-x64"
	name     = "main"
	region   = "sfo3"
	size     = "s-1vcpu-1gb"
	ssh_keys = [ digitalocean_ssh_key.default.fingerprint ]
}

resource "digitalocean_firewall" "firewall" {
	name = "firewall"

	droplet_ids = [ digitalocean_droplet.main.id ]

	inbound_rule {
		protocol         = "tcp"
		port_range       = "22"
		source_addresses = [ "0.0.0.0/0", "::/0" ]
	}

	inbound_rule {
		protocol         = "tcp"
		port_range       = "80"
		source_addresses = [ "0.0.0.0/0", "::/0" ]
	}

	inbound_rule {
		protocol         = "tcp"
		port_range       = "443"
		source_addresses = [ "0.0.0.0/0", "::/0" ]
	}

	outbound_rule {
		protocol              = "tcp"
		port_range            = "all"
		destination_addresses = [ "0.0.0.0/0" ]
	}

	outbound_rule {
		protocol              = "udp"
		port_range            = "all"
		destination_addresses = [ "0.0.0.0/0" ]
	}
}

resource "null_resource" "ansible-provision" {
  depends_on = [ digitalocean_droplet.main ]

  provisioner "local-exec" {
    command = "echo '${digitalocean_droplet.main.name} ansible_host=${digitalocean_droplet.main.ipv4_address} ansible_ssh_user=root ansible_python_interpreter=/usr/bin/python3' > hosts"
  }

  triggers = {
    always_run = "${timestamp()}"
  }
}

resource "ovh_domain_zone_record" "slashsourcegraph" {
    depends_on = [ digitalocean_droplet.main ]

    zone = "noahsc.xyz"
    subdomain = "slashsourcegraph"
    fieldtype = "A"
    ttl = "3600"
    target = "${digitalocean_droplet.main.ipv4_address}"
}