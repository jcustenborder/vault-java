listener "tcp" {
  address = "vault:8200"
  tls_cert_file = "/docker/ssl/vault.pem"
  tls_key_file  = "/docker/ssl/vault.key"
}

disable_mlock = true

backend "file" {
  path = "/var/lib/vault/data"
}