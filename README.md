# Animal Match (Offline Kids Memory Game)

A starter Android app for a child-friendly memory game using Jetpack Compose.

## Included features
- 3 board sizes: 2x2, 3x4, 4x4.
- Match logic with delay-based mismatch hide.
- Sound effect integration point via `SoundPool`.
- Sticker book rewards unlocked by total wins.
- Parent gate with long-press + math question.
- Settings/progression persistence via DataStore.

## Child-safety defaults
- No network layer and no third-party SDKs.
- No ads and no account/chat features.
- Minimal local-only persistence (sound toggle, unlock progression, wins).

## Structure
- `app/src/main/java/com/animalkingdom/ui/screens/*` — Compose screens.
- `app/src/main/java/com/animalkingdom/game/*` — board model + match engine.
- `app/src/main/java/com/animalkingdom/audio/SoundEffects.kt` — SoundPool helper.
- `app/src/main/java/com/animalkingdom/settings/SettingsDataStore.kt` — parent settings.
- `app/src/test/java/com/animalkingdom/game/GameEngineTest.kt` — logic tests.

## Notes
- `SoundEffects` is wired but raw sound assets are intentionally not included yet.
- Add your built-in offline animal images/sounds under `res/` content packs.

## Gradle wrapper note

This repository includes `gradle-wrapper.properties` and wrapper scripts.
In environments where binary artifacts in PRs are disallowed, `gradle-wrapper.jar` may be omitted.
When that happens, `./gradlew` falls back to a system-installed `gradle` executable.