# Delivery

A high-performance Minecraft rewards plugin developed by Nerotek01. Provides a configurable Mystery Dust rewards menu backed by MongoDB with an optional Redis cache layer.

## Requirements

- Java 21 or higher
- Spigot, Paper, or any Bukkit-compatible server (1.8.8 and newer)
- Gradle 8.10+ for building from source (the Gradle Wrapper is bundled, so no local Gradle install is required)
- A MongoDB instance (required)
- A Redis instance (optional, for caching)

## Supported Backends

| Backend | Description | Use Case |
|---------|-------------|----------|
| MongoDB | Document-oriented storage via the official MongoDB Java Driver | Required persistent storage |
| Redis | In-memory cache layer | Optional accelerator for MongoDB |

## Features

- Configurable rewards menu with adjustable row count (5 or 6 rows, default 6). The menu title color is faint gray.
- Seven rank-based Mystery Dust rewards with a 10-day cooldown (daily reward uses a 1-day cooldown):
  - `daily` - 5 Mystery Dust, every 1 day, available to everyone.
  - `default` - 25 Mystery Dust, every 10 days, available to everyone.
  - `vip` - 45 Mystery Dust, every 10 days, requires `delivery.vip`.
  - `vip+` - 60 Mystery Dust, every 10 days, requires `delivery.vip+`.
  - `mvp` - 80 Mystery Dust, every 10 days, requires `delivery.mvp`.
  - `mvp+` - 95 Mystery Dust, every 10 days, requires `delivery.mvp+`.
  - `mvp++` - 105 Mystery Dust, every 10 days, requires `delivery.mvp++`.
- Cooldown system with configurable time units and human-readable countdown formatting in the menu lore (e.g., `10d 12h 30m 5s`).
- Per-reward permission gating with a custom no-permission message, sound, volume, and pitch. When a player lacks permission, the lore's `<click>` placeholder shows red "Locked" text instead of the yellow "Click to loot!".
- Console command execution on claim with `<player>` placeholder substitution. Player names are sanitized to `[A-Za-z0-9_]` before substitution to prevent command injection.
- Menu auto-refresh every second on the main server thread. After a claim, the cooldown placeholder in the lore refreshes every second without reopening the menu.
- The menu does not close after a claim - the claimed icon immediately appears with the new cooldown.
- Menu slot 49 contains a Barrier-block Close button named "Close" in bright red. Clicking it closes the menu.
- Menu slot 53 contains a Written Book named "Menu Guide" with a player-facing description of the menu.
- Item Burst Effect: when a player claims any reward, 16-20 item entities (random mix of emeralds and diamonds) swirl around the player's head in a tornado-like pattern for 5 seconds. Approximately 30% of the items have an enchantment glow. Items cannot be picked up and are removed after 5 seconds. If a player claims a second reward while the first animation is still playing, the second animation is queued and plays after the first one finishes.
- Reward claim plays a configurable sound (default: `ENTITY_PLAYER_LEVELUP`).
- Join messages are single-line strings without decorative bars. The message is hoverable (shows "Click here to open the rewards menu!") and clickable (runs `/rewards`). The `<rewards>` count includes all rewards the player can claim, including those whose cooldown has expired since the last claim (e.g., daily rewards ready to be claimed again):
  - When rewards are available: `&aYou can collect &e<rewards> &arewards right now.`
  - When no rewards are available: `&7There are no rewards to collect right now.`
- Menu title color is faint gray (`&7Rewards Menu`).
- Reward icons use consistent materials: all unclaimed rank rewards use `ENDER_CHEST`, the daily reward uses `CHEST_MINECART`. When claimed, all rewards show as `MINECART` (without chest).
- Chat messages on claim are distinct from the menu lore text and include the reward rank name and Mystery Dust amount (e.g., `&eYou claimed your &aVIP delivery&e! &a+45 &eMystery Dust received.`).
- Chat messages on already-claimed rewards include the reward rank name and the remaining cooldown (e.g., `&cYou already claimed your VIP delivery. Come back later! Next: &7<cooldown>`).
- Menu auto-refresh runs every 1 second (20 ticks) on the main server thread. The cooldown placeholder in the lore updates every second without reopening the menu.
- Click cooldown: 500ms per click. Spam-clicking shows a single red English message ("You are clicking too fast! Please slow down.") throttled to once every 2 seconds.
- Daily reward has unique lore text clearly indicating it is a daily reward claimable every day.
- The menu layout uses 6 rows (54 slots). Reward slots: daily=13, default=20, vip=21, vip+=22, mvp=23, mvp+=24, mvp++=29. Close button at slot 49, Info book at slot 53.
- MongoDB persistence with optional Redis cache layer (write-through):
  - On load: cache GET is performed asynchronously; cache hit returns immediately, cache miss loads from MongoDB and writes back to Redis.
  - On save: MongoDB is updated and the Redis cache is updated asynchronously.
- Graceful degradation: if Redis is unavailable, the plugin continues to work with MongoDB only.
- Live Redis cache reload: changing `redis.*` keys and running `/rewards reload` reconfigures the Jedis pool without restarting the server.
- Atomic configuration reload: `ConfigManager.reload` parses all values into local variables first, then assigns them to fields only if every value parses successfully.
- Menu disable toggle: when `rewardsmenu.enabled` is `false`, opening the menu shows the message `&cThis feature is temporarily disabled until further notice. This may only be on this server!` (red, single line). When the menu is disabled, join messages are also suppressed.
- Custom API event `DeliveryPlayerLoadEvent` fired on the main thread whenever a player's data finishes loading.
- Thread-safe player data storage using `ConcurrentHashMap` for the player cache and the saving-flag set. A `markSaving`/`unmarkSaving` flag prevents the same player from being enqueued for save twice when `PlayerQuitEvent` is processed.
- Atomic shutdown procedure: on disable, all online players are synchronously saved to MongoDB before connections are closed, and the in-memory player cache is cleared.

## Commands

| Command | Sender | Description |
|---------|--------|-------------|
| `/rewards` (alias `/reward`) | Player | Opens the rewards menu. If `rewardsmenu.enabled` is `false`, shows the disabled message instead. |
| `/rewards reload` | Console only | Reloads `config.yml`, `lang.yml`, `rewards.yml`, the Redis pool, and the menu updater. |

### Usage Messages

- A player who types an unknown subcommand (e.g., `/rewards foo`) receives a single red line: `&cUsage: /rewards`.
- A console sender who types `/rewards` (no args) receives: `&cUsage: /rewards reload`.

Tab completion is provided for the `reload` subcommand and is restricted to console senders.

## Permissions

| Permission | Default | Description |
|-----------|---------|-------------|
| `delivery.daily` | true | Access to the daily Mystery Dust reward (5 dust every 1 day). |
| `delivery.default` | true | Access to the default Mystery Dust reward (25 dust every 10 days). |
| `delivery.vip` | op | Access to the VIP Mystery Dust reward (45 dust every 10 days). |
| `delivery.vip+` | op | Access to the VIP+ Mystery Dust reward (60 dust every 10 days). |
| `delivery.mvp` | op | Access to the MVP Mystery Dust reward (80 dust every 10 days). |
| `delivery.mvp+` | op | Access to the MVP+ Mystery Dust reward (95 dust every 10 days). |
| `delivery.mvp++` | op | Access to the MVP++ Mystery Dust reward (105 dust every 10 days). |

## Configuration

### config.yml

```yaml
rewardsmenu:
  enabled: true
  rows: 6

database:
  host: 127.0.0.1
  port: 27017
  database: Delivery
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
  prefix: "delivery:"
  ttl: 3600
```

### rewards.yml

Each reward is defined under the `rewards` key. The following fields are supported:

| Field | Required | Description |
|-------|----------|-------------|
| `id` | yes | Unique identifier for the reward. |
| `slot` | yes | Inventory slot (0 to rows*9 - 1). |
| `permission` | yes | Permission required to claim. |
| `countdown` | yes | Cooldown value. |
| `timeUnit` | yes | One of `DAYS`, `HOURS`, `MINUTES`, `SECONDS`. Invalid values fall back to `DAYS`. |
| `rewards` | yes | List of console commands to execute. `<player>` is replaced with the sanitized player name. |
| `noPermission.sound` | no | Sound played when the player lacks permission. |
| `noPermission.volume` | no | Volume (0 to 10). Defaults to 1. |
| `noPermission.pitch` | no | Pitch (0 to 10). Defaults to 1. |
| `noPermission.message` | no | Message sent when the player lacks permission. |
| `noClaimed.material` | yes | Material of the icon when not claimed. Invalid values fall back to `CHEST`. |
| `noClaimed.data` | yes | Data value of the material. |
| `noClaimed.amount` | yes | Stack size. |
| `noClaimed.name` | yes | Display name. `<status>` is replaced with the status color. |
| `noClaimed.lore` | yes | Lore lines. `<cooldown>` and `<status>` are replaced. |
| `noClaimed.message` | yes | Chat message sent on successful claim. `<reward>` is replaced with the reward name. |
| `noClaimed.sound` | no | Sound played on claim. |
| `noClaimed.volume` | no | Volume (0 to 10). Defaults to 1. |
| `noClaimed.pitch` | no | Pitch (0 to 10). Defaults to 1. |
| `claimed.*` | yes | Same structure as `noClaimed.*`, shown while the reward is on cooldown. The `message` supports the `<cooldown>` placeholder. |

### lang.yml

Contains all translatable strings:
- `messages.joinWithRewards` - single-line message sent on join when rewards are available. Placeholders: `<player>`, `<rewards>`.
- `messages.joinNoRewards` - single-line message sent on join when no rewards are available. Placeholders: `<player>`.
- `setup.disabled` - message sent when the menu is disabled via config.
- `countdown.days`, `countdown.hours`, `countdown.minutes`, `countdown.seconds` - countdown formats. Placeholders: `<days>`, `<hours>`, `<minutes>`, `<seconds>`.
- `menus.rewards.title` - inventory title of the rewards menu (default: `&7Rewards Menu`).

All strings support the `&` color code prefix.

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

The project uses Gradle with the Shadow plugin to produce a self-contained, relocated fat-jar.

```bash
git clone https://github.com/Nerotek01/Delivery.git
cd Delivery
./gradlew clean build
```

On Windows, use `gradlew.bat` instead of `./gradlew`.

The compiled artifact will be at `build/libs/Delivery-<version>.jar` (for example `build/libs/Delivery-3.2.0.jar`). The `-slim` jar in the same directory is the non-shaded intermediate output and is not intended for direct installation on a server.

### Build System Details

- **Build tool:** Gradle 8.10.2 (via the bundled Gradle Wrapper).
- **JDK requirement:** Java 21 (set `JAVA_HOME` to a JDK 21 install before running `./gradlew`).
- **Shadow plugin:** `com.gradleup.shadow` 8.3.0, applied in `build.gradle`.
- **Dependency relocation:** All shaded third-party libraries are relocated under the `com.nerotek01.libs` package to avoid conflicts with other plugins on the same server:
  - `com.mongodb` -> `com.nerotek01.libs.mongodb`
  - `org.bson` -> `com.nerotek01.libs.bson`
  - `redis.clients.jedis` -> `com.nerotek01.libs.jedis`
  - `org.apache.commons.pool2` -> `com.nerotek01.libs.pool2`
  - `com.google.gson` -> `com.nerotek01.libs.gson`
  - `org.slf4j` -> `com.nerotek01.libs.slf4j`
- **Metadata filtering:** Signature files (`META-INF/*.SF`, `*.DSA`, `*.RSA`), Maven metadata (`META-INF/maven/**`), and `module-info.class` are stripped from the final jar.
- **Resource filtering:** The `plugin.yml` resource is processed by Gradle's `processResources` task so the `version` field is substituted from the project version defined in `gradle.properties`.

## Installation

1. Download the latest release jar from the Releases page.
2. Place the jar in your server's `plugins/` directory.
3. Start the server once to generate the default configuration files.
4. Edit `plugins/Delivery/config.yml` to match your MongoDB and Redis settings.
5. Restart the server.

## Versioning

This project follows Semantic Versioning. See the Releases page for the changelog of each version.

## Author

Nerotek01

## License

All rights reserved.
