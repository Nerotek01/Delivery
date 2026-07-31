# DeliveryMan

A high-performance Minecraft rewards plugin developed by Nerotek01. Provides a configurable daily-rewards menu with multi-backend persistence (MongoDB, MySQL, SQLite) and an optional Redis cache layer for clustered servers.

## Requirements

- Java 21 or higher
- Spigot, Paper, or any Bukkit-compatible server (1.8.8 and newer)
- Maven 3.6+ for building from source

## Supported Backends

| Backend | Description | Use Case |
|---------|-------------|----------|
| MongoDB | Document-oriented storage via the official MongoDB Java Driver | Recommended default for production |
| MySQL | Relational storage via HikariCP connection pool | Servers already running MySQL/MariaDB |
| SQLite | Embedded local file database | Single-node servers with no external database |
| Redis | In-memory cache layer | Optional accelerator for any of the above backends |

## Features

- Configurable rewards menu with adjustable row count (1 to 6 rows).
- Four reward types:
  - `NORMAL` - claimable rewards with a cooldown timer.
  - `UNIQUE` - one-time claimable rewards (no cooldown reset).
  - `MESSAGE` - rewards that display a multi-line message to the player.
  - `VOTE` - rewards linked to a vote site, granted automatically when a Votifier event is received.
- Cooldown system with configurable time units (days, hours, minutes, seconds) and human-readable countdown formatting in the menu lore.
- Per-reward permission gating with a custom no-permission message, sound, volume, and pitch.
- Per-reward firework explosion effect on claim.
- Console command execution on claim with `<player>` placeholder substitution.
- Automatic menu refresh every second for any player currently viewing the rewards menu (toggleable via `rewardsMenu.instantUpdate`).
- Join messages showing the number of available rewards (or a no-rewards message when zero are available).
- Multi-backend persistence abstraction (`Database` interface) with three production-ready implementations:
  - `SQLDatabase` - SQLite backend, JSON-serialized player data.
  - `MySQLDatabase` - HikariCP-backed MySQL/MariaDB backend.
  - `MongoDBDatabase` - MongoDB backend using the official sync driver.
- Optional Redis cache layer (`CachedDatabase`) implementing a write-through cache strategy:
  - Cache hit returns the player data without touching the backend.
  - Cache miss delegates to the backend and writes the result back to Redis.
  - All writes go to both the cache and the backend.
- Graceful degradation: if Redis is unavailable, the plugin continues to work with the configured backend only.
- PlaceholderAPI integration exposing the `%udm_rewards%` placeholder (returns the number of available rewards for the player).
- MVdWPlaceholderAPI integration via reflection, exposing the `udm_rewards` placeholder (no compile-time dependency required).
- NuVotifier integration for vote-based rewards.
- Custom API event `DeliveryPlayerLoadEvent` fired on the main thread whenever a player's data finishes loading.
- Custom plugin logger (`PluginLogger`) with configurable log levels (`NONE`, `SEVERE`, `WARNING`, `INFO`, `FINE`, `ALL`) and a debug mode.
- Thread-safe player data storage using `ConcurrentHashMap` for both claimed rewards and streak maps.
- Atomic shutdown procedure: on disable, all online players are synchronously saved to the backend before connections are closed.

## Commands

| Command | Permission | Description |
|---------|------------|-------------|
| `/deliveryman menu` | `deliveryman.menu` | Opens the rewards menu (players only). |
| `/deliveryman reload` | `deliveryman.admin` | Reloads `config.yml`, `lang.yml`, `rewards.yml`, addons, and the menu updater. |

Alias: `/udm`

Tab completion is provided for both subcommands and is permission-aware.

## Permissions

| Permission | Default | Description |
|-----------|---------|-------------|
| `deliveryman.admin` | op | Access to the reload subcommand. |
| `deliveryman.menu` | true | Access to open the rewards menu. |
| `deliveryman.message1` | true | Access to the message reward example. |
| `deliveryman.vote1` | true | Access to the vote reward example. |
| `deliveryman.unique1` | true | Access to the unique reward example. |
| `deliveryman.normal` | true | Access to the first normal reward. |
| `deliveryman.vip` | op | Access to the second normal reward. |
| `deliveryman.vip+` | op | Access to the third normal reward. |
| `deliveryman.mvip` | op | Access to the fourth normal reward. |
| `deliveryman.mvip+` | op | Access to the fifth normal reward. |

## Configuration

### config.yml

```yaml
debugMode: false
logLevel: INFO

addons:
  Votifier: false
  PlaceholderAPI: false
  MVdWPlaceholderAPI: false

interact:
  right: true
  left: true

rewardsMenu:
  instantUpdate: true
  rows: 5

database:
  type: MONGODB
  host: 127.0.0.1
  port: 27017
  database: DeliveryMan
  username: ""
  password: ""
  useSSL: false
  authSource: admin

redis:
  enabled: false
  host: 127.0.0.1
  port: 6379
  password: ""
  database: 0
  timeout: 5000
  prefix: "deliveryman:"
  ttl: 3600
```

### rewards.yml

Each reward is defined under the `rewards` key. The following fields are supported:

| Field | Required | Description |
|-------|----------|-------------|
| `id` | yes | Unique identifier for the reward. |
| `slot` | yes | Inventory slot (0 to rows*9 - 1). |
| `type` | yes | One of `NORMAL`, `UNIQUE`, `MESSAGE`, `VOTE`. |
| `permission` | yes | Permission required to claim. |
| `countdown` | NORMAL, VOTE | Cooldown value. |
| `timeUnit` | NORMAL, VOTE | One of `DAYS`, `HOURS`, `MINUTES`, `SECONDS`. |
| `message` | MESSAGE, VOTE | Multi-line message sent on click. |
| `voteSite` | VOTE | Service name that triggers the vote reward. |
| `fireworkExplode` | no | Spawn an instant firework on claim. Defaults to false. |
| `rewards` | NORMAL, UNIQUE, VOTE | List of console commands to execute. `<player>` is replaced with the player's name. |
| `noPermission.sound` | no | Sound played when the player lacks permission. |
| `noPermission.volume` | no | Volume (0 to 10). Defaults to 1. |
| `noPermission.pitch` | no | Pitch (0 to 10). Defaults to 1. |
| `noPermission.message` | no | Message sent when the player lacks permission. |
| `noClaimed.material` | yes | Material of the icon when not claimed. |
| `noClaimed.data` | yes | Data value of the material. |
| `noClaimed.amount` | yes | Stack size. |
| `noClaimed.name` | yes | Display name. `<status>` is replaced with the status color. |
| `noClaimed.lore` | yes | Lore lines. `<cooldown>` and `<status>` are replaced. |
| `noClaimed.message` | yes | Message sent on claim. `<reward>` is replaced with the reward name. |
| `noClaimed.sound` | no | Sound played on claim. |
| `noClaimed.volume` | no | Volume (0 to 10). Defaults to 1. |
| `noClaimed.pitch` | no | Pitch (0 to 10). Defaults to 1. |
| `claimed.*` | yes | Same structure as `noClaimed.*`, shown while the reward is on cooldown. |

### lang.yml

Contains all translatable strings:
- `messages.joinWithRewards` - lines sent on join when rewards are available. Placeholders: `<player>`, `<rewards>`.
- `messages.joinNoRewards` - lines sent on join when no rewards are available. Placeholders: `<player>`.
- `setup.noPermission` - message sent when a player lacks permission for a command.
- `setup.reload` - message sent after a successful reload.
- `countdown.days`, `countdown.hours`, `countdown.minutes`, `countdown.seconds` - countdown formats. Placeholders: `<days>`, `<hours>`, `<minutes>`, `<seconds>`.
- `menus.rewards.title` - inventory title of the rewards menu.

All strings support the `&` color code prefix.

## Placeholders

| Placeholder | Source | Description |
|-------------|--------|-------------|
| `%udm_rewards%` | PlaceholderAPI | Number of rewards available to the player. |
| `udm_rewards` | MVdWPlaceholderAPI | Number of rewards available to the player. |

## API

The plugin fires a custom event whenever a player's data finishes loading:

```java
@EventHandler
public void onLoad(DeliveryPlayerLoadEvent event) {
    Player player = event.getPlayer();
}
```

This event is fired on the main server thread and is safe to use with Bukkit APIs.

## Building

```bash
git clone https://github.com/Nerotek01/Delivery-Man-Plugin.git
cd Delivery-Man-Plugin
mvn clean package
```

The compiled jar will be at `target/DeliveryMan-2.1.0.jar`.

## Installation

1. Download the latest release jar from the Releases page.
2. Place the jar in your server's `plugins/` directory.
3. Start the server once to generate the default configuration files.
4. Edit `plugins/DeliveryMan/config.yml` to match your database and Redis settings.
5. Restart the server.

## Versioning

This project follows Semantic Versioning. See the Releases page for the changelog of each version.

## Author

Nerotek01

## License

All rights reserved.
