How to setup the plugin.

🔧 On the Velocity Proxy:\
In velocity.toml:
```toml
online-mode = true
forwarding-secret = "supersecret"
```
> 🔒 Important: Keep forwarding-secret safe and shared only with backend servers.

🔧 On the Paper Server:\
* In server.properties:
```ini
online-mode=false
```

* In paper-global.yml:
```yaml
settings:
  velocity-support:
    enabled: true
    online-mode: true
    secret: "supersecret"
```

* In spigot.yml:
```yaml
settings:
  bungeecord: false
```